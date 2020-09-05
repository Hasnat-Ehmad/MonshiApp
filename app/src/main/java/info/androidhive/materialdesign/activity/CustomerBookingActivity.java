package info.androidhive.materialdesign.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.service_amount;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.service_position;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.service_time;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.services;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.services_id;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.staff;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.staff_id;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.staff_position;
import static info.androidhive.materialdesign.pagerfragments.DetailFragment_non_login.ed_description;

public class CustomerBookingActivity extends AppCompatActivity {

    Spinner spinner_service,spinner_staff;
    EditText ed_description;
    Button btn_submit;
    Toolbar mToolbar;
    SharedPreferences sharedPreferences;

    TextView tv_label_continous_booking,tv_label_time,tv_minute_service,tv_amount_service;

    ToggleButton toggle_repeat;
    LinearLayout layout_repeat,layout_dummy_lines;

    Spinner spinner_recursive_duration,spinner_recursive_booking;


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_booking);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(null);
        }


        spinner_service = findViewById(R.id.spinner_service);
        setSpinner_service();
        spinner_staff   = findViewById(R.id.spinner_staff);
        setSpinner_staff();
        ed_description =  findViewById(R.id.ed_description);

        //=====

        spinner_recursive_duration = findViewById(R.id.spinner_recursive_duration);//1 to 7
        spinner_recursive_booking  = findViewById(R.id.spinner_recursive_booking);//1 to 4

        tv_label_continous_booking = findViewById(R.id.tv_label_continous_booking);
        toggle_repeat = findViewById(R.id.toggle_repeat);

        layout_repeat = findViewById(R.id.layout_repeat);
        layout_dummy_lines = findViewById(R.id.layout_dummy_lines);

        tv_label_time = findViewById(R.id.tv_label_time);

        tv_minute_service = findViewById(R.id.tv_minute_service);
        tv_minute_service.setText(""+service_time[service_position]);
        tv_amount_service = findViewById(R.id.tv_amount_service);
        tv_amount_service.setText(""+service_amount[service_position]);

        SavePreferences("recursive_check"        ,"no");

        SavePreferences("recursive_duration"        ,"");
        SavePreferences("recursive_booking"        ,"1");


        toggle_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if (isChecked) {

                    SavePreferences("recursive_check"        ,"yes");

                    SavePreferences("recursive_duration"        ,""+(spinner_recursive_duration.getSelectedItemPosition()+1));
                    SavePreferences("recursive_booking"        ,""+(spinner_recursive_booking.getSelectedItemPosition()+1));

                    tv_label_time.setText("روز");

//                    Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                                                        ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_duration = " +sharedPreferences.getString("recursive_duration", "")
//                                                        ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_booking = " +sharedPreferences.getString("recursive_booking", "")
//                                                         ,Toast.LENGTH_SHORT).show();

                    layout_repeat.setVisibility(View.VISIBLE);
                    layout_dummy_lines.setVisibility(View.VISIBLE);
                } else {

                    SavePreferences("recursive_check"        ,"no");

                    SavePreferences("recursive_duration"        ,"");
                    SavePreferences("recursive_booking"        ,"1");

//                    Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                            ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_duration = " +sharedPreferences.getString("recursive_duration", "")
//                            ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_booking = " +sharedPreferences.getString("recursive_booking", "")
//                            ,Toast.LENGTH_SHORT).show();

                    layout_repeat.setVisibility(View.GONE);
                    layout_dummy_lines.setVisibility(View.GONE);
                }
            }
        });

        spinner_recursive_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SavePreferences("recursive_duration"        ,""+(position+1));


//
//                Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                        ,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),"recursive_duration = " +sharedPreferences.getString("recursive_duration", "")
//                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_recursive_booking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SavePreferences("recursive_booking"        ,""+(position+1));

                if (position+1==1){
                    tv_label_time.setText("روز");
                }
                if (position+1==2){
                    tv_label_time.setText("هفته");
                }
                if (position+1==3){
                    tv_label_time.setText("ماه");
                }
                if (position+1==4){
                    tv_label_time.setText("سال");
                }

//                Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                        ,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),"recursive_booking = " +sharedPreferences.getString("recursive_booking", "")
//                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //=====

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              /*title
                start	دوشنبه+آوریل+01+2019+16:50:00+GMT+0000
                end	دوشنبه+آوریل+01+2019+17:00:00+GMT+0000
                service_id	1
                staff_id	4
                business_id	2
                user_id	6
                customer_id	6
                name
                email
                address
                phone
                recursive_check	no
                recursive_booking	1
                recursive_duration	1
                customer_status	monshiapp_customer
                */

              /*  Toast.makeText(CustomerBookingActivity.this, "user_id = "+sharedPreferences.getString("user_id", ""), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "business_id = "+sharedPreferences.getString("business_id", ""), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "title = "+ed_description.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "start = "+sharedPreferences.getString("finaldata", ""), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "service_id = "+services_id[service_position], Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "staff_id = "+staff_id[service_position], Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "customer_id = "+staff_id[service_position], Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "name = "+ed_customer_name.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "email = "+ed_customer_email.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "address = "+ed_customer_addresss.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "phone = "+ed_customer_phone.getText().toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "recursive_check = no", Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "recursive_booking = 1", Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "recursive_duration = 1", Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "customer_status = monshiapp_customer", Toast.LENGTH_SHORT).show();
                Toast.makeText(CustomerBookingActivity.this, "added_by = admin", Toast.LENGTH_SHORT).show();*/



                //http://192.168.100.14/monshiapp/app/insert_appt_admin.php
                String url    =  getResources().getString(R.string.url)+"insert_appt_admin.php";
                String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                        "&business_id="+sharedPreferences.getString("selected_business", "")+
                        "&title="+ed_description.getText().toString()+
                        "&start="+sharedPreferences.getString("finaldata", "")+
                        "&end="+sharedPreferences.getString("end_time", "")+
                        "&service_id="+services_id[service_position]+
                        "&staff_id="+staff_id[staff_position]+
                        "&customer_id="+sharedPreferences.getString("user_id", "")+
                        "&name="+sharedPreferences.getString("full_name", "")+
                        "&email="+sharedPreferences.getString("email", "")+
                        "&address="+
                        "&phone="+sharedPreferences.getString("phone", "")+
                        "&recursive_check="+sharedPreferences.getString("recursive_check", "")+
                        "&recursive_booking="+sharedPreferences.getString("recursive_booking", "")+
                        "&recursive_duration="+sharedPreferences.getString("recursive_duration", "")+
                        "&customer_status=monshiapp_customer"+
                        "&added_by=own"//own
                        ;

                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {

                                Toast.makeText(CustomerBookingActivity.this, ""+jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                finish();
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
        });
    }

    void setSpinner_staff(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomerBookingActivity.this,
                R.layout.my_spinner_style, staff) {

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

        spinner_staff.setAdapter(adapter);

        spinner_staff .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //setSpinner_business_staff(services_id[position]);
                staff_position = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner_staff.setSelection(staff_position);

    }

    void setSpinner_service(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomerBookingActivity.this,
                R.layout.my_spinner_style, services) {

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

        spinner_service.setAdapter(adapter);

        spinner_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service_position = position;
                // Toast.makeText(getActivity().getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner_service.setSelection(service_position);
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CustomerBookingActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
