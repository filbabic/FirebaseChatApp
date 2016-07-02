package com.example.filip.chatappv2;

import android.app.Application;

import com.example.filip.chatappv2.api.authentication.AuthenticationHelper;
import com.example.filip.chatappv2.api.authentication.AuthenticationHelperImpl;
import com.example.filip.chatappv2.api.database.DatabaseHelper;
import com.example.filip.chatappv2.api.database.DatabaseHelperImpl;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Filip on 08/06/2016.
 */
public class App extends Application {
    private static App sInstance;

    private DatabaseHelper databaseHelper;
    private AuthenticationHelper authenticationHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            sInstance = this;
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            databaseHelper = new DatabaseHelperImpl(firebaseDatabase);
            authenticationHelper = new AuthenticationHelperImpl(firebaseAuth);
        }
    }

    public static App get() {
        return sInstance;
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public AuthenticationHelper getAuthenticationHelper() {
        return authenticationHelper;
    }
}