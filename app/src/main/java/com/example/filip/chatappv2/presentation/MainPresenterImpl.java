package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.api.RequestListener;
import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.utils.StringUtils;
import com.example.filip.chatappv2.view.MainView;

/**
 * Created by Filip on 08/06/2016.
 */
public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    private final AuthenticationHelper authenticationHelper;

    public MainPresenterImpl(AuthenticationHelper authenticationHelper) {
        this.authenticationHelper = authenticationHelper;
    }

    @Override
    public void handleOnUserClickedLoginButton(String userEmail, String userPassword) {
        mainView.hideEditTextKeyboard();
        mainView.showProgressBar();
        if (StringUtils.stringsAreNullOrEmpty(userEmail, userPassword)) {
            mainView.showOnEmptyEmailOrPasswordError();
            mainView.hideProgressBar();
        } else {
            authenticationHelper.logTheUserOut();
            authenticationHelper.logTheUserIn(userEmail, userPassword, provideLoginCallback());
        }
    }

    @Override
    public void handleOnUserClickedRegisterTextView() {
        mainView.startRegistrationActivity();
    }

    protected RequestListener provideLoginCallback() {
        return new RequestListener() {
            @Override
            public void onSuccessfulRequest() {
                mainView.hideProgressBar();
                mainView.showOnSuccessfulLoginMessage();
                mainView.startChatActivity();
            }

            @Override
            public void onFailedRequest() {
                mainView.hideProgressBar();
                mainView.showOnFailedToLoginErrorMessage();
            }
        };
    }

    @Override
    public void setView(MainView view) {
        this.mainView = view;
    }
}