package info.androidhive.materialdesign.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.MainActivity_bottom_view;
import info.androidhive.materialdesign.activity.NonLogin_Activity;
import info.androidhive.materialdesign.activity.SplashScreen;

/**
 * Created by hasnat on 29/08/15.
 */
public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    Activity activity;
    SharedPreferences sharedPreferences;


    public SlidingImage_Adapter(Context context, ArrayList<Integer> IMAGES) {
        this.context = context;
        activity = (Activity) context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context.getApplicationContext());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;

        final TextView tv_skip = (TextView) imageLayout.findViewById(R.id.tv_skip);
        final TextView tv_title = (TextView) imageLayout.findViewById(R.id.tv_title);
        final TextView tv_detail = (TextView) imageLayout.findViewById(R.id.tv_detail);

        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
        imageView.setImageResource(IMAGES.get(position));
        view.addView(imageLayout, 0);

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(sharedPreferences.contains("isLogin")){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (Objects.equals(sharedPreferences.getString("isLogin", ""), "yes")) {
                            Intent intent = new Intent(context, MainActivity_bottom_view.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            activity.finish();
                        }else{
                            //Intent intent = new Intent(context, NonLogin_Activity.class);
                            Intent intent = new Intent(context, MainActivity_bottom_view.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(intent);
                            activity.finish();
                        }
                    }
                //}
                /*Intent intent = new Intent(context,NonLogin_Activity.class);
                context.startActivity(intent);*/
            }
        });

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
