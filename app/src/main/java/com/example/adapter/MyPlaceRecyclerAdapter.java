package com.example.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dreamera_master.MyApplication;
import com.example.dreamera_master.R;
import com.example.utils.MyPicture;

import java.util.List;

/**
 * Created by Zhangzongxiang on 2017/5/21.
 */

public class MyPlaceRecyclerAdapter extends RecyclerView.Adapter<MyPlaceRecyclerAdapter.
        ViewHolder> {
    Context mContext;
    private List<MyPicture> picturesList;

    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView placeImage;
        TextView detailTitle;

        public ViewHolder(View view) {
            super(view);
            cardView = (CardView) view;
            placeImage = (ImageView) view.findViewById(R.id.place_picture);
            detailTitle = (TextView) view.findViewById(R.id.detail_title);
        }
    }

    public MyPlaceRecyclerAdapter(List<MyPicture> picturesList) {
        this.picturesList = picturesList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = MyApplication.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.place_card_view, parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (picturesList == null) {

        }
        MyPicture myPicture = picturesList.get(position);
        holder.detailTitle.setText(myPicture.getDetail_title());
        Glide.with(mContext).load(myPicture.getPictureUrl()).into(holder.placeImage);
    }

    @Override
    public int getItemCount() {
        return picturesList.size();
    }

}
