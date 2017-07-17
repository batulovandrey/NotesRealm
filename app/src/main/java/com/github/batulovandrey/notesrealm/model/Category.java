package com.github.batulovandrey.notesrealm.model;

import io.realm.RealmObject;

/**
 * Created by batul0ve on 17.07.2017.
 */

public class Category extends RealmObject {
    private String categoryName;

    public Category() {
        // Empty constructor needed by Realm
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
