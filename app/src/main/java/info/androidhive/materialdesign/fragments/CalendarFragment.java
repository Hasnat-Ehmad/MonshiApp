package info.androidhive.materialdesign.fragments;

import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.pagerfragmentcontiners.BookAppointmentFragment;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;


import static android.content.ContentValues.TAG;
import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;


public class CalendarFragment extends Fragment {
    SharedPreferences sharedPreferences;
    Button btn_continue;
    Spinner spinner_business_service,spinner_business_staff,spinner_business_timing;

     public static String [] services,services_id,service_time; public static int service_position;
     public static String [] staff,staff_id;   public static int staff_position;


    String [] time,time_persian;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
      /*  if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
            spinner_business_list.setVisibility(View.GONE);
        }else {
            spinner_business_list.setVisibility(View.VISIBLE);
        }*/


        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        spinner_business_service = (Spinner) rootView.findViewById(R.id.spinner_business_service);
        setSpinner_business_service();
        spinner_business_staff = (Spinner) rootView.findViewById(R.id.spinner_business_staff);

        spinner_business_timing = (Spinner) rootView.findViewById(R.id.spinner_business_timing);


        btn_continue = (Button) rootView.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if(spinner_business_service.getSelectedItem().toString().equals("")){
                        //ed_gender.setError("Enter gender");
                        ((TextView)spinner_business_service.getChildAt(0)).setError("Select service or change business");

                    }else if (spinner_business_staff.getSelectedItem().toString().equals("")){
                        ((TextView)spinner_business_staff.getChildAt(0)).setError("Select staff or change business");
                    }else if (spinner_business_timing != null && spinner_business_timing.getSelectedItem() ==null){
                            Toast.makeText(getActivity(),"select a time by pressing on date",Toast.LENGTH_LONG).show();
                    } else {

                        BookAppointmentFragment fragment = new BookAppointmentFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
            }
        });


        PersianDatePicker persianDatePicker = rootView.findViewById(R.id.persian_date_picker);
        persianDatePicker.setYearMonths( new mCalendar( new PersianCalendar().getPersianLongDate()).getYearMonths() )
                .setDefaultItemBackgroundColor(R.color.colorAccent)  // background color of non-selected item
                .setDefaultItemTextColor(R.color.windowBackground)  // text color of non-selected item
                .setSelectedItemBackgroundColor(R.color.colorPrimaryDark) // background color of selected item
                .setSelectedItemTextColor(R.color.windowBackground)   // text color of selected item
                .setListener(new PersianDatePicker.OnDaySelectListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDaySelect(YearMonth yearMonth, Day day) {
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

                        android.icu.util.Calendar cal= android.icu.util.Calendar.getInstance();
                        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                        int monthnum=api_month-1;
                        cal.set(android.icu.util.Calendar.MONTH,monthnum);
                        final String month_name = month_date.format(cal.getTime());

                        //Toast.makeText(getActivity(),"month_name = "+month_name, Toast.LENGTH_LONG).show();
                        String str_date = "";

                        if(api_month<10){
                            str_date = api_year+"-0"+api_month+"-"+api_date;
                        }
                        if(api_date<10){
                            str_date = api_year+"-"+api_month+"-0"+api_date;
                        }
                        if(api_month<10&&api_date<10){
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
                        String finalDay=format2.format(dt1);
                        //finalDay = "Monday";
                        //Toast.makeText(getActivity(),"day = "+finalDay, Toast.LENGTH_LONG).show();


                        //http://192.168.100.14/monshiapp/app/get_business_hour_on_specific_day.php
                        // user_id=1&business_id=1&day=Saturday&date=25-03-2019
                        String url    =  getResources().getString(R.string.url)+"business_time_staff.php";
                        String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                "&business_id="+sharedPreferences.getString("business_id", "")+
                                "&day="+finalDay+"&date="+str_date;

                        final String finalStr_date = str_date;
                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {
                                        JSONArray business_available_hours = jsonObject.getJSONArray("time_list");

                                        time    = new String [business_available_hours.length()];
                                        time_persian = new String [business_available_hours.length()];

                                        for(int i = 0; i < business_available_hours.length(); i++) {
                                            JSONObject c = business_available_hours.getJSONObject(i);
                                            String value = c.getString("value");
                                            String show_value_per = c.getString("show_value_per");

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
                                                android.icu.util.Calendar calendar = android.icu.util.Calendar.getInstance();
                                                calendar.setTime(date);
                                                calendar.add(android.icu.util.Calendar.MINUTE, Integer.parseInt(service_time[service_position]));

                                                String end_hour = String.valueOf(calendar.get(android.icu.util.Calendar.HOUR)+5);
                                                String end_min = String.valueOf(calendar.get(android.icu.util.Calendar.MINUTE));
                                                String end_sec = String.valueOf(calendar.get(Calendar.SECOND));
                                                //Toast.makeText(getActivity(),"end_time = "+calendar.getTime(), Toast.LENGTH_LONG).show();

                                                String end_time =finalStr_date+" "+end_hour+":"+end_min+":"+end_sec;

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
        //http://192.168.100.14/monshiapp/app/service_listing.php
        String url    =  getResources().getString(R.string.url)+"service_listing.php";
        String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("business_id", "");

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray service_listing = jsonObject.getJSONArray("service_listing");

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

                                if (getActivity()!=null){

                                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                            R.layout.my_spinner_style, services) {

                                        public View getView(int position, View convertView, ViewGroup parent) {
                                            View v = super.getView(position, convertView, parent);

                                            return v;
                                        }

                                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                            View v =super.getDropDownView(position, convertView, parent);
                                            // ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                            return v;
                                        }
                                    };
                                    spinner_business_service.setAdapter(adapter);
                                }





                            spinner_business_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    setSpinner_business_staff(services_id[position]);
                                    service_position = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }
                            });

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

    public void setSpinner_business_staff(String service_id){
        //http://192.168.100.14/monshiapp/app/get_staff_against_service.php
        String url    =  getResources().getString(R.string.url)+"get_staff_against_service.php";
        String params =   "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("business_id", "")+
                "&service_id="+service_id;


        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray staff_list = jsonObject.getJSONArray("staff_list");

                        if (staff_list.length()==0){

                        }else {
                            staff    = new String [staff_list.length()];
                            staff_id = new String [staff_list.length()];

                            for(int i = 0; i < staff_list.length(); i++) {
                                JSONObject c = staff_list.getJSONObject(i);
                                String id = c.getString("id");
                                String full_name = c.getString("full_name");

                                staff [i]  = full_name;
                                staff_id[i]= id;

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                    R.layout.my_spinner_style, staff) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);


                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
                                   // ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                    return v;
                                }
                            };

                            spinner_business_staff.setAdapter(adapter);

                            spinner_business_staff.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    staff_position = position;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }
                            });

                            spinner_business_staff.setSelection(0);


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


/*                        // right your code here when item is selected
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
                        int year  = yearMonth.getYear();

                        PersianDate pdate = new PersianDate();

                        int[] intArray = new int[3];
                        intArray = pdate.toGregorian( year,  month,  gday);

                        int api_year  = intArray[0];
                        int api_month = intArray[1];
                        int api_date  = intArray[2];

                        String str_date = api_date+"-"+api_month+"-"+api_year;


//                        Toast.makeText(getActivity(),"str_date = "+str_date, Toast.LENGTH_LONG).show();
//                        String dayOfTheWeek = (String) DateFormat.format("EEEE", stringDate); // Thursday
//                        String monthString  = (String) DateFormat.format("MMM",  stringDate); // Jun
//                        String monthNumber  = (String) DateFormat.format("MM",   stringDate); // 06
//                        Toast.makeText(getActivity(),"finalDay = "+finalDay, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"day = "+pdate.getGrgDay(), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"year = "+intArray[0], Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"month = "+intArray[1], Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"date = "+intArray[2], Toast.LENGTH_LONG).show();


*/