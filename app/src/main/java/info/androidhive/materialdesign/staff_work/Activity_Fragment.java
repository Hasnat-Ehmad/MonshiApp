package info.androidhive.materialdesign.staff_work;

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
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


public class Activity_Fragment extends Fragment {

    SharedPreferences sharedPreferences;

    ArrayList<Staff_Activity_List> staff_activity_lists =new ArrayList();

    ListView listView;

    Staff_Activity_List_Adapter staff_activity_list_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_activity, container, false);


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


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/staff_activity_list.php?user_id=29
        String url    =  getResources().getString(R.string.url)+"staff_activity_list.php";
        String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                         "&business_id="+sharedPreferences.getString("business_id", "");

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray activity_list = jsonObject.getJSONArray("activity_list");

                        for(int i = 0; i < activity_list.length(); i++) {
                            JSONObject c = activity_list.getJSONObject(i);

                            String jalali_date = c.getString("jalali_date");
                            String service_name = c.getString("service_name");
                            String customer_name = c.getString("customer_name");
                            String description = c.getString("description");
                            String amount = c.getString("amount");
                            String isRated = c.getString("isRated");
                            String rating = c.getString("rating");

                            Staff_Activity_List obj = new Staff_Activity_List
                            (""+jalali_date,""+service_name,""+customer_name,
                             ""+description,""+amount,""+isRated,""+rating);

                            staff_activity_lists.add(obj);

                        }
                        if (getActivity()!=null)
                        staff_activity_list_adapter = new Staff_Activity_List_Adapter(getActivity(),staff_activity_lists);

                        listView.setAdapter(staff_activity_list_adapter);
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
