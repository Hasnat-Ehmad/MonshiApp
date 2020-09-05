package info.androidhive.materialdesign.pagerfragmentcontiners;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterNewStaff;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterBookAppointment;
import info.androidhive.materialdesign.activity.MainActivity_bottom_view;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;


public class AddNewStaffFragment extends Fragment {

    @Override
    public void onPause() {
        super.onPause();
     //   Toast.makeText(getActivity(),"onPause ",Toast.LENGTH_SHORT).show();
        SavePreferences("staff_id" , "" );

    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    SharedPreferences sharedPreferences;
    TabLayout tabLayout ;  ViewPager viewPager;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        //View rootView =  inflater.inflate(R.layout.fragment_business_detail, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        Toolbar mToolbar;
        ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);
        tv_app_name.setText(getActivity().getResources().getString(R.string.app_name));

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.GONE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);

        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);

        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"continer",Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });

        //img_notifications.setVisibility(View.VISIBLE);

        tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("جزئیات"));//DetailFragment
        tabLayout.addTab(tabLayout.newTab().setText("استراحت"));//SelectCustomerFragment

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


         viewPager = (ViewPager) rootView.findViewById(R.id.pager);
                        //viewPager.setZ(1);
        final PagerAdapterNewStaff adapter = new PagerAdapterNewStaff
                (getChildFragmentManager(), tabLayout.getTabCount());
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
