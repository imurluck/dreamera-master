package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dreamera_master.MyApplication;
import com.example.dreamera_master.R;
import com.example.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangZongxiang on 2017/5/31.
 */

public class PopWindowRecyclerAdapter extends RecyclerView.Adapter<PopWindowRecyclerAdapter.
        ViewHolder> implements View.OnClickListener {

    private Context context;

    private OnItemClickListener mOnItemClickListener = null;

    private List<HashMap<String, String>> pointList = new ArrayList<HashMap<String, String>>();

    private int currentPosition;

    public PopWindowRecyclerAdapter(Context context, List<HashMap<String, String>> pointList) {
        this.context = context;
        this.pointList = pointList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleText;
        private ImageView imageView;
        private ImageView selectedTag;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.pop_window_recycler_item_image);
            titleText = (TextView) view.findViewById(R.id.pop_window_recycler_item_text);
            selectedTag = (ImageView) view.findViewById(R.id.pop_window_item_selected_tag);
        }
    }

    @Override
    public PopWindowRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = MyApplication.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.pop_window_recycler_item, null);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PopWindowRecyclerAdapter.ViewHolder holder, int position) {
        currentPosition = position;
        Map<String, String> point = pointList.get(position);
        holder.titleText.setText(point.get("title"));
        Glide.with(context).load(point.get("picture")).into(holder.imageView);
    }



    @Override
    public int getItemCount() {
        return pointList.size();
    }

    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, currentPosition);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
