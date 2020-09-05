package info.androidhive.materialdesign.PagerFragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import info.androidhive.materialdesign.pagerfragmentcontiners.AddNewStaffFragment;
import info.androidhive.materialdesign.pagerfragments.AddNewStaffBreakFragment;
import info.androidhive.materialdesign.pagerfragments.AddStaffFragment;
import info.androidhive.materialdesign.pagerfragments.DetailFragment;
import info.androidhive.materialdesign.pagerfragments.SelectCustomerFragment;

public class PagerAdapterNewStaff extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    AddStaffFragment tab1 = new AddStaffFragment();
    AddNewStaffBreakFragment tab2 = new AddNewStaffBreakFragment();

    public PagerAdapterNewStaff(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                //AddStaffFragment tab1 = new AddStaffFragment();
                return tab1;
            case 1:
                //AddNewStaffBreakFragment tab2 = new AddNewStaffBreakFragment();
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