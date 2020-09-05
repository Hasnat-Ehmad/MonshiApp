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
import info.androidhive.materialdesign.adapter.Appointments_List_Adapter;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.Appointments_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


public class AppointmentListFragment extends Fragment {

    SharedPreferences sharedPreferences;


    ArrayList<Appointments_List> appointments_lists =new ArrayList();

    ListView listView;

    Appointments_List_Adapter appointments_list_adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_appointment_list, container, false);

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

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/fetch_appt_admin.php?user_id=1&business_id=2&role=1
        String url    = "";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            url    =  getResources().getString(R.string.url)+"fetch_appt_admin.php";

            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                    "&role="+sharedPreferences.getString("user_role", "");
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            url    =  getResources().getString(R.string.url)+"fetch_appt_customer.php";

            params =  "user_id="+sharedPreferences.getString("user_id", "");
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {

                        appointments_lists.clear();
                        JSONArray appointment_list = jsonObject.getJSONArray("appointment_list");

                        for(int i = 0; i < appointment_list.length(); i++) {
                            JSONObject c = appointment_list.getJSONObject(i);
                            String id    = c.getString("id");
                            String title = c.getString("title");
                            String start = c.getString("start");
                            String end   = c.getString("end");
                            String business_id = c.getString("business_id");
                            String customer_id = c.getString("customer_id");
                            String staff_id    = c.getString("staff_id");
                            String staff_color = c.getString("staff_color");
                            String user_id     = c.getString("user_id");
                            String eStart      = c.getString("eStart");
                            String eEnd        = c.getString("eEnd");
                            String staff       = c.getString("staff");
                            String customer    = c.getString("customer");
                            String business    = c.getString("business");
                            String business_image    = c.getString("business_image");
                            String service     = c.getString("service");
                            String service_id  = c.getString("service_id");
                            String editable    = c.getString("editable");
                            String service_time= c.getString("service_time");
                            String check_multiple_staff = c.getString("check_multiple_staff");

                            Appointments_List obj = new Appointments_List
                                    (""+id,""+title,""+start,""+end, ""+business_id,
                                     ""+customer_id,""+staff_id,""+staff_color,
                                     ""+user_id,""+eStart,""+eEnd,""+staff,
                                     ""+customer,""+business,""+service,""+business_image,""+service_id,
                                     ""+editable,""+service_time,""+check_multiple_staff
                                    );

                            appointments_lists.add(obj);

                        }

                        if (getActivity()!=null)
                        appointments_list_adapter = new Appointments_List_Adapter(getActivity(),appointments_lists);

                        listView.setAdapter(appointments_list_adapter);
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
