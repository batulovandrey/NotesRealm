package com.github.batulovandrey.notesrealm.view;

import android.support.v4.app.FragmentManager;

import com.github.batulovandrey.notesrealm.adapter.CustomPagerAdapter;

/**
 * @author Andrey Batulov on 12/10/2017
 */

public interface MainView {

    void showToast(String text);

    void setTabsLongClickListener();

    void closeApp();

    FragmentManager getSupportFragmentManager();

    void setAdapterToViewPager(CustomPagerAdapter adapter);

    void deleteTabAt(final int position);

    void addNewCategory();
}