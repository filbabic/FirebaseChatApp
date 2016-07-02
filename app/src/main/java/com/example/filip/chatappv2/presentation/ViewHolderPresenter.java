package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.view.ViewHolderView;

/**
 * Created by flip6 on 21.6.2016..
 */
public interface ViewHolderPresenter extends Presenter<ViewHolderView> {
    void handleOnReceivedChatMessage(ChatMessage message);
}
