package com.example.filip.chatappv2.pojo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by flip6 on 2.7.2016..
 */
public class ChatMessageTest {
    @Test
    public void testObjectNotNull() throws Exception {
        assertNotNull(new ChatMessage());
    }
    @Test
    public void testValidateReturnsFalse() throws Exception {
       assertFalse(new ChatMessage().validate());
    }

    @Test
    public void testValidateReturnsTrueWhenDataIsSetThroughAConstructor() throws Exception {
        ChatMessage chatMessage = new ChatMessage("test", "test", "test");
        chatMessage.setAuthorImageURL("test");
        assertTrue(chatMessage.validate());
    }

    @Test
    public void testValidateReturnsTrue() throws Exception {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAuthorDisplayName("test");
        chatMessage.setMessageText("test");
        chatMessage.setAuthorImageURL("test");
        chatMessage.setLastMessageAuthor("test");
        assertTrue(chatMessage.validate());
    }

    @Test
    public void testGetAuthorDisplayNameReturnsNull() throws Exception {
        ChatMessage message = new ChatMessage();
        assertNull(message.getAuthorDisplayName());
    }

    @Test
    public void testGetAuthorDisplayNameReturnsValidString() throws Exception {
        ChatMessage message = new ChatMessage();
        message.setAuthorDisplayName("test");
        assertNotNull(message.getAuthorDisplayName());
    }

    @Test
    public void testGetLastAuthorReturnsNull() throws Exception {
        ChatMessage message = new ChatMessage();
        assertNull(message.getLastMessageAuthor());
    }

    @Test
    public void testGetLastAuthorReturnsValidString() throws Exception {
        ChatMessage message = new ChatMessage();
        message.setLastMessageAuthor("test");
        assertNotNull(message.getLastMessageAuthor());
    }
    @Test
    public void testGetAuthorURLReturnsNull() throws Exception {
        ChatMessage message = new ChatMessage();
        assertNull(message.getAuthorImageURL());
    }

    @Test
    public void testGetAuthorURLReturnsValidString() throws Exception {
        ChatMessage message = new ChatMessage();
        message.setAuthorImageURL("test");
        assertNotNull(message.getAuthorImageURL());
    }
    @Test
    public void testGetMessageReturnsNull() throws Exception {
        ChatMessage message = new ChatMessage();
        assertNull(message.getMessageText());
    }

    @Test
    public void testGetMessageReturnsValidString() throws Exception {
        ChatMessage message = new ChatMessage();
        message.setMessageText("test");
        assertNotNull(message.getMessageText());
    }
}