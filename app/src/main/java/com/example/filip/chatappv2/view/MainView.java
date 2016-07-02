package com.example.filip.chatappv2.view;

/**
 * Created by Filip on 08/06/2016.
 */
public interface MainView {
    void showOnFailedToLoginErrorMessage();

    void showOnSuccessfulLoginMessage();

    void startChatActivity();

    void showOnEmptyEmailOrPasswordError();

    void startRegistrationActivity();

    void showProgressBar();

    void hideProgressBar();

    void hideEditTextKeyboard();
}