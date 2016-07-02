package com.example.filip.chatappv2.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.filip.chatappv2.App;
import com.example.filip.chatappv2.R;
import com.example.filip.chatappv2.constants.Constants;
import com.example.filip.chatappv2.presentation.RegistrationPresenter;
import com.example.filip.chatappv2.presentation.RegistrationPresenterImpl;
import com.example.filip.chatappv2.ui.chat.ChatActivity;
import com.example.filip.chatappv2.view.RegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by flip6 on 2.7.2016..
 */

public class UserRegisterFragment extends Fragment implements RegisterView, View.OnClickListener {
    @BindView(R.id.register_user_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.register_user_email_edit_text)
    EditText mEmailEditText;

    @BindView(R.id.register_user_password_edit_text)
    EditText mPasswordEditText;

    @BindView(R.id.register_user_reenter_password_edit_text)
    EditText mReenterPasswordEditText;

    @BindView(R.id.register_user_create_account_button)
    Button mCreateAccountButton;

    @BindView(R.id.progress_bar)
    ProgressBar mRequestProgressBar;

    public static UserRegisterFragment newInstance(Bundle data) {
        UserRegisterFragment f = new UserRegisterFragment();
        if (data != null) {
            f.setArguments(data);
        }
        return f;
    }

    private RegistrationPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initUI();
        initPresenter();
    }

    protected void initPresenter() {
        presenter = new RegistrationPresenterImpl(App.get().getAuthenticationHelper());
        presenter.setView(this);
        receiveArguments();
    }

    protected void initUI() {
        mCreateAccountButton.setOnClickListener(this);
    }

    protected void receiveArguments() {
        Bundle arguments = this.getArguments();
        if (arguments != null && arguments.containsKey(Constants.USERNAME_KEY)) {
            presenter.setUsername(arguments.getString(Constants.USERNAME_KEY));
        }
    }

    @Override
    public void attemptToCreateUserOnPasswordsMatch() {
        presenter.attemptToRegisterTheUser(mEmailEditText.getText().toString(), mPasswordEditText.getText().toString());
    }

    @Override
    public void showPasswordsDoNotMatchOrAreEmptyErrorMessage() {
        Snackbar.make(mCoordinatorLayout, R.string.passwords_do_not_match_error_message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showOnFailedRequestErrorMessage() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.account_creation_failed_error_message), Snackbar.LENGTH_SHORT).show();
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
    public void sendTheUserToChatAfterRegistration() {
        startActivity(new Intent(getActivity(), ChatActivity.class));
    }

    @Override
    public void setNewUserDisplayName() {
        presenter.setNewUserDisplayName();
    }

    @Override
    public void onClick(View v) {
        if (v == mCreateAccountButton) {
            presenter.checkIfPasswordsMatch(mPasswordEditText.getText().toString(), mReenterPasswordEditText.getText().toString());
        }
    }
}