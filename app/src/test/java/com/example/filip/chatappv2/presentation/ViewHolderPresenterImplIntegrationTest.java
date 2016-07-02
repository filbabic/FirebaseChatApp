package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.view.ViewHolderView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

/**
 * Created by flip6 on 24.6.2016..
 */
@RunWith(MockitoJUnitRunner.class)
public class ViewHolderPresenterImplIntegrationTest {
    private ViewHolderPresenterImpl presenter;
    @Mock
    public ViewHolderView viewHolderView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ViewHolderPresenterImpl();
        presenter.setView(viewHolderView);
    }

    @Test
    public void testHandleOnReceivedMessageWhenNull() throws Exception {
        presenter.handleOnReceivedChatMessage(null);
        verifyZeroInteractions(viewHolderView);
    }

    @Test
    public void testHandleOnReceivedMessageWhenMessageIsEmpty() throws Exception {
        presenter.handleOnReceivedChatMessage(new ChatMessage());
        verifyZeroInteractions(viewHolderView);
    }

    @Test
    public void testHandleOnReceivedMessageWhenAuthorsAreDifferent() throws Exception {
        ChatMessage message = new ChatMessage("text", "user1", "user2");
        presenter.handleOnReceivedChatMessage(message);
        verify(viewHolderView).setMessageText(anyString());
        verify(viewHolderView).loadUserImage(anyString());
        verify(viewHolderView).setUserDisplayName(anyString());
    }

    @Test
    public void testHandleOnReceivedMessageWhenAuthorsAreTheSame() throws Exception {
        ChatMessage message = new ChatMessage("text", "user", "user");
        presenter.handleOnReceivedChatMessage(message);
        verify(viewHolderView).setMessageText(anyString());
        verify(viewHolderView).hideUserDisplayName();
        verify(viewHolderView).hideUserImage();
    }
}