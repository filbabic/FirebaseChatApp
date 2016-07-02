package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.api.database.DatabaseHelper;
import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.utils.StringUtils;
import com.example.filip.chatappv2.view.NotificationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/**
 * Created by flip6 on 25.6.2016..
 */

public class NotificationPresenterImpl implements NotificationPresenter {
    private NotificationView notificationView;
    private final DatabaseHelper databaseHelper;
    private final AuthenticationHelper authenticationHelper;

    public NotificationPresenterImpl(DatabaseHelper databaseHelper, AuthenticationHelper authenticationHelper) {
        this.databaseHelper = databaseHelper;
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void removeCallbacksFromFirebase() {
        databaseHelper.removeNotificationMessageCallback(provideNotificationCallback());
    }

    @Override
    public void addCallbacksToFirebase() {
        databaseHelper.addNotificationMessageCallback(provideNotificationCallback());
    }

    protected ChildEventListener provideNotificationCallback() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                    if (message != null && !message.getAuthorDisplayName().equals(authenticationHelper.getCurrentUserDisplayName())) {
                    notificationView.sendMessageNotification(message.getAuthorDisplayName());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(databaseError!=null){
                    StringUtils.logError(databaseError.toException());
                }
            }
        };
    }

    @Override
    public void setView(NotificationView view) {
        this.notificationView = view;
    }
}