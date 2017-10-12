package com.github.batulovandrey.notesrealm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.batulovandrey.notesrealm.adapter.NoteAdapter;
import com.github.batulovandrey.notesrealm.bean.Category;
import com.github.batulovandrey.notesrealm.bean.Note;
import com.github.batulovandrey.notesrealm.ui.CreateNoteActivity;
import com.github.batulovandrey.notesrealm.ui.NoteDetailActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.realm.Realm;
import io.realm.RealmList;

import static android.app.Activity.RESULT_OK;

/**
 * @author Andrey Batulov on 15/07/2017
 */

public class BasicFragment extends Fragment implements NoteAdapter.NoteClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Inject
    Realm mRealm;

    private Unbinder mUnbinder;
    private NoteAdapter mAdapter;
    private String mTitle;

    public static BasicFragment newInstance() {
        return new BasicFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NotesRealmApp.getNetComponent().inject(this);
        mAdapter = new NoteAdapter(this, getNotesRealm());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(int position) {
        Note note = getNotesRealm().get(position);
        startActivityForResult(NoteDetailActivity.createExplicitIntent(
                getContext(),
                mTitle,
                note.getTitle(),
                note.getId(),
                note.getBody()),
                1);
    }

    @OnClick(R.id.add_note_button)
    public void onClickButton(View v) {
        Snackbar.make(v, getString(R.string.create_note_question), Snackbar.LENGTH_SHORT)
                .setAction(R.string.yes, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(CreateNoteActivity.createExplicitIntent(getContext(), mTitle), 1);
                    }
                })
                .show();
    }

    public String getTitle() {
        return mTitle;
    }

    public BasicFragment setTitle(String title) {
        mTitle = title;
        return this;
    }

    private RealmList<Note> getNotesRealm() {
        RealmList<Note> notes = null;
        for (Category category : mRealm.allObjects(Category.class)) {
            if (category.getCategoryName().equals(mTitle)) {
                notes = category.getNotes();
                break;
            }
        }
        return notes;
    }
}