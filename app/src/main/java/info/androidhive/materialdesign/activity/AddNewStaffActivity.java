package info.androidhive.materialdesign.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterNewStaff;
import info.androidhive.materialdesign.R;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_menu;

public class AddNewStaffActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_staff);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //img_menu.setVisibility(View.GONE);
        img_back = mToolbar.findViewById(R.id.img_back);
        /*img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("جزئیات"));//DetailFragment
        tabLayout.addTab(tabLayout.newTab().setText("استراحت"));//SelectCustomerFragment

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_1);
        viewPager.setZ(1);
        final PagerAdapterNewStaff adapter = new PagerAdapterNewStaff
                (getSupportFragmentManager(), tabLayout.getTabCount());
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
    }

    @Override
    public void onStop() {
        super.onStop();
        //Toast.makeText(getActivity(),"onStop ",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Toast.makeText(getActivity(),"onDestroy ",Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onPause() {
        super.onPause();
        //   Toast.makeText(getActivity(),"onPause ",Toast.LENGTH_SHORT).show();
        SavePreferences("show_staff_detail" , "" );

    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
