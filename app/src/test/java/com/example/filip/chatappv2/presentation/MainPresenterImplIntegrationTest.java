package com.example.filip.chatappv2.presentation;


import com.example.filip.chatappv2.api.RequestListener;
import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.api.database.DatabaseHelper;
import com.example.filip.chatappv2.view.MainView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Created by flip6 on 24.6.2016..
 */
@RunWith(MockitoJUnitRunner.class)
public class MainPresenterImplIntegrationTest {
    private MainPresenterImpl presenter;
    @Mock
    public MainView mainView;

    @Mock
    public AuthenticationHelper authenticationHelper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenterImpl(authenticationHelper);
        presenter.setView(mainView);
    }

    @Test
    public void testHandleOnUserClickedLoginButtonDataIsNull() throws Exception {
        presenter.handleOnUserClickedLoginButton(null, null);
        verify(mainView).hideEditTextKeyboard();
        verify(mainView).showProgressBar();
        verify(mainView).showOnEmptyEmailOrPasswordError();
        verify(mainView).hideProgressBar();
        verifyNoMoreInteractions(mainView, authenticationHelper);
    }

    @Test
    public void testHandleOnUserClickedLoginButtonDataIsEmpty() throws Exception {
        presenter.handleOnUserClickedLoginButton("  ", "   ");
        verify(mainView).hideEditTextKeyboard();
        verify(mainView).showProgressBar();
        verify(mainView).showOnEmptyEmailOrPasswordError();
        verify(mainView).hideProgressBar();
        verifyNoMoreInteractions(mainView, authenticationHelper);
    }

    @Test
    public void testHandleOnUserClickedLoginButtonDataIsValid() throws Exception {
        presenter.handleOnUserClickedLoginButton("email", "pass");
        verify(mainView).hideEditTextKeyboard();
        verify(mainView).showProgressBar();
        verify(authenticationHelper).logTheUserOut();
        verify(authenticationHelper).logTheUserIn(anyString(), anyString(), any(RequestListener.class));
        verifyNoMoreInteractions(mainView, authenticationHelper);
    }

    @Test
    public void testHandleOnUserClickedRegisterTextView() throws Exception {
        presenter.handleOnUserClickedRegisterTextView();
        verify(mainView).startRegistrationActivity();
        verifyNoMoreInteractions(authenticationHelper, mainView);
    }

    @Test
    public void testProvideRequestCallbackOnSuccessfulRequest() throws Exception {
        presenter.provideLoginCallback().onSuccessfulRequest();
        verify(mainView).hideProgressBar();
        verify(mainView).showOnSuccessfulLoginMessage();
        verify(mainView).startChatActivity();
    }

    @Test
    public void testProvideRequestCallbackOnFailedRequest() throws Exception {
        presenter.provideLoginCallback().onFailedRequest();
        verify(mainView).hideProgressBar();
        verify(mainView).showOnFailedToLoginErrorMessage();
    }
}