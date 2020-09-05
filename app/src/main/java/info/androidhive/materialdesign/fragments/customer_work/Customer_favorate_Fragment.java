package info.androidhive.materialdesign.fragments.customer_work;

import android.content.SharedPreferences;
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
import info.androidhive.materialdesign.adapter.customer_adapter.Customer_frvt_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.customer_list.Customer_frvt_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


public class Customer_favorate_Fragment extends Fragment {

    ArrayList<Customer_frvt_List> customer_frvt_lists =new ArrayList();

    ListView listView;

    Customer_frvt_List_Adapter customer_frvt_list_adapter;

    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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

        View rootView = inflater.inflate(R.layout.fragment_customer_favorate, container, false);

        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/favourite_listing.php?user_id=1
        String url    =  getResources().getString(R.string.url)+"favourite_listing.php";
        String params =  "user_id="+sharedPreferences.getString("user_id", "");

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_listing = jsonObject.getJSONArray("fav_listing");



                        for(int i = 0; i < business_listing.length(); i++) {
                            JSONObject c = business_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String type = c.getString("type");
                            String staff_id = c.getString("staff_id");
                            String business_id = c.getString("business_id");
                            String name = c.getString("name");
                            String contact = c.getString("contact");
                            String description = c.getString("description");
                            String image = c.getString("image");


                            Customer_frvt_List obj = new Customer_frvt_List
                                    (""+business_id,""+name,""+contact,""+image,""+type);

                            customer_frvt_lists.add(obj);

                        }

                        customer_frvt_list_adapter = new Customer_frvt_List_Adapter(getActivity(),customer_frvt_lists);

                        listView.setAdapter(customer_frvt_list_adapter);
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
