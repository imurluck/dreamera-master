package com.example.dreamera_master;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private DrawerLayout mDrawerLayout;

    private NavigationView mNavigationView;

    private CircleImageView profilePhoto;

    private TextView userName;

    private TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        Log.d("MainActivity", "actionbar is empty");
        if (actionBar != null) {
            Log.d("MainActivity", "actionBar is not empty");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        View headerView = mNavigationView.inflateHeaderView(R.layout.nav_header);
        //setNavigationHeaderListener();
        setNavigationMenuListener();
    }

    private void setNavigationHeaderListener(){
        profilePhoto = (CircleImageView) findViewById(R.id.nav_profile_photo);
        userName = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        profilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "set profile photo----uncompelete",
                        Toast.LENGTH_SHORT).show();
            }
        });
        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "set userName----uncompelete",
                        Toast.LENGTH_SHORT).show();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "set email----uncompelete",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNavigationMenuListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_call:
                        Toast.makeText(MainActivity.this, "set Call----uncompelete",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_email:
                        Toast.makeText(MainActivity.this, "set email----uncompelete",
                                Toast.LENGTH_SHORT).show();
                    case R.id.nav_login_out:
                        Toast.makeText(MainActivity.this, "login out----uncompelete",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_exit_application:
                        Toast.makeText(MainActivity.this, "exit application----uncompelete",
                                Toast.LENGTH_SHORT).show();
                        break;
                    default:
                }
                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login_out:
                Toast.makeText(this, "Login out----uncompelete", Toast
                .LENGTH_SHORT).show();
                break;
            case R.id.exit_application:
                Toast.makeText(this, "Exit application----uncompelete", Toast
                .LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
     }
}
