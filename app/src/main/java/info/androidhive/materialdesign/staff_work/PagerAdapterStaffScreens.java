package info.androidhive.materialdesign.staff_work;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.androidhive.materialdesign.pagerfragments.DetailFragment;
import info.androidhive.materialdesign.pagerfragments.SelectCustomerFragment;

public class PagerAdapterStaffScreens extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterStaffScreens(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Staff_Calendar_Fragment tab1 = new Staff_Calendar_Fragment();
                return tab1;
            case 1:
                Activity_Fragment tab2 = new Activity_Fragment();
                return tab2;
            case 2:
                Account_Fragment tab3 = new Account_Fragment();
                return tab3;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}