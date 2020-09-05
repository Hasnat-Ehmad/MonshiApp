package info.androidhive.materialdesign;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by zfdang on 2016-4-15.
 */
public class MonshiappApplication extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();

        // the following line is important
        Fresco.initialize(getApplicationContext());
    }
}
