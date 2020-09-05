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
import info.androidhive.materialdesign.adapter.customer_adapter.Customer_Activity_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.customer_list.Customer_Activity_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


public class Customer_ActivitiesFragment extends Fragment {

    SharedPreferences sharedPreferences;

    ArrayList<Customer_Activity_List> customer_activity_lists =new ArrayList();

    ListView listView;

    Customer_Activity_List_Adapter customer_activity_list_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customer_activities, container, false);
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
        img_back.setVisibility(View.GONE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/user_activities.php?user_id=6&role=4
        String url    =  getResources().getString(R.string.url)+"user_activities.php";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&role="+sharedPreferences.getString("user_role", "");
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&role="+sharedPreferences.getString("user_role", "");
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_listing = jsonObject.getJSONArray("activity_listing");

                        for(int i = 0; i < business_listing.length(); i++) {
                            JSONObject c = business_listing.getJSONObject(i);
                            String appt_id = c.getString("appt_id");
                            String bus_id = c.getString("bus_id");
                            String service_id = c.getString("service_id");
                            String customer_id = c.getString("customer_id");
                            String staff_id = c.getString("staff_id");
                            String end_event = c.getString("end_event");
                            String business_name = c.getString("business_name");
                            String staff_name = c.getString("staff_name");
                            String service_name = c.getString("service_name");
                            String customer_name = c.getString("customer_name");
                            String description = c.getString("description");
                            String amount = c.getString("amount");
                            String rating = c.getString("rating");
                            String rated = c.getString("rated");

                            Customer_Activity_List obj = new Customer_Activity_List
                                    (""+appt_id,""+bus_id,""+service_id,""+customer_id,""+staff_id,
                                            ""+end_event,""+business_name,""+staff_name,""+service_name,
                                            ""+customer_name,""+description,""+amount,""+rating,""+rated);

                            customer_activity_lists.add(obj);

                        }

                        if (getActivity()!=null)
                        customer_activity_list_adapter = new Customer_Activity_List_Adapter(getActivity(),customer_activity_lists);
                        listView.setAdapter(customer_activity_list_adapter);

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
