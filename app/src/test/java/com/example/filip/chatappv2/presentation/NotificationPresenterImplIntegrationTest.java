package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.api.database.DatabaseHelper;
import com.example.filip.chatappv2.pojo.ChatMessage;
import com.example.filip.chatappv2.view.NotificationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Created by flip6 on 25.6.2016..
 */
@RunWith(MockitoJUnitRunner.class)
public class NotificationPresenterImplIntegrationTest {
    private NotificationPresenterImpl presenter;

    @Mock
    public AuthenticationHelper authenticationHelper;

    @Mock
    public DatabaseHelper databaseHelper;

    @Mock
    public NotificationView notificationView;

    @Mock
    public DataSnapshot dataSnapshot;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new NotificationPresenterImpl(databaseHelper, authenticationHelper);
        presenter.setView(notificationView);
    }

    @Test
    public void testRemoveCallbacksFromFirebase() throws Exception {
        presenter.removeCallbacksFromFirebase();
        verify(databaseHelper).removeNotificationMessageCallback(any(ChildEventListener.class));
    }

    @Test
    public void addCallbacksToFirebase() throws Exception {
        presenter.addCallbacksToFirebase();
        verify(databaseHelper).addNotificationMessageCallback(any(ChildEventListener.class));
    }

    @Test
    public void testProvideNotificationCallbackOnChildAddedMessageIsNull() throws Exception {
        when(dataSnapshot.getValue(ChatMessage.class)).thenReturn(null);
        presenter.provideNotificationCallback().onChildAdded(dataSnapshot, null);
        verifyZeroInteractions(notificationView);
    }

    @Test
    public void testProvideNotificationCallbackOnChildAddedAuthorsDifferentiate() throws Exception {
        ChatMessage message = new ChatMessage("text", "user", "user");
        when(dataSnapshot.getValue(ChatMessage.class)).thenReturn(message);
        when(authenticationHelper.getCurrentUserDisplayName()).thenReturn("last");
        presenter.provideNotificationCallback().onChildAdded(dataSnapshot, "");
        verify(notificationView).sendMessageNotification(anyString());
    }

    @Test
    public void testProvideNotificationCallbackOnChildAddedAuthorsAreTheSame() throws Exception {
        ChatMessage message = new ChatMessage("text", "user", "user");
        when(dataSnapshot.getValue(ChatMessage.class)).thenReturn(message);
        when(authenticationHelper.getCurrentUserDisplayName()).thenReturn("user");
        presenter.provideNotificationCallback().onChildAdded(dataSnapshot, "");
        verify(authenticationHelper).getCurrentUserDisplayName();
        verifyZeroInteractions(notificationView, databaseHelper);
    }

    @Test
    public void testProvideNotificationCallbackOnChildMoved() throws Exception {
        presenter.provideNotificationCallback().onChildMoved(null, null);
        verifyZeroInteractions(notificationView, authenticationHelper, databaseHelper);
    }

    @Test
    public void testProvideNotificationCallbackOnChildRemoved() throws Exception {
        presenter.provideNotificationCallback().onChildRemoved(null);
        verifyZeroInteractions(notificationView, authenticationHelper, databaseHelper);
    }

    @Test
    public void testProvideNotificationCallbackOnChildChanged() throws Exception {
        presenter.provideNotificationCallback().onChildChanged(null, null);
        verifyZeroInteractions(notificationView, authenticationHelper, databaseHelper);
    }

    @Test
    public void testProvideNotificationCallbackOnCancelledThrowableIsNull() throws Exception {
        presenter.provideNotificationCallback().onCancelled(null);
        verifyZeroInteractions(notificationView, databaseHelper, authenticationHelper);
    }
    @Test
    public void testProvideNotificationCallbackOnCancelledThrowableExists() throws Exception {
        presenter.provideNotificationCallback().onCancelled(DatabaseError.fromException(new Throwable()));
        verifyZeroInteractions(notificationView, databaseHelper, authenticationHelper);
    }
}