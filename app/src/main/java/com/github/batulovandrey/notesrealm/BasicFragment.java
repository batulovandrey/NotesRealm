package com.github.batulovandrey.notesrealm;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by batul0ve on 15.07.2017.
 */

public class BasicFragment extends Fragment {
    private static final String EXTRA_TITLE_KEY = "extra_title";

    private String mTitle;
    private TextView mTitleTextView;

    public static Bundle newArgs(@NonNull String title) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_TITLE_KEY, title);
        return bundle;
    }

    public static BasicFragment newInstance(@NonNull String title) {
        BasicFragment fragment = new BasicFragment();
        fragment.setArguments(newArgs(title));
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTitle = getArguments().getString(EXTRA_TITLE_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basic, container, false);
        mTitleTextView = (TextView) view.findViewById(R.id.title_text_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTitleTextView.setText(mTitle);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
