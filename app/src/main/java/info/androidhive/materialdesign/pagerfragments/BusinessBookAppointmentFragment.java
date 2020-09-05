package info.androidhive.materialdesign.pagerfragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BookAppt_NonLogin;
import info.androidhive.materialdesign.activity.CustomerBookingActivity;
import info.androidhive.materialdesign.activity.LoginActivity;
import info.androidhive.materialdesign.activity.MainActivity;
import info.androidhive.materialdesign.pagerfragmentcontiners.BookAppointmentFragment;
import info.androidhive.materialdesign.staff_work.StaffBookingActivity;
import info.androidhive.materialdesign.staff_work.StaffScreensActivity;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;

public class BusinessBookAppointmentFragment extends Fragment {
    Button btn_continue;
    SharedPreferences sharedPreferences;
    String [] time,time_persian,appt_booked;

    Spinner spinner_business_service,spinner_business_staff,spinner_business_timing;

    public static String [] services,services_id,service_time,service_amount; public static int service_position;
    public static String [] staff,staff_id;      public static int staff_position;

    int buttoncheck=0;


    LinearLayout time_layout;
    HorizontalScrollView Horizontalscreollview;
    ProgressBar simpleProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        android.support.v7.widget.Toolbar mToolbar;
        ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

/*        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);
        tv_app_name.setText(getActivity().getResources().getString(R.string.app_name));*/

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.GONE);

/*        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);

        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);*/

       /* img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.GONE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });*/

        // Inflate the layout for this fragmentbtn_continue
        View rootView = inflater.inflate(R.layout.fragment_business_book_appointment, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        simpleProgressBar = rootView.findViewById(R.id.simpleProgressBar);
        simpleProgressBar.setVisibility(View.VISIBLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        time_layout = rootView.findViewById(R.id.time_layout);
        Horizontalscreollview = rootView.findViewById(R.id.Horizontalscreollview);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


        //Toolbar toolbar = getActivity().findViewById(R.id.tv_title);

//        imageModelArrayList = populateData();
//        Log.d("hjhjh",imageModelArrayList.size()+"");

        //tv_title.setVisibility(View.VISIBLE);

        spinner_business_service = (Spinner) rootView.findViewById(R.id.spinner_business_service);
        setSpinner_business_service();
        spinner_business_staff = (Spinner) rootView.findViewById(R.id.spinner_business_staff);

        spinner_business_timing = (Spinner) rootView.findViewById(R.id.spinner_business_timing);
		 if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
            spinner_business_staff.setVisibility(View.GONE);
        }

        btn_continue = (Button) rootView.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((spinner_business_service != null && spinner_business_service.getSelectedItem() == null ) || spinner_business_service.getSelectedItemPosition()==0){
                    /*    spinner_business_service.setError("Enter gender");
                        ((TextView)spinner_business_service.getSelectedView()).setError("Select service or change business");
                        TextView errorText = (TextView)spinner_business_service.getSelectedView();
                        errorText.setError("anything here, just to add the icon");
                        errorText.setTextColor(Color.RED);//just to highlight that this is an error
                        errorText.setText("Select service or change business");//changes the selected item text to this*/

                    Toast.makeText(getActivity(),"select a service",
                            Toast.LENGTH_LONG).show();

                }else if ( (spinner_business_staff != null || spinner_business_staff.getSelectedItem() == null) && spinner_business_staff.getSelectedItemPosition() == 0 && !sharedPreferences.getString("user_role" ,""  ).equals("3")){
                    Toast.makeText(getActivity(),"select a staff or change the service",
                            Toast.LENGTH_LONG).show();
                }else if ( buttoncheck==0){

                    Toast.makeText(getActivity(),"select a time by pressing on date",
                            Toast.LENGTH_LONG).show();

                } else {

                    //Toast.makeText(getActivity(),"spinner_business_timing = "+spinner_business_timing.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();

                   /* BookAppointmentFragment_non_login fragment = new BookAppointmentFragment_non_login();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.container_body, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/

                    if(sharedPreferences.contains("isLogin")){
                        if(sharedPreferences.getString("isLogin","").equals("yes")) {
                            //here role check
                            if (sharedPreferences.getString("user_role",""  ).equals("1")){
                                //admin booking screen
                                Intent intent = new Intent(getActivity(), BookAppt_NonLogin.class);
                                getActivity().startActivity(intent);
                            }else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                                //Customer bookappointment activity here

                                Intent intent = new Intent(getActivity(), BookAppt_NonLogin.class);
                                getActivity().startActivity(intent);

                            }
                            else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                                //Customer bookappointment activity here
                                Intent intent = new Intent(getActivity(), StaffBookingActivity.class);
                                getActivity().startActivity(intent);
                            } else if (sharedPreferences.getString("user_role" ,""  ).equals("4")){
                                //Customer bookappointment activity here
                                Intent intent = new Intent(getActivity(), CustomerBookingActivity.class);
                                getActivity().startActivity(intent);
                            }

                        }else {

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.putExtra("finish", "finish");
                            getActivity().startActivity(intent);

                            //alertDialogShow_login(getActivity());
                        }
                    }else {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.putExtra("finish", "finish");
                        getActivity().startActivity(intent);

                        //alertDialogShow_login(getActivity());
                    }

             /*
                    Intent intent = new Intent(getActivity(),BookAppt_NonLogin.class);
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

//                        int g_month = Integer.parseInt(yearMonth.getMonthNumber());
//                        int g_day = day.getNumber();

//                        JalaliCalendar jalaliCalendar =
//                                new JalaliCalendar(yearMonth.getYear(),g_month , g_day);
//                        GregorianCalendar gc = jalaliCalendar.toGregorian();
//                        Toast.makeText(getActivity(),"gc = "+   gc.toString(), Toast.LENGTH_LONG).show();

//                        Toast.makeText(getActivity(),"gc = "+   gc.getActualMaximum(8), Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"gc = "+   gc.DATE, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"gc = "+   gc.MONTH, Toast.LENGTH_LONG).show();
//                        Toast.makeText(getActivity(),"gc = "+   gc.YEAR, Toast.LENGTH_LONG).show();

                     if ( (((spinner_business_staff != null && spinner_business_staff.getSelectedItem() == null)) ||
                                spinner_business_staff.getSelectedItem().toString().equals("انتخاب کارکنان")) &&

                                (!sharedPreferences.getString("user_role" ,""  ).equals("3" ))){

                            Toast.makeText(getActivity(),"Please select service and staff", Toast.LENGTH_LONG).show();

                        }else {
                         buttoncheck=0;
                            int month = Integer.parseInt(yearMonth.getMonthNumber());
                            int gday  = day.getNumber();
                            int year  = yearMonth.getYear();

                            PersianDate pdate = new PersianDate();

                            int[] intArray = new int[3];
                            intArray = pdate.toGregorian( year,  month,  gday);

                            int api_year  = intArray[0];
                            System.out.println("TAG api_year " + api_year);
                            int api_month = intArray[1];
                            System.out.println("TAG api_month => " + api_month);
                            int api_date  = intArray[2];
                            System.out.println("TAG api_date => " + api_date);

                            Calendar cal=Calendar.getInstance();
                            SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
                            int monthnum=api_month-1;
                            cal.set(Calendar.MONTH,monthnum);
                            final String month_name = month_date.format(cal.getTime());

                            //Toast.makeText(getActivity(),"month_name = "+month_name, Toast.LENGTH_LONG).show();
                            String str_date = "";

                            str_date = api_year+"-"+api_month+"-"+api_date; // implemented on 16 august if date or month both are greater than 9
                                                                            // all three check will not work in this case so need default values
                            if(api_month<10){
                                str_date = api_year+"-0"+api_month+"-"+api_date;
                            }
                            if(api_date<10){
                                str_date = api_year+"-"+api_month+"-0"+api_date;
                            }
                            if(api_month<10&&api_date<10){
                                str_date = api_year+"-0"+api_month+"-0"+api_date;
                            }

                             System.out.println("TAG str_date => " + str_date);

                             SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                             Date dt1= null;

                             try {
                                 dt1 = format1.parse(str_date);
                                 } catch (ParseException e) {
                                        e.printStackTrace();
                                        }
                            //Toast.makeText(getActivity(),"str_date = "+str_date, Toast.LENGTH_LONG).show();
                            DateFormat format2=new SimpleDateFormat("EEEE");
                            final String finalDay=format2.format(dt1);

                            // finalDay = "Monday";
                            // Toast.makeText(getActivity(),"day = "+finalDay, Toast.LENGTH_LONG).show();
                            // http://192.168.100.14/monshiapp/app/get_business_hour_on_specific_day.php
                            // user_id=1&business_id=1&day=Saturday&date=25-03-2019

                            String url = getResources().getString(R.string.url)+"business_time_staff.php";

                            String params = "";
                             if (sharedPreferences.getString("user_role" ,""  ).equals("1") ||
                                     sharedPreferences.getString("user_role" ,""  ).equals("4")){
                                //spinner_business_list.setVisibility(View.GONE);
                                 params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                        "&business_id="+sharedPreferences.getString("selected_business", "")+
                                        "&day="+finalDay+"&date="+str_date+"&staff_id="+staff_id[staff_position];

                            }else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                                //spinner_business_list.setVisibility(View.VISIBLE);
                                params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                        "&business_id="+sharedPreferences.getString("business_id", "")+
                                        "&day="+finalDay+"&date="+str_date+"&staff_id="+staff_id[staff_position];

                            }else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                                //spinner_business_list.setVisibility(View.VISIBLE);
                                params ="user_id="+sharedPreferences.getString("user_id", "")+
                                        "&business_id="+sharedPreferences.getString("business_id", "")+
                                        "&day="+finalDay+"&date="+str_date+"&staff_id="+sharedPreferences.getString("staff_user_id", "");
                            }else {
                                 params = "business_id="+sharedPreferences.getString("selected_business", "")+
                                          "&day="+finalDay+"&date="+str_date+"&staff_id="+staff_id[staff_position];
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

                                              /*
                                                  Model imageModel = new Model(""+show_value_per,""+value);
//                                                imageModel.setName(myImageNameList[i]);
//                                                //imageModel.setImage_drawable(myImageList[i]);
//                                                list.add(imageModel);
                                                  list.add(imageModel);*/

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

                                                                if (service_time.length>0) {
                                                                    Calendar calendar = Calendar.getInstance();
                                                                    calendar.setTime(date);
                                                                    calendar.add(Calendar.MINUTE, Integer.parseInt(service_time[service_position]));

                                                                    String end_hour = String.valueOf(calendar.get(Calendar.HOUR) );
                                                                    String end_min = String.valueOf(calendar.get(Calendar.MINUTE));
                                                                    String end_sec = String.valueOf(calendar.get(Calendar.SECOND));
                                                                    //Toast.makeText(getActivity(),"end_time = "+calendar.getTime(), Toast.LENGTH_LONG).show();

                                                                    String end_time = finalStr_date + " " + end_hour + ":" + end_min + ":" + end_sec;

                                                                    SavePreferences("end_time", end_time);

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



                                           /* customeAdapter = new HorizontalAdapter(getActivity(),list);
                                            horizontalListView.setAdapter(customeAdapter);*/

                                            /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
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
                                            };*/

                                          /*  spinner_business_timing.setAdapter(adapter);

                                            spinner_business_timing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                                    //start=دوشنبه+مارس+11+2019+08:50:00+GMT+0000

                                                    String start_time =  time[position];
                                                    //  Toast.makeText(getActivity(),"start_time = "+start_time, Toast.LENGTH_LONG).show();

                                                    String finaldata = finalStr_date +" "+start_time+finalDay+","+month_name;

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

                                                    if (service_time.length>0){
                                                        Calendar calendar = Calendar.getInstance();
                                                        calendar.setTime(date);
                                                        calendar.add(Calendar.MINUTE, Integer.parseInt(service_time[service_position]));

                                                        String end_hour = String.valueOf(calendar.get(Calendar.HOUR)+5);
                                                        String end_min = String.valueOf(calendar.get(Calendar.MINUTE));
                                                        String end_sec = String.valueOf(calendar.get(Calendar.SECOND));
                                                        //Toast.makeText(getActivity(),"end_time = "+calendar.getTime(), Toast.LENGTH_LONG).show();

                                                        String end_time =finalStr_date+" "+end_hour+":"+end_min+":"+end_sec;

                                                        SavePreferences("end_time"        ,end_time);

                                                        //Toast.makeText(getActivity(),"end_time = "+end_time, Toast.LENGTH_LONG).show();
                                                        System.out.println("Time here "+calendar.getTime());

                                                    }

                                                }

                                                @Override
                                                public void onNothingSelected(AdapterView<?> parent) {

                                                }
                                            });*/


                                            }else {

                                                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.str_no_time_avaiable)
                                                                             ,Toast.LENGTH_SHORT).show();
                                                time_layout.removeAllViews();
                                            /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.empty_array_time, R.layout.my_spinner_style);
                                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                            spinner_business_timing.setAdapter(adapter);*/
                                            }

                                            //  spinner_business_timing.setSelection(0);

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
                    }
                })
                .setItemElevation( 4f )  // default is 0
                .setItemRadius( 2f )  // default is 0
                .hasAnimation(true) // Animation for item selection . default is false
                .load();

        //Toast.makeText(getActivity(),rootView.getHeight()+" Book Appt",Toast.LENGTH_SHORT).show();
        //viewPager
        return rootView;
    }
    public void setSpinner_business_service(){
        //http://192.168.100.14/monshiapp/app/service_listing.php
        String url    =  getResources().getString(R.string.url)+"service_listing.php";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1") ||
                sharedPreferences.getString("user_role" ,""  ).equals("4")){
            //spinner_business_list.setVisibility(View.GONE);
             params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+sharedPreferences.getString("selected_business", "");
        }else if (sharedPreferences.getString("user_role" ,""  ).equals("2") ||
                sharedPreferences.getString("user_role" ,""  ).equals("3")){
            //spinner_business_list.setVisibility(View.VISIBLE);
             params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+sharedPreferences.getString("business_id", "");
        }else {
            params = "business_id="+sharedPreferences.getString("selected_business", "");
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray service_listing = jsonObject.getJSONArray("service_listing");

                        services      = new String [service_listing.length()];
                        services_id   = new String [service_listing.length()];
                        service_time  = new String [service_listing.length()];
                        service_amount= new String [service_listing.length()];

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

                            if (i==0){
                                services    [i] = "انتخاب خدمات";
                                services_id [i] = "";
                                service_time[i] = "";
                                service_amount[i] = "";
                            }else {
                                services      [i]= servicename;
                                services_id   [i]= id;
                                service_time  [i]= time;
                                service_amount[i]= amount;
                            }

                            if (getActivity()!=null){

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, services) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                        View v =super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);

                                        ((TextView) v).setGravity(Gravity.CENTER);
                                        return v;
                                    }
                                };

                                spinner_business_service.setAdapter(adapter);
                                simpleProgressBar.setVisibility(View.GONE);
                                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }

                            /*spinner_business_service.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    //
                                }
                            });*/
                            spinner_business_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    setSpinner_business_staff(services_id[position]);
                                    service_position = position;
                                    //Toast.makeText(getActivity().getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                            //spinner_business_service.setSelection(service_position);
                        }
                        if(service_listing.length() < 1){
                            simpleProgressBar.setVisibility(View.GONE);
                            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        }
                    }
                    else{
                        simpleProgressBar.setVisibility(View.GONE);
                        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
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

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1") ||
                sharedPreferences.getString("user_role" ,""  ).equals("4")){
            //spinner_business_list.setVisibility(View.GONE);
             params =   "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                    "&service_id="+service_id;
        }else if (sharedPreferences.getString("user_role" ,""  ).equals("2") ||
                sharedPreferences.getString("user_role" ,""  ).equals("3")){
            //spinner_business_list.setVisibility(View.VISIBLE);
            params =   "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+sharedPreferences.getString("business_id", "")+
                    "&service_id="+service_id;
        }else {
            params = "business_id="+sharedPreferences.getString("selected_business", "")+
                     "&service_id="+service_id;
        }

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

                                if (i==0){
                                    staff [i]  = "انتخاب کارکنان";
                                    staff_id[i]= "";
                                }else {
                                    staff [i]  = full_name;
                                    staff_id[i]= id;
                                }



                            }

                            if(getActivity() != null){
                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                        R.layout.my_spinner_style, staff) {

                                    public View getView(int position, View convertView, ViewGroup parent) {
                                        View v = super.getView(position, convertView, parent);

                                        return v;
                                    }

                                    public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                        View v =super.getDropDownView(position, convertView, parent);
                                        //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                        ((TextView) v).setGravity(Gravity.CENTER);
                                        return v;
                                    }
                                };

                                spinner_business_staff.setAdapter(adapter);

                                spinner_business_staff .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        //setSpinner_business_staff(services_id[position]);
                                        staff_position = position;
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {


                                    }
                                });
                            }
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

    public void alertDialogShow_login(final Context context) {


        //final JSONArray[] ticket_list = new JSONArray[1];
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_popup_login,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final AlertDialog d = alertDialogBuilder.show();

        final EditText ed_username = (EditText) promptsView.findViewById(R.id.ed_username);
        final EditText ed_password = (EditText) promptsView.findViewById(R.id.ed_password);


        Button p_btn_login = (Button) promptsView.findViewById(R.id.p_btn_login);
        p_btn_login.setTransformationMethod(null);

        p_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String url    =  getResources().getString(R.string.url)+"login.php";
                String params =  "username="+ed_username.getText().toString()+"&password="+ed_password.getText().toString();


                //System.out.println("URL = "+url_login+"?"+params);
                WebRequestCall webRequestCall_login = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {


                                SavePreferences("fname"        ,jsonObject.getString("fname"      ));
                                SavePreferences("lname"        ,jsonObject.getString("lname"));
                                SavePreferences("full_name"    ,jsonObject.getString("full_name" ));
                                SavePreferences("email"        ,jsonObject.getString("email"     ));
                                SavePreferences("phone"        ,jsonObject.getString("phone"     ));
                                SavePreferences("address"      ,jsonObject.getString("address"       ));
                                SavePreferences("user_id"      ,jsonObject.getString("user_id"       ));
                                SavePreferences("user_status"  ,jsonObject.getString("user_status"   ));
                                SavePreferences("user_role"    ,jsonObject.getString("user_role"     ));

                                SavePreferences("isLogin","yes");

                               /* if (jsonObject.getString("user_status"   ).equals("a")){
                                    fragment_no = 2;
                                }*/

                                if (jsonObject.getString("user_role"   ).equals("1")){
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    getActivity().startActivity(intent);
                                    getActivity().finish();
                                }else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                                    //staff bookappointment activity here

                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    getActivity().finish();

                                } else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                                    //staff bookappointment activity here
                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(getActivity(), StaffScreensActivity.class);
                                    getActivity().startActivity(intent);
                                    getActivity().finish();

                                } else if (jsonObject.getString("user_role"   ).equals("4")){
                                    //Customer bookappointment activity here
                                    Intent intent = new Intent(getActivity(), CustomerBookingActivity.class);
                                    getActivity().startActivity(intent);
                                }

                                //Toast.makeText(context,jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                d.dismiss();
                            }else{
                                //login_form_layout.setVisibility(View.VISIBLE);
                                Toast.makeText(context,jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                ed_username.setError(getResources().getString(R.string.enter_valid_username));
                                ed_password.setError(getResources().getString(R.string.enter_valid_password));
                                ed_username.requestFocus();
                            }

                        } catch (final JSONException e) {
                            //login_form_layout.setVisibility(View.VISIBLE);
                            //Log.e(TAG, "Json parsing error: " + e.getMessage());
                        }
                    }
                });
                webRequestCall_login.execute(url,"POST",params);



                /*if(haveNetworkConnection()){}else{
                    //showSnack(false);
                }*/

            }
        });


    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
