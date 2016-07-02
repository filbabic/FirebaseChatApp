package com.example.filip.chatappv2.view;

import com.example.filip.chatappv2.pojo.ChatMessage;

/**
 * Created by flip6 on 21.6.2016..
 */
public interface ViewHolderView {
    void loadUserImage(String imageURL);

    void setUserDisplayName(String userDisplayName);

    void setMessageText(String messageText);

    void receiveChatMessage(ChatMessage message);

    void hideUserDisplayName();

    void hideUserImage();
}