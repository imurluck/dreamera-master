package com.example.dreamera_master;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.utils.HttpUtil;
import com.example.utils.ParseJSON;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class GetFragment extends Fragment {

    private final String PLACEURL = "http://www.dreamera.net/cross/place/";

    private ListView placesListView;

    private List<String> placesList = new ArrayList<String>();
    public GetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_get, container, false);
        placesListView = (ListView) view.findViewById(R.id.place_list);
        getPlaces(PLACEURL);
        //placesListView.setAdapter(new ArrayAdapter(MyApplication.getContext(),
         //       android.R.layout.simple_list_item_1, placesList ));
        for (int i = 0; i < placesList.size(); i ++) {
            Log.d("GetFragment", placesList.get(i));
        }
        return view;
    }

    private void getPlaces(String placeUrl) {
        HttpUtil.getPlaces(placeUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                placesList = ParseJSON.parseJSONForPlace(jsonData);
                /**for (int i = 0; i < placesList.size(); i ++) {
                    Log.d("GetFragment", placesList.get(i));
                }*/
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        placesListView.setAdapter(new ArrayAdapter(MyApplication.getContext(),
                                android.R.layout.simple_list_item_1, placesList ));
                    }
                });
            }
        });
    }
}
