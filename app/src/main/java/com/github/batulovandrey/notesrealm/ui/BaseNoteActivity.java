package com.github.batulovandrey.notesrealm.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.github.batulovandrey.notesrealm.NotesRealmApp;
import com.github.batulovandrey.notesrealm.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Abstract class for other activity
 *
 * @author Andrey Batulov on 27/07/2017
 */

public abstract class BaseNoteActivity extends AppCompatActivity {

    protected static final String EXTRA_CATEGORY_NAME = "extra_category_name";
    protected static final String EXTRA_NOTE_TITLE = "extra_note_title";
    protected static final String EXTRA_NOTE_BODY = "extra_note_body";
    protected static final String EXTRA_NOTE_ID = "extra_note_id";
    protected static final int INPUT_MIN_LENGTH = 3;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.note_id_text_view)
    TextView mNoteIdTextView;

    @BindView(R.id.title_edit_text)
    TextInputEditText mTitleEditText;

    @BindView(R.id.body_edit_text)
    TextInputEditText mBodyEditText;

    @BindView(R.id.main_button)
    FloatingActionButton mMainButton;

    @BindView(R.id.delete_note_button)
    FloatingActionButton mSecondButton;

    @Inject
    Realm mRealm;

    protected String mCategoryName;
    protected String mNoteTitle;
    protected String mNoteBody;
    protected String mNoteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_main);
        ((NotesRealmApp) getApplicationContext()).getNetComponent().inject(this);
        ButterKnife.bind(this);
        getDataFromIntent();
        initUI();
        hideKeyboard(isEditTextEnabled());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            handleActivityClosing();
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        handleActivityClosing();
    }

    @OnClick({R.id.main_button, R.id.delete_note_button})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_button:
                onMainButtonClick(v);
                break;
            case R.id.delete_note_button:
                onSecondButtonClick(v);
                break;
        }
    }

    // region protected methods

    /**
     * Define in inherited classes to handle logic before closing activity
     */
    protected abstract void handleActivityClosing();

    /**
     * Define in inherited classes to get data from intent
     */
    protected abstract void getDataFromIntent();

    /**
     * Redefine in inherited class if need to change visibility
     *
     * @return visibility of note textView
     */
    protected int getNoteIdVisibility() {
        return View.VISIBLE;
    }

    /**
     * Redefine in inherited class if need to change visibility
     *
     * @return visibility of note textView
     */
    protected int getSecondButtonVisibility() {
        return View.GONE;
    }

    /**
     * Define in inherited classes to handle main button click
     *
     * @param view View
     */
    protected abstract void onMainButtonClick(View view);

    /**
     * Define in inherited class if need to handle second button click event
     *
     * @param view View
     */
    protected void onSecondButtonClick(View view) {
        // implement in inherited classes if needed
    }

    /**
     * Define in inherited classes to handle text inputs enabled flag
     *
     * @return flag true to be enabled or false
     */
    protected boolean isEditTextEnabled() {
        return true;
    }

    /**
     * Redefine in inherited classes to change drawable
     *
     * @return drawable of the main button
     */
    @IdRes
    protected int getMainButtonImageResource() {
        return android.R.drawable.ic_menu_save;
    }

    // endregion

    // region private methods

    private void initUI() {
        initToolbar();
        initViews();
        initButtons();
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initViews() {
        mNoteIdTextView.setVisibility(getNoteIdVisibility());
        mTitleEditText.setEnabled(isEditTextEnabled());
        mBodyEditText.setEnabled(isEditTextEnabled());
    }

    private void initButtons() {
        mSecondButton.setVisibility(getSecondButtonVisibility());
        mMainButton.setImageResource(getMainButtonImageResource());
    }

    private void hideKeyboard(boolean editTextFieldsEnabled) {
        if (!editTextFieldsEnabled) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mTitleEditText.getWindowToken(), 0);
            mTitleEditText.setFocusable(false);
            mBodyEditText.setFocusable(false);
        }
    }

    // endregion
}