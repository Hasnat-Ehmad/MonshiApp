package info.androidhive.materialdesign.staff_work;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapterStaffBooking extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapterStaffBooking(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Staff_Detail_Fragment tab1 = new Staff_Detail_Fragment();
                return tab1;
            case 1:
                Staff_Customer_Fragment tab2 = new Staff_Customer_Fragment();
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