package com.example.filip.chatappv2.utils;


import com.example.filip.chatappv2.App;
import com.example.filip.chatappv2.BuildConfig;
import com.example.filip.chatappv2.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;

import java.util.Locale;
import java.util.Random;

/**
 * Created by flip6 on 10.6.2016..
 */
public class StringUtils {
    public static void logError(Throwable throwable) {
        if(!FirebaseApp.getApps(App.get()).isEmpty()){
            FirebaseCrash.report(throwable);
            if (BuildConfig.DEBUG) {
                throwable.printStackTrace();
            }
        }
    }

    public static boolean stringsAreNullOrEmpty(String... strings) {
        for (String current : strings) {
            if (current == null || current.isEmpty() || current.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static String generateRandomUsername() {
        Random random = new Random();
        return String.format(Locale.getDefault(), App.get().getString(R.string.random_username_generator_string_template), random.nextInt(5001));
    }
}