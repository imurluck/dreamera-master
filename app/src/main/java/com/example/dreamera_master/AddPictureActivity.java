package com.example.dreamera_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class AddPictureActivity extends AppCompatActivity {

    private Toolbar addPictureToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_picture);
        EditText title = (EditText) findViewById(R.id.picture_title);
        EditText timeStr = (EditText) findViewById(R.id.picture_time_str);
        EditText detailUrl = (EditText) findViewById(R.id.picture_detail_url);
        EditText detailTitle = (EditText) findViewById(R.id.picture_detail_title);
        EditText likeCount = (EditText) findViewById(R.id.picture_like_count);
        EditText longitude = (EditText) findViewById(R.id.picture_longitude);
        EditText latitude = (EditText) findViewById(R.id.picture_latitude);
        EditText altitude = (EditText) findViewById(R.id.picture_altitude);
        addPictureToolbar = (Toolbar) findViewById(R.id.add_picture_toolbar);
        addPictureToolbar.setTitle("");
        setSupportActionBar(addPictureToolbar);
    }
}
