package com.example.filip.chatappv2.view;

/**
 * Created by flip6 on 14.6.2016..
 */
public interface ChooseUsernameView {
    void saveUsernameChoice(String username);

    void skipUsernameChoice();

    void hideKeyboardOnButtonPress();

    void showUsernameCannotBeEmpty();
}