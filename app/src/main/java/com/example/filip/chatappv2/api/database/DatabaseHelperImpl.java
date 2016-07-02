package com.example.filip.chatappv2.api.database;

import com.example.filip.chatappv2.constants.Constants;
import com.example.filip.chatappv2.constants.FirebaseConstants;
import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.pojo.UserModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by Filip on 08/06/2016.
 */
public class DatabaseHelperImpl implements DatabaseHelper {
    private final FirebaseDatabase firebaseDatabase;
    private Query mMessageQuery;

    public DatabaseHelperImpl(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    @Override
    public void receiveMessageFromFirebase(ChildEventListener listener) {
        firebaseDatabase.getReference(FirebaseConstants.MESSAGE_REFERENCE).limitToLast(Constants.MESSAGE_LIMIT).addChildEventListener(listener);
    }

    @Override
    public void sendMessageToFirebase(String username, String messageToSend, String lastMessageAuthor) {
        ChatMessage message = new ChatMessage(messageToSend, username, lastMessageAuthor);
        firebaseDatabase.getReference(FirebaseConstants.MESSAGE_REFERENCE).push().setValue(message);
    }

    @Override
    public void removeChatCallbacks(ChildEventListener listener) {
        firebaseDatabase.getReference(FirebaseConstants.MESSAGE_REFERENCE).removeEventListener(listener);
    }

    @Override
    public void setUserIsOnline(boolean isOnline, String username) {
        UserModel user = new UserModel(username, isOnline);
        firebaseDatabase.getReference(FirebaseConstants.USERS_REFERENCE).child(username).setValue(user);
    }

    @Override
    public void addNotificationMessageCallback(ChildEventListener listener) {
        mMessageQuery = firebaseDatabase.getReference(FirebaseConstants.MESSAGE_REFERENCE).limitToLast(Constants.ONLY_LAST_MESSAGE_LIMIT);
        mMessageQuery.addChildEventListener(listener);
    }

    @Override
    public void removeNotificationMessageCallback(ChildEventListener listener) {
        mMessageQuery.removeEventListener(listener);
    }
}