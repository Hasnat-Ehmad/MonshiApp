package info.androidhive.materialdesign.staff_work;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ratintech.behkha.persiandatepicker.PersianDatePicker;
import com.ratintech.behkha.persiandatepicker.models.Day;
import com.ratintech.behkha.persiandatepicker.models.YearMonth;
import com.ratintech.behkha.persiandatepicker.models.mCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.RequiresApi;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BookAppt_NonLogin;
import info.androidhive.materialdesign.activity.CustomerBookingActivity;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;


public class Staff_Calendar_Fragment extends Fragment {


    Button btn_continue;
    SharedPreferences sharedPreferences;
    String [] time,time_persian;

    Spinner spinner_business_service,spinner_business_staff,spinner_business_timing;

    public static String [] services,services_id,service_time; public static int service_position;

    View tv_label_staff,view2;

    String str_date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // View rootView =  inflater.inflate(R.layout.fragment_staff_calendar, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        View rootView = inflater.inflate(R.layout.fragment_business_book_appointment, container, false);

        tv_label_staff = rootView.findViewById(R.id.tv_label_staff);
        tv_label_staff.setVisibility(View.GONE);
        view2 = rootView.findViewById(R.id.view2);
        view2.setVisibility(View.GONE);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


        spinner_business_service = (Spinner) rootView.findViewById(R.id.spinner_business_service);
        setSpinner_business_service();
        spinner_business_staff = (Spinner) rootView.findViewById(R.id.spinner_business_staff);
        spinner_business_staff.setVisibility(View.GONE);//staff spinner not required here

        spinner_business_timing = (Spinner) rootView.findViewById(R.id.spinner_business_timing);

        btn_continue = (Button) rootView.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinner_business_service != null && spinner_business_service.getSelectedItem() == null){
                    /*    spinner_business_service.setError("Enter gender");
                        ((TextView)spinner_business_service.getSelectedView()).setError("Select service or change business");
                        TextView errorText = (TextView)spinner_business_service.getSelectedView();
                        errorText.setError("anything here, just to add the icon");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Select service or change business");//changes the selected item text to this*/

                    Toast.makeText(getActivity(),"select a service or change the business",
                            Toast.LENGTH_LONG).show();
                }else if (spinner_business_timing != null && spinner_business_timing.getSelectedItem() ==null){
                    Toast.makeText(getActivity(),"select a time by pressing on date",
                            Toast.LENGTH_LONG).show();
                } else {

                   /* BookAppointmentFragment_non_login fragment = new BookAppointmentFragment_non_login();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/

                    Intent intent = new Intent(getActivity(), StaffBookingActivity.class);
                    getActivity().startActivity(intent);

             /*     Intent intent = new Intent(getActivity(),BookAppt_NonLogin.class);
                    getActivity().startActivity(intent);*/
                }
            }
        });


        PersianDatePicker persianDatePicker = rootView.findViewById(R.id.persian_date_picker);
        persianDatePicker.setYearMonths( new mCalendar( new PersianCalendar().getPersianLongDate()).getYearMonths() )
                .setDefaultItemBackgroundColor(R.color.colorPrimary)  // background color of non-selected item
                .setDefaultItemTextColor(R.color.windowBackground)  // text color of non-selected item
                .setSelectedItemBackgroundColor(R.color.colorPrimaryDark) // background color of selected item
                .setSelectedItemTextColor(R.color.windowBackground)   // text color of selected item
                .setListener(new PersianDatePicker.OnDaySelectListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDaySelect(YearMonth yearMonth, final Day day) {
                        // right your code here when item is selected
                        // right your code here when item is selected
//                        Toast.makeText(getActivity(), yearMonth.getYear() + "/" +
//                                yearMonth.getMonth() + "/" + day.getDay() , Toast.LENGTH_SHORT).show();

//                        Toast.makeText(getActivity(), day.getNumber() + "-" +
//                                yearMonth.getMonthNumber() + "-" + yearMonth.getYear() , Toast.LENGTH_SHORT).show();
                        int g_month = Integer.parseInt(yearMonth.getMonthNumber());
                        int g_day = day.getNumber();

//                        JalaliCalendar jalaliCalendar =
//                                new JalaliCalendar(yearMonth.getYear(),g_month , g_day);
//                        GregorianCalendar gc = jalaliCalendar.toGregorian();
//                        Toast.makeText(getActivity(),"gc = "+   gc.toString(), Toast.LENGTH_LONG).show();

//                        Toast.makeText(getActivity(),"gc = "+   gc.getActualMaximum(8), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"gc = "+   gc.DATE, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"gc = "+   gc.MONTH, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"gc = "+   gc.YEAR, Toast.LENGTH_LONG).show();

                        int month = Integer.parseInt(yearMonth.getMonthNumber());
                        int gday  = day.getNumber();
                        int year  =yearMonth.getYear();

                        PersianDate pdate = new PersianDate();

                        int[] intArray = new int[3];
                        intArray = pdate.toGregorian( year,  month,  gday);

                        int api_year  = intArray[0];
                        int api_month = intArray[1];
                        int api_date  = intArray[2];

                        Calendar cal=Calendar.getInstance();
                        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                        int monthnum=api_month-1;
                        cal.set(Calendar.MONTH,monthnum);
                        final String month_name = month_date.format(cal.getTime());

                        //Toast.makeText(getActivity(),"month_name = "+month_name, Toast.LENGTH_LONG).show();

                        String str_date = null;

                        if (api_month<10){
                            str_date= api_year+"-0"+api_month+"-"+api_date;
                        }
                        if (api_date<10){
                            str_date = api_year+"-"+api_month+"-0"+api_date;
                        }
                        if (api_date<10 && api_month<10){
                            str_date = api_year+"-0"+api_month+"-0"+api_date;
                        }


                        // Toast.makeText(getActivity(),"str_date = "+str_date, Toast.LENGTH_LONG).show();

                        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                        Date dt1= null;
                        try {

                            dt1 = format1.parse(str_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        DateFormat format2=new SimpleDateFormat("EEEE");
                        final String finalDay=format2.format(dt1);
                        //Toast.makeText(getActivity(),"day = "+finalDay, Toast.LENGTH_LONG).show();


                        //http://192.168.100.14/monshiapp/app/business_time_staff.php
                        // business_id=2&staff_id=2&date=2019-04-02
                        String url    =  getResources().getString(R.string.url)+"business_time_staff.php";
                        String params =  "staff_id="+sharedPreferences.getString("staff_user_id", "")+
                                "&business_id="+sharedPreferences.getString("business_id", "")+
                                "&date="+str_date;

                        final String finalStr_date = str_date;
                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {
                                        JSONArray service_listing = jsonObject.getJSONArray("time_list");

                                        time    = new String [service_listing.length()];
                                        time_persian = new String [service_listing.length()];

                                        for(int i = 0; i < service_listing.length(); i++) {
                                            JSONObject c = service_listing.getJSONObject(i);
                                            String value = c.getString("value");
                                            String show_value_per = c.getString("show_value_per");
                                            //String show_value_per = c.getString("string");

                                            time        [i]  = value;
                                            time_persian[i]  = show_value_per;

                                        }

                                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                                R.layout.my_spinner_style, time_persian) {

                                            public View getView(int position, View convertView, ViewGroup parent) {
                                                View v = super.getView(position, convertView, parent);

                                                return v;
                                            }

                                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                                View v =super.getDropDownView(position, convertView, parent);
//                                                ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                                //((TextView) v).setGravity(Gravity.CENTER);
                                                return v;
                                            }
                                        };

                                        spinner_business_timing.setAdapter(adapter);

                                        spinner_business_timing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                //start=دوشنبه+مارس+11+2019+08:50:00+GMT+0000

                                                String start_time =  time[position];
                                                //  Toast.makeText(getActivity(),"start_time = "+start_time, Toast.LENGTH_LONG).show();


                                                String finaldata = finalStr_date +" "+start_time/*+finalDay+","+month_name*/;

                                                SavePreferences("finaldata"        ,finaldata);

                                                //  Toast.makeText(getActivity(),"finaldata = "+finaldata, Toast.LENGTH_LONG).show();

                                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                                String currentDateandTime = sdf.format(new Date());

                                                Date date = null;
                                                try {
                                                    date = sdf.parse(start_time);
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                                Calendar calendar = Calendar.getInstance();
                                                calendar.setTime(date);
                                                calendar.add(Calendar.MINUTE, Integer.parseInt(service_time[service_position]));



                                                String end_hour = String.valueOf(calendar.get(Calendar.HOUR)+5);
                                                String end_min = String.valueOf(calendar.get(Calendar.MINUTE));
                                                String end_sec = String.valueOf(calendar.get(Calendar.SECOND));
                                                //Toast.makeText(getActivity(),"end_time = "+calendar.getTime(), Toast.LENGTH_LONG).show();

                                                String end_time = finalStr_date +" "+end_hour+":"+end_min+":"+end_sec;

                                                SavePreferences("end_time"        ,end_time);

                                                //Toast.makeText(getActivity(),"end_time = "+end_time, Toast.LENGTH_LONG).show();
                                                System.out.println("Time here "+calendar.getTime());

                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {


                                            }
                                        });

                                        spinner_business_timing.setSelection(0);

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
                })
                .setItemElevation( 4f )  // default is 0
                .setItemRadius( 2f )  // default is 0
                .hasAnimation(true) // Animation for item selection . default is false
                .load();





        return rootView;
    }

    public void setSpinner_business_service(){
        //http://192.168.100.14/monshiapp/app/manage_staff_service_list.php?staff_id=14
        String url    =  getResources().getString(R.string.url)+"manage_staff_service_list.php";
        String params =  "staff_id="+sharedPreferences.getString("staff_user_id", "");
                /*"&business_id="+sharedPreferences.getString("selected_business", "")*/

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray service_listing = jsonObject.getJSONArray("service_list");

                        services    = new String [service_listing.length()];
                        services_id = new String [service_listing.length()];
                        service_time= new String [service_listing.length()];

                        for(int i = 0; i < service_listing.length(); i++) {
                            JSONObject c = service_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String user_id = c.getString("user_id");
                            String business_id = c.getString("business_id");
                            String servicename = c.getString("servicename");
                            String description = c.getString("description");
                            String amount = c.getString("amount");
                            String time = c.getString("time");
                            String buffer_time = c.getString("buffer_time");
                            String role = c.getString("role");

                            services   [i]  = servicename;
                            services_id[i]  = id;
                            service_time[i] = time;


                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, services) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);

                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
//                                    ((TextView) v).setBackgroundResource(R.drawable.grey_border);

                                    ((TextView) v).setGravity(Gravity.CENTER);
                                    return v;
                                }
                            };

                            spinner_business_service.setAdapter(adapter);

                            /*spinner_business_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //
                                }
                            });*/
                            spinner_business_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    service_position = position;
                                    // Toast.makeText(getActivity().getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }
                            });
                            //spinner_business_service.setSelection(service_position);
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
    }
    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
