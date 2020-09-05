package info.androidhive.materialdesign.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hasnat.imagecropper.CropIntent;
import com.squareup.picasso.Picasso;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.Monshi_Customers_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.Monshi_Customers_List;
import info.androidhive.materialdesign.webservice.JSONParser_Add_Business;
import info.androidhive.materialdesign.webservice.JSONParser_Add_Business_Single;
import info.androidhive.materialdesign.webservice.JSONParser_Staff_Profile;
import info.androidhive.materialdesign.webservice.JSONParser_Update_Business;
import info.androidhive.materialdesign.webservice.JSONParser_Update_Business_Single;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;
import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.persianUrlEncoder;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;


public class CreateBusinessAccountFragment extends Fragment {
    SharedPreferences sharedPreferences;
    Spinner spinner_business_state,spinner_business_city,
            spinner_business_category,spinner_business_sub_category;

    ToggleButton toggle_saturday,toggle_sunday,toggle_monday,toggle_tuesday,
                    toggle_wednesday,toggle_thursday,toggle_friday;

    Spinner spinner_saturday_from,spinner_sunday_from,spinner_monday_from,
            spinner_tuesday_from,spinner_wednesday_from,spinner_thursday_from,
            spinner_friday_from;

    Spinner spinner_saturday_to,spinner_sunday_to,spinner_monday_to,spinner_tuesday_to,
            spinner_wednesday_to,spinner_thursday_to,spinner_friday_to;

    EditText ed_business_name,ed_business_id,ed_business_contact,ed_business_address,ed_business_description;

    Button btn_submit,btn_profile_pic;
    int profilepic=0;

    String state_value,city_value,business_value,business_sub_value;

    //ImageView img_profile_pic;
    Boolean map_check = false;

    int api_call_check;

    String [] from_value,to_value;
    String [] from_value_int,to_value_int;

    LinearLayout result_layout;
    LinearLayout result_layout_download;

    Button btn_add_main_pic;

    ImageView img_main_image;


    JSONParser_Add_Business jsonParser_add_business;
    JSONParser_Add_Business_Single jsonParser_add_business_single;
    JSONParser_Update_Business_Single jsonParser_update_business_single;
    JSONParser_Update_Business jsonParser_update_business;

    String image_real_path="";
    ProgressBar simpleProgressBar;

    Context context;

    private ArrayList<String> mResults = new ArrayList<>();
    private static final int REQUEST_CODE = 732;

    boolean business_id_checker=false;

    MapView mMapView;
    GoogleMap googleMap;

   int image_counter=5;

    TextView tv_link;

    String temp_city = "";
    String temp_state = "";
    String longitude,latitude;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.fragment_create_business_account, container, false);

        tv_title.setVisibility(View.GONE);

        ConstraintLayout main_cons_layout = rootView.findViewById(R.id.main_cons_layout);

        tv_link = rootView.findViewById(R.id.tv_link);

        context = getActivity();

        Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        simpleProgressBar = rootView.findViewById(R.id.simpleProgressBar);
        simpleProgressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.GONE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);

        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);

        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        result_layout = rootView.findViewById(R.id.result_layout);
        result_layout_download = rootView.findViewById(R.id.result_layout_download);

        jsonParser_add_business = new JSONParser_Add_Business();
        jsonParser_add_business_single = new JSONParser_Add_Business_Single();
        jsonParser_update_business_single = new JSONParser_Update_Business_Single();
        jsonParser_update_business = new JSONParser_Update_Business();

        spinner_business_state = (Spinner) rootView.findViewById(R.id.spinner_business_state);
        spinner_business_city  = (Spinner) rootView.findViewById(R.id.spinner_business_city);
        spinner_business_category  = (Spinner) rootView.findViewById(R.id.spinner_business_category);
        spinner_business_sub_category  = (Spinner) rootView.findViewById(R.id.spinner_business_sub_category);

        toggle_saturday = (ToggleButton) rootView.findViewById(R.id.toggle_saturday);
        toggle_sunday   = (ToggleButton) rootView.findViewById(R.id.toggle_sunday);
        toggle_monday   = (ToggleButton) rootView.findViewById(R.id.toggle_monday);
        toggle_tuesday  = (ToggleButton) rootView.findViewById(R.id.toggle_tuesday);
        toggle_wednesday= (ToggleButton) rootView.findViewById(R.id.toggle_wednesday);
        toggle_thursday = (ToggleButton) rootView.findViewById(R.id.toggle_thursday);
        toggle_friday   = (ToggleButton) rootView.findViewById(R.id.toggle_friday);

        spinner_saturday_from = (Spinner)rootView.findViewById(R.id.spinner_saturday_from);
        spinner_saturday_from.setEnabled(false);
        spinner_saturday_from.setSelection(0);

        spinner_sunday_from   = (Spinner)rootView.findViewById(R.id.spinner_sunday_from);
        spinner_sunday_from.setEnabled(false);
        spinner_sunday_from.setSelection(0);

        spinner_monday_from   = (Spinner)rootView.findViewById(R.id.spinner_monday_from);
        spinner_monday_from.setEnabled(false);
        spinner_monday_from.setSelection(0);

        spinner_tuesday_from  = (Spinner)rootView.findViewById(R.id.spinner_tuesday_from);
        spinner_tuesday_from.setEnabled(false);
        spinner_tuesday_from.setSelection(0);

        spinner_wednesday_from= (Spinner)rootView.findViewById(R.id.spinner_wednesday_from);
        spinner_wednesday_from.setEnabled(false);
        spinner_wednesday_from.setSelection(0);

        spinner_thursday_from = (Spinner)rootView.findViewById(R.id.spinner_thursday_from);
        spinner_thursday_from.setEnabled(false);
        spinner_thursday_from.setSelection(0);

        spinner_friday_from   = (Spinner)rootView.findViewById(R.id.spinner_friday_from);
        spinner_friday_from.setEnabled(false);
        spinner_friday_from.setSelection(0);

        spinner_saturday_to = (Spinner)rootView.findViewById(R.id.spinner_saturday_to);
        spinner_saturday_to.setEnabled(false);
        spinner_saturday_to.setSelection(12);

        spinner_sunday_to   = (Spinner)rootView.findViewById(R.id.spinner_sunday_to);
        spinner_sunday_to.setEnabled(false);
        spinner_sunday_to.setSelection(12);

        spinner_monday_to   = (Spinner)rootView.findViewById(R.id.spinner_monday_to);
        spinner_monday_to.setEnabled(false);
        spinner_monday_to.setSelection(12);

        spinner_tuesday_to  = (Spinner)rootView.findViewById(R.id.spinner_tuesday_to);
        spinner_tuesday_to.setEnabled(false);
        spinner_tuesday_to.setSelection(12);

        spinner_wednesday_to= (Spinner)rootView.findViewById(R.id.spinner_wednesday_to);
        spinner_wednesday_to.setEnabled(false);
        spinner_wednesday_to.setSelection(12);

        spinner_thursday_to = (Spinner)rootView.findViewById(R.id.spinner_thursday_to);
        spinner_thursday_to.setEnabled(false);
        spinner_thursday_to.setSelection(12);

        spinner_friday_to   = (Spinner)rootView.findViewById(R.id.spinner_friday_to);
        spinner_friday_to.setEnabled(false);
        spinner_friday_to.setSelection(12);


        toggle_saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    spinner_saturday_from.setEnabled(true);
                    spinner_saturday_to.setEnabled(true);
                }else{
                    spinner_saturday_from.setEnabled(false);
                    spinner_saturday_to.setEnabled(false);
                }
            }
        });
        toggle_sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    spinner_sunday_from.setEnabled(true);
                    spinner_sunday_to.setEnabled(true);
                }else{
                    spinner_sunday_from.setEnabled(false);
                    spinner_sunday_to.setEnabled(false);
                }
            }
        });
        toggle_monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    spinner_monday_from.setEnabled(true);
                    spinner_monday_to.setEnabled(true);
                }else{
                    spinner_monday_from.setEnabled(false);
                    spinner_monday_to.setEnabled(false);
                }
            }
        });
        toggle_tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    spinner_tuesday_from.setEnabled(true);
                    spinner_tuesday_to.setEnabled(true);
                }else{
                    spinner_tuesday_from.setEnabled(false);
                    spinner_tuesday_to.setEnabled(false);
                }
            }
        });
        toggle_wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    spinner_wednesday_from.setEnabled(true);
                    spinner_wednesday_to.setEnabled(true);
                }else{
                    spinner_wednesday_from.setEnabled(false);
                    spinner_wednesday_to.setEnabled(false);
                }
            }
        });
        toggle_thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    spinner_thursday_from.setEnabled(true);
                    spinner_thursday_to.setEnabled(true);
                }else{
                    spinner_thursday_from.setEnabled(false);
                    spinner_thursday_to.setEnabled(false);
                }
            }
        });
        toggle_friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if(isChecked){
                    spinner_friday_from.setEnabled(true);
                    spinner_friday_to.setEnabled(true);
                }else{
                    spinner_friday_from.setEnabled(false);
                    spinner_friday_to.setEnabled(false);
                }
            }
        });

        ed_business_name       = (EditText) rootView.findViewById(R.id.ed_business_name);
        ed_business_id         = (EditText) rootView.findViewById(R.id.ed_business_id);
        ed_business_contact    = (EditText) rootView.findViewById(R.id.ed_business_contact);
        ed_business_address    = (EditText) rootView.findViewById(R.id.ed_business_address);
        ed_business_description= (EditText) rootView.findViewById(R.id.ed_business_description);

        img_main_image = rootView.findViewById(R.id.img_main_image);

        btn_add_main_pic = rootView.findViewById(R.id.btn_add_main_pic);
        btn_add_main_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button_press=1;

                if (checkPermissionForReadExtertalStorage()){

                    // 1. on Upload click call ACTION_GET_CONTENT intent
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    // 2. pick image only
                    intent.setType("image/*");
                    // 3. start activity
                    startActivityForResult(intent, 0);

                }else {
                    try {
                        requestPermissionForReadExtertalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        btn_profile_pic = (Button) rootView.findViewById(R.id.btn_profile_pic);


        mMapView = (MapView) rootView.findViewById(R.id.mapView);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume(); // needed to get the map to display immediately



        ed_business_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

//http://192.168.100.14/monshiapp/app/monshiapp_customer.php?name=a
                String url    =  getResources().getString(R.string.url)+"business_id_check.php";
                String params =  "busId="+persianUrlEncoder(ed_business_id.getText().toString())+"&temp=abc";

                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {

                                String status_alert = jsonObject.getString("status");
                                String business_link = jsonObject.getString("business_link");
                                tv_link.setText(""+business_link);
                                tv_link.setTextColor(getActivity().getResources().getColor(R.color.green));

                                business_id_checker = true;

                                //Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.str_valid_business_id),Toast.LENGTH_SHORT).show();

                            }
                            else{
                                tv_link.setTextColor(getActivity().getResources().getColor(R.color.red));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                webRequestCall.execute(url, "POST", params);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (getArguments() != null) {

            api_call_check=1;

            String business_id = getArguments().getString("business_id");
            temp_state = getArguments().getString("state");
            temp_city = getArguments().getString("city");
            latitude = ""+getLocationFromAddress(getActivity(), temp_city).latitude;
            longitude = ""+getLocationFromAddress(getActivity(), temp_city).longitude;



            //http://192.168.100.14/monshiapp/app/business_detail.php?user_id=2&business_id=1
            String url    =  getResources().getString(R.string.url)+"business_detail.php";

            String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                             "&business_id="+business_id;;

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getString("status").equals("200")) {

                            String id = jsonObject.getString("id");
                            String name = jsonObject.getString("name");
                            String business_id = jsonObject.getString("business_id");
                            ed_business_id.setText(""+business_id);
                            business_id_checker=true;

                            ed_business_name.setText(""+name);

                            String category_business_id = jsonObject.getString("category");

                            if (business_category==null){

                                spinner_business_category(category_business_id,"yes");

                            }else {
                                for (int z=0;z<business_category.length;z++){
                                    if (category_business_id.equals(business_category_id[z])){
                                        spinner_business_category.setSelection((z));
                                    }
                                }
                            }


                            String category_name_per = jsonObject.getString("category_name_per");
                            String category_name = jsonObject.getString("category_name");

                            String sub_category_business_id = jsonObject.getString("sub_category");

                            if (business_sub_category==null){

                                spinner_business_sub_category(category_business_id,sub_category_business_id,"yes");

                            }else {
                                for (int z=0;z<business_sub_category.length;z++){
                                    if (sub_category_business_id.equals(business_sub_category_id[z])){
                                        spinner_business_category.setSelection((z));
                                    }
                                }
                            }


                            String sub_category_name_per= jsonObject.getString("sub_category_name_per");
                            String sub_category_name    = jsonObject.getString("sub_category_name");

                            String business_image = jsonObject.getString("business_image");
                            Picasso.with(img_main_image.getContext()).load(""+business_image).into(img_main_image);

                            String state_business_id = jsonObject.getString("state");

                            if (states_id==null){
                                states(state_business_id,"yes");

                            }else {
                                for (int z=0;z<states_id.length;z++){
                                    if (state_business_id.equals(states_id[z])){
                                        spinner_business_state.setSelection(z);
                                    }
                                }
                            }


                            String state_name = jsonObject.getString("state_name");
                            String state_name_per = jsonObject.getString("state_name_per");

                            String city_business_id   = jsonObject.getString("city");

                            if (city_id==null){

                                cities(state_business_id,city_business_id,"yes");

                            }else {
                                for (int z=0;z<city_id.length;z++){
                                    if (city_business_id.equals(city_id[z])){
//                                        spinner_business_city.setSelection(Integer.parseInt(cities[z]));
                                    }
                                }
                            }

                            String city_name = jsonObject.getString("city_name");
                            String city_name_per = jsonObject.getString("city_name_per");
                            String rating = jsonObject.getString("rating");
                            String description = jsonObject.getString("description");
                            ed_business_description.setText(""+description);

                            String contact = jsonObject.getString("contact");
                            ed_business_contact.setText(""+contact);

                            String address = jsonObject.getString("address");
                            ed_business_address.setText(""+address);

                            String is_fav  = jsonObject.getString ("is_fav");
                            String business_type = jsonObject.getString("business_type");

                            System.out.println("business_type = "+business_type);

                            JSONArray business_timing = jsonObject.getJSONArray("business_timing");

                            for(int i = 0; i < business_timing.length(); i++) {
                                JSONObject c = business_timing.getJSONObject(i);
                                String is_selected      = c.getString("is_selected");
                                String operating_day_per= c.getString("operating_day_per");
                                String operating_day = c.getString("operating_day");
                                String from_time     = c.getString("from_time");
                                String to_time       = c.getString("to_time");
                                String from_time_per = c.getString("from_time_per");
                                String to_time_per   = c.getString("to_time_per");
                                String final_time_per= c.getString("final_time_per");
                                String final_time    = c.getString("final_time");

                                System.out.println("final_time = "+final_time);
                                if (is_selected.equals("1")){
                                    if (i==0){
                                        toggle_saturday.setChecked(true);
                                        spinner_saturday_from.setEnabled(true);
                                        spinner_saturday_to.setEnabled(true);
                                    }
                                    if (i==1){
                                        toggle_sunday.setChecked(true);
                                        spinner_sunday_from.setEnabled(true);
                                        spinner_sunday_to.setEnabled(true);
                                    }
                                    if (i==2){
                                        toggle_monday.setChecked(true);
                                        spinner_monday_from.setEnabled(true);
                                        spinner_monday_to.setEnabled(true);
                                    }
                                    if (i==3){
                                        toggle_tuesday.setChecked(true);
                                        spinner_tuesday_from.setEnabled(true);
                                        spinner_tuesday_to.setEnabled(true);
                                    }
                                    if (i==4){
                                        toggle_wednesday.setChecked(true);
                                        spinner_wednesday_from.setEnabled(true);
                                        spinner_wednesday_to.setEnabled(true);
                                    }
                                    if (i==5){
                                        toggle_thursday.setChecked(true);
                                        spinner_thursday_from.setEnabled(true);
                                        spinner_thursday_to.setEnabled(true);
                                    }
                                    if (i==6){
                                        toggle_friday.setChecked(true);
                                        spinner_friday_from.setEnabled(true);
                                        spinner_friday_to.setEnabled(true);
                                    }
                                }
                            }

                            JSONArray business_days_array = jsonObject.getJSONArray("business_days_array");

                            for(int i = 0; i < business_days_array.length(); i++) {
                                JSONObject c = business_days_array.getJSONObject(i);
                                String day_string_per = c.getString("day_string_per");
                                String day_string= c.getString("day_string");
                                String status = c.getString("status");

                                System.out.println("status = "+status);

                                JSONArray from_time_arr = c.getJSONArray("from_time_arr");

                                from_value     = new String[from_time_arr.length()];
                                from_value_int = new String[from_time_arr.length()];

                                int from_index=0;

                                for(int j = 0; j < from_time_arr.length(); j++) {
                                    JSONObject d = from_time_arr.getJSONObject(j);

                                    String value      = d.getString("value");
                                    String value_in_per= d.getString("value_in_per");
                                    String status_from = d.getString("status");

                                    from_value_int[j] = value;
                                    from_value    [j] = value_in_per;

                                    if (status_from.equals("enable")){
                                        from_index=j;
                                    }

                                    System.out.println("status_from = "+status_from);
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, from_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                        View v =super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                        return v;
                                    }
                                };

                                if (i==0){
                                    spinner_saturday_from.setAdapter(adapter);
                                    spinner_saturday_from.setSelection(from_index);
                                }

                                if (i==1){
                                    spinner_sunday_from.setAdapter(adapter);
                                    spinner_sunday_from.setSelection(from_index);
                                }
                                if (i==2){
                                    spinner_monday_from.setAdapter(adapter);
                                    spinner_monday_from.setSelection(from_index);
                                }

                                if (i==3){
                                    spinner_tuesday_from.setAdapter(adapter);
                                    spinner_tuesday_from.setSelection(from_index);
                                }
                                if (i==4){
                                    spinner_wednesday_from.setAdapter(adapter);
                                    spinner_wednesday_from.setSelection(from_index);
                                }
                                if (i==5){
                                    spinner_thursday_from.setAdapter(adapter);
                                    spinner_thursday_from.setSelection(from_index);
                                }
                                if (i==6){
                                    spinner_friday_from.setAdapter(adapter);
                                    spinner_friday_from.setSelection(from_index);
                                }


                                JSONArray to_time_arr = c.getJSONArray("to_time_arr");

                                to_value = new String[to_time_arr.length()];
                                to_value_int = new String[to_time_arr.length()];

                                int to_index = 0;

                                for(int j = 0; j < to_time_arr.length(); j++) {
                                    JSONObject d = to_time_arr.getJSONObject(j);

                                    String value       = d.getString("value");
                                    String value_in_per= d.getString("value_in_per");
                                    String status_to   = d.getString("status");

                                    to_value[j] = value_in_per;
                                    to_value_int[j] =value;

                                    if (status_to.equals("enable")){
                                        to_index = j;
                                    }

                                    System.out.println("status_to = "+status_to);
                                }

                                if (i==0){
                                    spinner_saturday_to.setAdapter(adapter);
                                    spinner_saturday_to.setSelection(to_index);
                                }

                                if (i==1){
                                    spinner_sunday_to.setAdapter(adapter);
                                    spinner_sunday_to.setSelection(to_index);
                                }
                                if (i==2) {
                                    spinner_monday_to.setAdapter(adapter);
                                    spinner_monday_to.setSelection(to_index);
                                }

                                if (i==3){
                                    spinner_tuesday_to.setAdapter(adapter);
                                    spinner_tuesday_to.setSelection(to_index);
                                }

                                if (i==4){
                                    spinner_wednesday_to.setAdapter(adapter);
                                    spinner_wednesday_to.setSelection(to_index);
                                }
                                if (i==5){
                                    spinner_thursday_to.setAdapter(adapter);
                                    spinner_thursday_to.setSelection(to_index);
                                }
                                if (i==6){
                                    spinner_friday_to.setAdapter(adapter);
                                    spinner_friday_to.setSelection(to_index);
                                }

                            }

                        }
                        else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall.execute(url, "POST", params);

            //=================================================================

            //http://192.168.100.14/monshiapp/app/get_business_gallery_image.php?user_id=1&bus_id=28
            String url_image    =  getResources().getString(R.string.url)+"get_business_gallery_image.php";

            String params_image = "";
            if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                //spinner_business_list.setVisibility(View.GONE);
                params_image =  "user_id="+sharedPreferences.getString("user_id", "")+
                        "&bus_id="+sharedPreferences.getString("selected_business", "");
            }else {
                //spinner_business_list.setVisibility(View.VISIBLE);
                params_image =  "user_id="+sharedPreferences.getString("user_id", "")+
                        "&bus_id="+sharedPreferences.getString("business_id", "");
            }

            WebRequestCall webRequestCall_image = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getString("status").equals("200")) {
                            JSONArray business_image_list = jsonObject.getJSONArray("business_image_list");
                            image_counter = Integer.parseInt(jsonObject.getString("image_counter"));

                            for(int i = 0; i < business_image_list.length(); i++) {
                                JSONObject c = business_image_list.getJSONObject(i);

                                String id = c.getString("id");
                                String image= c.getString("image");

                                LayoutInflater factory = LayoutInflater.from(getActivity());

                                final View row_image_selection = factory.inflate(R.layout.row_image_selection, null);

                                final ConstraintLayout layout_main = row_image_selection.findViewById(R.id.layout_main);
                                final ImageView img_selected = row_image_selection.findViewById(R.id.img_selected);
                                final ImageView img_cross = row_image_selection.findViewById(R.id.img_cross);


                                img_selected.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });


                                img_cross.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //delete_gallery_image.php?parameter image_id
                                        String url    =  getResources().getString(R.string.url)+"delete_gallery_image.php";

                                        String params = "";
                                        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                                            //spinner_business_list.setVisibility(View.GONE);
                                            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                                                    "&image_id="+img_cross.getTag().toString();
                                        }else {
                                            //spinner_business_list.setVisibility(View.VISIBLE);
                                            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                                    "&bus_id="+sharedPreferences.getString("business_id", "")+
                                                    "&image_id="+img_cross.getTag().toString();;
                                        }

                                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                            @Override
                                            public void TaskCompletionResult(String result) {

                                                try {

                                                    JSONObject jsonObject = new JSONObject(result);
                                                    if(jsonObject.getString("status").equals("200")) {
                                                        image_counter = Integer.parseInt(jsonObject.getString("image_counter"));
                                                        Toast.makeText(getActivity(),""+jsonObject.getString("status_alert")
                                                                ,Toast.LENGTH_SHORT).show();

                                                        row_image_selection.setVisibility(View.GONE);

                                                    }
                                                    else{

                                                        Toast.makeText(getActivity(),""+jsonObject.getString("status_alert")
                                                                ,Toast.LENGTH_SHORT).show();

                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                        webRequestCall.execute(url, "POST", params);
                                    }
                                });


                                Picasso.with(img_selected.getContext()).load(""+image).into(img_selected);

                                result_layout_download.addView(row_image_selection);

                                img_cross.setTag(id);

                                btn_profile_pic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //startCropImage();
                                        // start multiple photos selector
                                        button_press=2;
                                        Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
                                        // max number of images to be selected
                                        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, image_counter);
                                        // min size of image which will be shown; to filter tiny images (mainly icons)
                                        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                                        // show camera or not
                                        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                                        // pass current selected images as the initial value
                                        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                                        // start the selector
                                        startActivityForResult(intent, REQUEST_CODE);

                                    }
                                });

                            }

                            if(jsonObject.getString("image_counter").equals("5")){
                                btn_profile_pic.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //startCropImage();
                                        // start multiple photos selector
                                        button_press=2;
                                        Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
                                        // max number of images to be selected
                                        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, image_counter);
                                        // min size of image which will be shown; to filter tiny images (mainly icons)
                                        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                                        // show camera or not
                                        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                                        // pass current selected images as the initial value
                                        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                                        // start the selector
                                        startActivityForResult(intent, REQUEST_CODE);

                                    }
                                });

                            }
                        }
                        else{
                            btn_profile_pic.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //startCropImage();
                                    // start multiple photos selector
                                    button_press=2;
                                    Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
                                    // max number of images to be selected
                                    intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, image_counter);
                                    // min size of image which will be shown; to filter tiny images (mainly icons)
                                    intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                                    // show camera or not
                                    intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                                    // pass current selected images as the initial value
                                    intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                                    // start the selector
                                    startActivityForResult(intent, REQUEST_CODE);

                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall_image.execute(url_image, "POST", params_image);



        }else {

            states("","");
            spinner_business_category("","");


            btn_profile_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //startCropImage();
                    // start multiple photos selector
                    button_press=2;
                    Intent intent = new Intent(getActivity(), ImagesSelectorActivity.class);
                    // max number of images to be selected
                    intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, image_counter);
                    // min size of image which will be shown; to filter tiny images (mainly icons)
                    intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000);
                    // show camera or not
                    intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                    // pass current selected images as the initial value
                    intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                    // start the selector
                    startActivityForResult(intent, REQUEST_CODE);

                }
            });


            //http://192.168.100.14/monshiapp/app/business_detail.php?user_id=2&business_id=1
            String url    =  getResources().getString(R.string.url)+"get_business_time_new.php";

            String params =  "";

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getString("status").equals("200")) {

                            JSONArray days_array = jsonObject.getJSONArray("days_array");

                            for(int i = 0; i < days_array.length(); i++) {
                                JSONObject c = days_array.getJSONObject(i);
                                String day_string_per = c.getString("day_string_per");
                                String day_string= c.getString("day_string");
                                String status = c.getString("status");

                                System.out.println("status = "+status);

                                JSONArray from_time_arr = c.getJSONArray("from_time_arr");

                                from_value     = new String[from_time_arr.length()];
                                from_value_int = new String[from_time_arr.length()];

                                int from_index=0;

                                for(int j = 0; j < from_time_arr.length(); j++) {
                                    JSONObject d = from_time_arr.getJSONObject(j);

                                    String value      = d.getString("value");
                                    String value_in_per= d.getString("value_in_per");
                                    String status_from = d.getString("status");

                                    from_value_int[j] = value;
                                    from_value    [j] = value_in_per;

                                    if (status_from.equals("enable")){
                                        from_index=j;
                                    }

                                    System.out.println("status_from = "+status_from);
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, from_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                        View v =super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                        return v;
                                    }
                                };

                                if (i==0){
                                    spinner_saturday_from.setAdapter(adapter);
                                    spinner_saturday_from.setSelection(from_index);
                                }

                                if (i==1){
                                    spinner_sunday_from.setAdapter(adapter);
                                    spinner_sunday_from.setSelection(from_index);
                                }
                                if (i==2){
                                    spinner_monday_from.setAdapter(adapter);
                                    spinner_monday_from.setSelection(from_index);
                                }

                                if (i==3){
                                    spinner_tuesday_from.setAdapter(adapter);
                                    spinner_tuesday_from.setSelection(from_index);
                                }
                                if (i==4){
                                    spinner_wednesday_from.setAdapter(adapter);
                                    spinner_wednesday_from.setSelection(from_index);
                                }
                                if (i==5){
                                    spinner_thursday_from.setAdapter(adapter);
                                    spinner_thursday_from.setSelection(from_index);
                                }
                                if (i==6){
                                    spinner_friday_from.setAdapter(adapter);
                                    spinner_friday_from.setSelection(from_index);
                                }


                                JSONArray to_time_arr = c.getJSONArray("to_time_arr");

                                to_value = new String[to_time_arr.length()];
                                to_value_int = new String[to_time_arr.length()];

                                int to_index = 0;

                                for(int j = 0; j < to_time_arr.length(); j++) {
                                    JSONObject d = to_time_arr.getJSONObject(j);

                                    String value       = d.getString("value");
                                    String value_in_per= d.getString("value_in_per");
                                    String status_to   = d.getString("status");

                                    to_value[j] = value_in_per;
                                    to_value_int[j] =value;

                                    if (status_to.equals("enable")){
                                        to_index = j;
                                    }

                                    System.out.println("status_to = "+status_to);
                                }

                                if (i==0){
                                    spinner_saturday_to.setAdapter(adapter);
                                    spinner_saturday_to.setSelection(to_index);
                                }

                                if (i==1){
                                    spinner_sunday_to.setAdapter(adapter);
                                    spinner_sunday_to.setSelection(to_index);
                                }
                                if (i==2) {
                                    spinner_monday_to.setAdapter(adapter);
                                    spinner_monday_to.setSelection(to_index);
                                }

                                if (i==3){
                                    spinner_tuesday_to.setAdapter(adapter);
                                    spinner_tuesday_to.setSelection(to_index);
                                }

                                if (i==4){
                                    spinner_wednesday_to.setAdapter(adapter);
                                    spinner_wednesday_to.setSelection(to_index);
                                }
                                if (i==5){
                                    spinner_thursday_to.setAdapter(adapter);
                                    spinner_thursday_to.setSelection(to_index);
                                }
                                if (i==6){
                                    spinner_friday_to.setAdapter(adapter);
                                    spinner_friday_to.setSelection(to_index);
                                }

                            }
                            simpleProgressBar.setVisibility(View.GONE);
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                        else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall.execute(url, "POST", params);

        }

        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String check_operating_day = "";
                String from="",to="";
                String day_on_off="";

                if (persianUrlEncoder(ed_business_name.getText().toString()).equals("")){
                    ed_business_name.setError("Empty");
                }else if (persianUrlEncoder(ed_business_id.getText().toString()).equals("")){
                    ed_business_id.setError("Empty");
                }else if (persianUrlEncoder(ed_business_contact.getText().toString()).equals("")){
                    ed_business_contact.setError("Empty");
                }else if (persianUrlEncoder(ed_business_address.getText().toString()).equals("")){
                    ed_business_address.setError("Empty");
                }else if((spinner_business_state != null && spinner_business_state.getSelectedItem() == null)|| spinner_business_state.getSelectedItem().toString().equals("دولت خود را انتخاب کنید")){
                    Toast.makeText(getActivity(),"Please select state",
                            Toast.LENGTH_LONG).show();
                }else if ((spinner_business_category != null && spinner_business_category.getSelectedItem() ==null) || spinner_business_category.getSelectedItem().toString().equals("دسته خود را انتخاب کنید")){
                    Toast.makeText(getActivity(),"Please select Category",
                            Toast.LENGTH_LONG).show();
                }else {

                    if (business_id_checker){

                        if (api_call_check==1){

                            if (toggle_saturday.isChecked()){
                                check_operating_day +="Saturday,";
                                from += from_value_int[spinner_saturday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_saturday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_saturday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_saturday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_sunday.isChecked()){
                                check_operating_day +="Sunday,";
                                from += from_value_int[spinner_sunday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_sunday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_sunday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_sunday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_monday.isChecked()){
                                check_operating_day +="Monday,";
                                from += from_value_int[spinner_monday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_monday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_monday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_monday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_tuesday.isChecked()){
                                check_operating_day +="Tuesday,";
                                from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_wednesday.isChecked()){
                                check_operating_day +="Wednesday,";
                                from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_thursday.isChecked()){
                                check_operating_day +="Thursday,";
                                from += from_value_int[spinner_thursday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_thursday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_thursday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_thursday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_friday.isChecked()){
                                check_operating_day +="Friday,";
                                from += from_value_int[spinner_friday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_friday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_friday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_friday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }

                            //http://192.168.100.14/monshiapp/app/update_business.php
                            String url    =  getResources().getString(R.string.url)+"update_business.php";
                            String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                                    "&business_title="+persianUrlEncoder(ed_business_name.getText().toString())+
                                    "&business_id="+persianUrlEncoder(ed_business_id.getText().toString())+
                                    "&business_type=service"+
                                    "&contact="+persianUrlEncoder(ed_business_contact.getText().toString())+
                                    "&address="+persianUrlEncoder(ed_business_address.getText().toString())+
                                    "&state="+state_value+
                                    "&city="+city_value+
                                    "&industry="+business_value+
                                    "&sub_cat="+business_sub_value+
                                    "&description="+persianUrlEncoder(ed_business_description.getText().toString())+
                                    "&user_name="+sharedPreferences.getString("fname", "")+
                                    "&all_operating_day=Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday"+
                                    "&check_operating_day="+removeLastChar(check_operating_day)+
                                    "&day_on_off="+removeLastChar(day_on_off)+
                                    "&from_hour="+removeLastChar(from)+
                                    "&to_hour="+removeLastChar(to);

                            if (profilepic==1){

                                //updating already created business
                                send_data_update(sharedPreferences.getString("user_id", ""),
                                        sharedPreferences.getString("selected_business", ""),
                                        persianUrlEncoder(ed_business_name.getText().toString()),
                                        persianUrlEncoder(ed_business_id.getText().toString()),
                                        "service",
                                        persianUrlEncoder(ed_business_contact.getText().toString()),
                                        persianUrlEncoder(ed_business_address.getText().toString()),
                                        state_value,
                                        city_value,
                                        business_value,
                                        business_sub_value,
                                        persianUrlEncoder(ed_business_description.getText().toString()),
                                        sharedPreferences.getString("fname", ""),
                                        "Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday",
                                        removeLastChar(check_operating_day),
                                        removeLastChar(day_on_off),
                                        removeLastChar(from),
                                        removeLastChar(to));
                            }else {

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {

                                                System.out.println("mResults = "+ mResults.toString());

                                                if (mResults.size()>0){

                                                    int count=0;
                                                    for(String result_image : mResults) {

                                                        count++;

                                                        //Toast.makeText(getActivity(),"button_loop = "+ result_image,Toast.LENGTH_SHORT).show();
                                                        //System.out.println("button_loop = "+ result_image);

                                                        image_real_path = result_image;

                                                        send_data_update(sharedPreferences.getString("user_id", ""),sharedPreferences.getString("selected_business", ""),count,result_image);
                                                    }
                                                }else {
                                                    getFragmentManager().popBackStack();
                                                }

                                            }
                                            else{
                                                Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);
                            }

                        }else {

                            if (toggle_saturday.isChecked()){
                                check_operating_day +="Saturday,";
                                from += from_value_int[spinner_saturday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_saturday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_saturday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_saturday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_sunday.isChecked()){
                                check_operating_day +="Sunday,";
                                from += from_value_int[spinner_sunday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_sunday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_sunday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_sunday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_monday.isChecked()){
                                check_operating_day +="Monday,";
                                from += from_value_int[spinner_monday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_monday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_monday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_monday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_tuesday.isChecked()){
                                check_operating_day +="Tuesday,";
                                from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_wednesday.isChecked()){
                                check_operating_day +="Wednesday,";
                                from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_thursday.isChecked()){
                                check_operating_day +="Thursday,";
                                from += from_value_int[spinner_thursday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_thursday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_thursday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_thursday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }
                            if (toggle_friday.isChecked()){
                                check_operating_day +="Friday,";
                                from += from_value_int[spinner_friday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_friday_to.getSelectedItemPosition()]+",";
                                day_on_off+="on,";
                            }else {
                                from += from_value_int[spinner_friday_from.getSelectedItemPosition()]+",";
                                to += to_value_int[spinner_friday_to.getSelectedItemPosition()]+",";
                                day_on_off+="off,";
                            }

                            //http://192.168.100.14/monshiapp/app/dashboard_activity_listing.php
                            String url    =  getResources().getString(R.string.url)+"add_business.php";
                            String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_title="+persianUrlEncoder(ed_business_name.getText().toString())+
                                    "&business_id="+persianUrlEncoder(ed_business_id.getText().toString())+
                                    "&business_type=service"+
                                    "&contact="+persianUrlEncoder(ed_business_contact.getText().toString())+
                                    "&address="+persianUrlEncoder(ed_business_address.getText().toString())+
                                    "&state="+state_value+
                                    "&city="+city_value+
                                    "&industry="+business_value+
                                    "&sub_cat="+business_sub_value+
                                    "&description="+persianUrlEncoder(ed_business_description.getText().toString())+
                                    "&user_name="+sharedPreferences.getString("fname", "")+
                                    "&all_operating_day=Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday"+
                                    "&check_operating_day="+removeLastChar(check_operating_day)+
                                    "&day_on_off="+removeLastChar(day_on_off)+
                                    "&from_hour="+removeLastChar(from)+
                                    "&to_hour="+removeLastChar(to);

                            if (profilepic==1){

                                //uploading single image here
                                send_data_single(sharedPreferences.getString("user_id", ""), persianUrlEncoder(ed_business_name.getText().toString()),
                                        persianUrlEncoder(ed_business_id.getText().toString()),
                                        "service",
                                        persianUrlEncoder(ed_business_contact.getText().toString()),
                                        persianUrlEncoder(ed_business_address.getText().toString()),
                                        state_value,
                                        city_value,
                                        business_value,
                                        business_sub_value,
                                        persianUrlEncoder(ed_business_description.getText().toString()),
                                        sharedPreferences.getString("fname", ""),
                                        "Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday",
                                        removeLastChar(check_operating_day),
                                        removeLastChar(day_on_off),
                                        removeLastChar(from),
                                        removeLastChar(to));
                            }else {

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {

                                                if (mResults.size()>0){

                                                    int count=0;
                                                    for(String result_image : mResults) {

                                                        count++;
                                                        // Toast.makeText(getActivity(),"button_loop = "+ result_image,Toast.LENGTH_SHORT).show();
                                                        image_real_path = result_image;
                                                        //creating new business gallery
                                                        send_data(sharedPreferences.getString("user_id", ""),""
                                                                +jsonObject.getString("business_id"),count,result_image);
                                                    }
                                                }else {

                                                    if (sharedPreferences.getString("user_role" ,""  ).equals("4")){

                                                        SavePreferences("user_role"    ,"1");//business created by the customer user_role change to 1

                                                        Intent intent = getActivity().getIntent();
                                                        getActivity().finish();
                                                        startActivity(intent);

                                                    }else {
                                                        getFragmentManager().popBackStack();
                                                    }
                                                }
                                            }
                                            else{
                                                Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);
                            }
                        }
                    }else {
                        Toast.makeText(getActivity(),"Business Id is invalid",Toast.LENGTH_SHORT).show();
                    }
                }
                }
        });

        final ScrollView scrollView_temp = rootView.findViewById(R.id.scroll_view);
        scrollView_temp.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollX = scrollView_temp.getScrollX();
                //Log.d(TAG, "scrollX: " + scrollX);
                //Toast.makeText(getActivity(),scrollView_temp.getScaleX()+""+scrollView_temp.getScaleY(),Toast.LENGTH_SHORT).show();

                if(ed_business_name.isFocused()){
                    ed_business_name.clearFocus();

                }else if(ed_business_id.isFocused()){
                    ed_business_id.clearFocus();

                }else if(ed_business_contact.isFocused()){
                    ed_business_contact.clearFocus();

                }else if(ed_business_address.isFocused()){
                    ed_business_address.clearFocus();

                }else if(ed_business_description.isFocused()){
                    //Toast.makeText(getActivity(),"Touch",Toast.LENGTH_SHORT).show();
                    ed_business_description.clearFocus();

                }
            }
        });

        if(!temp_state.equals("")){
            int SPLASH_TIME_OUT = 10000;
            final int[] i = {0};
            final Handler handler = new Handler();
            Runnable runnable1  = null;
            final Runnable finalRunnable = runnable1;
            runnable1 = new Runnable() {

                /*
                 * Showing splash screen with a timer. This will be useful when you
                 * want to show case your app logo / company
                 */

                @Override
                public void run() {
                    String lat = latitude;
                    String lng = longitude;

                    //Toast.makeText(getActivity(),temp_city,Toast.LENGTH_SHORT).show();
                    if (getActivity() != null){
                        if (!temp_city.equals("")) {
                            map_view(Double.parseDouble(latitude), Double.parseDouble(longitude));
                        } else {
                            map_view(getLocationFromAddress(getActivity(), temp_state).latitude, getLocationFromAddress(getActivity(), temp_state).longitude);
                        }
                     }

                    //map_view(Double.parseDouble(lat),Double.parseDouble(lng));

                    if(i[0] > 0){
                        handler.removeCallbacks(finalRunnable);
                    }
                    i[0]++;
                }
            };
            handler.postDelayed(runnable1, SPLASH_TIME_OUT);

        }
        //handler.sendEmptyMessageDelayed(1, 5000);


        return rootView;
    }

    int button_press;
    Uri[] uris;
    File[] allFiles;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (button_press==1){

            //Toast.makeText(getActivity(),"button_press = "+button_press,Toast.LENGTH_SHORT).show();

            if (resultCode == Activity.RESULT_OK && data != null) {
                String realPath;
                // SDK < API11
                if (Build.VERSION.SDK_INT < 11)
                    realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(getActivity(), data.getData());

                    // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19)
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(getActivity(), data.getData());

                    // SDK > 19 (Android 4.4)
                else
                    realPath = RealPathUtil.getRealPathFromURI_API19(getActivity(), data.getData());


                setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(), realPath);
                //Toast.makeText(getActivity(),"setTextViews = ",Toast.LENGTH_SHORT).show();
            }
        }

        if (button_press==2){

            try {
                if (resultCode == RESULT_OK) {

                    mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                    assert mResults != null;
                    (result_layout).removeAllViews();
                    // show results in textview
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.format("Totally %d images selected:", mResults.size())).append("\n");
                    int counter = 0;

                    allFiles = new File[mResults.size()];

                    for(String result : mResults) {

                        allFiles[counter]= new File(result);

                        sb.append(result).append("\n");

                        LayoutInflater factory = LayoutInflater.from(getActivity());

                        final View row_image_selection = factory.inflate(R.layout.row_image_selection, null);

                        final ConstraintLayout layout_main = row_image_selection.findViewById(R.id.layout_main);
                        final ImageView img_selected       = row_image_selection.findViewById(R.id.img_selected);
                        final ImageView img_cross          = row_image_selection.findViewById(R.id.img_cross);


                        img_selected.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                img_cross.setVisibility(View.VISIBLE);

                                return true;
                            }
                        });


                        img_cross.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                int position = (Integer) layout_main.getTag();
                                result_layout.removeView(row_image_selection);

                                for (int i = 0 ; i<result_layout.getChildCount() ; i++){

                                    ConstraintLayout layout_data = (ConstraintLayout) result_layout.getChildAt(i);

                                    ConstraintLayout layout_main = layout_data.findViewById(R.id.layout_main);
                                    // Toast.makeText(getActivity(),i+" layout_main = "+ layout_main.getTag(),Toast.LENGTH_SHORT).show();

                                    ImageView img_selected = layout_data.findViewById(R.id.img_selected);
                                    // Toast.makeText(getActivity(),i+" img_selected = "+ img_selected.getTag(),Toast.LENGTH_SHORT).show();

                                    ImageView img_cross    = layout_data.findViewById(R.id.img_cross);
                                    // Toast.makeText(getActivity(),i+" img_cross = "+img_cross.getTag(),Toast.LENGTH_SHORT).show();

                                    int layout_main_pos  = (Integer) layout_main.getTag();
                                    int img_selected_pos = (Integer) img_selected.getTag();
                                    int img_cross_pos    = (Integer) img_cross.getTag();

                                    if (img_selected_pos>=position){
                                        img_selected.setTag(--img_selected_pos);
                                        //Toast.makeText(getActivity(),"img_selected_loop_after = "+ img_selected.getTag(),Toast.LENGTH_SHORT).show();
                                    }

                                    if (img_cross_pos>=position){
                                        img_cross.setTag(--img_cross_pos);
                                        //Toast.makeText(getActivity(),"img_cross_loop_after = "+ img_cross.getTag(),Toast.LENGTH_SHORT).show();
                                    }

                                    if (layout_main_pos>=position){
                                        layout_main.setTag(--layout_main_pos);
                                        //Toast.makeText(getActivity(),"layout_main_loop_after = "+ img_cross.getTag(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                                mResults.remove(position);


                            }
                        });

                        Bitmap myBitmap = BitmapFactory.decodeFile(result);

                       // Toast.makeText(getActivity(),"imges = "+ result,Toast.LENGTH_SHORT).show();
                        counter++;


                        img_selected.setImageBitmap(myBitmap);

                        result_layout.addView(row_image_selection);
                        int position = result_layout.indexOfChild(row_image_selection);

                        layout_main.setTag(position);

                        img_selected.setTag(position);
                        img_cross   .setTag(position);

                    }

                }
            } catch (Exception e) {
                Log.e("FileSelectorActivity", "File select error", e);
            }

        }

        //super.onActivityResult(requestCode, resultCode, data);
    }


    //uploading single image here
    private void send_data_single(final String user_id, final String s, final String s1, final String s2, final String s3, final String s4, final String s5,
                                  final String s6,      final String s7, final String s8, final String s9, String fname, final String s10, final String s11,
                                  final String s12,     final String s13, final String s14) {
        final String[] new_business_id = {""};

        //
        new AsyncTask<Void, Integer, Boolean>() {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Save Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
//String result = jsonParser.uploadImage(imgFile.getAbsolutePath(),getActivity()).toString();
//Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
//System.out.println("fdfdf"+imgFile.getAbsolutePath().toString());
                    JSONObject jsonObject;
//if(imgFile != null)
                    jsonObject =jsonParser_add_business_single.uploadImage(""+image_real_path,""+user_id,""+s,
                            ""+s1,""+s2,""+s3,""+s4,""+s5,""+s6,""+s7,""+s8,""+s9,
                            ""+s10,""+s11,""+s12,""+s13,""+s14,getActivity());
//else
//jsonObject =jsonParser_editProfile.uploadImage("",username,password,age,height,phone,weight,country,activity);

                    if (jsonObject != null) {
                        if(jsonObject.getString("status").equals("200")){
                            new_business_id[0] = jsonObject.getString("business_id");

                        }
                        return jsonObject.getString("status").equals("200");
                    }
                } catch (JSONException e) {
                    Log.i("TAG", "Error : " + e.getLocalizedMessage());
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
// if (progressDialog != null)
// progressDialog.dismiss();
                try
                {
                    if (aBoolean) {
//Toast.makeText(getActivity(), "Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(),"Profile Saved!",Toast.LENGTH_SHORT).show();

                        if (mResults.size()>0){

                            int count=0;
                            for(String result_image : mResults) {

                                count++;
                                // Toast.makeText(getActivity(),"button_loop = "+ result_image,Toast.LENGTH_SHORT).show();
                                image_real_path = result_image;
                                //creating new business gallery
                                send_data(sharedPreferences.getString("user_id", ""),
                                          ""+ new_business_id[0] ,count,result_image);
                            }

                        }else {
                            if (sharedPreferences.getString("user_role" ,""  ).equals("4")){

                                SavePreferences("user_role"    ,"1");//business created by the customer user_role change to 1

                                Intent intent = getActivity().getIntent();
                                getActivity().finish();
                                startActivity(intent);

                            }else {
                                getFragmentManager().popBackStack();

                            }
                        }



                    }
                    else
                        Toast.makeText(getActivity(), "Failed To Save Data, Try Again!", Toast.LENGTH_LONG).show();


                } catch (Exception e)
                {
                    e.printStackTrace();
                }finally {

                    progressDialog.dismiss();
                }
            }
        }.execute();


    }

    //creating new business gallery
    private void send_data(final String user_id, final String selected_business, final int count,final String real_path) {

        //
        new AsyncTask<Void, Integer, Boolean>() {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Save Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
//String result = jsonParser.uploadImage(imgFile.getAbsolutePath(),getActivity()).toString();
//Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
//System.out.println("fdfdf"+imgFile.getAbsolutePath().toString());
                    JSONObject jsonObject;
//if(imgFile != null)
                    jsonObject =jsonParser_add_business.uploadImage(""+real_path,""+user_id,""+selected_business,""+count, getActivity());
//else
//jsonObject =jsonParser_editProfile.uploadImage("",username,password,age,height,phone,weight,country,activity);

                    if (jsonObject != null) {
                        if(jsonObject.getString("status").equals("200")){


                        }
                        return jsonObject.getString("status").equals("200");
                    }
                } catch (JSONException e) {
                    Log.i("TAG", "Error : " + e.getLocalizedMessage());
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
// if (progressDialog != null)
// progressDialog.dismiss();
                try
                {
                    if (aBoolean) {

//                        Toast.makeText(getActivity(),"Profile Saved!",Toast.LENGTH_SHORT).show();
//                        getFragmentManager().popBackStack();


                        if (count==mResults.size()){

                            if (sharedPreferences.getString("user_role" ,""  ).equals("4")){

                                SavePreferences("user_role"    ,"1");//business created by the customer user_role change to 1

                                Intent intent = getActivity().getIntent();
                                getActivity().finish();
                                startActivity(intent);

                            }else {
                                getFragmentManager().popBackStack();
                            }
                        }




//                        if (count==mResults.size()){
//                            getFragmentManager().popBackStack();
//                        }

                    }
                    else
                        Toast.makeText(getActivity(), "Failed To Save Data, Try Again!", Toast.LENGTH_LONG).show();


                } catch (Exception e)
                {
                    e.printStackTrace();
                }finally {

                    progressDialog.dismiss();
                }

//imagePath = "";
            }
        }.execute();

    }

    //updating already created business (profile pic)
    private void send_data_update(final String user_id, final String selected_business, final String toString, final String toString1, final String service, final String toString2,
                                  final String toString3, final String state_value, final String city_value, final String business_value, final String business_sub_value,
                                  final String toString4, final String fname, final String s, final String removeLastChar,
                                  final String removeLastChar1, final String removeLastChar2, final String removeLastChar3) {

        //
        new AsyncTask<Void, Integer, Boolean>() {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Save Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
//String result = jsonParser.uploadImage(imgFile.getAbsolutePath(),getActivity()).toString();
//Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
//System.out.println("fdfdf"+imgFile.getAbsolutePath().toString());
                    JSONObject jsonObject;
//if(imgFile != null)
                    jsonObject =jsonParser_update_business_single.uploadImage(""+image_real_path,""+user_id,""+selected_business,
                            ""+toString,""+toString1,""+service,""+toString2,""+toString3,""+state_value,
                            ""+city_value,""+business_value,""+business_sub_value,
                            ""+toString4,""+fname,""+s,""+removeLastChar,
                            ""+removeLastChar1,""+removeLastChar2,""+removeLastChar3,getActivity());
//else
//jsonObject =jsonParser_editProfile.uploadImage("",username,password,age,height,phone,weight,country,activity);

                    if (jsonObject != null) {
                        if(jsonObject.getString("status").equals("200")){


                        }
                        return jsonObject.getString("status").equals("200");
                    }
                } catch (JSONException e) {
                    Log.i("TAG", "Error : " + e.getLocalizedMessage());
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                try
                {
                    if (aBoolean) {
//

                        Toast.makeText(getActivity(),"Profile Saved!",Toast.LENGTH_SHORT).show();

                        if (mResults.size()>0){

                            int count=0;
                            for(String result_image : mResults) {

                                count++;

                                image_real_path = result_image;

                                //updating already created business (gallery)
                                send_data_update(sharedPreferences.getString("user_id", ""),sharedPreferences.getString("selected_business", ""),count,result_image);
                            }
                        }else {

                            getFragmentManager().popBackStack();
                        }
                    }
                    else
                        Toast.makeText(getActivity(), "Failed To Save Data, Try Again!", Toast.LENGTH_LONG).show();


                } catch (Exception e)
                {
                    e.printStackTrace();
                }finally {

                    progressDialog.dismiss();
                }


//imagePath = "";
            }
        }.execute();
        
    }

    //updating already created business (gallery)
    private void send_data_update(final String user_id, final String selected_business, final int count, final String image_path) {

        //
        new AsyncTask<Void, Integer, Boolean>() {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Save Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
//String result = jsonParser.uploadImage(imgFile.getAbsolutePath(),getActivity()).toString();
//Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
//System.out.println("fdfdf"+imgFile.getAbsolutePath().toString());
                    JSONObject jsonObject;
//if(imgFile != null)
                    jsonObject =jsonParser_update_business.uploadImage(""+image_path,""+user_id,""+selected_business,""+count, getActivity());
//else
//jsonObject =jsonParser_editProfile.uploadImage("",username,password,age,height,phone,weight,country,activity);

                    if (jsonObject != null) {
                        if(jsonObject.getString("status").equals("200")){

                        }
                        return jsonObject.getString("status").equals("200");
                    }
                } catch (JSONException e) {
                    Log.i("TAG", "Error : " + e.getLocalizedMessage());
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);

                try
                {
                    if (aBoolean) {
            //Toast.makeText(getActivity(), "Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                       // Toast.makeText(getActivity(),"Profile Saved!",Toast.LENGTH_SHORT).show();
                        if (count==mResults.size()){
                            getFragmentManager().popBackStack();
                        }

                    }
                    else
                        Toast.makeText(getActivity(), "Failed To Save Data, Try Again!", Toast.LENGTH_LONG).show();


                } catch (Exception e)
                {
                    e.printStackTrace();
                }finally {

                    progressDialog.dismiss();
                }
            }
        }.execute();
    }

    private void setTextViews(int sdk, String uriPath, String realPath) {

        File file = new File(realPath);


        Uri uriFromPath = Uri.fromFile(file);

        // you have two ways to display selected image

        // ( 1 ) imageView.setImageURI(uriFromPath);

        // ( 2 ) imageView.setImageBitmap(bitmap);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        img_main_image.setImageBitmap(bitmap);

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:" + sdk);
        Log.d("HMKCODE", "URI Path:" + uriPath);
        Log.d("HMKCODE", "Real Path: " + realPath);

        image_real_path = file.getAbsolutePath();
        profilepic=1;

    }

    Marker marker;
    public void map_view(final double lat, final double lng){

        if(lat == 0 || lng == 0){

            return;
        }

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


                if (marker != null)
                    marker.remove();

                // For dropping a marker at a point on the Map
                LatLng position = new LatLng(lat, lng);
                marker = googleMap.addMarker(new MarkerOptions().position(position).title("" + sharedPreferences.getString("selected_name", ""))
                        .snippet("" + sharedPreferences.getString("selected_description", "")));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(position).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                if (!map_check){
                    simpleProgressBar.setVisibility(View.GONE);
                    getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    map_check = true;
                }
            }
        });

    }

    String [] states,states_id,states_english;
    public void states(final String state_business_id, final String loop_check){
        //http://192.168.100.14/monshiapp/app/state_list.php
        String url    =  getResources().getString(R.string.url)+"state_list.php";
        String params =  "";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray state_listing = jsonObject.getJSONArray("state_listing");

                        states        = new String [state_listing.length()];
                        states_id     = new String [state_listing.length()];
                        states_english= new String [state_listing.length()];

                        for(int i = 0; i < state_listing.length(); i++) {
                            JSONObject c = state_listing.getJSONObject(i);
                            String RegionID = c.getString("RegionID");
                            String Region = c.getString("Region");
                            String Region_per = c.getString("Region_per");


                            if (i==0){
                                states   [i]     = getString(R.string.str_select_state);
                                states_id[i]     = RegionID;
                                states_english[i]=Region;
                            }else {
                                states   [i]  = Region_per;
                                states_id[i]  = RegionID;
                                states_english[i] =Region;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.my_spinner_style, states) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
//                                ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_state.setAdapter(adapter);

                        spinner_business_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!states_id[position].equals("0")){
                                   // Toast.makeText(getActivity(),loop_check+"",Toast.LENGTH_SHORT).show();

                                    //if (!loop_check.equals("yes")){

                                        //cities(states_id[position],"","no");
                                    //}
                                    state_value = states_id[position];

                                    //temp_state = states[position];

                                    if(map_check){

                                        cities(states_id[position],"","no");
                                        map_view(getLocationFromAddress(getActivity(), states[position]).latitude, getLocationFromAddress(getActivity(), states[position]).longitude);

                                    }else if(temp_state.equals("")){

                                        cities(states_id[position],"","no");
                                        map_view(getLocationFromAddress(getActivity(), states[position]).latitude, getLocationFromAddress(getActivity(), states[position]).longitude);

                                    }

                                    //Toast.makeText(getActivity(),states[position]+" state",Toast.LENGTH_SHORT).show();
                                    /*if(getLocationFromAddress(getActivity(),states[position]).latitude == 0 || getLocationFromAddress(getActivity(),states[position]).longitude == 0){

                                        map_view(32.4279, 53.6880);
                                    }else {
                                        map_view(getLocationFromAddress(getActivity(), states[position]).latitude, getLocationFromAddress(getActivity(), states[position]).longitude);
                                        //map_view(32.4279, 53.6880);
                                    }*/

                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                        if (loop_check.equals("yes")){
                            for (int z=0;z<states_id.length;z++){
                                if (state_business_id.equals(states_id[z])){
                                    spinner_business_state.setSelection(z);
                                }
                            }
                        }

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);
    }

    String [] cities,city_id,city_english;
    public void cities(String states_id, final String city_business_id, final String loop_check){
        //http://192.168.100.14/monshiapp/app/city_list.php

        String url    =  getResources().getString(R.string.url)+"city_list.php";
        String params =  "state="+states_id;



        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray city_listing = jsonObject.getJSONArray("city_listing");


                        cities    = new String [city_listing.length()];
                        city_id   = new String [city_listing.length()];
                        city_english   = new String [city_listing.length()];

                        for(int i = 0; i < city_listing.length(); i++) {
                            JSONObject c = city_listing.getJSONObject(i);
                            String CityId = c.getString("CityId");
                            String City = c.getString("City");
                            String City_per = c.getString("City_per");



                            if (i==0){
                                cities   [i]  = "شهر خود را انتخاب کنید";
                                city_id  [i]  = CityId;
                                city_english[i] = City;
                            }else {
                                cities [i]  = City_per;
                                city_id[i]  = CityId;
                                city_english[i] = City;
                            }
                        }

                        if (getActivity()!=null){

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, cities) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);


                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
                                    //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                    return v;
                                }
                            };

                            spinner_business_city.setAdapter(adapter);

                        }



                        spinner_business_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!city_id[position].equals("0")){
                                    city_value = city_id[position];

                                   // temp_city = cities[position];

                                    if(map_check){
                                        map_view(getLocationFromAddress(getActivity(),cities[position]).latitude,getLocationFromAddress(getActivity(),cities[position]).longitude);
                                    }else if(temp_state.equals("")){
                                        map_view(getLocationFromAddress(getActivity(),cities[position]).latitude,getLocationFromAddress(getActivity(),cities[position]).longitude);
                                    }
                                    //Toast.makeText(getActivity(),cities[position]+" City",Toast.LENGTH_SHORT).show();
                                    //map_view(getLocationFromAddress(getActivity(),cities[position]).latitude,getLocationFromAddress(getActivity(),cities[position]).longitude);
                                    //map_view(32.4279, 53.6880);
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                        if (loop_check.equals("yes")){
                            for (int z=0;z<city_id.length;z++){

                                if (city_business_id.equals(city_id[z])){
                                     //Toast.makeText(getActivity(),"city_business_id = "+city_business_id+" city_id = "+city_id[z],Toast.LENGTH_SHORT).show();
                                    spinner_business_city.setSelection(z);

                                    break;
                                }
                            }
                        }

                    }
                    else{

                        cities    = new String [1];
                        city_id   = new String [1];
                        city_english   = new String [1];

                            cities   [0]  = "شهر خود را انتخاب کنید";
                            city_id  [0]  = "0";
                            city_english[0] = "";
                        if (getActivity()!=null){

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, cities) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);


                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
                                    //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                    return v;
                                }
                            };

                            spinner_business_city.setAdapter(adapter);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);

    }

    String [] business_category,business_category_id;
    public void spinner_business_category(final String category_business_id, final String loop_check){
        //http://192.168.100.14/monshiapp/app/business_category_list.php
        String url    =  getResources().getString(R.string.url)+"business_category_list.php";
        String params =  "";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_cat_listing = jsonObject.getJSONArray("business_cat_listing");

                        business_category    = new String [business_cat_listing.length()];
                        business_category_id = new String [business_cat_listing.length()];

                        for(int i = 0; i < business_cat_listing.length(); i++) {
                            JSONObject c = business_cat_listing.getJSONObject(i);
                            String category_id = c.getString("category_id");
                            String category_name = c.getString("category_name");
                            String category_name_per = c.getString("category_name_per");



                            if (i==0){
                                business_category[i]  = "دسته خود را انتخاب کنید";
                                business_category_id[i]  = category_id;
                            }else {
                                business_category   [i]  = category_name_per;
                                business_category_id[i]  = category_id;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.my_spinner_style, business_category) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_category.setAdapter(adapter);

                        spinner_business_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!business_category_id[position].equals("0")){
                                    //if (!loop_check.equals("yes")){
                                        spinner_business_sub_category(business_category_id[position],"","no");
                                    //}

                                    business_value = business_category_id[position] ;
                                }

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        if (loop_check.equals("yes")){
                            for (int z=0;z<business_category.length;z++){
                                if (category_business_id.equals(business_category_id[z])){
                                    spinner_business_category.setSelection((z));
                                }
                            }
                        }
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);
    }

    String [] business_sub_category,business_sub_category_id;
    public void spinner_business_sub_category(String id, final String sub_category_business_id, final String loop_check){
        // http://192.168.100.14/monshiapp/app/business_subcategory_list.php
        String url    =  getResources().getString(R.string.url)+"business_subcategory_list.php";
        String params =  "category="+id;

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_subcat_listing = jsonObject.getJSONArray("business_subcat_listing");

                        business_sub_category    = new String [business_subcat_listing.length()];
                        business_sub_category_id = new String [business_subcat_listing.length()];

                        for(int i = 0; i < business_subcat_listing.length(); i++) {
                            JSONObject c = business_subcat_listing.getJSONObject(i);
                            String category_id = c.getString("category_id");
                            String category_name = c.getString("category_name");
                            String category_name_per = c.getString("category_name_per");

                            if (i==0){
                                business_sub_category[i]  = "دسته خود را انتخاب کنید";
                                business_sub_category_id[i]  = category_id;
                            }else {
                                business_sub_category   [i]  = category_name_per;
                                business_sub_category_id[i]  = category_id;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.my_spinner_style, business_sub_category) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                // ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_sub_category.setAdapter(adapter);

                        spinner_business_sub_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!business_sub_category_id[position].equals("0")){

                                }
                                business_sub_value = business_sub_category_id[position];
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {


                            }
                        });

                        if (loop_check.equals("yes")){
                            for (int z=0;z<business_sub_category.length;z++){
                                if (sub_category_business_id.equals(business_sub_category_id[z])){
                                    spinner_business_sub_category.setSelection((z));
                                }
                            }
                        }



                    }
                    else{
                        business_sub_category    = new String [1];
                        business_sub_category_id = new String [1];

                        business_sub_category[0]  = "دسته خود را انتخاب کنید";
                        business_sub_category_id[0]  = "0";


                        if (getActivity()!=null){

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, business_sub_category) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);

                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
                                    // ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                    return v;
                                }
                            };

                            spinner_business_sub_category.setAdapter(adapter);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);
    }

    private static String removeLastChar(String str) {

        if(str.equals("")){
            return "";
        }else return str.substring(0, str.length() - 1);
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    int READ_STORAGE_PERMISSION_REQUEST_CODE=1;
    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public LatLng getLocationFromAddress(final Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        /*String url_map="https://maps.googleapis.com/maps/api/geocode/json?address="+strAddress+"&key="+getString(R.string.google_maps_key);
        final double[] lng = new double[1];
        final double[] lat = new double[1];

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) throws JSONException {

                JSONObject jsonObject = new JSONObject(result);
                lng[0] = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lng");

                lat[0] = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                        .getJSONObject("geometry").getJSONObject("location")
                        .getDouble("lat");
                Toast.makeText(context, lat[0] +" - "+ lng[0],Toast.LENGTH_SHORT).show();
                System.out.println("result_map = "+result);
            }
        });
        webRequestCall.execute(url_map,"GET","");*/


        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            //Toast.makeText(context,address+"",Toast.LENGTH_SHORT).show();
            if (address == null || address.size() == 0 ) {
                //Toast.makeText(getActivity(), "address = "+address.toString(), Toast.LENGTH_LONG).show();
                p1 = new LatLng(32.4279, 53.6880 );
            }else {

                Address location = address.get(0);

                //Toast.makeText(getActivity(), "address = "+address.toString(), Toast.LENGTH_LONG).show();
                p1 = new LatLng(location.getLatitude(), location.getLongitude() );
                //p1 = new LatLng(32.4279, 53.6880 );

            }
            //p1 = new LatLng(32.4279, 53.6880 );

        } catch (IOException ex) {

            ex.printStackTrace();
        }
        return p1;
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}

class RealPathUtil {

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
