package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Monshi_Customers_List_Adapter;
import info.androidhive.materialdesign.lists.Monshi_Customers_List;
import info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment;
import info.androidhive.materialdesign.pagerfragments.DetailFragment_non_login;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.fragments.CalendarFragment.service_position;
import static info.androidhive.materialdesign.fragments.CalendarFragment.services_id;
import static info.androidhive.materialdesign.fragments.CalendarFragment.staff_id;
import static info.androidhive.materialdesign.fragments.CalendarFragment.staff_position;
import static info.androidhive.materialdesign.pagerfragments.DetailFragment.ed_description;


public class ClassBookingFragment extends Fragment {

    EditText ed_monshiapp_customer,ed_non_monshiapp_customer;
    EditText ed_customer_name,ed_customer_email,ed_customer_phone,ed_customer_addresss;

    SharedPreferences sharedPreferences;

    ArrayList<Monshi_Customers_List> monshi_customers_lists =new ArrayList();

    ListView list_view_monshi_app,list_view_non_monshi_app;

    Monshi_Customers_List_Adapter monshi_customers_list_adapter;

    Button btn_submit;

    String customer_id;
    int customer_check=0;

    ImageView img_cross;
    LinearLayout layoutcustomerinfo;

    ConstraintLayout layout_parent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_class_booking, container, false);
          View rootView = inflater.inflate(R.layout.fragment_select_customer, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        img_cross = rootView.findViewById(R.id.img_cross);
        layoutcustomerinfo = rootView.findViewById(R.id.layoutcustomerinfo);

        list_view_monshi_app = (ListView) rootView.findViewById(R.id.list_view_monshi_app);
        list_view_non_monshi_app = (ListView) rootView.findViewById(R.id.list_view_non_monshi_app);

        ed_monshiapp_customer = (EditText) rootView.findViewById(R.id.ed_monshiapp_customer);
        ed_non_monshiapp_customer = (EditText) rootView.findViewById(R.id.ed_non_monshiapp_customer);

        ed_customer_name  = (EditText) rootView.findViewById(R.id.ed_customer_name);
        ed_customer_email = (EditText) rootView.findViewById(R.id.ed_customer_email);
        ed_customer_phone = (EditText) rootView.findViewById(R.id.ed_customer_phone);
        ed_customer_addresss = (EditText) rootView.findViewById(R.id.ed_customer_addresss);

        layout_parent = rootView.findViewById(R.id.layout_parent);
        layout_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_view_monshi_app.setVisibility(View.GONE);
                list_view_non_monshi_app.setVisibility(View.GONE);
            }
        });

        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img_cross.setVisibility(View.GONE);
                layoutcustomerinfo.setVisibility(View.GONE);
                ed_monshiapp_customer.setVisibility(View.VISIBLE);
                ed_non_monshiapp_customer.setVisibility(View.VISIBLE);
            }
        });


        btn_submit = rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //http://192.168.100.14/monshiapp/app/insert_class_booking.php
                String url    =  getResources().getString(R.string.url)+"insert_class_booking.php";

                String params="";
                if (customer_check==1){
                    if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                        params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                "&business_id="+sharedPreferences.getString("selected_business", "")+
                                "&class_id="+sharedPreferences.getString("class_id", "")+
                                "&session_id="+
                                "&customer_id="+customer_id+
                                "&name="+ed_customer_name.getText().toString()+
                                "&email="+ed_customer_email.getText().toString()+
                                "&address="+ed_customer_addresss.getText().toString()+
                                "&phone="+ed_customer_phone.getText().toString()+
                                "&recursive_check=no"+
                                "&recursive_booking=1"+
                                "&customer_status=monshiapp_customer"+
                                "&added_by=admin"//own
                        ;
                    }else {

                        params ="user_id="+sharedPreferences.getString("user_id", "")+
                                "&business_id="+sharedPreferences.getString("business_id", "")+
                                "&class_id="+sharedPreferences.getString("class_id", "")+
                                "&customer_id="+customer_id+
                                "&session_id="+
                                "&name="+ed_customer_name.getText().toString()+
                                "&email="+ed_customer_email.getText().toString()+
                                "&address="+ed_customer_addresss.getText().toString()+
                                "&phone="+ed_customer_phone.getText().toString()+
                                "&recursive_check=no"+
                                "&recursive_booking=1"+
                                "&customer_status=monshiapp_customer"+
                                "&added_by=admin"//own
                        ;
                    }
                }else {
                    if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                        params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                "&business_id="+sharedPreferences.getString("selected_business", "")+
                                "&class_id="+sharedPreferences.getString("class_id", "")+
                                "&session_id="+
                                "&customer_id="+customer_id+
                                "&name="+ed_customer_name.getText().toString()+
                                "&email="+ed_customer_email.getText().toString()+
                                "&address="+ed_customer_addresss.getText().toString()+
                                "&phone="+ed_customer_phone.getText().toString()+
                                "&recursive_check=no"+
                                "&recursive_booking=1"+
                                "&customer_status=monshiapp_customer"+
                                "&added_by=admin"//own
                        ;
                    }else {

                        params ="user_id="+sharedPreferences.getString("user_id", "")+
                                "&business_id="+sharedPreferences.getString("business_id", "")+
                                "&class_id="+sharedPreferences.getString("class_id", "")+
                                "&session_id="+
                                "&customer_id="+customer_id+
                                "&name="+ed_customer_name.getText().toString()+
                                "&email="+ed_customer_email.getText().toString()+
                                "&address="+ed_customer_addresss.getText().toString()+
                                "&phone="+ed_customer_phone.getText().toString()+
                                "&recursive_check=no"+
                                "&recursive_booking=1"+
                                "&customer_status=monshiapp_customer"+
                                "&added_by=admin"//own
                        ;
                    }
                }



                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {
                                Toast.makeText(getActivity(), ""+jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                            else{
                                Toast.makeText(getActivity(), "Work under progress", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                webRequestCall.execute(url, "POST", params);

            }
        });

        ed_monshiapp_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                monshi_customers_lists.clear();
//http://192.168.100.14/monshiapp/app/monshiapp_customer.php?name=a
                String url    =  getResources().getString(R.string.url)+"monshiapp_customer.php";
                String params =  "name="+ed_monshiapp_customer.getText().toString();

                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {
                                JSONArray business_listing = jsonObject.getJSONArray("monshiapp_customer_list");

                                for(int i = 0; i < business_listing.length(); i++) {
                                    JSONObject c = business_listing.getJSONObject(i);
                                    String id = c.getString("id");
                                    String full_name = c.getString("full_name");
                                    String phone = c.getString("phone");
                                    String email = c.getString("email");
                                    String address = c.getString("address");

                                    Monshi_Customers_List obj = new Monshi_Customers_List
                                            (""+id,""+full_name,""+phone
                                                    ,""+email,""+address);

                                    monshi_customers_lists.add(obj);

                                }

                                monshi_customers_list_adapter = new Monshi_Customers_List_Adapter(getActivity(),monshi_customers_lists);

                                list_view_monshi_app.setAdapter(monshi_customers_list_adapter);

                                list_view_monshi_app.setVisibility(View.VISIBLE);
                                list_view_monshi_app.bringToFront();
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

            @Override
            public void afterTextChanged(Editable s) {

            }
        });




        ed_non_monshiapp_customer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                monshi_customers_lists.clear();
//http://192.168.100.14/monshiapp/app/get_normal_customer.php?name=a&business_id=1
                String url    =  getResources().getString(R.string.url)+"get_normal_customer.php";
                String params =  "name="+ed_non_monshiapp_customer.getText().toString()+
                        "&business_id="+sharedPreferences.getString("business_id", "");;

                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {
                                JSONArray business_listing = jsonObject.getJSONArray("monshiapp_customer_list");

                                for(int i = 0; i < business_listing.length(); i++) {
                                    JSONObject c = business_listing.getJSONObject(i);
                                    String id = c.getString("id");
                                    String full_name = c.getString("full_name");
                                    String customer_email = c.getString("customer_email");
                                    String customer_phone = c.getString("customer_phone");
                                    String customer_address = c.getString("customer_address");

                                    Monshi_Customers_List obj = new Monshi_Customers_List
                                            (""+id,""+full_name,""+customer_phone
                                                    ,""+customer_email,""+customer_address);

                                    monshi_customers_lists.add(obj);

                                }

                                monshi_customers_list_adapter = new Monshi_Customers_List_Adapter(getActivity(),monshi_customers_lists);

                                list_view_non_monshi_app.setAdapter(monshi_customers_list_adapter);

                                list_view_non_monshi_app.setVisibility(View.VISIBLE);
                                list_view_non_monshi_app.bringToFront();
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

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        list_view_monshi_app.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                customer_check=1;

                TextView tvname = (TextView) view.findViewById(R.id.tv_name);
                String name = tvname.getText().toString();
                list_view_monshi_app.getItemAtPosition(position);
                monshi_customers_list_adapter.getItem(position).getName();
                customer_id =  monshi_customers_list_adapter.getItem(position).getId();
                // Toast.makeText(getActivity(), "name "+   monshi_customers_list_adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();

                ed_monshiapp_customer.setVisibility(View.INVISIBLE);
                ed_non_monshiapp_customer.setVisibility(View.INVISIBLE);

                img_cross.setVisibility(View.VISIBLE);
                layoutcustomerinfo.setVisibility(View.VISIBLE);


                ed_customer_name.setText(""+name);
                ed_customer_email.setText(""+monshi_customers_list_adapter.getItem(position).getService_name());
                ed_customer_phone.setText(""+monshi_customers_list_adapter.getItem(position).getAppointment_id());
                ed_customer_addresss.setText(""+monshi_customers_list_adapter.getItem(position).getStaff_name());

                list_view_monshi_app.setVisibility(View.GONE);



            }
        });

        list_view_non_monshi_app.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                customer_check=2;

                TextView tvname = (TextView) view.findViewById(R.id.tv_name);
                String name = tvname.getText().toString();
                list_view_monshi_app.getItemAtPosition(position);
                monshi_customers_list_adapter.getItem(position).getName();
                customer_id =  monshi_customers_list_adapter.getItem(position).getId();
                // Toast.makeText(getActivity(), "name "+   monshi_customers_list_adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(getActivity(), "name "+   monshi_customers_list_adapter.getItem(position).getName(), Toast.LENGTH_SHORT).show();

                ed_monshiapp_customer.setVisibility(View.INVISIBLE);
                ed_non_monshiapp_customer.setVisibility(View.INVISIBLE);

                img_cross.setVisibility(View.VISIBLE);
                layoutcustomerinfo.setVisibility(View.VISIBLE);

                ed_customer_name.setText(""+name);
                ed_customer_email.setText(""+monshi_customers_list_adapter.getItem(position).getService_name());
                ed_customer_phone.setText(""+monshi_customers_list_adapter.getItem(position).getAppointment_id());
                ed_customer_addresss.setText(""+monshi_customers_list_adapter.getItem(position).getStaff_name());

                list_view_non_monshi_app.setVisibility(View.GONE);

            }
        });


        return rootView;
    }

}
