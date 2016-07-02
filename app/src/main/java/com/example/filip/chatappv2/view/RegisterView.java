package com.example.filip.chatappv2.view;

/**
 * Created by flip6 on 14.6.2016..
 */
public interface RegisterView {
    void attemptToCreateUserOnPasswordsMatch();

    void showPasswordsDoNotMatchOrAreEmptyErrorMessage();

    void showOnFailedRequestErrorMessage();

    void showProgressBar();

    void hideProgressBar();

    void sendTheUserToChatAfterRegistration();

    void setNewUserDisplayName();
}