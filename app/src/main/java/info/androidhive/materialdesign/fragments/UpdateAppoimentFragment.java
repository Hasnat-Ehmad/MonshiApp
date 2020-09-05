package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

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
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;


public class UpdateAppoimentFragment extends Fragment {
    SharedPreferences sharedPreferences;

    LinearLayout time_layout;
    HorizontalScrollView Horizontalscreollview;

    String id,business_id,start,end,service_id,service_time,staff_id,user_id,customer_id;

    String [] time,time_persian,appt_booked;

    int buttoncheck=0;

    String START_DATE_TIME = "";
    String END_DATE_TIME   = "";

    Button btn_continue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_update_appoiment, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        time_layout           = rootView.findViewById(R.id.time_layout);
        Horizontalscreollview = rootView.findViewById(R.id.Horizontalscreollview);


        if (getArguments()!=null){

             id          = getArguments().getString("id");
             user_id     = getArguments().getString("user_id");
             business_id = getArguments().getString("business_id");
             service_id  = getArguments().getString("service_id");
             service_time= getArguments().getString("service_time");
             staff_id    = getArguments().getString("staff_id");
             customer_id = getArguments().getString("customer_id");
             start       = getArguments().getString("start");
             end         = getArguments().getString("end");
        }


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
                        //Toast.makeText(getActivity(), yearMonth.getYear() + "/" +
                        //yearMonth.getMonth() + "/" + day.getDay() , Toast.LENGTH_SHORT).show();
                        /*Toast.makeText(getActivity(), day.getNumber() + "-" +
                          yearMonth.getMonthNumber() + "-" + yearMonth.getYear() , Toast.LENGTH_SHORT).show();*/

                            int month = Integer.parseInt(yearMonth.getMonthNumber());
                            int gday  = day.getNumber();
                            int year  = yearMonth.getYear();

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
                            String str_date = api_year+"-"+api_month+"-"+api_date;;

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
                            final String finalDay=format2.format(dt1);
                            //finalDay = "Monday";
                            //Toast.makeText(getActivity(),"day = "+finalDay, Toast.LENGTH_LONG).show();


                            //http://192.168.100.14/monshiapp/app/get_business_hour_on_specific_day.php
                            // user_id=1&business_id=1&day=Saturday&date=25-03-2019
                            String url    =  getResources().getString(R.string.url)+"business_time_staff.php";

                            String params = "";
                            if (sharedPreferences.getString("user_role" ,""  ).equals("1") ||
                                    sharedPreferences.getString("user_role" ,""  ).equals("4")){
                                //spinner_business_list.setVisibility(View.GONE);
                                params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                        "&business_id="+business_id+
                                        "&day="+finalDay+"&date="+str_date+"&staff_id="+staff_id;

                            }else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                                //spinner_business_list.setVisibility(View.VISIBLE);
                                params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                        "&business_id="+business_id+
                                        "&day="+finalDay+"&date="+str_date+"&staff_id="+staff_id;

                            }else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                                //spinner_business_list.setVisibility(View.VISIBLE);
                                params ="user_id="+sharedPreferences.getString("user_id", "")+
                                        "&business_id="+business_id+
                                        "&day="+finalDay+"&date="+str_date+"&staff_id="+staff_id;
                            }else {
                                params = "business_id="+sharedPreferences.getString("selected_business", "")+
                                        "&day="+finalDay+"&date="+str_date+"&staff_id="+staff_id;
                            }

                            final String finalStr_date = str_date;
                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

//                                    scrollView_BusinessPublicDetail_Activity.smoothScrollTo(0, 0);
                                        time_layout.removeAllViews();
                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {
                                            JSONArray business_available_hours = jsonObject.getJSONArray("time_list");

                                            time    = new String [business_available_hours.length()];
                                            time_persian = new String [business_available_hours.length()];
                                            appt_booked = new String [business_available_hours.length()];


                                            if (business_available_hours.length()>0){
                                                //time_layout.removeView();

                                                for(int i = 0; i < business_available_hours.length(); i++) {

                                                    JSONObject c = business_available_hours.getJSONObject(i);
                                                    String value = c.getString("value");
                                                    String show_value_per = c.getString("show_value_per");
                                                    final String str_appt_booked = c.getString("appt_booked");

                                                    time        [i] = value;
                                                    time_persian[i] = show_value_per;
                                                    appt_booked[i] = str_appt_booked;


                                                    LayoutInflater factory = LayoutInflater.from(getActivity());
                                                    View row_view_session_day = factory.inflate(R.layout.lv_row, null);

                                                    ConstraintLayout constraintLayout = row_view_session_day.findViewById(R.id.main_layout);

                                                    final TextView tv_time = row_view_session_day.findViewById(R.id.tv_time);
                                                    final TextView tv_already_booked = row_view_session_day.findViewById(R.id.tv_already_booked);
                                                    tv_time.setText(""+show_value_per);
                                                    tv_time.setTag(""+value);
                                                    tv_already_booked.setTag(str_appt_booked);
                                                    if(str_appt_booked.equals("yes")){
                                                        tv_time.setBackgroundColor(getActivity().getResources().getColor(R.color.grey_));

                                                    }

                                                    tv_time.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {

                                                            // Toast.makeText(getActivity(),str_appt_booked,Toast.LENGTH_SHORT).show();

                                                            if(str_appt_booked.equals("yes")){

                                                                //tv_time.setBackgroundColor(getActivity().getResources().getColor(R.color.grey_));
                                                                Toast.makeText(getActivity(),getString(R.string.str_already_booked),Toast.LENGTH_SHORT).show();
                                                            }else{

                                                                buttoncheck=1;

                                                                for(int i=0; i<time_layout.getChildCount();i++)
                                                                {

                                                                    TextView tv_time_1 = (TextView)  time_layout.getChildAt(i).findViewById(R.id.tv_time);
                                                                    TextView tv_already_booked_1 = (TextView)  time_layout.getChildAt(i).findViewById(R.id.tv_already_booked);

                                                                    if(tv_already_booked_1.getTag().equals("no"))
                                                                        tv_time_1.setBackgroundColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorPrimary));

                                                                    // Toast.makeText(getActivity(), "i = "+ i, Toast.LENGTH_SHORT).show();
                                                                }
                                                                tv_time.setBackgroundColor(Objects.requireNonNull(getActivity()).getResources().getColor(R.color.colorPrimaryDark));
                                                                //Toast.makeText(getActivity(),"tv_time = "+tv_time.getText(), Toast.LENGTH_LONG).show();


                                                                String start_time =  tv_time.getTag().toString();
                                                                //  Toast.makeText(getActivity(),"start_time = "+start_time, Toast.LENGTH_LONG).show();

                                                                String finaldata = finalStr_date +" "+start_time;
                                                                // String finaldata = finalStr_date +" "+start_time+finalDay+","+month_name;

                                                                //SavePreferences("finaldata"        ,finaldata);

                                                                START_DATE_TIME = finaldata;

                                                                //  Toast.makeText(getActivity(),"finaldata = "+finaldata, Toast.LENGTH_LONG).show();

                                                                SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");
                                                                String currentDateandTime = sdf.format(new Date());

                                                                Date date = null;
                                                                try {
                                                                    date = sdf.parse(start_time);
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }

                                                                if (!service_time.equals("")) {
                                                                    Calendar calendar = Calendar.getInstance();
                                                                    calendar.setTime(date);
                                                                    calendar.add(Calendar.MINUTE, Integer.parseInt(service_time));

                                                                    String end_hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) );
                                                                    String end_min = String.valueOf(calendar.get(Calendar.MINUTE));
                                                                    String end_sec = String.valueOf(calendar.get(Calendar.SECOND));
                                                                    //Toast.makeText(getActivity(),"end_time = "+calendar.getTime(), Toast.LENGTH_LONG).show();

                                                                    String end_time="";
                                                                    if (end_hour.equals("8") || end_hour.equals("9")){
                                                                        end_time  = finalStr_date + " 0" + end_hour + ":" + end_min + ":0" + end_sec;
                                                                    }else {
                                                                        end_time  = finalStr_date + " " + end_hour + ":" + end_min + ":0" + end_sec;
                                                                    }

                                                                   // SavePreferences("end_time", end_time);
                                                                    END_DATE_TIME = end_time;

                                                                    //Toast.makeText(getActivity(),"end_time = "+end_time, Toast.LENGTH_LONG).show();
                                                                    System.out.println("Time here " + calendar.getTime());


                                                                }
                                                            }
                                                        }
                                                    });

                                                    time_layout.addView(constraintLayout);
                                                    Horizontalscreollview.postDelayed(new Runnable() {
                                                        public void run() {
                                                            Horizontalscreollview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                                                        }
                                                    }, 100L);
                                                }

                                            }else {

                                                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.str_no_time_avaiable)
                                                        ,Toast.LENGTH_SHORT).show();
                                                time_layout.removeAllViews();
                                            }
                                        }
                                        else{
                                            Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"), Toast.LENGTH_LONG).show();
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



        btn_continue = rootView.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://192.168.100.14/monshiapp/app/update_appointment.php
                String url    =  getResources().getString(R.string.url)+"update_appointment.php";

                String  params =  "user_id="+sharedPreferences.getString("user_id", "")+
                        "&business_id="+business_id+
                        "&id="+id+
                        "&service_id="+service_id+
                        "&staff_id="+staff_id+
                        "&customer_id="+customer_id+
                        "&role="+sharedPreferences.getString("user_role", "")+
                        "&start="+START_DATE_TIME+
                        "&end="+END_DATE_TIME
                        ;

                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {
                                Toast.makeText(getActivity(), ""+jsonObject.getString("status_alert"), Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();
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


        return rootView;

    }
}
