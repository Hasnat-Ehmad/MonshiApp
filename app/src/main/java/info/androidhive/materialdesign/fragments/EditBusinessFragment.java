package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BusinessDetail_Activity;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class_scroll;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_scroll;
import info.androidhive.materialdesign.pagerfragmentcontiners.BusinessDetailFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessClassListFragment;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;


public class EditBusinessFragment extends Fragment {

    TextView tv_dashboard,tv_manage_staff,tv_manage_service,tv_book,tv_customer,tv_appointments,tv_billing,tv_class;
    private FragmentActivity fragmentActivity;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_edit_business, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        tv_title.setVisibility(View.GONE);

        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        TextView tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);
        ImageView img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        ImageView img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().popBackStack();
            }
        });

        /* if (toolbar != null){
             getActivity().setSupportActionBar(mToolbar);
             if (getSupportActionBar() != null) {
                 getActivity().getSupportActionBar().setTitle(null);
                 getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                 getSupportActionBar().setDisplayShowHomeEnabled(true);
             }
         }*/

        fragmentActivity = (FragmentActivity) getActivity();

        tv_dashboard = rootView.findViewById(R.id.tv_dashboard);
        tv_manage_staff = rootView.findViewById(R.id.tv_manage_staff);
        tv_manage_service = rootView.findViewById(R.id.tv_manage_service);
        tv_book = rootView.findViewById(R.id.tv_book);
        tv_customer = rootView.findViewById(R.id.tv_customer);
        tv_appointments = rootView.findViewById(R.id.tv_appointments);
        tv_billing = rootView.findViewById(R.id.tv_billing);
        tv_class = rootView.findViewById(R.id.tv_class);

        tv_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DashboardFragment tab1= new DashboardFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();

            }
        });

        tv_manage_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ManagementStaffFragment tab1= new ManagementStaffFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();

            }
        });

        if (sharedPreferences.getString("selected_business_type" ,""  ).equals("service")){
            tv_manage_service.setVisibility(View.VISIBLE);
            tv_class.setVisibility(View.GONE);

            tv_manage_service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ManagementServiceFragment tab1= new ManagementServiceFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

        }else {
            tv_manage_service.setVisibility(View.GONE);
            tv_class.setVisibility(View.VISIBLE);

            tv_class.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    AddNewClassFragment tab1= new AddNewClassFragment();
//                    BusinessClassListFragment tab1= new BusinessClassListFragment();
                    ClassListFragment tab1= new ClassListFragment();
                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            });

        }

        tv_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* BusinessDetailFragment tab1= new BusinessDetailFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();*/
               if (sharedPreferences.getString("selected_business_type", "").equals("class")){

                   Intent intent = new Intent(getActivity(), BusinessPublicDetail_Activity_Class_scroll.class);
                   startActivity(intent);
               }else {
                   Intent intent = new Intent(getActivity(), BusinessPublicDetail_Activity_scroll.class);
                   startActivity(intent);
               }




            }
        });

        tv_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerListFragment tab1= new CustomerListFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();
            }
        });

        tv_appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppointmentListFragment tab1= new AppointmentListFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();
            }
        });

        tv_billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BillHistoryFragment tab1= new BillHistoryFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();
            }
        });

        return rootView;
    }


}
