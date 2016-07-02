package com.example.filip.chatappv2.ui.register;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.filip.chatappv2.R;
import com.example.filip.chatappv2.constants.Constants;
import com.example.filip.chatappv2.presentation.ChooseUsernamePresenter;
import com.example.filip.chatappv2.presentation.ChooseUsernamePresenterImpl;
import com.example.filip.chatappv2.view.ChooseUsernameView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by flip6 on 14.6.2016..
 */
public class ChooseUsernameFragment extends Fragment implements View.OnClickListener, ChooseUsernameView {

    @BindView(R.id.choose_username_coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;

    @BindView(R.id.choose_username_edit_text)
    EditText mUsernameEditText;

    @BindView(R.id.choose_username_button)
    Button mSaveChoiceUsernameButton;

    @BindView(R.id.skip_username_choice_button)
    Button mSkipUsernameChoiceButton;

    private ChooseUsernamePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_choose_username, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initUI();
        initPresenter();
    }

    private void initPresenter() {
        presenter = new ChooseUsernamePresenterImpl();
        presenter.setView(this);
    }

    private void initUI() {
        mSaveChoiceUsernameButton.setOnClickListener(this);
        mSkipUsernameChoiceButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSaveChoiceUsernameButton) {
            presenter.onUserClickedSaveUsernameChoice(mUsernameEditText.getText().toString());
        } else if (v == mSkipUsernameChoiceButton) {
            presenter.onUserClickedSkipUsernameChoice();
        }
    }

    @Override
    public void saveUsernameChoice(String username) {
        RegisterActivity activity = (RegisterActivity) getActivity();
        activity.replaceFragment(UserRegisterFragment.newInstance(createBundleData(username)), true);
    }

    @Override
    public void skipUsernameChoice() {
        RegisterActivity activity = (RegisterActivity) getActivity();
        activity.replaceFragment(UserRegisterFragment.newInstance(null), true);
    }

    @Override
    public void hideKeyboardOnButtonPress() {
        View current = getActivity().getCurrentFocus();
        if (current != null) {
            InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(current.getWindowToken(), 0);
        }
    }

    @Override
    public void showUsernameCannotBeEmpty() {
        Snackbar.make(mCoordinatorLayout, R.string.username_chosen_is_empty_error, Snackbar.LENGTH_LONG).show();
    }

    protected Bundle createBundleData(String username) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.USERNAME_KEY, username);
        return bundle;
    }
}