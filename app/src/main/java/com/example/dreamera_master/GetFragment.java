package com.example.dreamera_master;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utils.HttpUtil;
import com.example.utils.ParseJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetFragment extends Fragment {

    private ListView placesListView;

    private TextView nullText = null;

    private String placeName = null;

    private String placeId = null;

    private ArrayAdapter adapter;

    private List<String> placesList = new ArrayList<String>();
    public GetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get, container, false);
        placesListView = (ListView) view.findViewById(R.id.place_list);
        nullText = (TextView) view.findViewById(R.id.null_text);
        getPlaces();
        placesListView.setEmptyView(nullText);
        handleListView();
        return view;
    }

    private void getPlaces() {
        HttpUtil.getPlaces(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                placesList = ParseJSON.handleJSONForPlaces(jsonData);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ArrayAdapter(MyApplication.getContext(),
                                android.R.layout.simple_list_item_1, placesList );
                        placesListView.setAdapter(adapter);
                    }
                });
            }
        });
    }

    private void handleListView() {
        placesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentString = placesList.get(position);
                Intent intent = new Intent(getActivity(), MyPlaceActivity.class);
                intent.putExtra("placeName", currentString);
                startActivity(intent);
            }
        });
        placesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String currentString = placesList.get(position);
                final int currentPosition = position;
                SharedPreferences prefs = getActivity().getSharedPreferences("places", MODE_PRIVATE);
                placeId = prefs.getString(currentString, "");
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Delete this Place?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                HttpUtil.deletePlace(placeId, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        placesList.remove(currentPosition);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                });
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
                return true;
            }
        });
    }
}
