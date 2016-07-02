package com.example.filip.chatappv2.ui.main;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.filip.chatappv2.App;
import com.example.filip.chatappv2.R;
import com.example.filip.chatappv2.ui.chat.ChatActivity;
import com.example.filip.chatappv2.presentation.MainPresenter;
import com.example.filip.chatappv2.presentation.MainPresenterImpl;
import com.example.filip.chatappv2.ui.register.RegisterActivity;
import com.example.filip.chatappv2.view.MainView;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {
    @BindView(R.id.main_activity_email_edit_text)
    EditText mEmailEditText;

    @BindView(R.id.main_activity_password_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.main_activity_login_button)
    Button mLoginButton;

    @BindView(R.id.main_activity_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.main_activity_register_text_view)
    TextView mRegisterNewAccountTextView;

    @BindView(R.id.progress_bar)
    ProgressBar mRequestProgressBar;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPresenter();
        initUI();
    }

    private void initPresenter() {
        presenter = new MainPresenterImpl(App.get().getAuthenticationHelper());
        presenter.setView(this);
    }

    private void initUI() {
        mLoginButton.setOnClickListener(this);
        mRegisterNewAccountTextView.setOnClickListener(this);
    }

    @Override
    public void showOnFailedToLoginErrorMessage() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.login_failed_error_message), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showOnSuccessfulLoginMessage() {
        Snackbar.make(mCoordinatorLayout, R.string.login_successful_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startChatActivity() {
        mRegisterNewAccountTextView.requestFocus();
        startActivity(new Intent(this, ChatActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void showOnEmptyEmailOrPasswordError() {
        Snackbar.make(mCoordinatorLayout, R.string.email_or_password_empty_error_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startRegistrationActivity() {
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void showProgressBar() {
        mRequestProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mRequestProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideEditTextKeyboard() {
        View current = getCurrentFocus();
        if (current != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(current.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mLoginButton) {
            presenter.handleOnUserClickedLoginButton(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
        } else if (v == mRegisterNewAccountTextView) {
            presenter.handleOnUserClickedRegisterTextView();
        }
    }
}