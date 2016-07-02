package com.example.filip.chatappv2.ui.notification;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.filip.chatappv2.App;
import com.example.filip.chatappv2.R;
import com.example.filip.chatappv2.presentation.NotificationPresenter;
import com.example.filip.chatappv2.presentation.NotificationPresenterImpl;
import com.example.filip.chatappv2.ui.chat.ChatActivity;
import com.example.filip.chatappv2.view.NotificationView;

import java.util.Locale;

/**
 * Created by Filip on 08/06/2016.
 */
public class NotificationService extends Service implements NotificationView {
    private NotificationPresenter notificationPresenter;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationPresenter = new NotificationPresenterImpl(App.get().getDatabaseHelper(), App.get().getAuthenticationHelper());
        notificationPresenter.setView(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (notificationPresenter != null) {
            notificationPresenter.addCallbacksToFirebase();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        notificationPresenter.removeCallbacksFromFirebase();
        super.onDestroy();
    }

    @Override
    public void sendMessageNotification(String author) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(App.get())
                .setContentTitle(String.format(Locale.getDefault(), getString(R.string.notification_message), author))
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setSmallIcon(R.drawable.ic_chat_white_24dp);

        Intent resultIntent = new Intent(App.get(), ChatActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(App.get(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(App.get());
        notificationManagerCompat.notify(0, builder.build());
    }
}