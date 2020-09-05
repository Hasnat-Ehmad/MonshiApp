package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.LoginActivity;
import info.androidhive.materialdesign.activity.MainActivity_bottom_view;
import info.androidhive.materialdesign.activity.NonLogin_Activity;
import info.androidhive.materialdesign.activity.SignUpActivity;
import info.androidhive.materialdesign.fragments.customer_work.Customer_favorate_Fragment;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_filter;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;


public class MoreFragment extends Fragment {

    private FragmentActivity fragmentActivity;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_more, container, false);

        tv_title.setVisibility(View.GONE);

        img_back.setVisibility(View.GONE);

        Toolbar  mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        TextView tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);

        img_notifications.setVisibility(View.VISIBLE);
        //tv_title.setText(getString(R.string.str_more));
        /*tv_title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.monshiapp_logo, 0, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            tv_title.setGravity(View.TEXT_ALIGNMENT_TEXT_START);
        }*/
        img_filter.setVisibility(View.GONE);

        View view_0 = rootView.findViewById(R.id.view_0);
        View view_1 = rootView.findViewById(R.id.view_1);

        fragmentActivity = (FragmentActivity) getActivity();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());


        TextView tv_chat   = rootView.findViewById(R.id.tv_chat);
        TextView tv_logout = rootView.findViewById(R.id.tv_logout);
        TextView tv_signin = rootView.findViewById(R.id.tv_signin);
        TextView tv_signup = rootView.findViewById(R.id.tv_signup);
        TextView tv_tutorial = rootView.findViewById(R.id.tv_tutorial);
        TextView tv_contact_us = rootView.findViewById(R.id.tv_contact_us);
        TextView tv_fav_list      = rootView.findViewById(R.id.tv_fav_list);
        TextView tv_business_list = rootView.findViewById(R.id.tv_business_list);
        TextView tv_recep_customer = rootView.findViewById(R.id.tv_recep_customer);
        TextView tv_privacy_policy = rootView.findViewById(R.id.tv_privacy_policy);
        TextView tv_customer_appointment = rootView.findViewById(R.id.tv_customer_appointment);
        tv_recep_customer.setVisibility(View.GONE);
        tv_customer_appointment.setVisibility(View.GONE);
        tv_chat.setVisibility(View.GONE);

        if (sharedPreferences.getString("user_role" ,""  ).equals("1") ||
                    sharedPreferences.getString("user_role" ,""  ).equals("")){

            if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                tv_chat.setVisibility(View.VISIBLE);
                tv_chat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://qa.monshiapp.com/chat.php"));
                        startActivity(browserIntent);
                    }
                });
            }

            tv_business_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BusinessListFragment tab1= new BusinessListFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });


            tv_fav_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FavListFragment tab1= new FavListFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_signin.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                }
            });

            tv_signup.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), SignUpActivity.class);
                    Objects.requireNonNull(getActivity()).startActivity(intent);
                }
            });

            tv_contact_us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact_us_Fragment tab1= new Contact_us_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Privacy_Policy_Fragment tab1= new Privacy_Policy_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });


            tv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    SavePreferences("fname"        ,"");
                    SavePreferences("lname"        ,"");
                    SavePreferences("full_name"    ,"");
                    SavePreferences("email"        ,"");
                    SavePreferences("phone"        ,"");
                    SavePreferences("address"      ,"");
                    SavePreferences("user_id"      ,"");
                    SavePreferences("user_status"  ,"");
                    SavePreferences("user_role"    ,"");
                    SavePreferences("staff_user_id","");
                    SavePreferences("class_id"     ,"");
                    SavePreferences("session_id"   ,"");
                    SavePreferences("last_notification_id","0");

                    SavePreferences("isLogin"      ,"no");

                    // Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    //getActivity().stopService(service_notification);
                    //Intent intent = new Intent(getActivity(), NonLogin_Activity.class);
                    Intent intent = new Intent(getActivity(), MainActivity_bottom_view.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).finish();
                    }

                }
            });

            tv_tutorial.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    //Tutorial page under progress
                }
            });

            if(!sharedPreferences.contains("isLogin") || sharedPreferences.getString("isLogin","").equals("no")){
                tv_signup.setVisibility(View.VISIBLE);
                tv_signin.setVisibility(View.VISIBLE);

                tv_logout.setVisibility(View.GONE);
                tv_fav_list.setVisibility(View.GONE);
                view_0.setVisibility(View.GONE);
                tv_business_list.setVisibility(View.GONE);
            }else{
                tv_logout.setVisibility(View.VISIBLE);
                tv_fav_list.setVisibility(View.VISIBLE);
                tv_business_list.setVisibility(View.VISIBLE);

                tv_signup.setVisibility(View.GONE);
                tv_signin.setVisibility(View.GONE);
                view_0.setVisibility(View.GONE);

            }

        }

        if (sharedPreferences.getString("user_role" ,""  ).equals("2")){

            tv_chat.setVisibility(View.VISIBLE);
            tv_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://qa.monshiapp.com/chat.php"));
                    startActivity(browserIntent);
                }
            });

            tv_business_list.setVisibility(View.GONE);
            tv_business_list.setText(""+getString(R.string.str_create_business));
            tv_business_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreateBusinessAccountFragment tab1= new CreateBusinessAccountFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });


            tv_fav_list.setVisibility(View.GONE);
            tv_fav_list.setText(""+getString(R.string.str_favorites));
            tv_fav_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Customer_favorate_Fragment tab1= new Customer_favorate_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_customer_appointment.setVisibility(View.GONE);
            tv_customer_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppointmentListFragment tab1= new AppointmentListFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_recep_customer.setVisibility(View.VISIBLE);
            tv_recep_customer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomerListFragment tab1= new CustomerListFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_contact_us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact_us_Fragment tab1= new Contact_us_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Privacy_Policy_Fragment tab1= new Privacy_Policy_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    SavePreferences("fname"        ,"");
                    SavePreferences("lname"        ,"");
                    SavePreferences("full_name"    ,"");
                    SavePreferences("email"        ,"");
                    SavePreferences("phone"        ,"");
                    SavePreferences("address"      ,"");
                    SavePreferences("user_id"      ,"");
                    SavePreferences("user_status"  ,"");
                    SavePreferences("user_role"    ,"");
                    SavePreferences("staff_user_id","");
                    SavePreferences("class_id"     ,"");
                    SavePreferences("session_id"   ,"");
                    SavePreferences("last_notification_id","0");

                    SavePreferences("isLogin"      ,"no");

                    // Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    //getActivity().stopService(service_notification);
                    //Intent intent = new Intent(getActivity(), NonLogin_Activity.class);
                    Intent intent = new Intent(getActivity(), MainActivity_bottom_view.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).finish();
                    }

                }
            });

            tv_tutorial.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    //Tutorial page under progress
                }
            });


            if(!sharedPreferences.contains("isLogin") || sharedPreferences.getString("isLogin","").equals("no")){
                tv_signup.setVisibility(View.VISIBLE);
                tv_signin.setVisibility(View.VISIBLE);

                tv_logout.setVisibility(View.GONE);
                tv_fav_list.setVisibility(View.GONE);
                tv_business_list.setVisibility(View.GONE);
                tv_customer_appointment.setVisibility(View.GONE);
                tv_recep_customer.setVisibility(View.GONE);
                view_0.setVisibility(View.GONE);
                view_1.setVisibility(View.GONE);
            }else{
                tv_logout.setVisibility(View.VISIBLE);
                tv_recep_customer.setVisibility(View.VISIBLE);

                tv_fav_list.setVisibility(View.GONE);
                tv_customer_appointment.setVisibility(View.GONE);
                tv_business_list.setVisibility(View.GONE);
                tv_signup.setVisibility(View.GONE);
                tv_signin.setVisibility(View.GONE);
                view_0.setVisibility(View.VISIBLE);
                view_1.setVisibility(View.GONE);
            }
        }

        if (sharedPreferences.getString("user_role" ,""  ).equals("3")){

            tv_chat.setVisibility(View.VISIBLE);
            tv_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://qa.monshiapp.com/chat.php"));
                    startActivity(browserIntent);
                }
            });

            tv_business_list.setVisibility(View.GONE);
            tv_business_list.setText(""+getString(R.string.str_create_business));
            tv_business_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreateBusinessAccountFragment tab1= new CreateBusinessAccountFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });


            tv_fav_list.setVisibility(View.GONE);
            tv_fav_list.setText(""+getString(R.string.str_favorites));
            tv_fav_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Customer_favorate_Fragment tab1= new Customer_favorate_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_customer_appointment.setVisibility(View.GONE);
            tv_customer_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppointmentListFragment tab1= new AppointmentListFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_contact_us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact_us_Fragment tab1= new Contact_us_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Privacy_Policy_Fragment tab1= new Privacy_Policy_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    SavePreferences("fname"        ,"");
                    SavePreferences("lname"        ,"");
                    SavePreferences("full_name"    ,"");
                    SavePreferences("email"        ,"");
                    SavePreferences("phone"        ,"");
                    SavePreferences("address"      ,"");
                    SavePreferences("user_id"      ,"");
                    SavePreferences("user_status"  ,"");
                    SavePreferences("user_role"    ,"");
                    SavePreferences("staff_user_id","");
                    SavePreferences("class_id"     ,"");
                    SavePreferences("session_id"   ,"");
                    SavePreferences("last_notification_id","0");

                    SavePreferences("isLogin"      ,"no");

                    // Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    //getActivity().stopService(service_notification);
                    //Intent intent = new Intent(getActivity(), NonLogin_Activity.class);
                    Intent intent = new Intent(getActivity(), MainActivity_bottom_view.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).finish();
                    }

                }
            });

            tv_tutorial.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    //Tutorial page under progress
                }
            });


            if(!sharedPreferences.contains("isLogin") || sharedPreferences.getString("isLogin","").equals("no")){
                tv_signup.setVisibility(View.VISIBLE);
                tv_signin.setVisibility(View.VISIBLE);

                tv_logout.setVisibility(View.GONE);
                tv_fav_list.setVisibility(View.GONE);
                tv_business_list.setVisibility(View.GONE);
                tv_customer_appointment.setVisibility(View.GONE);
                view_0.setVisibility(View.GONE);
                view_1.setVisibility(View.GONE);
            }else{
                tv_logout.setVisibility(View.VISIBLE);
                tv_fav_list.setVisibility(View.GONE);
                tv_customer_appointment.setVisibility(View.GONE);
                tv_business_list.setVisibility(View.GONE);

                tv_signup.setVisibility(View.GONE);
                tv_signin.setVisibility(View.GONE);
                view_0.setVisibility(View.VISIBLE);
                view_1.setVisibility(View.GONE);
            }
        }

        if (sharedPreferences.getString("user_role" ,""  ).equals("4")){

            tv_chat.setVisibility(View.VISIBLE);
            tv_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://qa.monshiapp.com/chat.php"));
                    startActivity(browserIntent);
                }
            });

            tv_business_list.setVisibility(View.VISIBLE);
            tv_business_list.setText(""+getString(R.string.str_create_business));
            tv_business_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CreateBusinessAccountFragment tab1= new CreateBusinessAccountFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });


            tv_fav_list.setVisibility(View.VISIBLE);
            tv_fav_list.setText(""+getString(R.string.str_favorites));
            tv_fav_list.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Customer_favorate_Fragment tab1= new Customer_favorate_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_customer_appointment.setVisibility(View.VISIBLE);
            tv_customer_appointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppointmentListFragment tab1= new AppointmentListFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_contact_us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Contact_us_Fragment tab1= new Contact_us_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Privacy_Policy_Fragment tab1= new Privacy_Policy_Fragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

            tv_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    SavePreferences("fname"        ,"");
                    SavePreferences("lname"        ,"");
                    SavePreferences("full_name"    ,"");
                    SavePreferences("email"        ,"");
                    SavePreferences("phone"        ,"");
                    SavePreferences("address"      ,"");
                    SavePreferences("user_id"      ,"");
                    SavePreferences("user_status"  ,"");
                    SavePreferences("user_role"    ,"");
                    SavePreferences("staff_user_id","");
                    SavePreferences("class_id"     ,"");
                    SavePreferences("session_id"   ,"");
                    SavePreferences("last_notification_id","0");

                    SavePreferences("isLogin"      ,"no");

                    // Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                    //getActivity().stopService(service_notification);
                    //Intent intent = new Intent(getActivity(), NonLogin_Activity.class);
                    Intent intent = new Intent(getActivity(), MainActivity_bottom_view.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getActivity()).finish();
                    }

                }
            });

            tv_tutorial.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    //Tutorial page under progress
                }
            });


            if(!sharedPreferences.contains("isLogin") || sharedPreferences.getString("isLogin","").equals("no")){
                tv_signup.setVisibility(View.VISIBLE);
                tv_signin.setVisibility(View.VISIBLE);

                tv_logout.setVisibility(View.GONE);
                tv_fav_list.setVisibility(View.GONE);
                tv_business_list.setVisibility(View.GONE);
                tv_customer_appointment.setVisibility(View.GONE);
                view_0.setVisibility(View.GONE);
                view_1.setVisibility(View.GONE);
            }else{
                tv_logout.setVisibility(View.VISIBLE);
                tv_fav_list.setVisibility(View.VISIBLE);
                tv_customer_appointment.setVisibility(View.VISIBLE);
                tv_business_list.setVisibility(View.VISIBLE);

                tv_signup.setVisibility(View.GONE);
                tv_signin.setVisibility(View.GONE);
                view_0.setVisibility(View.VISIBLE);
                view_1.setVisibility(View.GONE);
            }
        }

        return rootView;
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
