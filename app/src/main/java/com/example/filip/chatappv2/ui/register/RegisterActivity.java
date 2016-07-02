package com.example.filip.chatappv2.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.filip.chatappv2.R;

/**
 * Created by flip6 on 14.6.2016..
 */
public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (savedInstanceState == null) {
            initFragment();
        }
    }

    private void initFragment() {
        replaceFragment(new ChooseUsernameFragment(), false);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    protected void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.register_activity_frame_layout, fragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }
}