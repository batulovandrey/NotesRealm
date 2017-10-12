package com.github.batulovandrey.notesrealm.presenter;

import android.support.v4.app.FragmentManager;

import com.github.batulovandrey.notesrealm.adapter.CustomPagerAdapter;
import com.github.batulovandrey.notesrealm.model.MainModel;
import com.github.batulovandrey.notesrealm.view.MainView;

/**
 * @author Andrey Batulov on 12/10/2017
 */

public class MainPresenterImpl implements MainPresenter {

    private final MainModel mMainModel;
    private final MainView mMainView;

    public MainPresenterImpl(MainView mainView) {
        mMainView = mainView;
        mMainModel = new MainModel(this);
    }

    @Override
    public void setTabsLongClickListener() {
        mMainView.setTabsLongClickListener();
    }

    @Override
    public void closeApp() {
        mMainView.closeApp();
    }

    @Override
    public void showToast(String text) {
        mMainView.showToast(text);
    }

    @Override
    public FragmentManager getSupportFragmentManager() {
        return mMainView.getSupportFragmentManager();
    }

    @Override
    public void setAdapterToViewPager(CustomPagerAdapter adapter) {
        mMainView.setAdapterToViewPager(adapter);
    }

    @Override
    public void deleteTabAt(int position) {
        mMainView.deleteTabAt(position);
    }

    @Override
    public void fillAdapter() {
        mMainModel.fillAdapter();
    }

    @Override
    public void saveCategoryName(String categoryName) {
        mMainModel.saveCategoryName(categoryName);
    }

    @Override
    public void deleteCategoryAtPosition(int position) {
        mMainModel.deleteCategoryAtPosition(position);
    }

    @Override
    public boolean isLengthCategoryTextOk(int length) {
        return mMainModel.isLengthCategoryTextOk(length);
    }

    @Override
    public void addNewCategory() {
        mMainView.addNewCategory();
    }
}