package info.androidhive.materialdesign.PagerFragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.androidhive.materialdesign.pagerfragments.BusinessClassListFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessInfoFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessStaffListFragment;

public class PagerAdapterBusinessList_class extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterBusinessList_class(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 2:
                BusinessClassListFragment tab2 = new BusinessClassListFragment();

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