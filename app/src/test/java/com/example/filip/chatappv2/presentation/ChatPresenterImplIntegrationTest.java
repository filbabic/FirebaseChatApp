package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.api.database.DatabaseHelper;
import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.view.ChatView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by flip6 on 24.6.2016..
 */
public class ChatPresenterImplIntegrationTest {
    private ChatPresenterImpl presenter;
    @Mock
    public DatabaseHelper databaseHelper;

    @Mock
    public AuthenticationHelper authenticationHelper;

    @Mock
    public ChatView chatView;

    @Mock
    public DataSnapshot dataSnapshot;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new ChatPresenterImpl(databaseHelper, authenticationHelper);
        presenter.setView(chatView);
    }

    @Test
    public void testStartChatNotificationService() throws Exception {
        presenter.startChatNotificationService();
        verify(chatView).startChatNotificationService();
    }

    @Test
    public void testStopChatNotificationService() throws Exception {
        presenter.stopChatNotificationService();
        verify(chatView).stopChatNotificationService();
    }

    @Test
    public void testRemoveChatCallbacks() throws Exception {
        presenter.removeChatCallbacks();
        verify(databaseHelper).removeChatCallbacks(any(ChildEventListener.class));
    }

    @Test
    public void testRequestChatMessages() throws Exception {
        presenter.requestChatMessagesFromNetwork();
        verify(databaseHelper).receiveMessageFromFirebase(any(ChildEventListener.class));
    }

    @Test
    public void testSendMessageShouldShowErrorMessageIsEmpty() throws Exception {
        presenter.sendMessageToNetwork("  ");
        verify(chatView).showErrorMessage();
    }

    @Test
    public void testSendMessageShouldShowErrorMessageIsNull() throws Exception {
        presenter.sendMessageToNetwork(null);
        verify(chatView).showErrorMessage();
    }

    @Test
    public void testSendMessageShouldSendToNetwork() throws Exception {
        presenter.sendMessageToNetwork("message");
        verify(chatView).clearMessageBox();
        verify(authenticationHelper).getCurrentUserDisplayName();
        verify(databaseHelper).sendMessageToFirebase(anyString(), anyString(), anyString());
    }

    @Test
    public void testLogTheUserOutUsernameExists() throws Exception {
        when(authenticationHelper.getCurrentUserDisplayName()).thenReturn("username");
        presenter.logTheUserOut();
        verify(authenticationHelper).getCurrentUserDisplayName();
        verify(databaseHelper).setUserIsOnline(anyBoolean(), anyString());
        verify(authenticationHelper).logTheUserOut();
    }

    @Test
    public void testLogTheUserOutUsernameIsEmpty() throws Exception {
        when(authenticationHelper.getCurrentUserDisplayName()).thenReturn("  ");
        presenter.logTheUserOut();
        verify(authenticationHelper).getCurrentUserDisplayName();
        verify(authenticationHelper).logTheUserOut();
        verifyNoMoreInteractions(authenticationHelper, chatView, databaseHelper);
    }

    @Test
    public void testLogTheUserOutUsernameIsNull() throws Exception {
        presenter.logTheUserOut();
        verify(authenticationHelper).getCurrentUserDisplayName();
        verify(authenticationHelper).logTheUserOut();
        verifyNoMoreInteractions(authenticationHelper, databaseHelper, chatView);
    }

    @Test
    public void testLogTheUserInDataIsValid() throws Exception {
        when(authenticationHelper.getCurrentUserDisplayName()).thenReturn("username");
        presenter.logTheUserIn();
        verify(authenticationHelper).getCurrentUserDisplayName();
        verify(databaseHelper).setUserIsOnline(anyBoolean(), anyString());
    }

    @Test
    public void testLogTheUserInUsernameIsEmpty() throws Exception {
        when(authenticationHelper.getCurrentUserDisplayName()).thenReturn("  ");
        presenter.logTheUserIn();
        verify(authenticationHelper).getCurrentUserDisplayName();
        verifyNoMoreInteractions(databaseHelper, authenticationHelper, chatView);
    }

    @Test
    public void testLogTheUserInUsernameIsNull() throws Exception {
        presenter.logTheUserIn();
        verify(authenticationHelper).getCurrentUserDisplayName();
        verifyNoMoreInteractions(databaseHelper, authenticationHelper, chatView);
    }

    @Test
    public void testCheckIfUserIsLoggedInReturnsFalse() throws Exception {
        when(authenticationHelper.checkIfUserIsLoggedIn()).thenReturn(false);
        presenter.checkIfUserIsLoggedIn();
        verify(authenticationHelper).checkIfUserIsLoggedIn();
        verifyNoMoreInteractions(authenticationHelper, databaseHelper, chatView);
    }

    @Test
    public void testCheckIfTheUserIsLoggedInReturnsTrue() throws Exception {
        when(authenticationHelper.checkIfUserIsLoggedIn()).thenReturn(true);
        presenter.checkIfUserIsLoggedIn();
        verify(authenticationHelper).checkIfUserIsLoggedIn();
        verify(chatView).removeCallbacksFromFirebase();
        verifyNoMoreInteractions(chatView, authenticationHelper, databaseHelper);
    }

    @Test
    public void testProvideMessageCallbackOnChildRemoved() throws Exception {
        presenter.provideMessageCallback().onChildRemoved(null);
        verifyZeroInteractions(chatView, databaseHelper);
    }

    @Test
    public void testProvideMessageCallbackOnChildChanged() throws Exception {
        presenter.provideMessageCallback().onChildChanged(null, null);
        verifyZeroInteractions(chatView, databaseHelper);
    }

    @Test
    public void testProvideMessageCallbackOnChildMoved() throws Exception {
        presenter.provideMessageCallback().onChildMoved(dataSnapshot, null);
        verifyZeroInteractions(chatView, databaseHelper);
    }

    @Test
    public void testProvideMessageCallbackOnCancelledThrowableExists() throws Exception {
        presenter.provideMessageCallback().onCancelled(DatabaseError.fromException(new Throwable()));
        verifyZeroInteractions(chatView, databaseHelper, authenticationHelper);
    }

    @Test
    public void testProvideMessageCallbackOnCancelledThrowableIsNull() throws Exception {
        presenter.provideMessageCallback().onCancelled(null);
        verifyZeroInteractions(chatView, databaseHelper, authenticationHelper);
    }

    @Test
    public void testProvideMessageCallbackOnChildAddedValidMessage() throws Exception {
        when(dataSnapshot.getValue(ChatMessage.class)).thenReturn(new ChatMessage());
        presenter.provideMessageCallback().onChildAdded(dataSnapshot, null);
        verify(chatView).sendNewMessageToAdapter(any(ChatMessage.class));
    }

    @Test
    public void testProvideMessageCallbackOnChildAddedNullMessage() throws Exception {
        when(dataSnapshot.getValue(ChatMessage.class)).thenReturn(null);
        presenter.provideMessageCallback().onChildAdded(dataSnapshot, null);
        verifyZeroInteractions(chatView);
    }
}