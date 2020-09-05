package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import info.androidhive.materialdesign.adapter.MangementClass_List_Adapter;
import info.androidhive.materialdesign.lists.ManagmentClass_List;
import info.androidhive.materialdesign.pagerfragmentcontiners.AddNewStaffFragment;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


public class ClassListFragment extends Fragment {

    ArrayList<ManagmentClass_List> managmentClass_lists =new ArrayList();

    ListView listView;

    MangementClass_List_Adapter mangementClass_list_adapter;
    SharedPreferences sharedPreferences;

    ImageView img_add;

    public static String new_class_id="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_class_list, container, false);
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        listView = (ListView) rootView.findViewById(R.id.list_view);

        img_add = rootView.findViewById(R.id.img_add);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("id","");

                new_class_id="";

                AddNewClassFragment fragment = new AddNewClassFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame_layout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

//http://192.168.100.14/monshiapp/app/service_listing.php
        String url    =  getResources().getString(R.string.url)+"class_listing.php";
        String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("selected_business", "");

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray staff_listing = jsonObject.getJSONArray("class_listing");

                        managmentClass_lists.clear();

                        for(int i = 0; i < staff_listing.length(); i++) {
                            JSONObject c = staff_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String user_id = c.getString("user_id");
                            String business_id = c.getString("business_id");
                            String classname = c.getString("classname");
                            String description = c.getString("description");
                            String amount = c.getString("amount");
                            String time = c.getString("time");
                            String buffer_time = c.getString("buffer_time");
                            String role = c.getString("role");


                            ManagmentClass_List obj = new ManagmentClass_List
                                    (""+id,""+classname,""+amount,""+time,""+description,""+description,"2");

                            managmentClass_lists.add(obj);

                        }

                        if (getActivity()!=null){
                            mangementClass_list_adapter = new MangementClass_List_Adapter(getActivity(),managmentClass_lists);
                            listView.setAdapter(mangementClass_list_adapter);
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



        return rootView;
    }
}
