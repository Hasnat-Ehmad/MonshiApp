package info.androidhive.materialdesign.fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

import static info.androidhive.materialdesign.fragments.ClassListFragment.new_class_id;


public class AddNewSession extends Fragment {

    SharedPreferences sharedPreferences;

    Spinner spinner_gender;

    String session_id="",class_id="",class_note="",s_staff_id="",class_name_no_id="",class_payment_no_id="",class_name_with_id="",class_payment_with_id="";

    String[] staff_name,staff_value;
    String[] gender,gender_value;

    EditText ed_startdate,ed_enddate,ed_totalseat,ed_price;

    Button btn_submit,btn_cancel;

    String[]  service_duration_array,service_duration_array_value;

    String[]  from_time_array,from_time_array_value;

    String start_date = "",end_date="";

     LinearLayout linearLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_new_session, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        btn_submit = rootView.findViewById(R.id.btn_submit);

        btn_cancel = rootView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        final Spinner  spinner_teacher = rootView.findViewById(R.id.spinner_teacher);
        spinner_gender  = rootView.findViewById(R.id.spinner_gender);

        ed_totalseat = rootView.findViewById(R.id.ed_totalseat);
        ed_price     = rootView.findViewById(R.id.ed_price);

        ed_startdate = rootView.findViewById(R.id.ed_startdate);
        ed_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"test",Toast.LENGTH_LONG).show();

                PersianCalendar persianCalendar = new PersianCalendar();
                persianCalendar.setPersianDate(persianCalendar.getPersianYear(),persianCalendar.getPersianMonth(),persianCalendar.getPersianDay());

                PersianDatePickerDialog  picker = new PersianDatePickerDialog(getActivity())
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setInitDate(persianCalendar)
//                        .setInitDate(initDate)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setActionTextColor(Color.GRAY)
//                        .setTypeFace(typeface)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                //Toast.makeText(getActivity(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();


                                int month = persianCalendar.getPersianMonth();
                                int gday  = persianCalendar.getPersianDay();
                                int year  = persianCalendar.getPersianYear();

                                PersianDate pdate = new PersianDate();

                                int[] intArray = new int[3];
                                intArray = pdate.toGregorian( year,  month,  gday);

                                int api_year  = intArray[0];
                                int api_month = intArray[1];
                                int api_date  = intArray[2];

                                if(api_month<10){
                                    start_date = api_year+"-0"+api_month+"-"+api_date;
                                }
                                if(api_date<10){
                                    start_date = api_year+"-"+api_month+"-0"+api_date;
                                }
                                if(api_month<10&&api_date<10){
                                    start_date = api_year+"-0"+api_month+"-0"+api_date;
                                }

                                ed_startdate.setText(""+persianCalendar.getPersianYear() + "-" + persianCalendar.getPersianMonth() + "-" + persianCalendar.getPersianDay());
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();
            }
        });

        ed_enddate = rootView.findViewById(R.id.ed_enddate);
        ed_enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PersianCalendar persianCalendar = new PersianCalendar();
                persianCalendar.setPersianDate(persianCalendar.getPersianYear(),persianCalendar.getPersianMonth(),persianCalendar.getPersianDay());

                PersianDatePickerDialog  picker = new PersianDatePickerDialog(getActivity())
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setInitDate(persianCalendar)
//                        .setInitDate(initDate)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1300)
                        .setActionTextColor(Color.GRAY)
//                        .setTypeFace(typeface)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                //Toast.makeText(getActivity(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();

                                int month = persianCalendar.getPersianMonth();
                                int gday  = persianCalendar.getPersianDay();
                                int year  = persianCalendar.getPersianYear();

                                PersianDate pdate = new PersianDate();

                                int[] intArray = new int[3];
                                intArray = pdate.toGregorian( year,  month,  gday);

                                int api_year  = intArray[0];
                                int api_month = intArray[1];
                                int api_date  = intArray[2];

                                if(api_month<10){
                                    end_date = api_year+"-0"+api_month+"-"+api_date;
                                }
                                if(api_date<10){
                                    end_date = api_year+"-"+api_month+"-0"+api_date;
                                }
                                if(api_month<10&&api_date<10){
                                    end_date = api_year+"-0"+api_month+"-0"+api_date;
                                }

                                ed_enddate.setText(""+persianCalendar.getPersianYear() + "-" + persianCalendar.getPersianMonth() + "-" + persianCalendar.getPersianDay());
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();

            }
        });

         linearLayout = (LinearLayout) rootView.findViewById(R.id.parent_day_layout);

        if (getArguments() != null){

            session_id = getArguments().getString("session_id");

            if (session_id==null){
                session_id="";
            }

            class_id   = getArguments().getString("class_id");
            class_note = getArguments().getString("class_note");
            s_staff_id = getArguments().getString("s_staff_id");

            class_name_no_id    = getArguments().getString("class_name_no_id");
            class_payment_no_id = getArguments().getString("class_payment_no_id");

            class_name_with_id    = getArguments().getString("class_name_with_id");
            class_payment_with_id = getArguments().getString("class_payment_with_id");

            if (session_id != null && session_id!=""){

                //http://192.168.100.14/monshiapp/app/class_session_detail_info.php?user_id=1&bus_id=1&session_id=99
                String url = getResources().getString(R.string.url)+"class_session_detail_info.php";

                String params = "";
                if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                    //spinner_business_list.setVisibility(View.GONE);
                    params =  "user_id="+sharedPreferences.getString("user_id", "")+
                              "&bus_id="+sharedPreferences.getString("selected_business", "")+
                              "&session_id="+session_id+"&staff_id="+s_staff_id;
                    ;
                }else {
                    //spinner_business_list.setVisibility(View.VISIBLE);
                    params =  "user_id="   +sharedPreferences.getString("user_id", "")+
                            "&bus_id="+sharedPreferences.getString("business_id", "")+
                            "&session_id=" +session_id+"&staff_id="+s_staff_id;
                }

                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {

                                String s_staff_id = jsonObject.getString("s_staff_id");
                                String from_date  = jsonObject.getString("from_date");
                                String to_date    = jsonObject.getString("to_date");

                                ed_startdate.setText(""+from_date);
                                ed_enddate.setText(""+to_date);

                                System.out.println("to_date = "+to_date);

                                JSONArray gender_list = jsonObject.getJSONArray("gender_list");

                                gender = new String[gender_list.length()];
                                gender_value= new String[gender_list.length()];

                                int gender_index = 0;

                                for(int i = 0; i < gender_list.length(); i++) {
                                    JSONObject c = gender_list.getJSONObject(i);
                                    String string = c.getString("string");
                                    String value = c.getString("value");
                                    String status = c.getString("status");

                                    gender[i] = string;
                                    gender_value[i]=value;

                                    if (status.equals("enable")){
                                        gender_index = i;
                                    }

                                    System.out.println("gender_list = "+status);
                                }
                                if (getActivity()!=null){

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                            R.layout.my_spinner_style, gender) {

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

                                    spinner_gender.setAdapter(adapter);
                                    spinner_gender.setSelection(gender_index);

                                }

                                String slot = jsonObject.getString("slot");
                                ed_totalseat.setText(""+slot);
                                String cost = jsonObject.getString("cost");
                                ed_price.setText(""+cost);

                                JSONArray staff_list_dropdown = jsonObject.getJSONArray("staff_list_dropdown");

                                staff_name = new String[staff_list_dropdown.length()];
                                staff_value= new String[staff_list_dropdown.length()];

                                int staff_index=0;

                                for(int i = 0; i < staff_list_dropdown.length(); i++) {
                                    JSONObject c = staff_list_dropdown.getJSONObject(i);

                                    String full_name = c.getString("full_name");
                                    String value = c.getString("value");
                                    String status = c.getString("status");

                                    staff_name [i] = full_name;
                                    staff_value[i] = value;

                                    System.out.println("staff_list_dropdown = "+status);

                                    if (status.equals("enable")){
                                        staff_index=i;
                                    }
                                }
                                if (getActivity()!=null){

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                            R.layout.my_spinner_style, staff_name) {

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

                                    spinner_teacher.setAdapter(adapter);
                                    spinner_teacher.setSelection(staff_index);
                                }

                                JSONArray days_array = jsonObject.getJSONArray("days_array");

                                for(int i = 0; i < days_array.length(); i++) {
                                    JSONObject c = days_array.getJSONObject(i);
                                    String day_string = c.getString("day_string");
                                    String value = c.getString("value");
                                    String status = c.getString("status");

                                    System.out.println("days_array = "+status);

                                    LayoutInflater factory = LayoutInflater.from(getActivity());
                                    View row_view_session_day = factory.inflate(R.layout.row_session_day, null);

                                    TextView tv_day_label = row_view_session_day.findViewById(R.id.tv_day_label);
                                    tv_day_label.setText(""+day_string);

                                    TextView tv_day_label_english = row_view_session_day.findViewById(R.id.tv_day_label_english);
                                    tv_day_label_english.setText(""+value);

                                    final TextView tv_dummy = row_view_session_day.findViewById(R.id.tv_dummy);


                                    final SwitchCompat btn_switch = row_view_session_day.findViewById(R.id.btn_switch);

                                    final Spinner spinner_from_time     = row_view_session_day.findViewById(R.id.spinner_from_time);
                                    final Spinner spinner_service_duration = row_view_session_day.findViewById(R.id.spinner_service_duration);

                                    //btn_switch.setTag(status);

                                    if (status.equals("enable")){
                                        btn_switch.setChecked(true);
                                        spinner_from_time.setEnabled(true);
                                        spinner_service_duration.setEnabled(true);
                                    }else {
                                        btn_switch.setChecked(false);
                                        spinner_from_time.setEnabled(false);
                                        spinner_service_duration.setEnabled(false);
                                    }

                                    btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                            if (isChecked) {
                                                if (btn_switch.getTag().equals("enable")){

                                                    if (tv_dummy.getTag().equals("enable")){
                                                        spinner_from_time.setEnabled(true);
                                                        spinner_service_duration.setEnabled(true);
                                                    }else {
                                                        Toast.makeText(getActivity(),getString(R.string.str_staff_disable),Toast.LENGTH_SHORT).show();
                                                        btn_switch.setChecked(false);
                                                    }

                                                }else {
                                                    Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                    btn_switch.setChecked(false);
                                                }

                                            } else {
                                                spinner_from_time.setEnabled(false);
                                                spinner_service_duration.setEnabled(false);
                                            }
                                        }
                                    });

                                    JSONArray service_duration = c.getJSONArray("service_duration");

                                    service_duration_array = new String[service_duration.length()];
                                    service_duration_array_value= new String[service_duration.length()];

                                    int service_duratiion = 0;

                                    for(int j = 0; j < service_duration.length(); j++) {
                                        JSONObject d = service_duration.getJSONObject(j);
                                        String string = d.getString("string");
                                        String value_service = d.getString("value");
                                        String status_service = d.getString("status");

                                        service_duration_array[j] = string;
                                        service_duration_array_value[j]=value_service;

                                        System.out.println("service_duration = "+status);

                                        if (status_service.equals("enable")){
                                            service_duratiion = j;
                                        }
                                    }
                                    if (getActivity()!=null){

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                R.layout.my_spinner_style, service_duration_array) {

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

                                        spinner_service_duration.setAdapter(adapter);
                                        spinner_service_duration.setSelection(service_duratiion);
                                    }

                                    JSONArray from_time_arr = c.getJSONArray("from_time_arr");

                                    from_time_array = new String[from_time_arr.length()];
                                    from_time_array_value= new String[from_time_arr.length()];

                                    int from_time_array_index = 0;

                                    for(int j = 0; j < from_time_arr.length(); j++) {
                                        JSONObject d = from_time_arr.getJSONObject(j);
                                        String value_from_time = d.getString("value");
                                        String value_in_per = d.getString("value_in_per");
                                        String status_from_time = d.getString("status");

                                        from_time_array      [j] = value_in_per;
                                        from_time_array_value[j] = value_from_time;

                                        System.out.println("from_time_arr = "+status_from_time);

                                        if (status_from_time.equals("enable")){

                                            from_time_array_index = j;
                                        }
                                    }
                                    if (getActivity()!=null){

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                R.layout.my_spinner_style, from_time_array) {

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

                                        spinner_from_time.setAdapter(adapter);
                                        spinner_from_time.setSelection(from_time_array_index);

                                    }

                                    linearLayout.addView(row_view_session_day);
                                }

                                JSONArray business_day = jsonObject.getJSONArray("business_day");

                                for(int j = 0; j < business_day.length(); j++) {
                                    JSONObject d = business_day.getJSONObject(j);
                                    String day = d.getString("day");
                                    //String status_staff_day = d.getString("status");
                                    String bus_status_business_day = d.getString("bus_status");

                                    ConstraintLayout layout_data = (ConstraintLayout) linearLayout.getChildAt(j);

                                    SwitchCompat btn_switch = layout_data.findViewById(R.id.btn_switch);
                                    TextView tv_day_label = layout_data.findViewById(R.id.tv_day_label);
                                    TextView tv_day_label_english = layout_data.findViewById(R.id.tv_day_label_english);
                                    TextView tv_dummy = layout_data.findViewById(R.id.tv_dummy);
                                    Spinner spinner_from_time = layout_data.findViewById(R.id.spinner_from_time);
                                    Spinner spinner_service_duration = layout_data.findViewById(R.id.spinner_service_duration);

                                    btn_switch.setTag(bus_status_business_day);
                                    //tv_dummy.setTag(status_staff_day);
                                    //Toast.makeText(getActivity(), "bus_status_business_day = " + bus_status_business_day, Toast.LENGTH_SHORT).show();
                                }

                            } else{

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                webRequestCall.execute(url, "POST", params);

                //Toast.makeText(getActivity(),"staff_id = "+staff_value[spinner_teacher.getSelectedItemPosition()],Toast.LENGTH_SHORT).show();

                spinner_teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                       // Toast.makeText(getActivity(),"staff_id = "+staff_value[position],Toast.LENGTH_SHORT).show();
                        dummyfun(staff_value[position]);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //here
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String check_list = "";
                        String from_time = "";
                        String service_duration = "";
                        String gender = spinner_gender.getSelectedItem().toString();

                        String staff_id = staff_value[spinner_teacher.getSelectedItemPosition()];
//                            Toast.makeText(getActivity(),"staff_id = "+staff_id,Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(),"gender = "+gender,Toast.LENGTH_SHORT).show();

                        for (int i = 0;i<linearLayout.getChildCount();i++){

                            ConstraintLayout layout_data = (ConstraintLayout) linearLayout.getChildAt(i);

                            SwitchCompat btn_switch = layout_data.findViewById(R.id.btn_switch);
                            TextView tv_day_label   = layout_data.findViewById(R.id.tv_day_label);
                            TextView tv_day_label_english    = layout_data.findViewById(R.id.tv_day_label_english);
                            Spinner  spinner_from_time       = layout_data.findViewById(R.id.spinner_from_time);
                            Spinner  spinner_service_duration= layout_data.findViewById(R.id.spinner_service_duration);


                            if (btn_switch.isChecked()){

                                check_list += tv_day_label_english.getText().toString()+",";
                                //Toast.makeText(getActivity(),"checked_day = "+check_list,Toast.LENGTH_SHORT).show();


                                from_time  += from_time_array_value[spinner_from_time.getSelectedItemPosition()]+",";
                                //Toast.makeText(getActivity(),"from_time = "+from_time,Toast.LENGTH_SHORT).show();

                                service_duration += service_duration_array_value[spinner_service_duration.getSelectedItemPosition()]+",";
                                //Toast.makeText(getActivity(),"service_duration = "+service_duration,Toast.LENGTH_SHORT).show();


                            }else {
                                //Toast.makeText(getActivity(),"tv_day_label_no = "+tv_day_label_english.getText().toString(),Toast.LENGTH_SHORT).show();
                            }

                        }

                        if (ed_startdate.equals("")){
                            ed_startdate.setError("Empty");
                        }else if (ed_enddate.equals("")){
                            ed_enddate.setError("Empty");
                        }else if (ed_price.equals("")){
                            ed_price.setError("Empty");
                        }else if (ed_totalseat.equals("")){
                            ed_totalseat.setError("Empty");
                        }else {

                            if (class_id!=null && session_id!=null){
                                //editing exiting session in exiting class
                                //edit session api call here

                                //http://192.168.100.14/monshiapp/app/edit_session.php
                                String url = getResources().getString(R.string.url)+"edit_session.php";

                                String params = "";
                                if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                                    //spinner_business_list.setVisibility(View.GONE);
                                    params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                            "&business_id="+sharedPreferences.getString("selected_business", "")+
                                            "&class_id="+class_id+
                                            "&cost="+ed_price.getText().toString()+
                                            "&slot="+ed_totalseat.getText().toString()+
                                            "&gender="+gender_value[spinner_gender.getSelectedItemPosition()]+
                                            "&staff_id="+staff_value[spinner_teacher.getSelectedItemPosition()]+
                                            "&session_id="+session_id+
                                            "&from_date="+ed_startdate.getText().toString()+
                                            "&to_date="+ed_enddate.getText().toString()+
                                            "&from_time="+removeLastChar(from_time)+
                                            "&service_duration="+removeLastChar(service_duration)+
                                            "&check_list="+removeLastChar(check_list)
                                    ;
                                }else {
                                    //spinner_business_list.setVisibility(View.VISIBLE);
                                    params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                            "&business_id="+sharedPreferences.getString("business_id", "")+
                                            "&class_id="+class_id+
                                            "&cost="+ed_price.getText().toString()+
                                            "&slot="+ed_totalseat.getText().toString()+
                                            "&gender="+spinner_gender.getSelectedItem().toString()+
                                            "&staff_id="+staff_value[spinner_teacher.getSelectedItemPosition()]+
                                            "&session_id="+session_id+
                                            "&from_date="+ed_startdate.getText().toString()+
                                            "&to_date="+ed_enddate.getText().toString()+
                                            "&from_time="+removeLastChar(from_time)+
                                            "&service_duration="+removeLastChar(service_duration)+
                                            "&check_list="+removeLastChar(check_list)
                                    ;
                                }

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {

                                                Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                                getActivity().getSupportFragmentManager().popBackStack();

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

            }else {

                    //http://192.168.100.14/monshiapp/app/class_session_detail_info.php?user_id=1&bus_id=1&session_id=99

                    String url    =  getResources().getString(R.string.url)+"class_session_detail_info.php";

                    String params = "";
                    if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                        //spinner_business_list.setVisibility(View.GONE);
                        params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                "&bus_id="+sharedPreferences.getString("selected_business", "")+
                                "&session_id=&staff_id=";
                        ;
                    }else {
                        //spinner_business_list.setVisibility(View.VISIBLE);
                        params =  "user_id="   +sharedPreferences.getString("user_id", "")+
                                "&bus_id="+sharedPreferences.getString("business_id", "")+
                                "&session_id=&staff_id=";
                    }

                    WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                        @Override
                        public void TaskCompletionResult(String result) {

                            try {

                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getString("status").equals("200")) {

                                 /*  String s_staff_id = jsonObject.getString("s_staff_id");
                                    String from_date = jsonObject.getString("from_date");
                                    String to_date = jsonObject.getString("to_date");

                                    ed_startdate.setText(""+from_date);
                                    ed_enddate.setText(""+to_date);

                                    System.out.println("to_date = "+to_date);*/

                                    JSONArray gender_list = jsonObject.getJSONArray("gender_list");

                                    gender = new String[gender_list.length()];
                                    gender_value= new String[gender_list.length()];

                                    for(int i = 0; i < gender_list.length(); i++) {
                                        JSONObject c = gender_list.getJSONObject(i);
                                        String string = c.getString("string");
                                        String value = c.getString("value");
                                        String status = c.getString("status");

                                        gender[i] = string;
                                        gender_value[i]=value;



                                        System.out.println("gender_list = "+status);
                                    }
                                    if (getActivity()!=null){

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                R.layout.my_spinner_style, gender) {

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

                                        spinner_gender.setAdapter(adapter);
                                    }

                                  /*  String slot = jsonObject.getString("slot");
                                    ed_totalseat.setText(""+slot);
                                    String cost = jsonObject.getString("cost");
                                    ed_price.setText(""+cost);*/

                                    JSONArray staff_list_dropdown = jsonObject.getJSONArray("staff_list_dropdown");

                                    staff_name = new String[staff_list_dropdown.length()];
                                    staff_value= new String[staff_list_dropdown.length()];

                                    int staff_index=0;

                                    for(int i = 0; i < staff_list_dropdown.length(); i++) {
                                        JSONObject c = staff_list_dropdown.getJSONObject(i);

                                        String full_name = c.getString("full_name");
                                        String value = c.getString("value");
                                        String status = c.getString("status");

                                        staff_name [i] = full_name;
                                        staff_value[i] = value;


                                        if (status.equals("enable")){
                                            staff_index=i;
                                        }

                                        System.out.println("staff_list_dropdown = "+status);
                                    }
                                    if (getActivity()!=null){

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                R.layout.my_spinner_style, staff_name) {

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

                                        spinner_teacher.setAdapter(adapter);
                                        spinner_teacher.setSelection(staff_index);
                                    }


                                    JSONArray days_array = jsonObject.getJSONArray("days_array");

                                    for(int i = 0; i < days_array.length(); i++) {
                                        JSONObject c = days_array.getJSONObject(i);
                                        String day_string = c.getString("day_string");
                                        String value = c.getString("value");
                                        String status = c.getString("status");

                                        System.out.println("days_array = "+status);

                                        LayoutInflater factory = LayoutInflater.from(getActivity());
                                        View row_view_session_day = factory.inflate(R.layout.row_session_day, null);

                                        TextView tv_day_label = row_view_session_day.findViewById(R.id.tv_day_label);
                                        tv_day_label.setText(""+day_string);

                                        TextView tv_day_label_english = row_view_session_day.findViewById(R.id.tv_day_label_english);
                                        tv_day_label_english.setText(""+value);

                                        final Spinner spinner_service_duration = row_view_session_day.findViewById(R.id.spinner_service_duration);

                                        final Spinner spinner_from_time     = row_view_session_day.findViewById(R.id.spinner_from_time);

                                        final SwitchCompat btn_switch = row_view_session_day.findViewById(R.id.btn_switch);


                                        if (status.equals("enable")){
                                            btn_switch.setChecked(true);
                                            spinner_from_time.setEnabled(true);
                                            spinner_service_duration.setEnabled(true);
                                        }else {
                                            btn_switch.setChecked(false);
                                            spinner_from_time.setEnabled(false);
                                            spinner_service_duration.setEnabled(false);
                                        }

                                        btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                            @Override
                                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                if (isChecked) {
                                                    if (btn_switch.getTag().equals("enable")){
                                                        spinner_from_time.setEnabled(true);
                                                        spinner_service_duration.setEnabled(true);
                                                    }else {
                                                        Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                                        btn_switch.setChecked(false);
                                                    }

                                                } else {
                                                    spinner_from_time.setEnabled(false);
                                                    spinner_service_duration.setEnabled(false);
                                                }
                                            }
                                        });


                                        JSONArray service_duration = c.getJSONArray("service_duration");

                                        service_duration_array = new String[service_duration.length()];
                                        service_duration_array_value= new String[service_duration.length()];

                                        for(int j = 0; j < service_duration.length(); j++) {
                                            JSONObject d = service_duration.getJSONObject(j);
                                            String string = d.getString("string");
                                            String value_service = d.getString("value");
                                            String status_service = d.getString("status");

                                            service_duration_array[j] = string;
                                            service_duration_array_value[j]=value_service;

                                            System.out.println("service_duration = "+status);
                                        }
                                        if (getActivity()!=null){

                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                    R.layout.my_spinner_style, service_duration_array) {

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

                                            spinner_service_duration.setAdapter(adapter);
                                        }

                                        JSONArray from_time_arr = c.getJSONArray("from_time_arr");

                                        from_time_array = new String[from_time_arr.length()];
                                        from_time_array_value= new String[from_time_arr.length()];


                                        for(int j = 0; j < from_time_arr.length(); j++) {
                                            JSONObject d = from_time_arr.getJSONObject(j);
                                            String value_from_time = d.getString("value");
                                            String value_in_per = d.getString("value_in_per");
                                            String status_from_time = d.getString("status");

                                            from_time_array [j] = value_in_per;
                                            from_time_array_value[j] = value_from_time;

                                            System.out.println("from_time_arr = "+status_from_time);
                                        }
                                        if (getActivity()!=null){

                                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                    R.layout.my_spinner_style, from_time_array) {

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

                                            spinner_from_time.setAdapter(adapter);
                                        }

                                        linearLayout.addView(row_view_session_day);

                                    }

                                    JSONArray business_day = jsonObject.getJSONArray("business_day");

                                    for(int j = 0; j < business_day.length(); j++) {
                                        JSONObject d = business_day.getJSONObject(j);
                                        String day = d.getString("day");
                                        //String status_staff_day = d.getString("status");
                                        String bus_status_business_day = d.getString("bus_status");

                                        ConstraintLayout layout_data = (ConstraintLayout) linearLayout.getChildAt(j);

                                        SwitchCompat btn_switch = layout_data.findViewById(R.id.btn_switch);
                                        TextView tv_day_label = layout_data.findViewById(R.id.tv_day_label);
                                        TextView tv_day_label_english = layout_data.findViewById(R.id.tv_day_label_english);
                                        Spinner spinner_from_time = layout_data.findViewById(R.id.spinner_from_time);
                                        Spinner spinner_service_duration = layout_data.findViewById(R.id.spinner_service_duration);

                                        btn_switch.setTag(bus_status_business_day);
                                        //Toast.makeText(getActivity(), "bus_status_business_day = " + bus_status_business_day, Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(getActivity(),"staff_id = "+staff_value[spinner_teacher.getSelectedItemPosition()],Toast.LENGTH_SHORT).show();

                spinner_teacher.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //Toast.makeText(getActivity(),"staff_id = "+staff_value[position],Toast.LENGTH_SHORT).show();

                        dummyfun(staff_value[position]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                //here
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String check_list = "";
                        String from_time = "";
                        String service_duration = "";
                        String gender = spinner_gender.getSelectedItem().toString();

                        String staff_id = staff_value[spinner_teacher.getSelectedItemPosition()];

//                            Toast.makeText(getActivity(),"staff_id = "+staff_id,Toast.LENGTH_SHORT).show();
//                            Toast.makeText(getActivity(),"gender = "+gender,Toast.LENGTH_SHORT).show();

                        for (int i = 0;i<linearLayout.getChildCount();i++){

                            ConstraintLayout layout_data = (ConstraintLayout) linearLayout.getChildAt(i);

                            SwitchCompat btn_switch = layout_data.findViewById(R.id.btn_switch);
                            TextView tv_day_label   = layout_data.findViewById(R.id.tv_day_label);
                            TextView tv_day_label_english    = layout_data.findViewById(R.id.tv_day_label_english);
                            Spinner  spinner_from_time       = layout_data.findViewById(R.id.spinner_from_time);
                            Spinner  spinner_service_duration= layout_data.findViewById(R.id.spinner_service_duration);


                            if (btn_switch.isChecked()){

                                check_list += tv_day_label_english.getText().toString()+",";
                                //Toast.makeText(getActivity(),"checked_day = "+check_list,Toast.LENGTH_SHORT).show();

                                from_time  += from_time_array_value[spinner_from_time.getSelectedItemPosition()]+",";
                                //Toast.makeText(getActivity(),"from_time = "+from_time,Toast.LENGTH_SHORT).show();

                                service_duration += service_duration_array_value[spinner_service_duration.getSelectedItemPosition()]+",";
                                //Toast.makeText(getActivity(),"service_duration = "+service_duration,Toast.LENGTH_SHORT).show();


                            }else {
                                //Toast.makeText(getActivity(),"tv_day_label_no = "+tv_day_label_english.getText().toString(),Toast.LENGTH_SHORT).show();
                            }

                        }


                        if (ed_startdate.equals("")){
                            ed_startdate.setError("Empty");
                        }else if (ed_enddate.equals("")){
                            ed_enddate.setError("Empty");
                        }else if (ed_price.equals("")){
                            ed_price.setError("Empty");
                        }else if (ed_totalseat.equals("")){
                            ed_totalseat.setError("Empty");
                        }else {

                              if (class_id!=null && session_id==null ){
                                //adding new session existing class
                                //add_edit_classs api call here

/*
$user_id //compulsory
$business_id //compulsory

$class_id //compulsory incase of edit
$class_name //compulsory
$class_note
$roll //1 for yes , 0 for no ---- compulsory

$cost //compulsory if session_add=yes
$gender //compulsory if session_add=yes male,female,both
$staff_id //compulsory if session_add=yes
$session_add //if session add yes else no compulsory
$from_date //compulsory if session_add=yes
$to_date //compulsory if session_add=yes

$from_time //compulsory if session_add=yes , separated array  //done
$service_duration //compulsory if session_add=yes , separated array  //done
$check_list //compulsory if session_add=yes , separated array  //done
*/

                                //http://192.168.100.14/monshiapp/app/add_edit_class.php
                                String url = getResources().getString(R.string.url)+"add_edit_class.php";

                                String params = "";
                                if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                                    //spinner_business_list.setVisibility(View.GONE);
                                    params ="user_id="+sharedPreferences.getString("user_id", "")+
                                            "&business_id="+sharedPreferences.getString("selected_business", "")+
                                            "&class_id="+class_id+
                                            "&class_name="+class_name_with_id+
                                            "&class_note="+class_note+
                                            "&roll="+class_payment_with_id+
                                            "&cost="+ed_price.getText().toString()+
                                            "&slot="+ed_totalseat.getText().toString()+
                                            "&gender="+gender_value[spinner_gender.getSelectedItemPosition()]+
                                            "&staff_id="+staff_value[spinner_teacher.getSelectedItemPosition()]+
                                            "&session_add=yes"+
                                            "&from_date="+ed_startdate.getText().toString()+
                                            "&to_date="+ed_enddate.getText().toString()+
                                            "&from_time="+removeLastChar(from_time)+
                                            "&service_duration="+removeLastChar(service_duration)+
                                            "&check_list="+removeLastChar(check_list)
                                    ;
                                }else {
                                    //spinner_business_list.setVisibility(View.VISIBLE);
                                    params ="user_id="+sharedPreferences.getString("user_id", "")+
                                            "&business_id="+sharedPreferences.getString("business_id", "")+
                                            "&class_id="+class_id+
                                            "&class_name="+class_name_with_id+
                                            "&class_note="+class_note+
                                            "&roll="+class_payment_with_id+
                                            "&cost="+ed_price.getText().toString()+
                                            "&slot="+ed_totalseat.getText().toString()+
                                            "&gender="+spinner_gender.getSelectedItem().toString()+
                                            "&staff_id="+staff_value[spinner_teacher.getSelectedItemPosition()]+
                                            "&session_add=yes"+
                                            "&from_date="+ed_startdate.getText().toString()+
                                            "&to_date="+ed_enddate.getText().toString()+
                                            "&from_time="+removeLastChar(from_time)+
                                            "&service_duration="+removeLastChar(service_duration)+
                                            "&check_list="+removeLastChar(check_list)
                                            /*"&from_date="+ed_startdate.getText().toString()+
                                            "&to_date="+ed_enddate.getText().toString()+
                                            "&from_date="+start_date+
                                            "&to_date="+end_date+*/
                                    ;
                                }

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {

                                                Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                                getActivity().getSupportFragmentManager().popBackStack();

                                            }
                                            else{

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);

                            }else if (class_id.equals("") && session_id.equals("") ){
                                //adding new session in new class
                                //add_edit_classs api call here


                                  /*
$user_id //compulsory
$business_id //compulsory

$class_id //compulsory incase of edit
$class_name //compulsory
$class_note
$roll //1 for yes , 0 for no ---- compulsory

$cost //compulsory if session_add=yes
$gender //compulsory if session_add=yes male,female,both
$staff_id //compulsory if session_add=yes
$session_add //if session add yes else no compulsory
$from_date //compulsory if session_add=yes
$to_date //compulsory if session_add=yes

$from_time //compulsory if session_add=yes , separated array  //done
$service_duration //compulsory if session_add=yes , separated array  //done
$check_list //compulsory if session_add=yes , separated array  //done
*/
                                  //http://192.168.100.14/monshiapp/app/add_edit_class.php
                                  String url = getResources().getString(R.string.url)+"add_edit_class.php";

                                  String params = "";
                                  if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                                      //spinner_business_list.setVisibility(View.GONE);
                                      params ="user_id="+sharedPreferences.getString("user_id", "")+
                                              "&business_id="+sharedPreferences.getString("selected_business", "")+
                                              "&class_id="+
                                              "&class_name="+class_name_no_id+
                                              "&class_note="+class_note+
                                              "&roll="+class_payment_no_id+
                                              "&cost="+ed_price.getText().toString()+
                                              "&slot="+ed_totalseat.getText().toString()+
                                              "&gender="+gender_value[spinner_gender.getSelectedItemPosition()]+
                                              "&staff_id="+staff_value[spinner_teacher.getSelectedItemPosition()]+
                                              "&session_add=yes"+
                                              "&from_date="+ed_startdate.getText().toString()+
                                              "&to_date="+ed_enddate.getText().toString()+
                                              "&from_time="+removeLastChar(from_time)+
                                              "&service_duration="+removeLastChar(service_duration)+
                                              "&check_list="+removeLastChar(check_list)
                                      ;
                                  }else {
                                      //spinner_business_list.setVisibility(View.VISIBLE);
                                      params ="user_id="+sharedPreferences.getString("user_id", "")+
                                              "&business_id="+sharedPreferences.getString("business_id", "")+
                                              "&class_id="+class_id+
                                              "&class_name="+class_name_no_id+
                                              "&class_note="+class_note+
                                              "&roll="+class_payment_no_id+
                                              "&cost="+ed_price.getText().toString()+
                                              "&slot="+ed_totalseat.getText().toString()+
                                              "&gender="+spinner_gender.getSelectedItem().toString()+
                                              "&staff_id="+staff_value[spinner_teacher.getSelectedItemPosition()]+
                                              "&session_add=yes"+
                                              "&from_date="+ed_startdate.getText().toString()+
                                              "&to_date="+ed_enddate.getText().toString()+
                                              "&from_time="+removeLastChar(from_time)+
                                              "&service_duration="+removeLastChar(service_duration)+
                                              "&check_list="+removeLastChar(check_list)
                                      ;
                                  }

                                  WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                      @Override
                                      public void TaskCompletionResult(String result) {

                                          try {

                                              JSONObject jsonObject = new JSONObject(result);
                                              if(jsonObject.getString("status").equals("200")) {

                                                  Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                                  new_class_id = jsonObject.getString("class_id");
                                                  //Toast.makeText(getActivity(),"new_class_id adding class = "+new_class_id,Toast.LENGTH_SHORT).show();
                                                  Log.d("TAG", "File...:::: new_class_id adding class =" + new_class_id);
                                                  getActivity().getSupportFragmentManager().popBackStack();

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
            }
        }
        return rootView;
    }

    private static String removeLastChar(String str) {
        if(str.equals("")){
            return "";
        }else return str.substring(0, str.length() - 1);
    }
    public void dummyfun(String staff_id){
        linearLayout.removeAllViews();

        //http://192.168.100.14/monshiapp/app/class_session_detail_info.php?user_id=1&bus_id=1&session_id=99
        String url = getResources().getString(R.string.url)+"class_session_detail_info.php";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                    "&session_id="+session_id+"&staff_id="+staff_id;
            ;
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params =  "user_id="   +sharedPreferences.getString("user_id", "")+
                    "&bus_id="+sharedPreferences.getString("business_id", "")+
                    "&session_id=" +session_id+"&staff_id="+staff_id;
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {

                        String s_staff_id = jsonObject.getString("s_staff_id");
                        String from_date  = jsonObject.getString("from_date");
                        String to_date    = jsonObject.getString("to_date");

                        ed_startdate.setText(""+from_date);
                        ed_enddate.setText(""+to_date);

                        System.out.println("to_date = "+to_date);

                        JSONArray gender_list = jsonObject.getJSONArray("gender_list");

                        gender = new String[gender_list.length()];
                        gender_value= new String[gender_list.length()];

                        int gender_index = 0;

                        for(int i = 0; i < gender_list.length(); i++) {
                            JSONObject c = gender_list.getJSONObject(i);
                            String string = c.getString("string");
                            String value = c.getString("value");
                            String status = c.getString("status");

                            gender[i] = string;
                            gender_value[i]=value;

                            if (status.equals("enable")){
                                gender_index = i;
                            }

                            System.out.println("gender_list = "+status);
                        }
                        if (getActivity()!=null){

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, gender) {

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

                            spinner_gender.setAdapter(adapter);
                            spinner_gender.setSelection(gender_index);

                        }

                        String slot = jsonObject.getString("slot");
                        ed_totalseat.setText(""+slot);
                        String cost = jsonObject.getString("cost");
                        ed_price.setText(""+cost);



                        JSONArray days_array = jsonObject.getJSONArray("days_array");

                        for(int i = 0; i < days_array.length(); i++) {
                            JSONObject c = days_array.getJSONObject(i);
                            String day_string = c.getString("day_string");
                            String value = c.getString("value");
                            String status = c.getString("status");

                            System.out.println("days_array = "+status);

                            LayoutInflater factory = LayoutInflater.from(getActivity());
                            View row_view_session_day = factory.inflate(R.layout.row_session_day, null);

                            TextView tv_day_label = row_view_session_day.findViewById(R.id.tv_day_label);
                            tv_day_label.setText(""+day_string);

                            TextView tv_day_label_english = row_view_session_day.findViewById(R.id.tv_day_label_english);
                            tv_day_label_english.setText(""+value);

                            final TextView tv_dummy = row_view_session_day.findViewById(R.id.tv_dummy);


                            final SwitchCompat btn_switch = row_view_session_day.findViewById(R.id.btn_switch);

                            final Spinner spinner_from_time     = row_view_session_day.findViewById(R.id.spinner_from_time);
                            final Spinner spinner_service_duration = row_view_session_day.findViewById(R.id.spinner_service_duration);

                            //btn_switch.setTag(status);

                            if (status.equals("enable")){
                                btn_switch.setChecked(true);
                                spinner_from_time.setEnabled(true);
                                spinner_service_duration.setEnabled(true);
                            }else {
                                btn_switch.setChecked(false);
                                spinner_from_time.setEnabled(false);
                                spinner_service_duration.setEnabled(false);
                            }

                            btn_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                    if (isChecked) {
                                        if (btn_switch.getTag().equals("enable")){

                                            if (tv_dummy.getTag().equals("enable")){
                                                spinner_from_time.setEnabled(true);
                                                spinner_service_duration.setEnabled(true);
                                            }else {
                                                Toast.makeText(getActivity(),getString(R.string.str_staff_disable),Toast.LENGTH_SHORT).show();
                                                btn_switch.setChecked(false);
                                            }

                                        }else {
                                            Toast.makeText(getActivity(),getString(R.string.str_business_is_off),Toast.LENGTH_SHORT).show();
                                            btn_switch.setChecked(false);
                                        }

                                    } else {
                                        spinner_from_time.setEnabled(false);
                                        spinner_service_duration.setEnabled(false);
                                    }
                                }
                            });

                            JSONArray service_duration = c.getJSONArray("service_duration");

                            service_duration_array = new String[service_duration.length()];
                            service_duration_array_value= new String[service_duration.length()];

                            int service_duratiion = 0;

                            for(int j = 0; j < service_duration.length(); j++) {
                                JSONObject d = service_duration.getJSONObject(j);
                                String string = d.getString("string");
                                String value_service = d.getString("value");
                                String status_service = d.getString("status");

                                service_duration_array[j] = string;
                                service_duration_array_value[j]=value_service;

                                System.out.println("service_duration = "+status);

                                if (status_service.equals("enable")){
                                    service_duratiion = j;
                                }
                            }
                            if (getActivity()!=null){

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, service_duration_array) {

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

                                spinner_service_duration.setAdapter(adapter);
                                spinner_service_duration.setSelection(service_duratiion);
                            }

                            JSONArray from_time_arr = c.getJSONArray("from_time_arr");

                            from_time_array = new String[from_time_arr.length()];
                            from_time_array_value= new String[from_time_arr.length()];

                            int from_time_array_index = 0;

                            for(int j = 0; j < from_time_arr.length(); j++) {
                                JSONObject d = from_time_arr.getJSONObject(j);
                                String value_from_time = d.getString("value");
                                String value_in_per = d.getString("value_in_per");
                                String status_from_time = d.getString("status");

                                from_time_array      [j] = value_in_per;
                                from_time_array_value[j] = value_from_time;

                                System.out.println("from_time_arr = "+status_from_time);

                                if (status_from_time.equals("enable")){

                                    from_time_array_index = j;
                                }
                            }
                            if (getActivity()!=null){

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, from_time_array) {

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

                                spinner_from_time.setAdapter(adapter);
                                spinner_from_time.setSelection(from_time_array_index);

                            }

                            linearLayout.addView(row_view_session_day);
                        }

                        JSONArray business_day = jsonObject.getJSONArray("business_day");

                        for(int j = 0; j < business_day.length(); j++) {
                            JSONObject d = business_day.getJSONObject(j);
                            String day = d.getString("day");
                            String status_staff_day = d.getString("status");
                            String bus_status_business_day = d.getString("bus_status");

                            ConstraintLayout layout_data = (ConstraintLayout) linearLayout.getChildAt(j);

                            SwitchCompat btn_switch = layout_data.findViewById(R.id.btn_switch);
                            TextView tv_day_label = layout_data.findViewById(R.id.tv_day_label);
                            TextView tv_day_label_english = layout_data.findViewById(R.id.tv_day_label_english);
                            TextView tv_dummy = layout_data.findViewById(R.id.tv_dummy);
                            Spinner spinner_from_time = layout_data.findViewById(R.id.spinner_from_time);
                            Spinner spinner_service_duration = layout_data.findViewById(R.id.spinner_service_duration);

                            btn_switch.setTag(bus_status_business_day);
                            tv_dummy.setTag(status_staff_day);
                            //Toast.makeText(getActivity(), "bus_status_business_day = " + bus_status_business_day, Toast.LENGTH_SHORT).show();
                        }

                    } else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);

    }
}
