package info.androidhive.materialdesign.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.MacAddress;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.fragments.AddNewCustomerFragment;
import info.androidhive.materialdesign.fragments.AddNewSession;
import info.androidhive.materialdesign.fragments.AppointmentListFragment;
import info.androidhive.materialdesign.fragments.BookNowFragment;
import info.androidhive.materialdesign.fragments.BusinessListFragment;
import info.androidhive.materialdesign.fragments.CalendarFragment;
import info.androidhive.materialdesign.fragments.CustomerListFragment;
import info.androidhive.materialdesign.fragments.DashboardFragment;
import info.androidhive.materialdesign.fragments.ManagementServiceFragment;
import info.androidhive.materialdesign.fragments.ManagementStaffFragment;
import info.androidhive.materialdesign.fragments.MoreFragment;
import info.androidhive.materialdesign.fragments.Notification_Fragment;
import info.androidhive.materialdesign.fragments.customer_work.Customer_Account_Fragment;
import info.androidhive.materialdesign.fragments.customer_work.Customer_ActivitiesFragment;
import info.androidhive.materialdesign.fragments.customer_work.Customer_favorate_Fragment;
import info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment;
import info.androidhive.materialdesign.service.Notification_Service;
import info.androidhive.materialdesign.staff_work.Account_Fragment;
import info.androidhive.materialdesign.staff_work.Activity_Fragment;
import info.androidhive.materialdesign.staff_work.StaffBookingActivity;
import info.androidhive.materialdesign.staff_work.StaffScreensActivity;
import info.androidhive.materialdesign.staff_work.Staff_Calendar_Fragment;




public class MainActivity_bottom_view extends AppCompatActivity  {

    //private TextView mTextMessage;
    Fragment fragment = null;
    String title="";

    public static Boolean filter_check = false;

    public static Intent service_notification;
    public static Intent appointment_pop_up_service;

    public static Activity activity;

    String service_check="";

    SharedPreferences sharedPreferences;
    public Toolbar mToolbar;
    public static TextView tv_title;
    public static ImageView img_back;
    public static ImageView img_filter;
    public static ImageView img_notifications;
    public static CircleImageView img_menu;
    public static CircleImageView img_app_logo;

     BottomNavigationView navigation;

     FragmentActivity fragmentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bottom_view);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        activity = MainActivity_bottom_view.this;

        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tv_title = mToolbar.findViewById(R.id.tv_app_name);
        img_back = mToolbar.findViewById(R.id.img_back);
        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_app_logo = mToolbar.findViewById(R.id.img_app_logo);

        if (sharedPreferences.getString("user_role" ,""  ).equals("1") ||
                    sharedPreferences.getString("user_role" ,""  ).equals("")){

            img_app_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    navigation.getMenu().findItem(R.id.navigation_book).setChecked(true);
                    filter_check = false;
                    BookNowFragment bookNowFragment = new BookNowFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, bookNowFragment);
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentTransaction.commit();

                }
            });

        }
        if (sharedPreferences.getString("user_role" ,""  ).equals("4")){

            img_app_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    navigation.getMenu().findItem(R.id.navigation_business_list_screen).setChecked(true);
                    filter_check = false;
                    BookNowFragment bookNowFragment = new BookNowFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frame_layout, bookNowFragment);
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragmentTransaction.commit();

                }
            });

        }

        fragmentActivity = (FragmentActivity) MainActivity_bottom_view.this;

        img_menu = mToolbar.findViewById(R.id.img_profile);
        img_back.setVisibility(View.GONE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MainActivity_bottom_view.this.getFragmentManager().popBackStack();
            }
        });
        img_filter.setVisibility(View.GONE);
        tv_title.setVisibility(View.GONE);
        img_notifications.setVisibility(View.VISIBLE);

        img_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity_bottom_view.this,"notification",Toast.LENGTH_SHORT).show();

                Notification_Fragment notification_fragment = new Notification_Fragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, notification_fragment)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();
            }
        });


        service_notification = new Intent(MainActivity_bottom_view.this, Notification_Service .class);
        stopService(service_notification);
        startService(service_notification);

//        appointment_pop_up_service = new Intent(MainActivity_bottom_view.this, Appointment_pop_up_Service.class);
//        stopService(appointment_pop_up_service);
//        startService(appointment_pop_up_service);

//        service_check = sharedPreferences.getString("service_check" ,""  );
//        if (service_check.equals("")) {
//
//            SavePreferences("service_check","yes");
//
//        }

//        tv_title.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity_bottom_view.this,MainActivity_bottom_view.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
//        });
//
//
//        img_menu = mToolbar.findViewById(R.id.img_profile);
//        img_menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity_bottom_view.this,MenuActivity.class);
//                startActivity(intent);
//                //Intent intent = new Intent(NonLogin_Activity.this,LoginActivity.class);
//                //startActivity(intent);
//            }
//        });


        if (sharedPreferences.getString("user_role" ,""  ).equals("1") || sharedPreferences.getString("user_role" ,""  ).equals("")){
            navigation.inflateMenu(R.menu.navigation_admin);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, new  BookNowFragment());
            transaction.commit();

            navigation.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            //Fragment fragment = null;

                            //menuItem = item;
                            switch (item.getItemId()) {
                                case R.id.navigation_book:
                                    fragment = new BookNowFragment();
                                    title = getString(R.string.str_book);
                                    filter_check = false;
                                    //mTextMessage.setText(R.string.title_notifications);
                                    break;

                              /* case R.id.navigation_dashboard:
                                fragment = new DashboardFragment();
                                title = getString(R.string.str_dashboard);
                                //mTextMessage.setText(R.string.title_dashboard);
                                break;*/

                                case R.id.navigation_business:
                                    //Toast.makeText(getApplicationContext(),"hh",Toast.LENGTH_SHORT).show();

//                                    fragment = new BusinessListFragment();
//                                    title = getString(R.string.str_businesses);



                                    if (filter_check){
                                        filter_check = false;
                                    }else {
                                        filter_check = true;
                                    }

                                    fragment = new BookNowFragment();
                                    title = getString(R.string.str_book);

                                    //dummy_fun(getApplicationContext());

                                    //mTextMessage.setText(R.string.title_home);
                                    break;
                         /*   case R.id.navigation_customer:
                                fragment = new CustomerListFragment();
                                title = getString(R.string.str_customer);
                                //mTextMessage.setText(R.string.title_notifications);
                                break;*/

                                case R.id.navigation_more:
                                    filter_check = false;
                                    fragment = new MoreFragment();
                                    title = getString(R.string.str_more);
                                    break;
                                default:
                                    break;

                            }

                            if(fragment != null){
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout, fragment);
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragmentTransaction.commit();

                                //tv_title.setText(""+title);
                            }
                            return true;
                        }
                    });

        }
        else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
            navigation.inflateMenu(R.menu.navigation_recp);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, new ManagementServiceFragment());
            transaction.commit();

            navigation.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            //Fragment fragment = null;

                            //menuItem = item;
                            switch (item.getItemId()) {
                                case R.id.navigation_manage_service:
                                    //Toast.makeText(getApplicationContext(),"hh",Toast.LENGTH_SHORT).show();
                                    fragment = new ManagementServiceFragment();
                                    title = getString(R.string.str_service_list);

                                    //mTextMessage.setText(R.string.title_home);
                                    break;
                            case R.id.navigation_manage_staff:
                                fragment = new ManagementStaffFragment();
                                title = getString(R.string.str_staff_list);
                                //mTextMessage.setText(R.string.title_dashboard);
                                break;
                                case R.id.navigation_Booking:
                                    fragment = new BusinessBookAppointmentFragment();
                                    title = getString(R.string.str_book);
                                    //mTextMessage.setText(R.string.title_notifications);
                                    break;
                           /* case R.id.navigation_manage_customer:
                                fragment = new CustomerListFragment();
                                title = getString(R.string.str_customer);
                                //mTextMessage.setText(R.string.title_notifications);
                                break;*/

                                case R.id.navigation_more:
                                    fragment = new MoreFragment();
                                    title = getString(R.string.str_more);
                                    break;
                                default:
                                    break;

                            }

                            if(fragment != null){
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout, fragment);
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragmentTransaction.commit();

                                //tv_title.setText(""+title);
                            }
                            return true;
                        }
                    });

        }
        else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
            navigation.inflateMenu(R.menu.navigation_staff);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, new Account_Fragment());
            transaction.commit();

            navigation.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            //Fragment fragment = null;

                            //menuItem = item;
                            switch (item.getItemId()) {
                                case R.id.navigation_staff_profile:
                                    //Toast.makeText(getApplicationContext(),"hh",Toast.LENGTH_SHORT).show();
                                    fragment = new Account_Fragment();
                                    title = getString(R.string.str_service_list);

                                    //mTextMessage.setText(R.string.title_home);
                                    break;
                                case R.id.navigation_staff_activity:
                                    fragment = new Activity_Fragment();
                                    title = getString(R.string.str_staff_list);
                                    //mTextMessage.setText(R.string.title_dashboard);
                                    break;
                                case R.id.navigation_staff_booking:
                                    fragment = new BusinessBookAppointmentFragment();
                                    title = getString(R.string.str_book);
                                    break;
                                case R.id.navigation_more:
                                    fragment = new MoreFragment();
                                    title = getString(R.string.str_more);
                                    break;

                                default:
                                    break;

                            }

                            if(fragment != null){
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout, fragment);
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragmentTransaction.commit();

                                //tv_title.setText(""+title);
                            }
                            return true;
                        }
                    });


        }else  if (sharedPreferences.getString("user_role" ,""  ).equals("4")){
            navigation.inflateMenu(R.menu.navigation_customer);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, new Customer_Account_Fragment());
            transaction.commit();

            navigation.setOnNavigationItemSelectedListener
                    (new BottomNavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            //Fragment fragment = null;

                            //menuItem = item;
                            switch (item.getItemId()) {
                                case R.id.navigation_customer_profile:
                                    //Toast.makeText(getApplicationContext(),"hh",Toast.LENGTH_SHORT).show();
                                    filter_check = false;
                                    fragment = new Customer_Account_Fragment();
                                    //mTextMessage.setText(R.string.title_home);
                                    break;
                                case R.id.navigation_business_list_screen:

                                    filter_check = false;
                                    fragment = new BookNowFragment();
                                    break;

                                    case R.id.navigation_customer_favorites:
                                    // fragment = new Customer_favorate_Fragment();
                                    if (filter_check){
                                        filter_check = false;
                                    }else {
                                        filter_check = true;
                                    }

                                    fragment = new BookNowFragment();
                                    break;

                                case R.id.navigation_customer_activity:
                                    filter_check = false;
                                    fragment = new Customer_ActivitiesFragment();

                                    break;

                                case R.id.navigation_customer_appointments:
                                    filter_check = false;
                                    //fragment = new AppointmentListFragment();
                                    fragment = new MoreFragment();

                                    break;

                                default:
                                    break;

                            }

                            if(fragment != null){
                                FragmentManager fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frame_layout, fragment);
                                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                                fragmentTransaction.commit();

                                tv_title.setText(""+title);
                            }
                            return true;
                        }
                    });

        }

        //BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        //BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);


        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SavePreferences("selected_business"      ,"");
    }

    public static String persianUrlEncoder(String string){
        String encodedbusiness_id = null;
        try {
            encodedbusiness_id = URLEncoder.encode(string, "UTF-8");
        } catch (
                UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //String params =  "busId="+encodedbusiness_id;

        return string;
    }

    /* @Override
    public void onFragmentInteraction(Uri uri) {

    }*/
}


 /*private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                if (sharedPreferences.getString("user_role" ,""  ).equals("1")) {


                    switch (item.getItemId()) {
                        case R.id.navigation_business:
                            Toast.makeText(getApplicationContext(),"hh",Toast.LENGTH_SHORT).show();
                            fragment = new BusinessListFragment();
                            title = getString(R.string.str_businesses);

                            //mTextMessage.setText(R.string.title_home);
                            return true;
                        case R.id.navigation_dashboard:
                            fragment = new DashboardFragment();
                            title = getString(R.string.str_dashboard);
                            //mTextMessage.setText(R.string.title_dashboard);
                            return true;
                        case R.id.navigation_book:
                            fragment = new BookNowFragment();
                            title = getString(R.string.str_book);
                            //mTextMessage.setText(R.string.title_notifications);
                            return true;
                        case R.id.navigation_customer:
                            fragment = new CustomerListFragment();
                            title = getString(R.string.str_customer);
                            //mTextMessage.setText(R.string.title_notifications);
                            return true;

                        case R.id.navigation_more:
                            //mTextMessage.setText(R.string.title_notifications);
                            return true;
                        default:
                            break;

                    }

                    if (fragment != null) {
                        Toast.makeText(getApplicationContext(),"hh",Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frame_layout, fragment);
                        fragmentTransaction.commit();

                        // set the toolbar title
                        //getSupportActionBar().setTitle(title);

////                tv_app_name.setText(""+title);
//                        if (getSupportActionBar() != null) {
//                            getSupportActionBar().setTitle(title);
////                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////                    getSupportActionBar().setDisplayShowHomeEnabled(true);
//                        }
                    }
                }
                else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                    //navigation.inflateMenu(R.menu.navigation_recp);
                }
                else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                    //navigation.inflateMenu(R.menu.navigation_staff);

                }else  if (sharedPreferences.getString("user_role" ,""  ).equals("4")){
                    //navigation.inflateMenu(R.menu.navigation_customer);

                }

            return false;
        }
    };*/