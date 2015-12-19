package com.example.rodrigo.recyclergrid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by rodrigo on 19/12/15.
 */
public class NumAdapter extends RecyclerView.Adapter<NumAdapter.ViewHolder> {

    public static final int TYPE_HEADER = 0;

    public int mSize;

    public NumAdapter(int size) {
        mSize = size;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(Integer.toString(position));
    }

    @Override
    public int getItemCount() {
        return mSize;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 5;
    }

    public boolean isHeader(int position) {
        return getItemViewType(position) == TYPE_HEADER;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
