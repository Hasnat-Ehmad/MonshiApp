package info.androidhive.materialdesign.PagerFragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessServiceListFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessStaffListFragment;
import info.androidhive.materialdesign.pagerfragments.DetailFragment;
import info.androidhive.materialdesign.pagerfragments.SelectCustomerFragment;

public class PagerAdapterBookAppointment extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterBookAppointment(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DetailFragment tab1 = new DetailFragment();
                return tab1;
            case 1:
                SelectCustomerFragment tab2 = new SelectCustomerFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}