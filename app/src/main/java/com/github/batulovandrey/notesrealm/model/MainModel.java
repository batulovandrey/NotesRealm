package com.github.batulovandrey.notesrealm.model;

import com.github.batulovandrey.notesrealm.BasicFragment;
import com.github.batulovandrey.notesrealm.NotesRealmApp;
import com.github.batulovandrey.notesrealm.adapter.CustomPagerAdapter;
import com.github.batulovandrey.notesrealm.bean.Category;
import com.github.batulovandrey.notesrealm.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * @author Andrey Batulov on 12/10/2017
 */

public class MainModel {

    private static final int CATEGORY_NAME_MIN_LENGTH = 3;

    @Inject
    Realm mRealm;

    private final MainPresenter mMainPresenter;
    private final CustomPagerAdapter mAdapter;

    public MainModel(MainPresenter mainPresenter) {
        NotesRealmApp.getNetComponent().inject(this);
        mMainPresenter = mainPresenter;
        mAdapter = new CustomPagerAdapter(mMainPresenter.getSupportFragmentManager());
    }

    public void saveCategoryName(String categoryName) {
        boolean isContains = false;
        for (Category category : mRealm.allObjects(Category.class)) {
            if (category.getCategoryName().equals(categoryName)) {
                isContains = true;
                break;
            }
        }
        if (isContains) {
            mMainPresenter.showToast(categoryName + " уже существует");
        } else {
            mRealm.beginTransaction();
            Category category = mRealm.createObject(Category.class);
            category.setCategoryName(categoryName);
            mRealm.commitTransaction();
        }
    }

    public void fillAdapter() {
        mAdapter.clear();
        List<BasicFragment> fragments = new ArrayList<>();
        for (Category category : mRealm.allObjects(Category.class)) {
            fragments.add(BasicFragment.newInstance().setTitle(category.getCategoryName()));
        }
        mAdapter.addFragments(fragments);
        mMainPresenter.setAdapterToViewPager(mAdapter);
        mMainPresenter.setTabsLongClickListener();
    }

    public void deleteCategoryAtPosition(final int position) {
        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Category> rows = realm.where(Category.class)
                        .equalTo("categoryName", mRealm.allObjects(Category.class)
                                .get(position)
                                .getCategoryName())
                        .findAll();
                rows.clear();
            }
        });
        fillAdapter();
    }


    public boolean isLengthCategoryTextOk(int length) {
        return length >= CATEGORY_NAME_MIN_LENGTH;
    }
}