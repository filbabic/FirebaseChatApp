package com.example.filip.chatappv2.api.database;

import com.google.firebase.database.ChildEventListener;

/**
 * Created by Filip on 08/06/2016.
 */
public interface DatabaseHelper {
    void receiveMessageFromFirebase(ChildEventListener listener);

    void sendMessageToFirebase(String username, String messageToSend, String lastMessageAuthor);

    void removeChatCallbacks(ChildEventListener listener);

    void setUserIsOnline(boolean isOnline, String username);

    void addNotificationMessageCallback(ChildEventListener listener);

    void removeNotificationMessageCallback(ChildEventListener listener);
}