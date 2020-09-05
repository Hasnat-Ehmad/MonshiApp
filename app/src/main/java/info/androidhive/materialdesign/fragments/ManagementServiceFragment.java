package info.androidhive.materialdesign.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MangementService_List_Adapter;
import info.androidhive.materialdesign.adapter.MangementStaff_List_Adapter;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;

/**
 * Created by Ravi on 29/07/15.
 */
public class ManagementServiceFragment extends Fragment {

    ArrayList<ManagmentService_List> managmentService_lists =new ArrayList();

    ListView listView;

    MangementService_List_Adapter mangementService_list_adapter;

    ImageView img_add;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            ImageView img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.VISIBLE);

            TextView tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
            tv_app_name.setVisibility(View.GONE);

            img_notifications.setVisibility(View.VISIBLE);

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getFragmentManager()).popBackStack();
                    }
                }
            });
        }else {

            Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;

            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

            TextView tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
            tv_app_name.setVisibility(View.GONE);

            img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
            img_profile_tool_bar.setVisibility(View.VISIBLE);

            img_notifications = mToolbar.findViewById(R.id.img_notifications);
            img_notifications.setVisibility(View.VISIBLE);

            img_filter = mToolbar.findViewById(R.id.img_filter);
            img_filter.setVisibility(View.GONE);

            img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.GONE);
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
        }




        TextView tv_label_dashboard = rootView.findViewById(R.id.tv_label_dashboard);
        tv_label_dashboard.setText(R.string.str_service_list);

        img_add = (ImageView) rootView.findViewById(R.id.img_add);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewServiceFragment fragment = new AddNewServiceFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/service_listing.php
        String url    =  getResources().getString(R.string.url)+"service_listing.php";
        String params =  "";

        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){

            params = "user_id="+sharedPreferences.getString("user_id", "")+
                         "&business_id="+sharedPreferences.getString("selected_business", "");

        }else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){

            params = "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+sharedPreferences.getString("business_id", "");
        }



        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray staff_listing = jsonObject.getJSONArray("service_listing");


                        for(int i = 0; i < staff_listing.length(); i++) {
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


                            if(!id.equals("0")){
                                ManagmentService_List obj = new ManagmentService_List
                                        (""+id,""+servicename,""+amount,""+time,"","",""+description);

                                managmentService_lists.add(obj);

                            }

                        }

                        if (getActivity()!=null)
                        mangementService_list_adapter = new MangementService_List_Adapter(getActivity(),managmentService_lists);

                        listView.setAdapter(mangementService_list_adapter);
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

    @Override
    public void onResume() {
        super.onResume();
        managmentService_lists.clear();
    }
}
