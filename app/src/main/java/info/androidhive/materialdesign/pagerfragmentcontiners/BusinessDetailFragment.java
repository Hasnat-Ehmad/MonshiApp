package info.androidhive.materialdesign.pagerfragmentcontiners;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterBusinessList;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;


public class BusinessDetailFragment extends Fragment {
    SharedPreferences sharedPreferences;

    TextView tv_number,tv_category,tv_address,tv_description,tv_label_dashboard;

    ImageView img_profile_pic;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_business_detail, container, false);

        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        tv_label_dashboard=rootView.findViewById(R.id.tv_label_dashboard);
        tv_label_dashboard.setText(""+sharedPreferences.getString("selected_name", ""));

        tv_number=rootView.findViewById(R.id.tv_number);
        tv_number.setText(""+sharedPreferences.getString("selected_contact", ""));

        tv_category=rootView.findViewById(R.id.tv_category);
        tv_category.setText(""+sharedPreferences.getString("selected_category_name_per", ""));

        tv_address=rootView.findViewById(R.id.tv_address);
        tv_address.setText(""+sharedPreferences.getString("selected_address", ""));

        tv_description=rootView.findViewById(R.id.tv_description);
        tv_description.setText(""+sharedPreferences.getString("selected_description", ""));

        img_profile_pic = rootView.findViewById(R.id.img_profile_pic);
        Picasso.with(img_profile_pic.getContext()).load(""+sharedPreferences.getString("selected_image", "")).into(img_profile_pic);



        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText("وقت ملاقات"));//BusinessBookAppointmentFragment
        tabLayout.addTab(tabLayout.newTab().setText("لیست خدمات"));//BusinessServiceListFragment
        tabLayout.addTab(tabLayout.newTab().setText("فهرست کارکنان"));//BusinessStaffListFragment
        tabLayout.addTab(tabLayout.newTab().setText("اطلاعات کسب و کار\n"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        final PagerAdapterBusinessList adapter = new PagerAdapterBusinessList
                (getActivity().getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        int count = tabLayout.getTabCount();
        viewPager.setCurrentItem(3);

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


/* BottomNavigationView bottomNavigationView = (BottomNavigationView) rootView.findViewById(R.id.navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;

                        menuItem = item;
                        switch (item.getItemId()) {
                            case R.id.action_overview:
                                selectedFragment = new BusinessInfoFragment();

                                break;
                            case R.id.action_trainer:
                                selectedFragment =new BusinessServiceListFragment();

                                break;
                            case R.id.action_control:
                                selectedFragment = new BusinessStaffListFragment();

                                break;
                            case R.id.action_complete:

                                selectedFragment = new BusinessBookAppointmentFragment();

                                break;
                        }

                        if(selectedFragment != null){
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_layout, selectedFragment);
                            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                            fragmentTransaction.commit();
                        }


                        return true;
                    }
                });

//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, FragmentOverview.newInstance());
//        transaction.commit();*/