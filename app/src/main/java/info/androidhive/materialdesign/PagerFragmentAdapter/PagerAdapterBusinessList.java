package info.androidhive.materialdesign.PagerFragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.androidhive.materialdesign.pagerfragments.BusinessInfoFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessStaffListFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessServiceListFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment;

public class PagerAdapterBusinessList extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterBusinessList(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 3:
                BusinessBookAppointmentFragment tab3 = new BusinessBookAppointmentFragment();

                return tab3;
            case 2:
                BusinessServiceListFragment tab2 = new BusinessServiceListFragment();
                return tab2;
            case 1:
                BusinessStaffListFragment tab1 = new BusinessStaffListFragment();

                return tab1;
            case 0:
                BusinessInfoFragment tab0 = new BusinessInfoFragment();
                return tab0;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}