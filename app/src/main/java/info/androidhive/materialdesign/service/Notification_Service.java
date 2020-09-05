package info.androidhive.materialdesign.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.MainActivity_bottom_view;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 3/22/2018.
 */

    // Handler that receives messages from the thread
public class Notification_Service extends Service {

    final static int REQUEST_LOCATION = 199;
    private GoogleApiClient googleApiClient;
    LocationManager manager;

    int counter=0;
    SharedPreferences sharedPreferences;
    public String check_notify_id="";

    String id="0",notification_="";

    private Handler mHandler;
    // default interval for syncing data
    final long DEFAULT_SYNC_INTERVAL = 15 * 1000;

    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {

            // Todo Location Already on  ... start


                syncData();
                // counter++;
                //Toast.makeText(getApplication(),"40 = "+counter, Toast.LENGTH_SHORT).show();

                // Repeat this runnable code block again every ... min

            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);

             /*syncData();
             //counter++;
             //Toast.makeText(getApplication(),"40 = "+counter, Toast.LENGTH_SHORT).show();

             //Repeat this runnable code block again every ... min
               mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);*/
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

            mHandler = new Handler();
           // Execute a runnable task as soon as possible
            mHandler.post(runnableService);
            //stopSelf();

          return  START_REDELIVER_INTENT;// tells the OS to restart the service when destroyed!!
          //return  START_NOT_STICKY ;//system will not try to restart the service
          //return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("LongLogTag")
    private synchronized void syncData() {

//         counter++;
//         Toast.makeText(getApplication()," counter = "+counter, Toast.LENGTH_SHORT).show();

         //======================================================================================

        // call your rest service here

        //online_status = "1";
        String url = getString(R.string.url)+"fetch_notifications.php";
        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params = "role="+sharedPreferences.getString("user_role", "")+
                    "&user_id="+sharedPreferences.getString("user_id", "")+
                    "&staff_id="+sharedPreferences.getString("staff_user_id", "")+
                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                    "&notification_id="+sharedPreferences.getString("last_notification_id", "0");

        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params = "role="+sharedPreferences.getString("user_role", "")+
                    "&user_id="+sharedPreferences.getString("user_id", "")+
                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                    "&staff_id="+sharedPreferences.getString("staff_user_id", "")+
                    "&notification_id="+sharedPreferences.getString("last_notification_id", "0");
        }

        //String params = "user_id="+id;

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @SuppressLint("LongLogTag")
            @Override
            public void TaskCompletionResult(String result) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);

                    String status = jsonObject.getString("status");

                    if (status.equals("200")) {

                        JSONArray notification_list = jsonObject.getJSONArray("notification_list");


                        for (int i = 0; i < notification_list.length(); i++) {
                            JSONObject c = notification_list.getJSONObject(i);



                             id = c.getString("id");

                            if (i==0){
                                SavePreferences("last_notification_id",""+id);
                            }

                             notification_ = c.getString("notification");

                            if (!check_notify_id.equals("" + id)) {
                                //Toast.makeText(getApplicationContext()," value of counter inside = "+counter,Toast.LENGTH_SHORT).show();
                                check_notify_id = id;
                               // SavePreferences("check_notify_id",""+check_notify_id);
                                sendNotification(notification_);
                            }
                        }



                    } else if (status.equals("300")) {
                        //String status_alert = jsonObject.getString("status_alert");
                        //Toast.makeText(getApplicationContext(), "" + status_alert, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }

        });
        webRequestCall.execute(url, "POST", params);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
         //mHandler.removeCallbacksAndMessages(runnableService);
         mHandler.removeCallbacks(runnableService);  // stopping handler ("this" is the handler runnable you are stopping)
         //Toast.makeText(getApplication(),"on destroy ", Toast.LENGTH_SHORT).show();
    }

    public static NotificationCompat.Builder mBuilder ; public static NotificationManager notifManager;

    public void sendNotification(String notification) {

        notifManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        //notifManager.cancelAll();

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
                    mChannel.setLightColor(Color.BLUE);

            mChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notifManager.createNotificationChannel(mChannel);
        }

        mBuilder = new NotificationCompat.Builder(this,channelId);

        //Create the intent that’ll fire when the user taps the notification//
        //String value = "1";
        Intent resultIntent = new Intent(this, MainActivity_bottom_view.class);

        resultIntent.putExtra("value",true);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        //resultIntent.setAction(Intent.ACTION_MAIN);
        //resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity_bottom_view.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);

        //PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,0);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.androidauthority.com/"));
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        //mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.ic_person_black_24dp);
        mBuilder.setContentTitle("Monshi App");
        mBuilder.setContentText (notification);

        mBuilder.setVibrate(new long[] { 400, 400}); //this for vibration


        mBuilder.setPriority(Notification.PRIORITY_MAX);

        mBuilder.setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        mBuilder.setAutoCancel(true);


        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        mBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mNotificationManager.notify(Integer.parseInt(check_notify_id), mBuilder.build());
        }
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}