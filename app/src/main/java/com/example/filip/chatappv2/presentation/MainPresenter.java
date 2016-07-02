package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.view.MainView;

/**
 * Created by Filip on 08/06/2016.
 */
public interface MainPresenter extends Presenter<MainView> {
    void handleOnUserClickedLoginButton(String userEmail, String userPassword);

    void handleOnUserClickedRegisterTextView();
}