package com.example.filip.chatappv2.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.filip.chatappv2.App;
import com.example.filip.chatappv2.R;
import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.presentation.ChatPresenter;
import com.example.filip.chatappv2.presentation.ChatPresenterImpl;
import com.example.filip.chatappv2.ui.chat.adapter.ChatRecyclerAdapter;
import com.example.filip.chatappv2.ui.notification.NotificationService;
import com.example.filip.chatappv2.view.ChatView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Filip on 08/06/2016.
 */
public class ChatActivity extends AppCompatActivity implements ChatView, View.OnClickListener {
    private ChatPresenter presenter;
    private ChatRecyclerAdapter adapter;

    @BindView(R.id.chat_activity_chat_recycler_view)
    RecyclerView mChatRecyclerView;

    @BindView(R.id.chat_activity_chat_message_input_edit_text)
    EditText mMessageEditText;

    @BindView(R.id.chat_activity_chat_send_message_button)
    ImageButton mSendMessageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initPresenter();
        initAdapter();
        initUI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.logTheUserIn();
        adapter.clearChatMessages();
        presenter.requestChatMessagesFromNetwork();
    }

    @Override
    public void onBackPressed() {
        presenter.logTheUserOut();
        adapter.clearChatMessages();
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void initPresenter() {
        presenter = new ChatPresenterImpl(App.get().getDatabaseHelper(), App.get().getAuthenticationHelper());
        presenter.setView(this);
    }

    @Override
    protected void onPause() {
        if (presenter != null) {
            presenter.checkIfUserIsLoggedIn();
        }
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (presenter != null) {
            presenter.stopChatNotificationService();
            presenter.requestChatMessagesFromNetwork();
        }
    }

    @Override
    public void stopChatNotificationService() {
        stopService(new Intent(this, NotificationService.class));
    }

    @Override
    public void startChatNotificationService() {
        startService(new Intent(this, NotificationService.class));
    }

    @Override
    public void sendNewMessageToAdapter(ChatMessage message) {
        adapter.addNewMessage(message);
        mChatRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void clearMessageBox() {
        mMessageEditText.setText("");
    }

    @Override
    public void removeCallbacksFromFirebase() {
        presenter.removeChatCallbacks();
        presenter.startChatNotificationService();
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(App.get(), R.string.empty_message_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == mSendMessageButton) {
            presenter.sendMessageToNetwork(mMessageEditText.getText().toString());
        }
    }

    private void initAdapter() {
        adapter = new ChatRecyclerAdapter();
    }

    private void initUI() {
        mSendMessageButton.setOnClickListener(this);
        mChatRecyclerView.setHasFixedSize(true);
        mChatRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(App.get()));
        mChatRecyclerView.setAdapter(adapter);
    }
}