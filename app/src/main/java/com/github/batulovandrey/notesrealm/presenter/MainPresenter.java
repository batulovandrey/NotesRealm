package com.github.batulovandrey.notesrealm.presenter;

import android.support.v4.app.FragmentManager;

import com.github.batulovandrey.notesrealm.adapter.CustomPagerAdapter;

/**
 * @author Andrey Batulov on 12/10/2017
 */

public interface MainPresenter {

    void setTabsLongClickListener();

    void closeApp();

    void showToast(String text);

    FragmentManager getSupportFragmentManager();

    void setAdapterToViewPager(CustomPagerAdapter adapter);

    void deleteTabAt(final int position);

    void fillAdapter();

    void saveCategoryName(String categoryName);

    void deleteCategoryAtPosition(final int position);

    boolean isLengthCategoryTextOk(int length);

    void addNewCategory();
}