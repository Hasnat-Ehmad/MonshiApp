package info.androidhive.materialdesign.pagerfragments;

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
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hasnat.imagecropper.CropIntent;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.MangementService_List_Adapter;
import info.androidhive.materialdesign.adapter.add_staff_Service_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.webservice.JSONParser_Add_Staff_Profile;
import info.androidhive.materialdesign.webservice.JSONParser_Staff_Profile;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static android.app.Activity.RESULT_OK;
import static info.androidhive.materialdesign.adapter.add_staff_Service_List_Adapter.positionArray;


public class AddStaffFragment extends Fragment {

    EditText ed_name, ed_password, ed_fullname, ed_email, ed_mobile, ed_expertise, ed_experence;

    ToggleButton toggle_saturday, toggle_sunday, toggle_monday, toggle_tuesday,
            toggle_wednesday, toggle_thursday, toggle_friday;

    Spinner spinner_saturday_from, spinner_sunday_from, spinner_monday_from,
            spinner_tuesday_from, spinner_wednesday_from, spinner_thursday_from,
            spinner_friday_from;

    Spinner spinner_saturday_to, spinner_sunday_to, spinner_monday_to, spinner_tuesday_to,
            spinner_wednesday_to, spinner_thursday_to, spinner_friday_to;

    Button btn_submit, btn_profile_pic;

    ImageView img_profile_pic;

    ArrayList<ManagmentService_List> managmentService_lists = new ArrayList();

    ListView listView;

    add_staff_Service_List_Adapter add_staff_service_list_adapter;

    SharedPreferences sharedPreferences;

    ScrollView scrollView;

    String[] from_value, to_value;
    String[] from_value_int, to_value_int;
    int api_call_check = 0;

    JSONParser_Staff_Profile jsonParser_staff_profile;
    JSONParser_Add_Staff_Profile jsonParser_add_staff_profile;

    String image_real_path="";

    Spinner spinner_make_recep;

    public static String new_staff_id = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

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

                getActivity().getSupportFragmentManager().popBackStack();
                Toast.makeText(getActivity(),"Add staff fragment",Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_staff, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        listView = (ListView) rootView.findViewById(R.id.list_view);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        jsonParser_staff_profile = new JSONParser_Staff_Profile();
        jsonParser_add_staff_profile = new JSONParser_Add_Staff_Profile();

        scrollView = rootView.findViewById(R.id.scrollView);

        ed_name = (EditText) rootView.findViewById(R.id.ed_name);
        ed_password = (EditText) rootView.findViewById(R.id.ed_password);
        ed_fullname = (EditText) rootView.findViewById(R.id.ed_fullname);
        ed_email = (EditText) rootView.findViewById(R.id.ed_email);
        ed_mobile = (EditText) rootView.findViewById(R.id.ed_mobile);
        ed_expertise = (EditText) rootView.findViewById(R.id.ed_expertise);
        ed_experence = (EditText) rootView.findViewById(R.id.ed_experence);


        toggle_saturday = (ToggleButton) rootView.findViewById(R.id.toggle_saturday);
        toggle_sunday = (ToggleButton) rootView.findViewById(R.id.toggle_sunday);
        toggle_monday = (ToggleButton) rootView.findViewById(R.id.toggle_monday);
        toggle_tuesday = (ToggleButton) rootView.findViewById(R.id.toggle_tuesday);
        toggle_wednesday = (ToggleButton) rootView.findViewById(R.id.toggle_wednesday);
        toggle_thursday = (ToggleButton) rootView.findViewById(R.id.toggle_thursday);
        toggle_friday = (ToggleButton) rootView.findViewById(R.id.toggle_friday);

        spinner_make_recep = rootView.findViewById(R.id.spinner_make_recep);
        if (sharedPreferences.getString("user_role", "").equals("1")) {
            spinner_make_recep.setVisibility(View.VISIBLE);
        }else {
            spinner_make_recep.setVisibility(View.GONE);
        }

        spinner_saturday_from = (Spinner) rootView.findViewById(R.id.spinner_saturday_from);
        spinner_saturday_from.setEnabled(false);
        spinner_saturday_from.setSelection(0);

        spinner_sunday_from = (Spinner) rootView.findViewById(R.id.spinner_sunday_from);
        spinner_sunday_from.setEnabled(false);
        spinner_sunday_from.setSelection(0);

        spinner_monday_from = (Spinner) rootView.findViewById(R.id.spinner_monday_from);
        spinner_monday_from.setEnabled(false);
        spinner_monday_from.setSelection(0);

        spinner_tuesday_from = (Spinner) rootView.findViewById(R.id.spinner_tuesday_from);
        spinner_tuesday_from.setEnabled(false);
        spinner_tuesday_from.setSelection(0);

        spinner_wednesday_from = (Spinner) rootView.findViewById(R.id.spinner_wednesday_from);
        spinner_wednesday_from.setEnabled(false);
        spinner_wednesday_from.setSelection(0);

        spinner_thursday_from = (Spinner) rootView.findViewById(R.id.spinner_thursday_from);
        spinner_thursday_from.setEnabled(false);
        spinner_thursday_from.setSelection(0);

        spinner_friday_from = (Spinner) rootView.findViewById(R.id.spinner_friday_from);
        spinner_friday_from.setEnabled(false);
        spinner_friday_from.setSelection(0);

        spinner_saturday_to = (Spinner) rootView.findViewById(R.id.spinner_saturday_to);
        spinner_saturday_to.setEnabled(false);
        spinner_saturday_to.setSelection(12);

        spinner_sunday_to = (Spinner) rootView.findViewById(R.id.spinner_sunday_to);
        spinner_sunday_to.setEnabled(false);
        spinner_sunday_to.setSelection(12);

        spinner_monday_to = (Spinner) rootView.findViewById(R.id.spinner_monday_to);
        spinner_monday_to.setEnabled(false);
        spinner_monday_to.setSelection(12);

        spinner_tuesday_to = (Spinner) rootView.findViewById(R.id.spinner_tuesday_to);
        spinner_tuesday_to.setEnabled(false);
        spinner_tuesday_to.setSelection(12);

        spinner_wednesday_to = (Spinner) rootView.findViewById(R.id.spinner_wednesday_to);
        spinner_wednesday_to.setEnabled(false);
        spinner_wednesday_to.setSelection(12);

        spinner_thursday_to = (Spinner) rootView.findViewById(R.id.spinner_thursday_to);
        spinner_thursday_to.setEnabled(false);
        spinner_thursday_to.setSelection(12);

        spinner_friday_to = (Spinner) rootView.findViewById(R.id.spinner_friday_to);
        spinner_friday_to.setEnabled(false);
        spinner_friday_to.setSelection(12);



        if (!sharedPreferences.getString("staff_id", "").equals("")) {
            //.makeText(getActivity(),"show_staff_detail = ",Toast.LENGTH_SHORT).show();

            new_staff_id = sharedPreferences.getString("staff_id", "");

            //http://192.168.100.14/monshiapp/app/staff_info_detail.php?staff_id=2
            String url = getResources().getString(R.string.url) + "staff_info_detail.php";

            String params = "staff_id=" + sharedPreferences.getString("staff_id", "");

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("status").equals("200")) {

                            scrollView.smoothScrollTo(0, 0);

                            api_call_check = 1;

                            String username = jsonObject.getString("username");
                            String full_name= jsonObject.getString("full_name");
                            String email = jsonObject.getString("email");
                            String mobile= jsonObject.getString("mobile");
                            String role  = jsonObject.getString("role");
                            String expertise = jsonObject.getString("expertise");
                            String experience= jsonObject.getString("experience");
                            String image = jsonObject.getString("image");

                            ed_name.setText("" + username);
                            ed_fullname.setText("" + full_name);
                            ed_email.setText("" + email);
                            ed_mobile.setText("" + mobile);
                            ed_expertise.setText("" + expertise);
                            ed_experence.setText("" + experience);

                            Picasso.with(img_profile_pic.getContext()).load("" + image).into(img_profile_pic);

                            //Toast.makeText(getActivity(),"full_name = "+full_name,Toast.LENGTH_SHORT).show();
                            System.out.println("full_name = " + full_name);


                            JSONArray timing = jsonObject.getJSONArray("timing");
                            for (int i = 0; i < timing.length(); i++) {

                                JSONObject c = timing.getJSONObject(i);

                                String day_per = c.getString("day_per");
                                String day = c.getString("day");
                                String selected = c.getString("selected");
                                String business_on_off = c.getString("business_on_off");


                                if (i==0){
                                    toggle_saturday.setTag(business_on_off);
                                }
                                if (i==1){
                                    toggle_sunday.setTag(business_on_off);
                                }
                                if (i==2){
                                    toggle_monday.setTag(business_on_off);
                                }
                                if (i==3){
                                    toggle_tuesday.setTag(business_on_off);
                                }
                                if (i==4){
                                    toggle_wednesday.setTag(business_on_off);
                                }
                                if (i==5){
                                    toggle_thursday.setTag(business_on_off);
                                }
                                if (i==6){
                                    toggle_friday.setTag(business_on_off);
                                }

                                toggle_saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_saturday.getTag().equals("yes")){
                                                spinner_saturday_from.setEnabled(true);
                                                spinner_saturday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_saturday.setChecked(false);
                                            }

                                        } else {
                                            spinner_saturday_from.setEnabled(false);
                                            spinner_saturday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_sunday.getTag().equals("yes")){
                                                spinner_sunday_from.setEnabled(true);
                                                spinner_sunday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_sunday.setChecked(false);
                                            }

                                        } else {
                                            spinner_sunday_from.setEnabled(false);
                                            spinner_sunday_to.setEnabled(false);
                                        }
                                    }
                                });

                                toggle_monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_monday.getTag().equals("yes")){
                                                spinner_monday_from.setEnabled(true);
                                                spinner_monday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_monday.setChecked(false);
                                            }

                                        } else {
                                            spinner_monday_from.setEnabled(false);
                                            spinner_monday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_tuesday.getTag().equals("yes")){
                                                spinner_tuesday_from.setEnabled(true);
                                                spinner_tuesday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_tuesday.setChecked(false);
                                            }

                                        } else {
                                            spinner_tuesday_from.setEnabled(false);
                                            spinner_tuesday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_wednesday.getTag().equals("yes")){
                                                spinner_wednesday_from.setEnabled(true);
                                                spinner_wednesday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_wednesday.setChecked(false);
                                            }

                                        } else {
                                            spinner_wednesday_from.setEnabled(false);
                                            spinner_wednesday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_thursday.getTag().equals("yes")){
                                                spinner_thursday_from.setEnabled(true);
                                                spinner_thursday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_thursday.setChecked(false);
                                            }

                                        } else {
                                            spinner_thursday_from.setEnabled(false);
                                            spinner_thursday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_friday.getTag().equals("yes")){
                                                spinner_friday_from.setEnabled(true);
                                                spinner_friday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_friday.setChecked(false);
                                            }

                                        } else {
                                            spinner_friday_from.setEnabled(false);
                                            spinner_friday_to.setEnabled(false);
                                        }
                                    }
                                });


                                if (business_on_off.equals("yes")){

                                    if (selected.equals("yes")){
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

                                //Toast.makeText(getActivity(),"day = "+day,Toast.LENGTH_SHORT).show();
                                System.out.println("day = " + day);

                                JSONArray from_time_array = c.getJSONArray("from_time_array");

                                from_value = new String[from_time_array.length()];
                                from_value_int = new String[from_time_array.length()];

                                int from_index=0;
//                                Toast.makeText(getActivity(),"from_value = "+from_value.length,Toast.LENGTH_LONG).show();

                                for (int j = 0; j < from_time_array.length(); j++) {
                                    JSONObject d = from_time_array.getJSONObject(j);

                                    String value = d.getString("value");
                                    String value_in_per = d.getString("value_in_per");
                                    String status_from = d.getString("status");

                                    from_value[j] = value_in_per;
                                    from_value_int[j] = value;

                                    //Toast.makeText(getActivity(),"value from = "+value,Toast.LENGTH_SHORT).show();

                                    if (status_from.equals("enable")){
                                        from_index=j;
                                    }

                                    System.out.println("value from = " + value);

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, from_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getDropDownView(position, convertView, parent);
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

                                JSONArray to_time_array = c.getJSONArray("to_time_array");

                                to_value = new String[to_time_array.length()];
                                to_value_int = new String[to_time_array.length()];

                                int to_index = 0;

                                for (int j = 0; j < to_time_array.length(); j++) {
                                    JSONObject d = to_time_array.getJSONObject(j);
                                    String value = d.getString("value");
                                    String value_in_per = d.getString("value_in_per");
                                    String status_to = d.getString("status");

                                    to_value[j] = value_in_per;
                                    to_value_int[j] = value;

                                    if (status_to.equals("enable")){
                                        to_index = j;
                                    }

                                    // Toast.makeText(getActivity(),"value to = "+value,Toast.LENGTH_SHORT).show();
                                    System.out.println("value to = " + value);
                                }

                                ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, to_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                        return v;
                                    }
                                };

                                scrollView.smoothScrollTo(0, 0);
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
                                scrollView.smoothScrollTo(0, 0);
                            }
                            managmentService_lists.clear();
                            JSONArray service_list = jsonObject.getJSONArray("service_list");

                            for (int j = 0; j < service_list.length(); j++) {
                                JSONObject d = service_list.getJSONObject(j);
                                String service_id = d.getString("id");
                                String selected = d.getString("selected");
                                String servicename = d.getString("servicename");
                                String time = d.getString("time");
                                String amount = d.getString("amount");


                                ManagmentService_List obj = new ManagmentService_List
                                        ("" + service_id, "" + servicename, "" + amount, "" + time, "", ""+selected,"");

                                managmentService_lists.add(obj);

                                // Toast.makeText(getActivity(),"value to = "+value,Toast.LENGTH_SHORT).show();

                            }

                            add_staff_service_list_adapter = new add_staff_Service_List_Adapter(getActivity(), managmentService_lists);

                            listView.setAdapter(add_staff_service_list_adapter);
                            setDynamicHeight(listView);

                            scrollView.smoothScrollTo(0, 0);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall.execute(url, "POST", params);
        }else {

            //http://192.168.100.14/monshiapp/app/service_listing.php
            String url = getResources().getString(R.string.url) + "service_listing.php";

//            String params =  "user_id="+sharedPreferences.getString("user_id", "")+
//                    "&business_id="+sharedPreferences.getString("business_id", "");
            String params = "";
            scrollView.smoothScrollTo(0, 0);

            if (sharedPreferences.getString("user_role", "").equals("1")) {
                params = "user_id=" + sharedPreferences.getString("user_id", "") +
                        "&business_id=" + sharedPreferences.getString("selected_business", "");
            } else {
                params = "user_id=" + sharedPreferences.getString("user_id", "") +
                        "&business_id=" + sharedPreferences.getString("business_id", "");
            }

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);

                        managmentService_lists.clear();

                        if (jsonObject.getString("status").equals("200")) {
                            JSONArray staff_listing = jsonObject.getJSONArray("service_listing");


                            for (int i = 0; i < staff_listing.length(); i++) {


                                JSONObject c = staff_listing.getJSONObject(i);
                                String id = c.getString("id");
                                String user_id = c.getString("user_id");
                                String business_id = c.getString("business_id");
                                String servicename = c.getString("servicename");
                                String description = c.getString("description");
                                String amount = c.getString("amount");
                                String time = c.getString("time");
                                String buffer_time = c.getString("buffer_time");
                                String role = c.getString("role");

                                if (i==0){

                                }else {

                                    ManagmentService_List obj = new ManagmentService_List
                                            ("" + id, "" + servicename, "" + amount, "" + time, "", "","");

                                    managmentService_lists.add(obj);

                                    scrollView.smoothScrollTo(0, 0);

                                }

                            }

                            add_staff_service_list_adapter = new add_staff_Service_List_Adapter(getActivity(), managmentService_lists);

                            listView.setAdapter(add_staff_service_list_adapter);

                            setDynamicHeight(listView);

                            scrollView.smoothScrollTo(0, 0);

                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall.execute(url, "POST", params);


            //===========================================================



            //http://192.168.100.14/monshiapp/app/get_business_timing.php?user_id=1&business_id=2
            String url_timing = getResources().getString(R.string.url) + "get_business_timing.php";

            String params_timing = "";

            if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                //spinner_business_list.setVisibility(View.GONE);
                params_timing =  "user_id="+sharedPreferences.getString("user_id", "")+
                        "&business_id="+sharedPreferences.getString("selected_business", "");
            }else {
                //spinner_business_list.setVisibility(View.VISIBLE);
                params_timing =  "user_id="+sharedPreferences.getString("user_id", "")+
                        "&business_id="+sharedPreferences.getString("business_id", "");
            }

            WebRequestCall webRequestCall_timing = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.getString("status").equals("200")) {

                            scrollView.smoothScrollTo(0, 0);




                            JSONArray days_array = jsonObject.getJSONArray("days_array");
                            for (int i = 0; i < days_array.length(); i++) {

                                JSONObject c = days_array.getJSONObject(i);

                                String day_string_per = c.getString("day_string_per");
                                String day_string = c.getString("day_string");
                                String status_time = c.getString("status");



                                if (i==0){
                                    toggle_saturday.setTag(status_time);
                                }
                                if (i==1){
                                    toggle_sunday.setTag(status_time);
                                }
                                if (i==2){
                                    toggle_monday.setTag(status_time);
                                }
                                if (i==3){
                                    toggle_tuesday.setTag(status_time);
                                }
                                if (i==4){
                                    toggle_wednesday.setTag(status_time);
                                }
                                if (i==5){
                                    toggle_thursday.setTag(status_time);
                                }
                                if (i==6){
                                    toggle_friday.setTag(status_time);
                                }

                                toggle_saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_saturday.getTag().equals("enable")){
                                                spinner_saturday_from.setEnabled(true);
                                                spinner_saturday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_saturday.setChecked(false);
                                            }

                                        } else {
                                            spinner_saturday_from.setEnabled(false);
                                            spinner_saturday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_sunday.getTag().equals("enable")){
                                                spinner_sunday_from.setEnabled(true);
                                                spinner_sunday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_sunday.setChecked(false);
                                            }

                                        } else {
                                            spinner_sunday_from.setEnabled(false);
                                            spinner_sunday_to.setEnabled(false);
                                        }
                                    }
                                });

                                toggle_monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_monday.getTag().equals("enable")){
                                                spinner_monday_from.setEnabled(true);
                                                spinner_monday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_monday.setChecked(false);
                                            }

                                        } else {
                                            spinner_monday_from.setEnabled(false);
                                            spinner_monday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_tuesday.getTag().equals("enable")){
                                                spinner_tuesday_from.setEnabled(true);
                                                spinner_tuesday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_tuesday.setChecked(false);
                                            }

                                        } else {
                                            spinner_tuesday_from.setEnabled(false);
                                            spinner_tuesday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_wednesday.getTag().equals("enable")){
                                                spinner_wednesday_from.setEnabled(true);
                                                spinner_wednesday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_wednesday.setChecked(false);
                                            }

                                        } else {
                                            spinner_wednesday_from.setEnabled(false);
                                            spinner_wednesday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_thursday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_thursday.getTag().equals("enable")){
                                                spinner_thursday_from.setEnabled(true);
                                                spinner_thursday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_thursday.setChecked(false);
                                            }

                                        } else {
                                            spinner_thursday_from.setEnabled(false);
                                            spinner_thursday_to.setEnabled(false);
                                        }
                                    }
                                });
                                toggle_friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                        //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                                        if (isChecked) {
                                            if (toggle_friday.getTag().equals("enable")){
                                                spinner_friday_from.setEnabled(true);
                                                spinner_friday_to.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                toggle_friday.setChecked(false);
                                            }

                                        } else {
                                            spinner_friday_from.setEnabled(false);
                                            spinner_friday_to.setEnabled(false);
                                        }
                                    }
                                });

                                if (status_time.equals("yes")){
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
                                //Toast.makeText(getActivity(),"day = "+day,Toast.LENGTH_SHORT).show();

                                JSONArray from_time_array = c.getJSONArray("from_time_arr");

                                from_value = new String[from_time_array.length()];
                                from_value_int = new String[from_time_array.length()];

                                int from_index=0;
//                                Toast.makeText(getActivity(),"from_value = "+from_value.length,Toast.LENGTH_LONG).show();

                                for (int j = 0; j < from_time_array.length(); j++) {
                                    JSONObject d = from_time_array.getJSONObject(j);

                                    String value = d.getString("value");
                                    String value_in_per = d.getString("value_in_per");
                                    String status_from = d.getString("status");

                                    from_value[j] = value_in_per;
                                    from_value_int[j] = value;

                                    //Toast.makeText(getActivity(),"value from = "+value,Toast.LENGTH_SHORT).show();

                                    if (status_from.equals("enable")){
                                        from_index=j;
                                    }

                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, from_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getDropDownView(position, convertView, parent);
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

                                JSONArray to_time_array = c.getJSONArray("to_time_arr");

                                to_value = new String[to_time_array.length()];
                                to_value_int = new String[to_time_array.length()];

                                int to_index = 0;

                                for (int j = 0; j < to_time_array.length(); j++) {
                                    JSONObject d = to_time_array.getJSONObject(j);
                                    String value = d.getString("value");
                                    String value_in_per = d.getString("value_in_per");
                                    String status_to = d.getString("status");

                                    to_value[j] = value_in_per;
                                    to_value_int[j] = value;

                                    if (status_to.equals("enable")){
                                        to_index = j;
                                    }

                                    // Toast.makeText(getActivity(),"value to = "+value,Toast.LENGTH_SHORT).show();
                                    System.out.println("value to = " + value);
                                }

                                ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, to_value) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                        return v;
                                    }
                                };

                                scrollView.smoothScrollTo(0, 0);
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
                                scrollView.smoothScrollTo(0, 0);
                            }

                            scrollView.smoothScrollTo(0, 0);
                        } else {

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall_timing.execute(url_timing, "POST", params_timing);
        }


        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_name.getText().toString().equals("")){
                    ed_name.setError("Empty");

                }else if (ed_fullname.getText().toString().equals("")){
                    ed_fullname.setError("Empty");

                }else if (ed_email.getText().toString().equals("")){
                    ed_email.setError("Empty");

                }else if (ed_mobile.getText().toString().equals("")){
                    ed_mobile.setError("Empty");

                }else if (ed_expertise.getText().toString().equals("")){
                    ed_expertise.setError("Empty");

                }else if (ed_experence.getText().toString().equals("")){
                    ed_experence.setError("Empty");

                }else if (spinner_make_recep.getSelectedItem().toString().equals("انتخاب نقش")){
                    Toast.makeText(getActivity(),"Please select staff role",Toast.LENGTH_SHORT).show();
                }
                else {

                    if (api_call_check == 1) {//api call check

                        String check_operating_day = "";
                        String from = "", to = "";
                        String day_on_off = "";

                        String checkbox_ids = "";

                        for (int j = 0; j < positionArray.size(); j++) {

                            if (positionArray.get(j)) {
                                //Toast.makeText(getActivity(), "" + order_lists.get(j).getRestaurant_id(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getActivity(),"item_number = "+i,Toast.LENGTH_SHORT).show();
                                checkbox_ids += (managmentService_lists.get(j).getId()) + ",";
                            }
                        }

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

                        //192.168.100.14/monshiapp/app/update_staff.php
                        String url = getResources().getString(R.string.url) + "update_staff.php";

                        String params = "";
                        if (sharedPreferences.getString("user_role", "").equals("1")) {

                            if (image_real_path.equals("")){

                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);

                                params = "user_id=" + sharedPreferences.getString("user_id", "") +
                                        "&business_id=" + sharedPreferences.getString("selected_business", "") +
                                        "&staff_id=" + sharedPreferences.getString("staff_id", "") +
                                        "&username=" + ed_name.getText().toString() +
                                        "&password=" + ed_password.getText().toString() +
                                        "&fullname=" + ed_fullname.getText().toString() +
                                        "&roll="+spinner_index+
                                        "&expertise=" + ed_expertise.getText().toString() +
                                        "&experience_years=" + ed_experence.getText().toString() +
                                        "&email=" + ed_email.getText().toString() +
                                        "&mobilenumber=" + ed_mobile.getText().toString() +
                                        "&all_operating_day=Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday" +
                                        "&check_operating_day=" + removeLastChar(check_operating_day) +
                                        "&day_on_off=" + removeLastChar(day_on_off) +
                                        "&from_hour=" + removeLastChar(from) +
                                        "&to_hour=" + removeLastChar(to) +
                                        "&service_list=" + removeLastChar(checkbox_ids) +
                                        "&color=white";

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if (jsonObject.getString("status").equals("200")) {
                                                Toast.makeText(getActivity(), "staff update successfully", Toast.LENGTH_SHORT).show();

                                                getFragmentManager().popBackStack();

                                            } else {
                                                Toast.makeText(getActivity(), jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);

                            }else {

                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);
                                send_data(sharedPreferences.getString("user_id", ""),
                                          sharedPreferences.getString("selected_business", ""),
                                          ed_name.getText().toString(),
                                          ed_password.getText().toString(),
                                          ed_fullname.getText().toString(),
                                          ""+spinner_index,
                                          ed_expertise.getText().toString(),
                                          ed_experence.getText().toString(),
                                          ed_email.getText().toString(),
                                          ed_mobile.getText().toString(),
                                          sharedPreferences.getString("staff_id", ""),
                                         "Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday",
                                          removeLastChar(check_operating_day),removeLastChar(day_on_off),
                                          removeLastChar(from),
                                          removeLastChar(to),
                                          removeLastChar(checkbox_ids) );
                            }

                        } else {

                            if (image_real_path.equals("")){

                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);
                                params = "user_id=" + sharedPreferences.getString("user_id", "") +
                                        "&business_id=" + sharedPreferences.getString("business_id", "") +

                                        "&username=" + ed_name.getText().toString() +
                                        "&password=" + ed_password.getText().toString() +
                                        "&fullname=" + ed_fullname.getText().toString() +

                                        "&roll="+spinner_index+

                                        "&expertise=" + ed_expertise.getText().toString() +
                                        "&experience_years=" + ed_experence.getText().toString() +

                                        "&email=" + ed_email.getText().toString() +
                                        "&mobilenumber=" + ed_mobile.getText().toString() +

                                        "&staff_id=" + sharedPreferences.getString("staff_id", "") +

                                        "&all_operating_day=Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday" +
                                        "&check_operating_day=" + removeLastChar(check_operating_day) +
                                        "&day_on_off=" + removeLastChar(day_on_off) +
                                        "&from_hour=" + removeLastChar(from) +
                                        "&to_hour=" + removeLastChar(to) +

                                        "&service_list=" + removeLastChar(checkbox_ids) +

                                        "&color=white";

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if (jsonObject.getString("status").equals("200")) {
                                                getFragmentManager().popBackStack();

                                            } else {
                                                Toast.makeText(getActivity(), jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);

                            }
                            else {
                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);
                                send_data(sharedPreferences.getString("user_id", ""),
                                          sharedPreferences.getString("business_id", ""),
                                          ed_name.getText().toString(),
                                          ed_password.getText().toString(),
                                          ed_fullname.getText().toString(),
                                        ""+spinner_index,
                                          ed_expertise.getText().toString(),
                                          ed_experence.getText().toString(),
                                          ed_email.getText().toString(),
                                          ed_mobile.getText().toString(),
                                          sharedPreferences.getString("staff_id", ""),
                                         "Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday",
                                          removeLastChar(check_operating_day),
                                          removeLastChar(day_on_off),
                                          removeLastChar(from),
                                          removeLastChar(to),
                                          removeLastChar(checkbox_ids));
                            }

                        }


                    } else {
                        // check_cause 0
                        String check_operating_day = "";
                        String from = "", to = "";
                        String day_on_off = "";

                        String checkbox_ids = "";

                        for (int j = 0; j < positionArray.size(); j++) {

                            if (positionArray.get(j)) {
                                //Toast.makeText(getActivity(), "" + order_lists.get(j).getRestaurant_id(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getActivity(),"item_number = "+i,Toast.LENGTH_SHORT).show();
                                checkbox_ids += (managmentService_lists.get(j).getId()) + ",";
                            }
                        }


                        if (toggle_saturday.isChecked()) {
                            check_operating_day += "Saturday,";

                            from += from_value_int[spinner_saturday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_saturday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "on,";
                        } else {
                            from += from_value_int[spinner_saturday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_saturday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "off,";
                        }
                        if (toggle_sunday.isChecked()) {
                            check_operating_day += "Sunday,";
                            from += from_value_int[spinner_sunday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_sunday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "on,";
                        } else {
                            from += from_value_int[spinner_sunday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_sunday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "off,";
                        }
                        if (toggle_monday.isChecked()) {
                            check_operating_day += "Monday,";
                            from += from_value_int[spinner_monday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_monday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "on,";
                        } else {
                            from += from_value_int[spinner_monday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_monday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "off,";
                        }
                        if (toggle_tuesday.isChecked()) {
                            check_operating_day += "Tuesday,";
                            from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "on,";
                        } else {
                            from += from_value_int[spinner_tuesday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_tuesday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "off,";
                        }
                        if (toggle_wednesday.isChecked()) {
                            check_operating_day += "Wednesday,";
                            from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "on,";
                        } else {
                            from += from_value_int[spinner_wednesday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_wednesday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "off,";
                        }
                        if (toggle_thursday.isChecked()) {
                            check_operating_day += "Thursday,";
                            from += from_value_int[spinner_thursday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_thursday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "on,";
                        } else {
                            from += from_value_int[spinner_thursday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_thursday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "off,";
                        }
                        if (toggle_friday.isChecked()) {
                            check_operating_day += "Friday,";
                            from += from_value_int[spinner_friday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_friday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "on,";
                        } else {
                            from += from_value_int[spinner_friday_from.getSelectedItemPosition()] + ",";
                            to += to_value_int[spinner_friday_to.getSelectedItemPosition()] + ",";
                            day_on_off += "off,";
                        }

                        //http://192.168.100.14/monshiapp/app/add_staff.php
                        String url = getResources().getString(R.string.url) + "add_staff.php";

                        String params = "";
                        if (sharedPreferences.getString("user_role", "").equals("1")) {

                            if (image_real_path.equals("")){

                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);

                                params = "user_id=" + sharedPreferences.getString("user_id", "") +
                                        "&business_id=" + sharedPreferences.getString("selected_business", "") +

                                        "&username=" + ed_name.getText().toString() +
                                        "&password=" + ed_password.getText().toString() +
                                        "&fullname=" + ed_fullname.getText().toString() +
                                        "&roll="+spinner_index+
                                        "&all_operating_day=Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday" +
                                        "&check_operating_day=" + removeLastChar(check_operating_day) +
                                        "&day_on_off=" + removeLastChar(day_on_off) +
                                        "&from_hour=" + removeLastChar(from) +
                                        "&to_hour=" + removeLastChar(to) +

                                        "&service_list=" + removeLastChar(checkbox_ids) +
                                        "&roll="+spinner_index+
                                        "&mobilenumber=" + ed_mobile.getText().toString() +
                                        "&email=" + ed_email.getText().toString() +
                                        "&expertise=" + ed_expertise.getText().toString() +
                                        "&experience_years=" + ed_experence.getText().toString() +
                                        "&color=white"
                                ;

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if (jsonObject.getString("status").equals("200")) {
                                                Toast.makeText(getActivity(), jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();

                                                getFragmentManager().popBackStack();
                                            } else {
                                                Toast.makeText(getActivity(), jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);

                            }else {
                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);
                                send_data_add(sharedPreferences.getString("user_id", ""),
                                              sharedPreferences.getString("selected_business", ""),
                                              ed_name.getText().toString(),
                                              ed_password.getText().toString(),
                                              ed_fullname.getText().toString(),
                                            ""+spinner_index,
                                              ed_expertise.getText().toString(),
                                              ed_experence.getText().toString(),
                                              ed_email.getText().toString(),
                                              ed_mobile.getText().toString(),
                                              sharedPreferences.getString("staff_id", ""),
                                         "Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday",
                                              removeLastChar(check_operating_day),
                                              removeLastChar(day_on_off),
                                              removeLastChar(from),
                                              removeLastChar(to),
                                              removeLastChar(checkbox_ids) );
                            }


                        } else {

                            if (image_real_path.equals("")){
                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);
                                params = "user_id=" + sharedPreferences.getString("user_id", "") +
                                        "&business_id=" + sharedPreferences.getString("business_id", "") +

                                        "&username=" + ed_name.getText().toString() +
                                        "&password=" + ed_password.getText().toString() +
                                        "&fullname=" + ed_fullname.getText().toString() +
                                        "&roll="+spinner_index+
                                        "&all_operating_day=Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday" +
                                        "&check_operating_day=" + removeLastChar(check_operating_day) +
                                        "&day_on_off=" + removeLastChar(day_on_off) +
                                        "&from_hour=" + removeLastChar(from) +
                                        "&to_hour=" + removeLastChar(to) +

                                        "&service_list=" + removeLastChar(checkbox_ids) +
                                        "&roll="+spinner_index+
                                        "&mobilenumber=" + ed_mobile.getText().toString() +
                                        "&email=" + ed_email.getText().toString() +
                                        "&expertise=" + ed_expertise.getText().toString() +
                                        "&experience_years=" + ed_experence.getText().toString() +
                                        "&color=white"
                                ;

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if (jsonObject.getString("status").equals("200")) {

                                                Toast.makeText(getActivity(), jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                                getFragmentManager().popBackStack();
                                            } else {
                                                Toast.makeText(getActivity(), jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);

                            }else {
                                int spinner_index = (spinner_make_recep.getSelectedItemPosition()+1);
                                send_data_add(sharedPreferences.getString("user_id", ""),
                                              sharedPreferences.getString("business_id", ""),
                                              ed_name.getText().toString(),
                                              ed_password.getText().toString(),
                                              ed_fullname.getText().toString(),
                                           ""+spinner_index,
                                              ed_expertise.getText().toString(),
                                              ed_experence.getText().toString(),
                                              ed_email.getText().toString(),
                                              ed_mobile.getText().toString(),
                                              sharedPreferences.getString("staff_id", ""),
                                          "Saturday,Sunday,Monday,Tuesday,Wednesday,Thursday,Friday",
                                              removeLastChar(check_operating_day),
                                              removeLastChar(day_on_off),
                                              removeLastChar(from),
                                              removeLastChar(to),
                                              removeLastChar(checkbox_ids) );

                            }
                        }
                    }
                }
            }
        });

        img_profile_pic = rootView.findViewById(R.id.img_profile_pic);

        btn_profile_pic = (Button) rootView.findViewById(R.id.btn_profile_pic);
        btn_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startCropImage();


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


        return rootView;
    }

    public void setDynamicHeight(ListView listView) {
        //Checkout_List_Adapter adapter = listView.getAdapter();
        //check adapter if null
        if (add_staff_service_list_adapter.getCount() == 0) {
            return;
        }
        int height = 100;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getHeight(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < add_staff_service_list_adapter.getCount(); i++) {
            View listItem = add_staff_service_list_adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (add_staff_service_list_adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }

    private void send_data(final String user_id , final String selected_business, final String toString      , final String toString1, final String toString2,
                           final String s       , final String toString3        , final String toString4     , final String toString5, final String toString6,
                           final String staff_id, final String s1               , final String removeLastChar, final String removeLastChar1,
                           final String removeLastChar2, final String removeLastChar3, final String removeLastChar4) {

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
                    jsonObject =jsonParser_staff_profile.uploadImage(""+image_real_path,""+user_id,""+selected_business,
                                              ""+toString,""+toString1,""+toString2,
                                              ""+s,""+toString3,""+toString4,
                                              ""+toString5,""+toString6,
                                              ""+sharedPreferences.getString("staff_id", ""),
                                              ""+s1,""+removeLastChar,""+removeLastChar1,
                                              ""+removeLastChar2,""+removeLastChar3,""+removeLastChar4,getActivity());
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
//Toast.makeText(getActivity(), "Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(),"Profile Saved!",Toast.LENGTH_SHORT).show();

                        getFragmentManager().popBackStack();
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
    private void send_data_add(final String user_id, final String selected_business, final String toString, final String toString1, final String toString2, final String s, final String toString3, final String toString4, final String toString5, final String toString6, final String staff_id, final String s1, final String removeLastChar, final String removeLastChar1, final String removeLastChar2, final String removeLastChar3, final String removeLastChar4) {

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
                    jsonObject =jsonParser_add_staff_profile.uploadImage(image_real_path,""+user_id,""+selected_business,""+toString,
                            ""+toString1,""+toString2,""+s,""+toString3,""+toString4,""+toString5,""+toString6,""+s1,""+removeLastChar,""+removeLastChar1,""+removeLastChar2,""+removeLastChar3,""+removeLastChar4,getActivity());
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
//Toast.makeText(getActivity(), "Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(),"Profile Saved!",Toast.LENGTH_SHORT).show();

                        getFragmentManager().popBackStack();
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

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == Activity.RESULT_OK && data != null) {
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
        }
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
        img_profile_pic.setImageBitmap(bitmap);

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:" + sdk);
        Log.d("HMKCODE", "URI Path:" + uriPath);
        Log.d("HMKCODE", "Real Path: " + realPath);

        image_real_path = file.getAbsolutePath();
    }

    public static String getPath( Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
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
    public static final String CROPPED_IMAGE_FILEPATH = "/sdcard/Monshi_App_cropped.jpg";
    private void startCropImage() {

        CropIntent intent = new CropIntent();

        // Set the source image filepath/URL and output filepath/URL (Optional)
        //intent.setImagePath(imageFile.getAbsolutePath());
        intent.setOutputPath(CROPPED_IMAGE_FILEPATH);

        // Set a fixed crop window size (Optional)
        //intent.setOutputSize(640,480);

        // set the max crop window size (Optional)
        //intent.setMaxOutputSize(800,600);

        // Set a fixed crop window's width/height aspect (Optional)
        //intent.setAspect(3,2);

        // start ImageCropper activity with certain request code and listen for result
        startActivityForResult(intent.getIntent(getActivity()), 0);
    }
    private String ffilepath = null;
    private static String removeLastChar(String str) {

        if(str.equals("")){
            return "";
        }else
            return str.substring(0, str.length() - 1);
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
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
                ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        READ_STORAGE_PERMISSION_REQUEST_CODE);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
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

