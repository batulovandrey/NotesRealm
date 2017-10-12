package com.github.batulovandrey.notesrealm.dagger;

import android.content.Context;

import com.github.batulovandrey.notesrealm.manager.RealmManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;

/**
 * @author Andrey Batulov on 10/10/2017
 */

@Module
public class NetModule {

    private Context mContext;

    public NetModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Realm provideRealm() {
        return new RealmManager(mContext).getRealm();
    }
}