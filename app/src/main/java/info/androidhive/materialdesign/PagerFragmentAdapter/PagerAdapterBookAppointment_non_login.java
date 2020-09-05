package info.androidhive.materialdesign.PagerFragmentAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import info.androidhive.materialdesign.pagerfragments.DetailFragment;
import info.androidhive.materialdesign.pagerfragments.DetailFragment_non_login;
import info.androidhive.materialdesign.pagerfragments.SelectCustomerFragment;
import info.androidhive.materialdesign.pagerfragments.SelectCustomerFragment_non_login;

public class PagerAdapterBookAppointment_non_login extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterBookAppointment_non_login(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                DetailFragment_non_login tab1 = new DetailFragment_non_login();
                return tab1;
            case 1:
                SelectCustomerFragment_non_login tab2 = new SelectCustomerFragment_non_login();
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