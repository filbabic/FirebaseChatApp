package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.view.RegisterView;

/**
 * Created by flip6 on 25.6.2016..
 */

public interface RegistrationPresenter extends Presenter<RegisterView> {
    void attemptToRegisterTheUser(String email, String password);

    void setNewUserDisplayName();

    void checkIfPasswordsMatch(String password, String reenteredPassword);

    void setUsername(String username);
}