package com.example.filip.chatappv2.api.authentication;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.filip.chatappv2.api.RequestListener;
import com.example.filip.chatappv2.utils.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

/**
 * Created by flip6 on 25.6.2016..
 */

public class AuthenticationHelperImpl implements AuthenticationHelper {
    private final FirebaseAuth firebaseAuth;

    public AuthenticationHelperImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public void logTheUserIn(String email, String password, final RequestListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        listener.onSuccessfulRequest();
                    }
                } else {
                    listener.onFailedRequest();
                }
            }
        });
    }

    @Override
    public void attemptToRegisterTheUser(String email, String password, final RequestListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null) {
                        listener.onSuccessfulRequest();
                    }
                } else {
                    listener.onFailedRequest();
                }
            }
        });
    }

    @Override
    public void setUserDisplayName(String username) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (StringUtils.stringsAreNullOrEmpty(username)) {
            username = StringUtils.generateRandomUsername();
        }
        if (currentUser != null) {
            UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(username).build();
            currentUser.updateProfile(request).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            });
        }
    }

    @Override
    public void logTheUserOut() {
        firebaseAuth.signOut();
    }

    @Override
    public boolean checkIfUserIsLoggedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public String getCurrentUserDisplayName() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            return user.getDisplayName();
        }
        return null;
    }
}