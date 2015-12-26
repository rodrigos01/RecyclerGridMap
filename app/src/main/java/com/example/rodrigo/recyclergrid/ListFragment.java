package com.example.rodrigo.recyclergrid;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    RecyclerView mRecyclerView;
    Toolbar mStickyHeader;
    MapAdapter mAdapter;
    GridLayoutManager mLayoutManager;

    public static final String ARG_MULTIPLIER = "arg_multiplier";

    int mMultiplier = 1;

    public static ListFragment newInstance(int multiplier) {
        Bundle args = new Bundle();
        args.putInt(ARG_MULTIPLIER, multiplier);
        ListFragment fragment = new ListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMultiplier = getArguments().getInt(ARG_MULTIPLIER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        mStickyHeader = (Toolbar) v.findViewById(R.id.sticky_header);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<String> items = new ArrayList<>();
            map.put("Category "+ (i*mMultiplier), items);
            for (int j = 0; j<20; j++) {
                items.add("item "+(j*mMultiplier));
            }
        }
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MapAdapter();
        mAdapter.setMap(map);

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getSpan(position);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(mScrollListener);

        return v;
    }

    RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {

        int direction;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if(newState == RecyclerView.SCROLL_STATE_IDLE) {
                int position = mLayoutManager.findFirstVisibleItemPosition();
                if (position == 0 && direction < 0) {
                    ((MainActivity)getActivity()).getAppBar().setExpanded(true, true);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            direction = dy;
            int position = mLayoutManager.findFirstVisibleItemPosition();
            if (mAdapter.isHeader(position)) {
                if(direction < 0 && position > 0) {
                    position--;
                }
                String title = mAdapter.getTitleForPosition(position);
                mStickyHeader.setTitle(title);
            }

        }
    };

}
