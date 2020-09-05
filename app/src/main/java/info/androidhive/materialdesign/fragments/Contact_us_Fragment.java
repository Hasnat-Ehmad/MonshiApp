package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MenuActivity.img_back;
import static info.androidhive.materialdesign.activity.MenuActivity.toolbar_menu;


public class Contact_us_Fragment extends Fragment {

    String [] support_id,title_per;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView =  inflater.inflate(R.layout.fragment_contact_us, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        Toolbar mToolbar;
        ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);
        tv_app_name.setText(getActivity().getResources().getString(R.string.app_name));

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.INVISIBLE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);

        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.INVISIBLE);

        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        final EditText ed_name  = rootView.findViewById(R.id.ed_name);
        final EditText ed_email = rootView.findViewById(R.id.ed_email);
        final EditText ed_phone = rootView.findViewById(R.id.ed_phone);
        final EditText ed_description = rootView.findViewById(R.id.ed_description);


        final TextInputLayout ed_email_layout = rootView.findViewById(R.id.ed_email_layout);
        final TextInputLayout ed_phone_layout = rootView.findViewById(R.id.ed_phone_layout);
                        ed_phone_layout.setVisibility(View.GONE);

        final Spinner spinner_contact_us = rootView.findViewById(R.id.spinner_contact_us);
        spinner_contact_us.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity(),""+position,Toast.LENGTH_SHORT).show();

                if (position==0){
                    ed_email_layout.setVisibility(View.VISIBLE);
                    ed_phone_layout.setVisibility(View.GONE);
                }else {
                    ed_email_layout.setVisibility(View.GONE);
                    ed_phone_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        final Spinner spinner_support_type = rootView.findViewById(R.id.spinner_support_type);

        //http://192.168.100.14/monshiapp/app/support_listing.php
        String url    =  getResources().getString(R.string.url)+"support_listing.php";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray support_listing = jsonObject.getJSONArray("support_listing");

                        support_id= new String[support_listing.length()];
                        title_per = new String[support_listing.length()];

                        for(int i = 0; i < support_listing.length(); i++) {
                            JSONObject c = support_listing.getJSONObject(i);
                             support_id[i]= c.getString("support_id");
                             String title = c.getString("title");
                             title_per[i] = c.getString("title_per");
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                R.layout.my_spinner_style, title_per) {

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

                        spinner_support_type.setAdapter(adapter);

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST","");


        Button btn_confirm = rootView.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (sharedPreferences.getString("user_id", "").equals("")){

                    Toast.makeText(getContext(), "Please Signin in the Application", Toast.LENGTH_SHORT).show();

                }else if(ed_name.getText().toString().equals("")){

                    ed_name.setError("Empty");

                }else if (ed_description.getText().toString().equals("")){

                    ed_description.setError("Empty");

                }else if(spinner_contact_us.getSelectedItem().toString().equals("پست الکترونیک")) {

                    if (ed_email.getText().toString().equals("")){
                        ed_email.setError("Empty");
                    }else {
                        //http://192.168.100.14/monshiapp/app/contact_us.php
                        String url =  getResources().getString(R.string.url)+"contact_us.php";

                        String   params =
                                        "user_id="+sharedPreferences.getString("user_id", "")+
                                        "&name="+ed_name.getText().toString()+
                                        "&contactway="+spinner_contact_us.getSelectedItem().toString()+
                                        "&message="+ed_description.getText().toString()+
                                        "&support_id="+support_id[spinner_support_type.getSelectedItemPosition()]+
                                        "&phone="+ed_phone.getText().toString()+
                                        "&email="+ed_email.getText().toString()+
                                        "&subject="+spinner_support_type.getSelectedItem().toString();

                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {
                                        getActivity().getFragmentManager().popBackStack();
                                    }
                                    else{
                                        Toast.makeText(getContext(), ""+jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        webRequestCall.execute(url, "POST", params);
                    }

                }else if(spinner_contact_us.getSelectedItem().toString().equals("تلفن")) {
                    if (ed_phone.getText().toString().equals("")){
                        ed_phone.setError("Empty");
                    }else {

                        //http://192.168.100.14/monshiapp/app/contact_us.php
                        String url =  getResources().getString(R.string.url)+"contact_us.php";

                        String   params =
                                "user_id="+sharedPreferences.getString("user_id", "")+
                                        "&name="+ed_name.getText().toString()+
                                        "&contactway="+spinner_contact_us.getSelectedItem().toString()+
                                        "&message="+ed_description.getText().toString()+
                                        "&support_id="+support_id[spinner_support_type.getSelectedItemPosition()]+
                                        "&phone="+ed_phone.getText().toString()+
                                        "&email="+ed_email.getText().toString()+
                                        "&subject="+spinner_support_type.getSelectedItem().toString();

                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {

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

            }
        });


        return rootView;
    }

}
