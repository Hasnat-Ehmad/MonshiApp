package info.androidhive.materialdesign.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import androidx.annotation.RequiresApi;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Monshi_Customers_List_Adapter;
import info.androidhive.materialdesign.lists.Monshi_Customers_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class ClassBookingActivity extends AppCompatActivity {

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

    Button btn_new_customer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_booking);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView img_profile = mToolbar.findViewById(R.id.img_profile);
        img_profile.setVisibility(View.GONE);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String session_id = getIntent().getExtras().getString("session_id");
        //Toast.makeText(ClassBookingActivity.this, "session_id = "+session_id, Toast.LENGTH_SHORT).show();


        img_cross = findViewById(R.id.img_cross);
        layoutcustomerinfo = findViewById(R.id.layoutcustomerinfo);

        list_view_monshi_app = (ListView) findViewById(R.id.list_view_monshi_app);
        list_view_non_monshi_app = (ListView) findViewById(R.id.list_view_non_monshi_app);

        ed_monshiapp_customer = (EditText) findViewById(R.id.ed_monshiapp_customer);
        ed_non_monshiapp_customer = (EditText) findViewById(R.id.ed_non_monshiapp_customer);

        ed_customer_name  = (EditText) findViewById(R.id.ed_customer_name);
        ed_customer_email = (EditText) findViewById(R.id.ed_customer_email);
        ed_customer_email.setVisibility(View.GONE);
        ed_customer_phone = (EditText) findViewById(R.id.ed_customer_phone);
        ed_customer_addresss = (EditText) findViewById(R.id.ed_customer_addresss);
        ed_customer_addresss.setVisibility(View.GONE);

        layout_parent = findViewById(R.id.layout_parent);
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
                monshi_customers_lists.clear();
                btn_new_customer.setVisibility(View.VISIBLE);
            }
        });


        btn_submit = findViewById(R.id.btn_submit);//button commented here
                btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (ed_customer_name.getText().toString().equals("")){
                    ed_customer_name.setError("Empty");
                }else if (ed_customer_phone.getText().toString().equals("")){
                    ed_customer_phone.setError("Empty");
                }else {


                    //http://192.168.100.14/monshiapp/app/insert_class_booking.php
                    String url    =  getResources().getString(R.string.url)+"insert_class_booking.php";

                    String params="";
                    if (customer_check==1){

                        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                                    "&class_id="+sharedPreferences.getString("class_id", "")+
                                    "&session_id="+sharedPreferences.getString("session_id", "")+
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
                                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                                    "&class_id="+sharedPreferences.getString("class_id", "")+
                                    "&customer_id="+customer_id+
                                    "&session_id="+sharedPreferences.getString("session_id", "")+
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
                                    "&session_id="+sharedPreferences.getString("session_id", "")+
                                    "&customer_id="+customer_id+
                                    "&name="+ed_customer_name.getText().toString()+
                                    "&email="+ed_customer_email.getText().toString()+
                                    "&address="+ed_customer_addresss.getText().toString()+
                                    "&phone="+ed_customer_phone.getText().toString()+
                                    "&recursive_check=no"+
                                    "&recursive_booking=1"+
                                    "&customer_status=normal_customer"+
                                    "&added_by=admin"//own
                            ;
                        }else {

                            params ="user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("business_id", "")+
                                    "&class_id="+sharedPreferences.getString("class_id", "")+
                                    "&session_id="+sharedPreferences.getString("session_id", "")+
                                    "&customer_id="+customer_id+
                                    "&name="+ed_customer_name.getText().toString()+
                                    "&email="+ed_customer_email.getText().toString()+
                                    "&address="+ed_customer_addresss.getText().toString()+
                                    "&phone="+ed_customer_phone.getText().toString()+
                                    "&recursive_check=no"+
                                    "&recursive_booking=1"+
                                    "&customer_status=normal_customer"+
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
                                    Toast.makeText(ClassBookingActivity.this, ""+jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else{
                                    Toast.makeText(ClassBookingActivity.this, ""+jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();

                                    //Toast.makeText(ClassBookingActivity.this, "Work under progress", Toast.LENGTH_SHORT).show();
                                    //finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    webRequestCall.execute(url, "POST", params);

                }
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

                                monshi_customers_list_adapter = new Monshi_Customers_List_Adapter(ClassBookingActivity.this,monshi_customers_lists);

                                list_view_monshi_app.setAdapter(monshi_customers_list_adapter);

                                list_view_monshi_app.setVisibility(View.VISIBLE);
                                list_view_monshi_app.bringToFront();
                                btn_new_customer.setVisibility(View.INVISIBLE);
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

                                monshi_customers_list_adapter = new Monshi_Customers_List_Adapter(ClassBookingActivity.this,monshi_customers_lists);

                                list_view_non_monshi_app.setAdapter(monshi_customers_list_adapter);

                                list_view_non_monshi_app.setVisibility(View.VISIBLE);
                                list_view_non_monshi_app.bringToFront();
                                btn_new_customer.setVisibility(View.INVISIBLE);
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

        btn_new_customer = findViewById(R.id.btn_new_customer);
        btn_new_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutcustomerinfo.setVisibility(View.VISIBLE);
                ed_monshiapp_customer.setVisibility(View.GONE);
                ed_non_monshiapp_customer.setVisibility(View.GONE);
                btn_new_customer.setVisibility(View.GONE);
                img_cross.setVisibility(View.VISIBLE);

                ed_customer_name.setText("");
                ed_customer_email.setText("");
                ed_customer_addresss.setText("");
                ed_customer_phone.setText("");

                customer_check=2;//adding new Customer here
            }
        });

    }
}
