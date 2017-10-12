package com.github.batulovandrey.notesrealm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.batulovandrey.notesrealm.adapter.CustomPagerAdapter;
import com.github.batulovandrey.notesrealm.presenter.MainPresenter;
import com.github.batulovandrey.notesrealm.presenter.MainPresenterImpl;
import com.github.batulovandrey.notesrealm.view.MainView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Andrey Batulov on 15/07/2017
 */

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private MainPresenter mMainPresenter;

    // region Activity lifeCycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mMainPresenter = new MainPresenterImpl(this);
        initToolbar();
        mMainPresenter.fillAdapter();
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMainPresenter.closeApp();
                return true;
            case R.id.new_category:
                mMainPresenter.addNewCategory();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        mMainPresenter.closeApp();
    }

    // endregion

    @Override
    public void addNewCategory() {
        LinearLayout view = (LinearLayout) getLayoutInflater().inflate(R.layout.create_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.input_category_edit_text);
        new AlertDialog.Builder(this)
                .setTitle(R.string.input_category_name)
                .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mMainPresenter.isLengthCategoryTextOk(editText.getText().length())) {
                            mMainPresenter.saveCategoryName(editText.getText().toString().toLowerCase());
                            mMainPresenter.fillAdapter();
                        } else {
                            mMainPresenter.showToast(getString(R.string.need_more_symbols));
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setView(view)
                .show();
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setTabsLongClickListener() {
        LinearLayout tabStrip = (LinearLayout) mTabLayout.getChildAt(0);
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            final int index = i;
            tabStrip.getChildAt(i).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mMainPresenter.deleteTabAt(index);
                    return false;
                }
            });
        }
    }

    @Override
    public void deleteTabAt(final int position) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.remove_category)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mMainPresenter.deleteCategoryAtPosition(position);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void closeApp() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.exit_app)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void setAdapterToViewPager(CustomPagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}