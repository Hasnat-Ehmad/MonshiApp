package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
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

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.Notification_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.Notification_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.activity;


public class Notification_Fragment extends Fragment {
    SharedPreferences sharedPreferences;

    ArrayList<Notification_List> notification_lists =new ArrayList();

    ListView listView;

    Notification_List_Adapter notification_list_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        Toolbar mToolbar;
        ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);
        tv_app_name.setText(getActivity().getResources().getString(R.string.app_name));

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.GONE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.GONE);

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


        listView = (ListView) rootView.findViewById(R.id.list_view);




        //http://192.168.100.14/monshiapp/app/notification_list.php
        String url    =  getResources().getString(R.string.url)+"notification_list.php";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                      "&role="+sharedPreferences.getString("user_role", "")+
                      "&staff_id="+sharedPreferences.getString("staff_user_id", "");
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                      "&role="+sharedPreferences.getString("user_role", "")+
                      "&staff_id="+sharedPreferences.getString("staff_user_id", "");
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    notification_lists.clear();
                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray notification_list = jsonObject.getJSONArray("notification_list");

                        for(int i = 0; i < notification_list.length(); i++) {
                            JSONObject c = notification_list.getJSONObject(i);
                            String id = c.getString("id");
                            String notification = c.getString("notification");

                            Notification_List obj = new Notification_List
                                    (""+id,""+notification);

                            notification_lists.add(obj);
                        }

                        notification_list_adapter = new Notification_List_Adapter(activity,notification_lists);

                        listView.setAdapter(notification_list_adapter);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);











        for(int i = 0; i < 15; i++) {




        }




        return rootView;
    }


}
