package com.example.dreamera_master;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import java.util.Calendar;

//import com.baidu.mapapi.search.geocode.GeoCodeResult;
//import com.baidu.mapapi.search.geocode.GeoCoder;
//import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
//import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment implements View.OnClickListener {

    private Button chooseDatetime;

    private TextView datetimeText;

    private Button addPicture;

    private ImageView showImage;

    private String imagePath;

    private final int ADD_PICTURE = 1;

    public LocationClient mLocationClient;

    private MapView mapView;

    private BaiduMap baiduMap;

    private boolean isFirstLocate = true;

    private double tagPositionLatitude = 0;

    private double tagPositionLongitude = 0;

    private Marker marker = null;

    private InfoWindow mInfoWindow = null;

    private BMapManager bMapManager;

    private String address = null;

    public PostFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post, container, false);
        /**chooseDatetime = (Button) view.findViewById(R.id.choose_datatime);
        datetimeText = (TextView) view.findViewById(R.id.datatime_text);
        addPicture = (Button) view.findViewById(R.id.add_picture);
        showImage = (ImageView) view.findViewById(R.id.show_image);*/
        mapView = (MapView) view.findViewById(R.id.bd_map_view);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        //chooseDatetime.setOnClickListener(this);
        //addPicture.setOnClickListener(this);
        ///mLocationClient = new LocationClient(getActivity().getApplication());
        ///mLocationClient.registerLocationListener(new MyLocationListener());
        //requestLocation();
        tagMap();
        //mLocationClient.registerLocationListener();
        return view;
    }

    private void tagMap() {
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                tagPositionLatitude = latLng.latitude;
                tagPositionLongitude = latLng.longitude;
                Log.d("postFragment", latLng.toString());
                if (marker != null) {
                    marker.remove();
                }
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.tag_icon);
                OverlayOptions option = new MarkerOptions()
                        .position(latLng)
                        .icon(bitmap);
                marker = (Marker) (baiduMap.addOverlay(option));
                getAddress(latLng);
            }
            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    private void getAddress(final LatLng latLng) {
        GeoCoder mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                address = reverseGeoCodeResult.getAddress();
                TextView textView = new TextView(MyApplication.getContext());
                textView.setBackgroundColor(Color.WHITE);
                Log.d("address", address);
                textView.setText(address);
                mInfoWindow = new InfoWindow(textView, latLng, 80);
                baiduMap.showInfoWindow(mInfoWindow);
            }
        });
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
    }
    private void requestLocation() {
        initLocation();
        mLocationClient.start();
    }
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }

    private void navigateTo(BDLocation location) {
        if (isFirstLocate) {
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(18f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        baiduMap.setMyLocationData(locationData);
    }

    public class MyLocationListener implements BDLocationListener {

        public void onReceiveLocation(BDLocation location) {
            Log.d("PostFragment", "have not get in ");
            if (location.getLocType() == BDLocation.TypeGpsLocation
                || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                navigateTo(location);
                Log.d("PostFragment", "have been in here");
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
    }

    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();
        /**switch (v.getId()) {
        case R.id.choose_datatime:
            new DatePickerDialog(getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            String info = year + "-" + ++month + "-" + day;
                            datetimeText.setText(info);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
            break;
        //Toast.makeText(getActivity(), "Choose datetime is clicked",
        //       Toast.LENGTH_SHORT).show();
        case R.id.add_picture:
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new
                        String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                openAlbum();
            }
        default:
    }*/
}

    /**private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, ADD_PICTURE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ADD_PICTURE:
                displayImage(HandleImagePath.handleImagePath(data));
                }
        }
    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            showImage.setImageBitmap(bitmap);
        } else {
            Toast.makeText(getActivity(), "failed to get image",
                    Toast.LENGTH_SHORT).show();
        }
    }*/
}
