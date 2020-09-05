package info.androidhive.materialdesign.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.MangementStaff_List_Adapter;
import info.androidhive.materialdesign.adapter.NewServiceStaff_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;
import static info.androidhive.materialdesign.adapter.NewServiceStaff_List_Adapter.positionArray;


public class AddNewServiceFragment extends Fragment {

    ArrayList<ManagmentService_List> managmentService_lists =new ArrayList();//using previous classes

    ListView listView;

    NewServiceStaff_List_Adapter newServiceStaff_list_adapter;
    SharedPreferences sharedPreferences;

    EditText ed_service_name,ed_service_fee,ed_service_description;

    Spinner spinner_service_term,spinner_waiting_time,spinner_pay_online;

    String [] duration_value,duration_persian;
    String [] buffer_value,buffer_persian;

    Button btn_submit;

    int api_call_check;

    String service_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //spinner_business_list.setVisibility(View.GONE);
        // Inflate the layout for this fragment

        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        img_notifications.setVisibility(View.VISIBLE);

        View rootView = inflater.inflate(R.layout.fragment_add_new_service, container, false);

        ed_service_name = rootView.findViewById(R.id.ed_service_name);
        ed_service_fee  = rootView.findViewById(R.id.ed_service_fee);
        ed_service_description = rootView.findViewById(R.id.ed_service_description);

        spinner_service_term = rootView.findViewById(R.id.spinner_service_term);
        spinner_waiting_time = rootView.findViewById(R.id.spinner_waiting_time);
        spinner_pay_online   = rootView.findViewById(R.id.spinner_pay_online);

        btn_submit = rootView.findViewById(R.id.btn_submit);


        listView = (ListView) rootView.findViewById(R.id.list_view);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        //http://192.168.100.14/monshiapp/app/staff_listing.php

        if (getArguments() != null) {

            api_call_check=1;

            service_id = getArguments().getString("service_id");
            //Toast.makeText(getActivity(),"service_id = "+service_id,Toast.LENGTH_SHORT).show();
            //http://192.168.100.14/monshiapp/app/service_detail_info.php?bus_id=2&service_id=1
            String url    =  getResources().getString(R.string.url)+"service_detail_info.php";

            String params = "";

            if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                params = "service_id="+service_id+
                        "&bus_id="+sharedPreferences.getString("selected_business", "");
            }else {
                params = "service_id="+service_id+
                        "&bus_id="+sharedPreferences.getString("business_id", "");
            }

       /* String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("business_id", "");*/

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {
                        managmentService_lists.clear();
                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getString("status").equals("200")) {


                            String servicename = jsonObject.getString("servicename");

                            ed_service_name.setText(""+servicename);

                            JSONArray duration = jsonObject.getJSONArray("duration");

                            duration_value    = new String [duration.length()];
                            duration_persian      = new String [duration.length()];

                            int duration_value_selected = 0;

                            for(int i = 0; i < duration.length(); i++) {
                                JSONObject c = duration.getJSONObject(i);
                                String select = c.getString("select");
                                String duration_time = c.getString("duration");
                                String duration_per = c.getString("duration_per");

                                if (i==0){
                                    duration_persian[i] = "مدت خدمات";
                                }else {
                                    duration_value   [i]  = duration_time;
                                    duration_persian [i]  = duration_per;
                                }

                                if (select.equals("yes")){
                                    duration_value_selected=i;
                                }
                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, duration_persian) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);

                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
                                    //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                    return v;
                                }
                            };

                            spinner_service_term.setAdapter(adapter);
                            spinner_service_term.setSelection(duration_value_selected);


                            JSONArray buffer = jsonObject.getJSONArray("buffer");

                            buffer_value   = new String [buffer.length()];
                            buffer_persian = new String [buffer.length()];

                            int buffer_value_selected = 0;

                            for(int i = 0; i < buffer.length(); i++) {
                                JSONObject c = buffer.getJSONObject(i);
                                String select = c.getString("select");
                                String buffer_duration = c.getString("buffer_duration");
                                String buffer_per = c.getString("buffer_per");

                                if (i==0){
                                    buffer_persian [i] = "زمان انتظار";
                                }else {
                                    buffer_value   [i]  = buffer_duration;
                                    buffer_persian [i]  = buffer_per;
                                }

                                if (select.equals("yes")){
                                    buffer_value_selected=i;
                                }

                            }

                            ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, buffer_persian) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);

                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
                                    //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                    return v;
                                }
                            };

                            spinner_waiting_time.setAdapter(adapter_);
                            spinner_waiting_time.setSelection(buffer_value_selected);

                            String amount = jsonObject.getString("amount");
                            ed_service_fee.setText(""+amount);
                            String role = jsonObject.getString("role");

                            if (role.equals("1")){
                                spinner_pay_online.setSelection(1);//yes
                            }else {
                                spinner_pay_online.setSelection(2);//no
                            }

                            String description = jsonObject.getString("description");
                            ed_service_description.setText(""+description);

                            //Toast.makeText(getActivity(),"description = "+description,Toast.LENGTH_SHORT).show();

                            JSONArray staff_list = jsonObject.getJSONArray("staff_list");
                            for(int i = 0; i < staff_list.length(); i++) {
                                JSONObject c = staff_list.getJSONObject(i);
                                String full_name = c.getString("full_name");
                                String image_url = c.getString("image_url");
                                String id = c.getString("id");
                                String select = c.getString("select");


                                ManagmentService_List obj = new ManagmentService_List
                                        (""+id,""+full_name,"","",""+image_url,""+select,"");
                                managmentService_lists.add(obj);

                            }

                            newServiceStaff_list_adapter = new NewServiceStaff_List_Adapter(getActivity(),managmentService_lists);

                            listView.setAdapter(newServiceStaff_list_adapter);
                            setDynamicHeight(listView);
                        }
                        else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall.execute(url, "POST", params);




        }else {

            String url    =  getResources().getString(R.string.url)+"staff_listing.php";

            String params = "";

            if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                params = "user_id="+sharedPreferences.getString("user_id", "")+
                        "&business_id="+sharedPreferences.getString("selected_business", "");
            }else {
                params = "user_id="+sharedPreferences.getString("user_id", "")+
                        "&business_id="+sharedPreferences.getString("business_id", "");
            }


            managmentService_lists.clear();

       /* String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("business_id", "");*/

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

                                ManagmentService_List obj = new ManagmentService_List
                                        (""+id,""+full_name,"","",""+image,"","");
                                managmentService_lists.add(obj);

                            }

                            newServiceStaff_list_adapter = new NewServiceStaff_List_Adapter(getActivity(),managmentService_lists);

                            listView.setAdapter(newServiceStaff_list_adapter);
                            setDynamicHeight(listView);
                        }
                        else{

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            webRequestCall.execute(url, "POST", params);


            service_time();//setting time here

        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(getActivity(),"service_id = "+service_id,Toast.LENGTH_SHORT).show();

                if (ed_service_name.getText().toString().equals("")){
                    ed_service_name.setError("Empty");

                }else if (ed_service_fee.getText().toString().equals("")){
                    ed_service_fee.setError("Empty");

                } else if(spinner_service_term.getSelectedItem().toString().equals("مدت خدمات")){
                    Toast.makeText(getActivity(),"please select service time",Toast.LENGTH_SHORT).show();

                }else if(spinner_waiting_time.getSelectedItem().toString().equals("زمان انتظار")){
                    Toast.makeText(getActivity(),"please select waiting time",Toast.LENGTH_SHORT).show();

                }else if(spinner_pay_online.getSelectedItem().toString().equals("اجازه پرداخت آنلاین")){
                    Toast.makeText(getActivity(),"please select pay method",Toast.LENGTH_SHORT).show();
                }
                else {
                    String online = spinner_pay_online.getSelectedItem().toString();
                    String online_value;

                    if (online.equals("بله")){
                        online_value="1";
                    }else {
                        online_value="0";
                    }



                    if (api_call_check==1) {

                        String checkbox_ids="";

                        for (int j=0 ;j<positionArray.size();j++){

                            if (positionArray.get(j)) {
                                //Toast.makeText(getActivity(), "" + order_lists.get(j).getRestaurant_id(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getActivity(),"item_number = "+i,Toast.LENGTH_SHORT).show();
                                checkbox_ids += (managmentService_lists.get(j).getId()) + ",";
                            }
                        }

/*
http://192.168.100.14/monshiapp/app/update_service.php

user_id
business_id
service_id
staff_list //comma separeted
roll // 1 fot yes and 0 for no
service_note
service_name
service_cost
service_duration
service_time
*/
                        //http://192.168.100.14/monshiapp/app/update_service.php
                        String url    =  getResources().getString(R.string.url)+"update_service.php";

                        String params = "";
                        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                            //spinner_business_list.setVisibility(View.GONE);
                            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                                    "&service_id="+service_id+
                                    "&staff_list="+removeLastChar(checkbox_ids)+
                                    "&roll="+online_value+
                                    "&service_note="+ed_service_description.getText().toString()+
                                    "&service_name="+ed_service_name.getText().toString()+
                                    "&service_cost="+ed_service_fee.getText().toString()+
                                    "&service_duration="+duration_value[spinner_service_term.getSelectedItemPosition()]+
                                    "&service_time="+buffer_value[spinner_waiting_time.getSelectedItemPosition()]
                            ;
                        }else {
                            //spinner_business_list.setVisibility(View.VISIBLE);
                            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("business_id", "")+
                                    "&service_id="+service_id+
                                    "&staff_list="+removeLastChar(checkbox_ids)+
                                    "&roll="+online_value+
                                    "&service_note="+ed_service_description.getText().toString()+
                                    "&service_name="+ed_service_name.getText().toString()+
                                    "&service_cost="+ed_service_fee.getText().toString()+
                                    "&service_duration="+duration_value[spinner_service_term.getSelectedItemPosition()]+
                                    "&service_time="+buffer_value[spinner_waiting_time.getSelectedItemPosition()]
                            ;
                        }

                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {
                                        Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();

                                    }
                                    else{
                                        Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        webRequestCall.execute(url, "POST", params);

                    }else {
                        String checkbox_ids="";

                        for (int j=0 ;j<positionArray.size();j++){

                            if (positionArray.get(j)) {
                                //Toast.makeText(getActivity(), "" + order_lists.get(j).getRestaurant_id(), Toast.LENGTH_SHORT).show();
                                //Toast.makeText(getActivity(),"item_number = "+i,Toast.LENGTH_SHORT).show();
                                checkbox_ids += (managmentService_lists.get(j).getId()) + ",";
                            }
                        }



//Add Service API
//http://192.168.100.14/monshiapp/app/add_service.php
//
//parameters :
//
//user_id
//business_id
//service_name
//service_duration
//service_cost
//roll // 1 for yes and 0 for no
//staff_list //comma separated

                        //http://192.168.100.14/monshiapp/app/add_service.php
                        String url    =  getResources().getString(R.string.url)+"add_service.php";

                        String params = "";
                        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                            //spinner_business_list.setVisibility(View.GONE);
                            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                                    "&service_name="+ed_service_name.getText().toString()+
                                    "&service_duration="+duration_value[spinner_service_term.getSelectedItemPosition()]+
                                    "&service_time="+buffer_value[spinner_waiting_time.getSelectedItemPosition()]+
                                    "&service_cost="+ed_service_fee.getText().toString()+
                                    "&roll="+online_value+
                                    "&service_note="+ed_service_description.getText().toString()+
                                    "&staff_list="+removeLastChar(checkbox_ids)
                            ;
                        }else {
                            //spinner_business_list.setVisibility(View.VISIBLE);
                            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("business_id", "")+
                                    "&service_name="+ed_service_name.getText().toString()+
                                    "&service_duration="+duration_value[spinner_service_term.getSelectedItemPosition()]+
                                    "&service_time="+buffer_value[spinner_waiting_time.getSelectedItemPosition()]+
                                    "&service_cost="+ed_service_fee.getText().toString()+
                                    "&roll="+online_value+
                                    "&service_note="+ed_service_description.getText().toString()+
                                    "&staff_list="+removeLastChar(checkbox_ids);
                        }

                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {
                                        Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                        getFragmentManager().popBackStack();

                                    }
                                    else{
                                        Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        webRequestCall.execute(url, "POST", params);

                    }

                }

            }
        });




        return rootView;
    }

    public void setDynamicHeight(ListView listView) {
        //Checkout_List_Adapter adapter = listView.getAdapter();
        //check adapter if null
        if (newServiceStaff_list_adapter.getCount() == 0) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getHeight(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < newServiceStaff_list_adapter.getCount(); i++) {
            View listItem = newServiceStaff_list_adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = height + (listView.getDividerHeight() * (newServiceStaff_list_adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }

    private static String removeLastChar(String str) {

        if(str.equals("")){
            return "";
        }else
            return str.substring(0, str.length() - 1);
    }

    public String ArabicToEnglish(int value) {
        String newValue = (((((((((((value+"")
                .replaceAll("١", "1")).replaceAll("٢", "2"))
                .replaceAll("٣", "3")).replaceAll("٤", "4"))
                .replaceAll("٥", "5")).replaceAll("٦", "6"))
                .replaceAll("٧", "7")).replaceAll("٨", "8"))
                .replaceAll("٩", "9")).replaceAll("٠", "0"));
        return newValue;
    }

    public void service_time(){

        //http://192.168.100.14/monshiapp/app/service_duration_buffer_arr.php
        String url    =  getResources().getString(R.string.url)+"service_duration_buffer_arr.php";

        String params = "";

        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            params = "service_id="+service_id+
                    "&bus_id="+sharedPreferences.getString("selected_business", "");
        }else {
            params = "service_id="+service_id+
                    "&bus_id="+sharedPreferences.getString("business_id", "");
        }

       /* String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("business_id", "");*/

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {


                        JSONArray duration = jsonObject.getJSONArray("duration");

                        duration_value    = new String [duration.length()];
                        duration_persian      = new String [duration.length()];

                        int duration_value_selected = 0;

                        for(int i = 0; i < duration.length(); i++) {
                            JSONObject c = duration.getJSONObject(i);
                            String select = c.getString("select");
                            String duration_time = c.getString("duration");
                            String duration_per = c.getString("duration_per");

                            if (i==0){
                                duration_persian[i] = "مدت خدمات";
                            }else {
                                duration_value   [i]  = duration_time;
                                duration_persian [i]  = duration_per;
                            }



                            if (select.equals("yes")){
                                duration_value_selected=i;
                            }

                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.my_spinner_style, duration_persian) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_service_term.setAdapter(adapter);
                        spinner_service_term.setSelection(duration_value_selected);


                        JSONArray buffer = jsonObject.getJSONArray("buffer");

                        buffer_value   = new String [buffer.length()];
                        buffer_persian = new String [buffer.length()];

                        int buffer_value_selected = 0;

                        for(int i = 0; i < buffer.length(); i++) {
                            JSONObject c = buffer.getJSONObject(i);
                            String select = c.getString("select");
                            String buffer_duration = c.getString("buffer_duration");
                            String buffer_per = c.getString("buffer_per");

                            if (i==0){
                                buffer_persian [i] = "زمان انتظار";
                            }else {
                                buffer_value   [i]  = buffer_duration;
                                buffer_persian [i]  = buffer_per;
                            }



                            if (select.equals("yes")){
                                buffer_value_selected=i;
                            }

                        }

                        ArrayAdapter<String> adapter_ = new ArrayAdapter<String>(getActivity(),
                                R.layout.my_spinner_style, buffer_persian) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_waiting_time.setAdapter(adapter_);
                        spinner_waiting_time.setSelection(buffer_value_selected);

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
