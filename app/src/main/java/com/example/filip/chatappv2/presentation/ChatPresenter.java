package com.example.filip.chatappv2.presentation;


import com.example.filip.chatappv2.view.ChatView;

/**
 * Created by flip6 on 10.6.2016..
 */
public interface ChatPresenter extends Presenter<ChatView> {
    void startChatNotificationService();

    void stopChatNotificationService();

    void removeChatCallbacks();

    void requestChatMessagesFromNetwork();

    void sendMessageToNetwork(String messageToSend);

    void logTheUserOut();

    void logTheUserIn();

    void checkIfUserIsLoggedIn();
}