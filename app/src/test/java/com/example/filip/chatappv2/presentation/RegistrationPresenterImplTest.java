package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.api.RequestListener;
import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.view.RegisterView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by flip6 on 25.6.2016..
 */
@RunWith(MockitoJUnitRunner.class)
public class RegistrationPresenterImplTest {
    private RegistrationPresenterImpl presenter;
    @Mock
    public AuthenticationHelper authenticationHelper;

    @Mock
    public RegisterView registerView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new RegistrationPresenterImpl(authenticationHelper);
        presenter.setView(registerView);
    }

    @Test
    public void testAttemptToRegisterTheUserEmailIsEmpty() throws Exception {
        presenter.attemptToRegisterTheUser("  ", "test");
        verify(registerView).hideProgressBar();
        verify(registerView).showOnFailedRequestErrorMessage();
    }

    @Test
    public void testAttemptToRegisterTheUserEmailIsNull() throws Exception {
        presenter.attemptToRegisterTheUser(null, "test");
        verify(registerView).hideProgressBar();
        verify(registerView).showOnFailedRequestErrorMessage();
    }

    @Test
    public void testAttemptToRegisterTheUserDataIsValid() throws Exception {
        presenter.attemptToRegisterTheUser("email", "test");
        verify(authenticationHelper).logTheUserOut();
        verify(authenticationHelper).attemptToRegisterTheUser(anyString(), anyString(), any(RequestListener.class));
    }

    @Test
    public void testSetNewUserDisplayNameSendsNull() throws Exception {
        presenter.setNewUserDisplayName();
        verify(authenticationHelper).setUserDisplayName(null);
    }

    @Test
    public void testSetNewUserDisplayNameSendsValidUsername() throws Exception {
        presenter.setUsername("test");
        presenter.setNewUserDisplayName();
        verify(authenticationHelper).setUserDisplayName(anyString());
    }

    @Test
    public void testCheckIfPasswordsMatchDataIsNull() throws Exception {
        presenter.checkIfPasswordsMatch(null, null);
        verify(registerView).showProgressBar();
        verify(registerView).hideProgressBar();
        verify(registerView).showPasswordsDoNotMatchOrAreEmptyErrorMessage();
    }

    @Test
    public void testCheckIfPasswordsMatchDataIsEmpty() throws Exception {
        presenter.checkIfPasswordsMatch(" ", "      ");
        verify(registerView).showProgressBar();
        verify(registerView).hideProgressBar();
        verify(registerView).showPasswordsDoNotMatchOrAreEmptyErrorMessage();
    }

    @Test
    public void testCheckIfPasswordsMatchPasswordsAreDifferent() throws Exception {
        presenter.checkIfPasswordsMatch("password1", "password2");
        verify(registerView).showProgressBar();
        verify(registerView).showPasswordsDoNotMatchOrAreEmptyErrorMessage();
        verify(registerView).hideProgressBar();
    }

    @Test
    public void testCheckIfPasswordsMatchPasswordsAreSame() throws Exception {
        presenter.checkIfPasswordsMatch("password", "password");
        verify(registerView).showProgressBar();
        verify(registerView).attemptToCreateUserOnPasswordsMatch();
    }

    @Test
    public void testProvideRegisterCallbackOnSuccessfulRequest() throws Exception {
        presenter.provideRegisterCallback().onSuccessfulRequest();
        verify(registerView).hideProgressBar();
        verify(registerView).setNewUserDisplayName();
        verify(registerView).sendTheUserToChatAfterRegistration();
    }

    @Test
    public void testProvideRegisterCallbackOnFailedRequest() throws Exception {
        presenter.provideRegisterCallback().onFailedRequest();
        verify(registerView).hideProgressBar();
        verify(registerView).showOnFailedRequestErrorMessage();
    }
}