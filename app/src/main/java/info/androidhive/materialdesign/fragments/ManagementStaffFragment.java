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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.MangementStaff_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.pagerfragmentcontiners.AddNewStaffFragment;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;

/**
 * Created by Ravi on 29/07/15.
 */
public class ManagementStaffFragment extends Fragment {

    ArrayList<ManagmentStaff_List> managmentStaff_lists =new ArrayList();

    ListView listView;

    MangementStaff_List_Adapter mangementStaff_list_adapter;

    ImageView img_add;
    SharedPreferences sharedPreferences;
    ImageView img_back,img_profile_tool_bar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
     /*   if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
            spinner_business_list.setVisibility(View.GONE);
        }else {
            spinner_business_list.setVisibility(View.VISIBLE);
        }*/
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);

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

        img_add = (ImageView) rootView.findViewById(R.id.img_add);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewStaffFragment fragment = new AddNewStaffFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        listView = (ListView) rootView.findViewById(R.id.list_view);

        // Inflate the layout for this fragment


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        managmentStaff_lists.clear();


        //http://192.168.100.14/monshiapp/app/staff_listing.php
        String url    =  getResources().getString(R.string.url)+"staff_listing.php";
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
                        JSONArray staff_listing = jsonObject.getJSONArray("staff_listing");


                        for(int i = 0; i < staff_listing.length(); i++) {
                            JSONObject c = staff_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String user_id = c.getString("user_id");
                            String business_id = c.getString("business_id");
                            String username = c.getString("username");
                            String full_name = c.getString("full_name");
                            String role = c.getString("role");
                            String expertise = c.getString("expertise");
                            String experience = c.getString("experience");
                            String email = c.getString("email");
                            String mobile = c.getString("mobile");
                            String image = c.getString("image");

                            ManagmentStaff_List obj = new ManagmentStaff_List
                                    (""+id,""+username,""+email,""+mobile,"",""+image);

                            managmentStaff_lists.add(obj);

                        }

                        if (getActivity()!=null)
                            mangementStaff_list_adapter = new MangementStaff_List_Adapter(getActivity(),managmentStaff_lists);

                        listView.setAdapter(mangementStaff_list_adapter);
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
}
