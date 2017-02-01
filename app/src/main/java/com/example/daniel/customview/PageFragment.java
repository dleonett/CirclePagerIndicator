package com.example.daniel.customview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Daniel Leonett (d@hustling.me) on 1/2/2017.
 * Copyright (c) 2017 Hustling. All rights reserved.
 */
public class PageFragment extends Fragment {

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    private static final String TAG = PageFragment.class.getSimpleName();
    public static final String KEY_TITLE = "key_title";

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // Fields
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private String title;

    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // Methods
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    public static PageFragment newInstace(String title) {
        PageFragment fragment = new PageFragment();

        Bundle bundle = new Bundle();
        bundle.putString("key_title", title);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!getArguments().containsKey(KEY_TITLE)) {
            throw new RuntimeException("Fragment must contain a \"" + KEY_TITLE + "\" argument!");
        }
        this.title = getArguments().getString(KEY_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);

        ButterKnife.bind(this, view);

        tvTitle.setText(title);

        return view;
    }

}
