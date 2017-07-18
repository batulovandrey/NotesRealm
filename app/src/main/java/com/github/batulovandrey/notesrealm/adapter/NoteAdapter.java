package com.github.batulovandrey.notesrealm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.batulovandrey.notesrealm.R;
import com.github.batulovandrey.notesrealm.model.Note;

import org.w3c.dom.Text;

import io.realm.RealmList;

/**
 * Created by batul0ve on 18.07.2017.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private RealmList<Note> mNoteList;

    public NoteAdapter(RealmList<Note> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        Note note = mNoteList.get(position);
        holder.idTextView.setText(note.getId());
        holder.titleTextView.setText(note.getTitle());
    }

    @Override
    public int getItemCount() {
        return mNoteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView idTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.title_text_view);
            idTextView = (TextView) itemView.findViewById(R.id.id_text_view);
        }
    }
}