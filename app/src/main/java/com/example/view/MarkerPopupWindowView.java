package com.example.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.example.adapter.PopWindowRecyclerAdapter;
import com.example.dreamera_master.R;
import com.example.interfaces.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by ZhangZongxiang on 2017/5/31.
 */

public class MarkerPopupWindowView extends PopupWindow {

    private BaiduMap mBaiduMap;

    private Activity activity;

    public static View popupView;

    private ImageButton closeButton;

    private TextView nameText;

    private String placeName;

    private FloatingActionButton floatingActionButton;

    private PopWindowRecyclerAdapter adapter;

    private View lastView;

    private List<HashMap<String, String>> pictureList = new ArrayList<HashMap<String, String>>();

    private RecyclerView galleryRecycler;
    public MarkerPopupWindowView(Context context, BaiduMap mBaiduMap, List<HashMap<String, String>> pictureList,
                                 String placeName) {
        super(((Activity)context));
        this.mBaiduMap = mBaiduMap;
        this.activity = (Activity) context;
        this.placeName = placeName;
        this.pictureList = pictureList;
        popupView = LayoutInflater.from(activity).inflate(R.layout.popup_window_practice, null);
        closeButton = (ImageButton) popupView.findViewById(R.id.id_close_popup);
        nameText = (TextView) popupView.findViewById(R.id.id_marker_name);
        galleryRecycler = (RecyclerView) popupView.findViewById(R.id.pop_window_recycler);
        floatingActionButton = (FloatingActionButton) popupView.findViewById(R.id.pop_window_floating);
        floatingActionButton.setVisibility(View.GONE);
        /**closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });*/
        initViews();
        this.setContentView(popupView);//
        this.setAnimationStyle(R.style.popup_windows_anim);//
        this.setWidth(((Activity) context).getWindowManager().getDefaultDisplay().getWidth());
        this.setHeight(((Activity)context).getWindowManager().getDefaultDisplay().getHeight()/2 - 300);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //this.update();
        ColorDrawable dw = new ColorDrawable(0);
        this.setBackgroundDrawable(dw);

    }

    private void initViews() {
        adapter = new PopWindowRecyclerAdapter(activity, pictureList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        galleryRecycler.setLayoutManager(layoutManager);
        galleryRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                choosePhoto(view, position);
            }
        });
        nameText.setText(placeName);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void choosePhoto(View view, int position) {
        if (lastView != null) {
            lastView.findViewById(R.id.pop_window_item_selected_tag).setVisibility(View.GONE);
        }
        view.findViewById(R.id.pop_window_item_selected_tag).setVisibility(View.VISIBLE);
        floatingActionButton.setVisibility(View.VISIBLE);
        lastView = view;
    }
}
