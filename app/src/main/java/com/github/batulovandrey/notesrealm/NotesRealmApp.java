package com.github.batulovandrey.notesrealm;

import android.app.Application;

import com.github.batulovandrey.notesrealm.dagger.AppModule;
import com.github.batulovandrey.notesrealm.dagger.DaggerNetComponent;
import com.github.batulovandrey.notesrealm.dagger.NetComponent;
import com.github.batulovandrey.notesrealm.dagger.NetModule;

/**
 * @author Andrey Batulov on 10/10/2017
 */

public class NotesRealmApp extends Application {

    private NetComponent mNetComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(getApplicationContext()))
                .build();
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }
}