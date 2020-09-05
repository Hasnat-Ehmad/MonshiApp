package info.androidhive.materialdesign.activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.SlidingImage_Adapter;

public class TutorialDotViewActivity extends AppCompatActivity {

    ViewPager mPager;
    int currentPage = 0;
    int NUM_PAGES = 0;
    ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    Integer[] IMAGES= {R.drawable.ic_perm_contact_calendar_black_24dp,R.drawable.ic_perm_contact_calendar_black_24dp,R.drawable.ic_perm_contact_calendar_black_24dp};
    View view;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_dot_view_fragment);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SavePreferences("tutorial_display","no");
        init();
    }

    private void init() {

        ImagesArray.addAll(Arrays.asList(IMAGES));
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new SlidingImage_Adapter(TutorialDotViewActivity.this,ImagesArray));
        CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);
        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
       /* Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);*/

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }
    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
