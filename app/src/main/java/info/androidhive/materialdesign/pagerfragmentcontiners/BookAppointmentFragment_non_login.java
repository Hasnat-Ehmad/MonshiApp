package info.androidhive.materialdesign.pagerfragmentcontiners;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterBookAppointment;
import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterBookAppointment_non_login;
import info.androidhive.materialdesign.R;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;


public class BookAppointmentFragment_non_login extends Fragment {

    String[] services; int services_position;
    String[] staff   ; int staff_position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        spinner_business_list.setVisibility(View.GONE); ;
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        //View rootView =  inflater.inflate(R.layout.fragment_business_detail, container, false);

        if (getArguments() != null) {
            services = getArguments().getStringArray("services");
            services_position = getArguments().getInt("service_position");

            staff = getArguments().getStringArray("staff");
            staff_position = getArguments().getInt("staff_position");
        }


        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("جزئیات"));//DetailFragment
        tabLayout.addTab(tabLayout.newTab().setText("مشتری"));//SelectCustomerFragment

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        final PagerAdapterBookAppointment_non_login adapter = new PagerAdapterBookAppointment_non_login
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return rootView;
    }


}
