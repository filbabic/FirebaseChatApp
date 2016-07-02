package com.example.filip.chatappv2.pojo;

import com.example.filip.chatappv2.utils.StringUtils;

/**
 * Created by Filip on 08/06/2016.
 */
public class ChatMessage extends BaseModel {
    private String messageText;
    private String authorDisplayName;
    private String lastMessageAuthor;
    private String authorImageURL;

    public ChatMessage(String messageText, String authorDisplayName, String lastMessageAuthor) {
        this.messageText = messageText;
        this.authorDisplayName = authorDisplayName;
        this.lastMessageAuthor = lastMessageAuthor;
    }

    public ChatMessage() {
    }

    public String getLastMessageAuthor() {
        return lastMessageAuthor;
    }

    public void setLastMessageAuthor(String lastMessageAuthor) {
        this.lastMessageAuthor = lastMessageAuthor;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getAuthorDisplayName() {
        return authorDisplayName;
    }

    public void setAuthorDisplayName(String authorDisplayName) {
        this.authorDisplayName = authorDisplayName;
    }

    public String getAuthorImageURL() {
        return authorImageURL;
    }

    public void setAuthorImageURL(String authorImageURL) {
        this.authorImageURL = authorImageURL;
    }

    @Override
    protected boolean validate() {
        return !StringUtils.stringsAreNullOrEmpty(lastMessageAuthor, messageText, authorDisplayName, authorImageURL);
    }
}