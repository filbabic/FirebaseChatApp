package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.view.ChooseUsernameView;

/**
 * Created by flip6 on 25.6.2016..
 */

public interface ChooseUsernamePresenter extends Presenter<ChooseUsernameView> {
    void onUserClickedSaveUsernameChoice(String chosenUsername);

    void onUserClickedSkipUsernameChoice();
}