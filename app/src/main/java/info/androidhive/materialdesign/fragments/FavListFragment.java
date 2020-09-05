package info.androidhive.materialdesign.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Customer_List_Adapter;
import info.androidhive.materialdesign.adapter.Fav_List_Adapter;
import info.androidhive.materialdesign.lists.Customer_List;
import info.androidhive.materialdesign.lists.Fav_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;


public class FavListFragment extends Fragment {

    ArrayList<Fav_List> fav_lists =new ArrayList();
    ListView listView;
    Fav_List_Adapter fav_list_adapter;

    //ImageView img_add;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        img_back.setVisibility(View.VISIBLE);
        tv_title.setText(getString(R.string.str_favorites));

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fav_list, container, false);

       img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        img_notifications.setVisibility(View.VISIBLE);

        listView = (ListView) rootView.findViewById(R.id.list_view);

       /* img_add = (ImageView) rootView.findViewById(R.id.img_add);
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
        });*/


        //http://192.168.100.14/monshiapp/app/customer_listing.php
        String url    =  getResources().getString(R.string.url)+"favourite_listing.php";
        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params =  "user_id="+sharedPreferences.getString("user_id", "");
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params =  "user_id="+sharedPreferences.getString("user_id", "");
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray staff_listing = jsonObject.getJSONArray("fav_listing");

                        fav_lists.clear();

                        for(int i = 0; i < staff_listing.length(); i++) {
                            JSONObject c = staff_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String fav_id = c.getString("fav_id");
                            String type = c.getString("type");
                            String staff_id = c.getString("staff_id");
                            String name = c.getString("name");
                            String contact = c.getString("contact");
                            String description = c.getString("description");
                            String image = c.getString("image");
                            String business_id = c.getString("business_id");


                            Fav_List obj = new Fav_List
                                    (""+id,""+fav_id,""+type,""+business_id,""+staff_id,""+contact,
                                     ""+name,""+description,""+image);

                            fav_lists.add(obj);

                        }

                        fav_list_adapter = new Fav_List_Adapter(getActivity(),fav_lists);

                        listView.setAdapter(fav_list_adapter);
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
