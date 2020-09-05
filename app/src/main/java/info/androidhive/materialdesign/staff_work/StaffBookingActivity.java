package info.androidhive.materialdesign.staff_work;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

import info.androidhive.materialdesign.R;

public class StaffBookingActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_booking);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getApplicationContext());

        String title = getString(R.string.title_messages);
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
            ImageView img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.VISIBLE);

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getFragmentManager()).popBackStack();
                    }
                }
            });
        }else {

            Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
            TextView tv_app_name;

            mToolbar = (Toolbar) findViewById(R.id.toolbar);

            tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
            tv_app_name.setVisibility(View.VISIBLE);

            img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
            img_profile_tool_bar.setVisibility(View.INVISIBLE);

            img_notifications = mToolbar.findViewById(R.id.img_notifications);
            img_notifications.setVisibility(View.INVISIBLE);

            img_filter = mToolbar.findViewById(R.id.img_filter);
            img_filter.setVisibility(View.GONE);

            img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.VISIBLE);
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("تقویم"));//BusinessBookAppointmentFragment
        tabLayout.addTab(tabLayout.newTab().setText("فعالیت"));//BusinessServiceListFragment

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterStaffBooking adapter = new PagerAdapterStaffBooking
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    /*    int count = tabLayout.getTabCount();
        viewPager.setCurrentItem(3);*/

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

    }
}
