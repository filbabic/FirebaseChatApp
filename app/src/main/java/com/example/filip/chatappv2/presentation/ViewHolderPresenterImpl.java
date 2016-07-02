package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.view.ViewHolderView;
import com.example.filip.chatappv2.utils.StringUtils;

/**
 * Created by flip6 on 21.6.2016..
 */

public class ViewHolderPresenterImpl implements ViewHolderPresenter {
    private ViewHolderView viewHolderView;

    public ViewHolderPresenterImpl() {
    }

    @Override
    public void handleOnReceivedChatMessage(ChatMessage message) {
        if (message != null && !StringUtils.stringsAreNullOrEmpty(message.getMessageText(), message.getAuthorDisplayName(), message.getLastMessageAuthor())) {
            String lastAuthor = message.getLastMessageAuthor();
            String currentAuthor = message.getAuthorDisplayName();
            if (lastAuthor.equals(currentAuthor)) {
                viewHolderView.hideUserDisplayName();
                viewHolderView.hideUserImage();
            } else {
                viewHolderView.setUserDisplayName(currentAuthor);
                viewHolderView.loadUserImage(message.getAuthorImageURL());
            }
            viewHolderView.setMessageText(message.getMessageText());
        }
    }

    @Override
    public void setView(ViewHolderView view) {
        this.viewHolderView = view;
    }
}