package com.example.rodrigo.recyclergrid;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by rodrigo on 19/12/15.
 */
public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder> {

    public static int TYPE_HEADER = 0;
    public static int TYPE_ITEM = 1;

    private int mCount;
    private Map<String, List<String>> mMap;
    private SparseArray<String> mHeaders;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(viewType == TYPE_HEADER) {
            v = inflater.inflate(R.layout.list_header, parent, false);
        } else {
            v = inflater.inflate(R.layout.list_item, parent, false);
        }
        return new ViewHolder(v, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TYPE_HEADER) {
            String key = mHeaders.get(position);
            holder.text.setText(key);
        } else if (type == TYPE_ITEM) {
            int keyCount = 0;
            for (List<String> values: mMap.values()) {
                keyCount++;
                int itemPosition = position - keyCount;
                if(itemPosition < values.size()) {
                    String item = values.get(itemPosition);
                    holder.text.setText(item);
                    return;
                }
                position -= values.size();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaders.get(position) != null) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    public boolean isHeader(int position) {
        return getItemViewType(position) == TYPE_HEADER;
    }

    public String getTitleForPosition(int position) {
        if(isHeader(position)) {
            return mHeaders.get(position);
        } else {
            for (int i = 0; i < mHeaders.size(); i++) {
                int key = mHeaders.keyAt(i);
                if(position > key) {
                    int nextIndex = i + 1;
                    if (nextIndex < mHeaders.size()) {
                        int nextKey = mHeaders.keyAt(nextIndex);
                        if (position < nextKey) {
                            return mHeaders.get(key);
                        }
                    } else {
                        return mHeaders.get(key);
                    }
                }
            }
            return null;
        }
    }

    public int getSpan(int position) {
        int type = getItemViewType(position);
        if(type == TYPE_HEADER) {
            return 2;
        }
        return 1;
    }

    public Map<String, List<String>> getMap() {
        return mMap;
    }

    public void setMap(Map<String, List<String>> map) {
        mMap = map;
        mCount = 0;
        mHeaders = new SparseArray<>();
        for (String key: mMap.keySet()) {
            //Index for the list headers
            mHeaders.put(mCount, key);
            List<String> values = mMap.get(key);
            mCount += values.size() + 1;
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
