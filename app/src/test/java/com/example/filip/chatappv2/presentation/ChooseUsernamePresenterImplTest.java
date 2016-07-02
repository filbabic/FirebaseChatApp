package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.view.ChooseUsernameView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by flip6 on 25.6.2016..
 */
@RunWith(MockitoJUnitRunner.class)
public class ChooseUsernamePresenterImplTest {
    private ChooseUsernamePresenterImpl presenter;

    @Mock
    public ChooseUsernameView chooseUsernameView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ChooseUsernamePresenterImpl();
        presenter.setView(chooseUsernameView);
    }

    @Test
    public void testOnUserClickedSaveUsernameChoiceUsernameIsNull() throws Exception {
        presenter.onUserClickedSaveUsernameChoice(null);
        verify(chooseUsernameView).hideKeyboardOnButtonPress();
        verify(chooseUsernameView).showUsernameCannotBeEmpty();
    }

    @Test
    public void testOnUserClickedSaveUsernameChoiceUsernameIsEmpty() throws Exception {
        presenter.onUserClickedSaveUsernameChoice("");
        verify(chooseUsernameView).hideKeyboardOnButtonPress();
        verify(chooseUsernameView).showUsernameCannotBeEmpty();
    }

    @Test
    public void testOnUserClickedSaveUsernameChoiceUsernameIsValid() throws Exception {
        presenter.onUserClickedSaveUsernameChoice("test");
        verify(chooseUsernameView).saveUsernameChoice(anyString());
    }

    @Test
    public void testOnUserClickedSkipUsernameChoice() throws Exception {
        presenter.onUserClickedSkipUsernameChoice();
        verify(chooseUsernameView).skipUsernameChoice();
    }
}