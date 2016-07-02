package com.example.filip.chatappv2.api.authentication;

import com.example.filip.chatappv2.api.RequestListener;

/**
 * Created by flip6 on 25.6.2016..
 */
public interface AuthenticationHelper {
    void logTheUserIn(String email, String password, RequestListener listener);

    void attemptToRegisterTheUser(String email, String password, RequestListener listener);

    void setUserDisplayName(String username);

    void logTheUserOut();

    boolean checkIfUserIsLoggedIn();

    String getCurrentUserDisplayName();
}