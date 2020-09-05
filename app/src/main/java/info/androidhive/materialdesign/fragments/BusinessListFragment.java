package info.androidhive.materialdesign.fragments;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.MainActivity;
import info.androidhive.materialdesign.adapter.Business_List_Adapter;
import info.androidhive.materialdesign.lists.Business_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_filter;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_menu;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;


public class BusinessListFragment extends Fragment {

    ArrayList<Business_List> business_lists =new ArrayList();

    ListView listView;

    Business_List_Adapter business_list_adapter;

    ImageView add_business;

    private FragmentActivity fragmentActivity;

    SharedPreferences sharedPreferences;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //spinner_business_list.setVisibility(View.GONE);
        // Inflate the layout for this fragment
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.fragment_business_list, container, false);
        fragmentActivity = (FragmentActivity) getActivity();

        img_filter.setVisibility(View.GONE);

        tv_title.setVisibility(View.GONE);
        img_menu.setVisibility(View.GONE);
        img_notifications.setVisibility(View.VISIBLE);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        /*tv_title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.monshiapp_logo, 0, 0, 0);
        tv_title.setGravity(View.TEXT_ALIGNMENT_TEXT_START);*/

        add_business = rootView.findViewById(R.id.add_business);
        add_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CreateBusinessAccountFragment tab1= new CreateBusinessAccountFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();

            }
        });

        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/business_listing.php?user_id=1
        String url    =  getResources().getString(R.string.url)+"business_listing.php";
        String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                         "&screen=2";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {

                        business_lists.clear();

                        JSONArray business_listing = jsonObject.getJSONArray("business_listing");

                        for(int i = 0; i < business_listing.length(); i++) {
                            JSONObject c = business_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String userID = c.getString("userID");

                            String name = c.getString("name");
                            String state = c.getString("state");
                            String state_name = c.getString("state_name");
                            String state_name_per = c.getString("state_name_per");
                            String city = c.getString("city");
                            String city_name = c.getString("city_name");
                            String city_name_per = c.getString("city_name_per");
                            String description = c.getString("description");
                            String contact = c.getString("contact");
                            String address = c.getString("address");
                            String latitude = c.getString("latitude");
                            String longitude = c.getString("longitude");

                            String business_type = c.getString("business_type");

                            String category_name_per = c.getString("category_name_per");
                            String category = c.getString("category");
                            String category_name = c.getString("category_name");
                            String rating = c.getString("rating");

                            String sub_cat = c.getString("sub_cat");
                            String key_staff = c.getString("key_staff");
                            String key_services = c.getString("key_services");
                            String business_id = c.getString("business_id");

                            String image = c.getString("image");

                            Business_List obj = new Business_List
                                    (""+id,""+userID,""+name,""+state,""+state_name,""+state_name_per,
                                            ""+city, ""+city_name,""+city_name_per,""+description,""+contact,
                                            ""+address,""+latitude,""+longitude,""+business_type,""+category, ""+category_name_per,
                                            ""+category_name, ""+rating,""+sub_cat,""+key_staff,""+key_services,
                                            ""+business_id, ""+image);

                            business_lists.add(obj);

                        }

                        if (getActivity()!=null)
                        business_list_adapter = new Business_List_Adapter(getActivity(),business_lists);

                        listView.setAdapter(business_list_adapter);

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


/*
        BusinessDetailFragment fragment = new BusinessDetailFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fragment);
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        fragmentTransaction.commit();

        */