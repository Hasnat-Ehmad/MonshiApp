package info.androidhive.materialdesign.pagerfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity.scrollView_BusinessPublicDetail_Activity;

public class BusinessInfoFragment extends Fragment {

    SharedPreferences sharedPreferences;

    ImageView map;

    MapView mMapView;
    private GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_business_info, container, false);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


//        final String url = "http://maps.googleapis.com/maps/api/staticmap?zoom=18&key=AIzaSyA3jIE7ZC2PWEPN39exyeFoX4yUrzpxVhI&size=560x550&markers=size:mid|color:red|"
//                + ""+sharedPreferences.getString("selected_lat", "")
//                + ","
//                + ""+sharedPreferences.getString("selected_lng", "")
//                + "&sensor=false";
//        map = rootView.findViewById(R.id.map);
//        Picasso.with(map.getContext()).load(""+url).into(map);
//        System.out.println("url = "+url);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately


        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);
                //googleMap.getUiSettings().setMapToolbarEnabled(false);

                googleMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener(){
                    @Override
                    public boolean onMyLocationButtonClick()
                    {
                        //TODO: Any custom actions

                        googleMap.clear();

                        //googleMap.getUiSettings().setMapToolbarEnabled(false);

                        LatLng position = new LatLng( Float.parseFloat(sharedPreferences.getString("selected_lat", "")), Float.parseFloat(sharedPreferences.getString("selected_lng", "")));
                        googleMap.addMarker(new MarkerOptions().position(position).title(""+sharedPreferences.getString("selected_name", ""))
                                .snippet(""+sharedPreferences.getString("selected_description", "")));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(12).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                        //Toast.makeText(getActivity(),"google",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                //googleMap.getUiSettings().setZoomGesturesEnabled(false);

                googleMap.getUiSettings().setMapToolbarEnabled(true);


//                Rect rect = new Rect();
//                mMapView.getLocalVisibleRect(rect);
//                googleMap.setPadding(rect.width(), 0, 0, rect.height());
//
                // For dropping a marker at a point on the Map
                LatLng position = new LatLng( Float.parseFloat(sharedPreferences.getString("selected_lat", "")), Float.parseFloat(sharedPreferences.getString("selected_lng", "")));
                googleMap.addMarker(new MarkerOptions().position(position).title(""+sharedPreferences.getString("selected_name", ""))
                                                        .snippet(""+sharedPreferences.getString("selected_description", "")));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        //Toast.makeText(getActivity(),rootView.getHeight()+" Info",Toast.LENGTH_SHORT).show();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
