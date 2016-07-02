package com.example.filip.chatappv2.presentation;

import com.example.filip.chatappv2.view.NotificationView;

/**
 * Created by flip6 on 25.6.2016..
 */

public interface NotificationPresenter extends Presenter<NotificationView>{
    void removeCallbacksFromFirebase();

    void addCallbacksToFirebase();
}