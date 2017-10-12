package com.github.batulovandrey.notesrealm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.batulovandrey.notesrealm.R;
import com.github.batulovandrey.notesrealm.bean.Note;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmList;

/**
 * @author Andrey Batulov on 18/07/2017
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final NoteClickListener mNoteClickListener;
    private RealmList<Note> mNoteList;

    public NoteAdapter(NoteClickListener clickListener, RealmList<Note> noteList) {
        mNoteClickListener = clickListener;
        mNoteList = noteList;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.title_text_view)
        TextView titleTextView;

        @BindView(R.id.id_text_view)
        TextView idTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mNoteClickListener.onClick(getLayoutPosition());
        }
    }

    public interface NoteClickListener {
        void onClick(int position);
    }
}