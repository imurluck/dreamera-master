package com.example.dreamera_master;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.adapter.MyPlaceRecyclerAdapter;
import com.example.utils.HttpUtil;
import com.example.utils.ParseJSON;
import com.example.utils.Place;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MyPlaceActivity extends AppCompatActivity {

    private MyPlaceRecyclerAdapter adapter;

    private RecyclerView recyclerView;

    private String placeId = null;

    private String placeName = null;

    private String fromWhere = null;

    private Toolbar myPlaceToolbar = null;

    private FloatingActionButton addPictureButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_place);
        myPlaceToolbar = (Toolbar) findViewById(R.id.my_place_toolbar);
        addPictureButton = (FloatingActionButton) findViewById(R.id.add_picture_floating_button);
        TextView titleText = (TextView) findViewById(R.id.my_place_title);
        Intent intent = getIntent();
        placeName = intent.getStringExtra("placeName");
        myPlaceToolbar.setTitle("");
        titleText.setText(placeName);
        setSupportActionBar(myPlaceToolbar);
        Log.d("MyPlaceActivity", placeName);
        placeId = getPlaceIdFromPlaceName(placeName);
        Log.d("MyPlaceActivity", placeId);
        recyclerView = (RecyclerView) findViewById(R.id.my_place_recycler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setVisibility(View.GONE);
        loadingPictures(placeId);
        addPicture();
    }

    private void loadingPictures(String placeId) {
        HttpUtil.getConCretePlace(placeId, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseContent = response.body().string();
                final Place concretePlace = ParseJSON.handleJSONForConcretePlace(responseContent);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView pictureNullText = (TextView) findViewById(R.id.picture_null_text);
                        TextView noPicturesText = (TextView) findViewById(R.id.no_pictures_text);
                        if (concretePlace.getPicturesList().size() >= 1) {
                            pictureNullText.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new MyPlaceRecyclerAdapter(concretePlace.getPicturesList()));
                        } else {
                            pictureNullText.setVisibility(View.GONE);
                            noPicturesText.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    private String getPlaceIdFromPlaceName(String placeName) {
        SharedPreferences prefs = getSharedPreferences("places", MODE_PRIVATE);
        return prefs.getString(placeName, "");
    }
    private void addPicture() {
        addPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPlaceActivity.this, AddPictureActivity.class);
                intent.putExtra("placeName", placeName);
                intent.putExtra("placeId", placeId);
                startActivity(intent);
            }
        });
    }
}
