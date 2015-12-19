package com.example.rodrigo.recyclergrid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MapAdapter mAdapter;
    GridLayoutManager mManager;
    GridLayoutManager.SpanSizeLookup mSpanSizeLookup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setHasFixedSize(true);
        mManager = new GridLayoutManager(this, 2);
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < 3; i++) {
            ArrayList<String> items = new ArrayList<>();
            map.put("Category "+i, items);
            for (int j = 0; j<20; j++) {
                items.add("item "+j);
            }
        }
        mSpanSizeLookup = new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getSpan(position);
            }
        };
        mManager.setSpanSizeLookup(mSpanSizeLookup);
        mRecyclerView.setLayoutManager(mManager);
        View listHeader = getLayoutInflater().inflate(R.layout.list_main_header, mRecyclerView, false);
        mAdapter = new MapAdapter();
        mAdapter.setMainHeaderView(listHeader);
        mAdapter.setMap(map);
        mRecyclerView.setAdapter(mAdapter);
    }


}
