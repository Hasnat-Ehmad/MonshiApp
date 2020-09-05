package info.androidhive.materialdesign.fragments;

/**
 * Created by Ravi on 29/07/15.
 */
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Business_List_Adapter;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.MangementStaff_List_Adapter;
import info.androidhive.materialdesign.lists.Business_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;


public class DashboardFragment extends Fragment {
    SharedPreferences sharedPreferences;
    Button btn_calendar;
    PersianDatePickerDialog picker;

    ArrayList<Dashboard_List> dashboard_lists =new ArrayList();

    ListView listView;

    Dashboard_List_Adapter dashboard_list_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        img_notifications.setVisibility(View.VISIBLE);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/dashboard_activity_listing.php
        String url    =  getResources().getString(R.string.url)+"dashboard_activity_listing.php";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                      "&business_id="+sharedPreferences.getString("selected_business", "");
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                      "&business_id="+sharedPreferences.getString("business_id", "");
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_listing = jsonObject.getJSONArray("business_listing");

                        for(int i = 0; i < business_listing.length(); i++) {
                            JSONObject c = business_listing.getJSONObject(i);
                            String end_event = c.getString("end_event");
                            String appointment_id = c.getString("appointment_id");
                            String service_name = c.getString("service_name");
                            String staff_name = c.getString("staff_name");
                            String booked_by = c.getString("booked_by");
                            String is_rated = c.getString("is_rated");
                            String rating = c.getString("rating");
                            String status = c.getString("status");

                            Dashboard_List obj = new Dashboard_List
                                    (""+end_event,""+appointment_id,""+service_name,
                                    ""+staff_name,""+booked_by,""+is_rated,""+rating,
                                    ""+status
                                    );

                            dashboard_lists.add(obj);

                        }

                        dashboard_list_adapter = new Dashboard_List_Adapter(getActivity(),dashboard_lists);

                        listView.setAdapter(dashboard_list_adapter);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);


                // Inflate the layout for this fragment
        return rootView;
    }

}


/*btn_calendar = (Button) rootView.findViewById(R.id.btn_calendar);

        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker = new PersianDatePickerDialog(getActivity())
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
//                        .setInitDate(initDate)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setActionTextColor(Color.GRAY)
//                        .setTypeFace(typeface)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                Toast.makeText(getActivity(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();
            }
        });*/