package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import info.androidhive.materialdesign.adapter.Customer_List_Adapter;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.MangementStaff_List_Adapter;
import info.androidhive.materialdesign.lists.Customer_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;


public class CustomerListFragment extends Fragment {

    ArrayList<Customer_List> customer_lists =new ArrayList();
    ListView listView;
    Customer_List_Adapter customer_list_adapter;

    ImageView img_add;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            ImageView img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.VISIBLE);

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getFragmentManager()).popBackStack();
                    }
                }
            });
            img_notifications.setVisibility(View.VISIBLE);
        }else {

            Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
            TextView tv_app_name;

            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

            tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
            tv_app_name.setVisibility(View.GONE);

            img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
            img_profile_tool_bar.setVisibility(View.VISIBLE);

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
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customer_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view);

        img_add = (ImageView) rootView.findViewById(R.id.img_add);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewCustomerFragment fragment = new AddNewCustomerFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        //http://192.168.100.14/monshiapp/app/customer_listing.php
        String url    =  getResources().getString(R.string.url)+"customer_listing.php";
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
                        JSONArray staff_listing = jsonObject.getJSONArray("customer_listing");

                        customer_lists.clear();

                        for(int i = 0; i < staff_listing.length(); i++) {
                            JSONObject c = staff_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String user_id = c.getString("user_id");
                            String business_id = c.getString("business_id");
                            String booked_for = c.getString("booked_for");
                            String customer_image = c.getString("customer_image");
                            String customer_name = c.getString("customer_name");
                            String customer_email = c.getString("customer_email");
                            String customer_phone = c.getString("customer_phone");
                            String customer_address = c.getString("customer_address");


                            Customer_List obj = new Customer_List
                                    (""+id,""+user_id,""+business_id,""+booked_for,""+customer_image,
                                     ""+customer_name,""+customer_email,""+customer_phone,""+customer_address);

                            customer_lists.add(obj);

                        }

                        if (getActivity()!=null)
                        customer_list_adapter = new Customer_List_Adapter(getActivity(),customer_lists);

                        listView.setAdapter(customer_list_adapter);
                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);


        return rootView;
    }

}
