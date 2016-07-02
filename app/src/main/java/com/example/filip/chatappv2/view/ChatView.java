package com.example.filip.chatappv2.view;

import com.example.filip.chatappv2.pojo.ChatMessage;

/**
 * Created by flip6 on 10.6.2016..
 */
public interface ChatView {
    void stopChatNotificationService();

    void startChatNotificationService();

    void sendNewMessageToAdapter(ChatMessage message);

    void clearMessageBox();

    void removeCallbacksFromFirebase();

    void showErrorMessage();
}