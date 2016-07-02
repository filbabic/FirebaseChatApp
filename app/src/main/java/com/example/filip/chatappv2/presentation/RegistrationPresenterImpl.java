package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.api.RequestListener;
import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.utils.StringUtils;
import com.example.filip.chatappv2.view.RegisterView;

/**
 * Created by flip6 on 25.6.2016..
 */

public class RegistrationPresenterImpl implements RegistrationPresenter {
    private final AuthenticationHelper authenticationHelper;

    private RegisterView registerView;

    private String username;

    public RegistrationPresenterImpl(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void attemptToRegisterTheUser(String email, String password) {
        if (StringUtils.stringsAreNullOrEmpty(email)) {
            registerView.showOnFailedRequestErrorMessage();
            registerView.hideProgressBar();
        } else {
            authenticationHelper.logTheUserOut();
            authenticationHelper.attemptToRegisterTheUser(email, password, provideRegisterCallback());
        }
    }

    @Override
    public void setNewUserDisplayName() {
        authenticationHelper.setUserDisplayName(username);
    }
    @Override
    public void checkIfPasswordsMatch(String password, String reenteredPassword) {
        registerView.showProgressBar();
        if (!StringUtils.stringsAreNullOrEmpty(password, reenteredPassword) && password.equals(reenteredPassword)) {
            registerView.attemptToCreateUserOnPasswordsMatch();
        } else {
            registerView.hideProgressBar();
            registerView.showPasswordsDoNotMatchOrAreEmptyErrorMessage();
        }
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    protected RequestListener provideRegisterCallback() {
        return new RequestListener() {
            @Override
            public void onSuccessfulRequest() {
                registerView.hideProgressBar();
                registerView.setNewUserDisplayName();
                registerView.sendTheUserToChatAfterRegistration();
            }

            @Override
            public void onFailedRequest() {
                registerView.hideProgressBar();
                registerView.showOnFailedRequestErrorMessage();
            }
        };
    }

    @Override
    public void setView(RegisterView view) {
        this.registerView = view;
    }
}