package com.example.filip.chatappv2.presentation;


import com.example.filip.chatappv2.R;
import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.api.database.DatabaseHelper;
import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.view.ChatView;
import com.example.filip.chatappv2.utils.StringUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;


/**
 * Created by flip6 on 10.6.2016..
 */
public class ChatPresenterImpl implements ChatPresenter {
    private ChatView chatView;

    private final DatabaseHelper databaseHelper;
    private final AuthenticationHelper authenticationHelper;

    private String lastMessageAuthor;

    public ChatPresenterImpl(DatabaseHelper databaseHelper, AuthenticationHelper authenticationHelper) {
        this.databaseHelper = databaseHelper;
        this.authenticationHelper = authenticationHelper;
        this.lastMessageAuthor = "";
    }

    @Override
    public void startChatNotificationService() {
        chatView.startChatNotificationService();
    }

    @Override
    public void stopChatNotificationService() {
        chatView.stopChatNotificationService();
    }

    @Override
    public void removeChatCallbacks() {
        databaseHelper.removeChatCallbacks(provideMessageCallback());
    }

    @Override
    public void requestChatMessagesFromNetwork() {
        databaseHelper.receiveMessageFromFirebase(provideMessageCallback());
    }

    @Override
    public void sendMessageToNetwork(String messageToSend) {
        if (!StringUtils.stringsAreNullOrEmpty(messageToSend)) {
            chatView.clearMessageBox();
            databaseHelper.sendMessageToFirebase(authenticationHelper.getCurrentUserDisplayName(), messageToSend, lastMessageAuthor);
        } else {
            chatView.showErrorMessage();
        }
    }

    @Override
    public void logTheUserOut() {
        String username = authenticationHelper.getCurrentUserDisplayName();
        if (!StringUtils.stringsAreNullOrEmpty(username)) {
            databaseHelper.setUserIsOnline(false, username);
        }
        authenticationHelper.logTheUserOut();
    }

    @Override
    public void logTheUserIn() {
        String username = authenticationHelper.getCurrentUserDisplayName();
        if (!StringUtils.stringsAreNullOrEmpty(username)) {
            databaseHelper.setUserIsOnline(true, username);
        }
    }

    @Override
    public void checkIfUserIsLoggedIn() {
        if (authenticationHelper.checkIfUserIsLoggedIn()) {
            chatView.removeCallbacksFromFirebase();
        }
    }

    protected ChildEventListener provideMessageCallback() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatMessage message = dataSnapshot.getValue(ChatMessage.class);
                if (message != null) {
                    lastMessageAuthor = message.getAuthorDisplayName();
                    chatView.sendNewMessageToAdapter(message);
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
                if (databaseError != null) {
                    StringUtils.logError(databaseError.toException());
                }
            }
        };
    }

    @Override
    public void setView(ChatView view) {
        this.chatView = view;
    }
}