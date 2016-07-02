package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.utils.StringUtils;
import com.example.filip.chatappv2.view.ChooseUsernameView;

/**
 * Created by flip6 on 25.6.2016..
 */

public class ChooseUsernamePresenterImpl implements ChooseUsernamePresenter {
    private ChooseUsernameView chooseUsernameView;

    public ChooseUsernamePresenterImpl() {
    }

    @Override
    public void onUserClickedSaveUsernameChoice(String chosenUsername) {
        chooseUsernameView.hideKeyboardOnButtonPress();
        if (!StringUtils.stringsAreNullOrEmpty(chosenUsername)) {
            chooseUsernameView.saveUsernameChoice(chosenUsername);
        } else {
            chooseUsernameView.showUsernameCannotBeEmpty();
        }
    }

    @Override
    public void onUserClickedSkipUsernameChoice() {
        chooseUsernameView.skipUsernameChoice();
    }

    @Override
    public void setView(ChooseUsernameView view) {
        this.chooseUsernameView = view;
    }
}