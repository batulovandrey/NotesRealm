package com.github.batulovandrey.notesrealm.dagger;

import com.github.batulovandrey.notesrealm.BasicFragment;
import com.github.batulovandrey.notesrealm.model.MainModel;
import com.github.batulovandrey.notesrealm.ui.BaseNoteActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author Andrey Batulov on 10/10/2017
 */

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    void inject(BasicFragment fragment);

    void inject(BaseNoteActivity activity);

    void inject(MainModel mainModel);
}