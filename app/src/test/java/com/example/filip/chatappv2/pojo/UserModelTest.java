package com.example.filip.chatappv2.pojo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by flip6 on 2.7.2016..
 */
public class UserModelTest {

    @Test
    public void testValidateReturnsFalse() throws Exception {
        assertFalse(new UserModel().validate());
    }
    @Test
    public void testValidateReturnsTrue() throws Exception {
        UserModel model = new UserModel("test", false);
        assertTrue(model.validate());
    }

    @Test
    public void testObjectNotNull() throws Exception {
        assertNotNull(new UserModel());
    }

    @Test
    public void testGetIsOnlineReturnsFalse() throws Exception {
        assertFalse(new UserModel().isOnline());
    }
    @Test
    public void testGetIsOnlineReturnsTrue() throws Exception {
        UserModel model = new UserModel();
        model.setOnline(true);
        assertTrue(model.isOnline());
    }

    @Test
    public void testGetUserDisplayNameReturnsNull() throws Exception {
        UserModel model = new UserModel();
        assertNull(model.getUserDisplayName());
    }
    @Test
    public void testGetUserDisplayNameReturnsValidString() throws Exception {
        UserModel model = new UserModel();
        model.setUserDisplayName("test");
        assertNotNull(model.getUserDisplayName());
    }
}