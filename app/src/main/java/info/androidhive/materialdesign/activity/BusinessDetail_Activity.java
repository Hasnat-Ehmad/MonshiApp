package info.androidhive.materialdesign.activity;

import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class BusinessDetail_Activity extends AppCompatActivity {

    ImageView img_profile_pic,img_time,img_detail;

    LinearLayout layout_besic_detail;
    TextView tv_business_name,tv_business_address,tv_business_contact,tv_business_description;

    LinearLayout layout_business_time;
    TextView spinner_saturday_from,spinner_sunday_from,spinner_monday_from,
            spinner_tuesday_from,spinner_wednesday_from,spinner_thursday_from,
            spinner_friday_from;

    TextView spinner_saturday_to,spinner_sunday_to,spinner_monday_to,spinner_tuesday_to,
            spinner_wednesday_to,spinner_thursday_to,spinner_friday_to;

    int[] textview_to = new int[]
            {R.id.spinner_saturday_to, R.id.spinner_sunday_to, R.id.spinner_monday_to,
                    R.id.spinner_tuesday_to, R.id.spinner_wednesday_to,R.id.spinner_thursday_to,
                    R.id.spinner_friday_to };

    int[] textview_from = new int[]
            { R.id.spinner_saturday_from, R.id.spinner_sunday_from, R.id.spinner_monday_from,
              R.id.spinner_tuesday_from, R.id.spinner_wednesday_from,R.id.spinner_thursday_from,
              R.id.spinner_friday_from };

    int[] textview_dash = new int[]
            {       R.id.dash1, R.id.dash2,R.id.dash3,
                    R.id.dash4, R.id.dash5,R.id.dash6,
                    R.id.dash7 };



    private TextView[] textViews;


    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_detail);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
            ImageView img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.VISIBLE);

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getFragmentManager()).popBackStack();
                    }
                }
            });
        }else {

            Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
            TextView tv_app_name;

            mToolbar = (Toolbar) findViewById(R.id.toolbar);

            tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
            tv_app_name.setVisibility(View.VISIBLE);

            img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
            img_profile_tool_bar.setVisibility(View.GONE);

            img_notifications = mToolbar.findViewById(R.id.img_notifications);
            img_notifications.setVisibility(View.VISIBLE);

            img_filter = mToolbar.findViewById(R.id.img_filter);
            img_filter.setVisibility(View.GONE);

            img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.VISIBLE);
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   finish();
                }
            });
        }


        img_profile_pic = findViewById(R.id.img_profile_pic);
        img_detail      = findViewById(R.id.img_detail);
        img_time        = findViewById(R.id.img_time);

        layout_besic_detail = findViewById(R.id.layout_besic_detail);
        tv_business_name    = findViewById(R.id.tv_business_name);
        tv_business_address = findViewById(R.id.tv_business_address);
        tv_business_contact = findViewById(R.id.tv_business_contact);
        tv_business_description = findViewById(R.id.tv_business_description);


        layout_business_time = findViewById(R.id.layout_business_time);

        spinner_saturday_from = findViewById(R.id.spinner_saturday_from);

        spinner_sunday_from   = findViewById(R.id.spinner_sunday_from);

        spinner_monday_from   = findViewById(R.id.spinner_monday_from);

        spinner_tuesday_from  = findViewById(R.id.spinner_tuesday_from);

        spinner_wednesday_from= findViewById(R.id.spinner_wednesday_from);

        spinner_thursday_from = findViewById(R.id.spinner_thursday_from);

        spinner_friday_from   = findViewById(R.id.spinner_friday_from);


        spinner_saturday_to = findViewById(R.id.spinner_saturday_to);

        spinner_sunday_to   = findViewById(R.id.spinner_sunday_to);

        spinner_monday_to   = findViewById(R.id.spinner_monday_to);

        spinner_tuesday_to  = findViewById(R.id.spinner_tuesday_to);

        spinner_wednesday_to= findViewById(R.id.spinner_wednesday_to);

        spinner_thursday_to = findViewById(R.id.spinner_thursday_to);

        spinner_friday_to   = findViewById(R.id.spinner_friday_to);

        layout_besic_detail.setVisibility(View.VISIBLE);

        img_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_besic_detail.setVisibility(View.VISIBLE);
                layout_business_time.setVisibility(View.GONE);
            }
        });

        img_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_besic_detail.setVisibility(View.GONE);
                layout_business_time.setVisibility(View.VISIBLE);
            }
        });


        //http://192.168.100.14/monshiapp/app/business_detail.php?business_id=1
        String url    =  getResources().getString(R.string.url)+"business_detail.php";
        String params =  /*"user_id="+sharedPreferences.getString("user_id", "")+*/
                "business_id="+sharedPreferences.getString("selected_business", "");

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        //JSONArray business_listing = jsonObject.getJSONArray("business_listing");
                        String id = jsonObject.getString("id");
                        String name = jsonObject.getString("name");
                        String category = jsonObject.getString("category");
                        String category_name_per = jsonObject.getString("category_name_per");
                        String category_name = jsonObject.getString("category_name");
                        String state = jsonObject.getString("state");
                        String state_name = jsonObject.getString("state_name");
                        String state_name_per = jsonObject.getString("state_name_per");
                        String city = jsonObject.getString("city");
                        String city_name = jsonObject.getString("city_name");
                        String city_name_per = jsonObject.getString("city_name_per");
                        String rating = jsonObject.getString("rating");
                        String description = jsonObject.getString("description");
                        String contact = jsonObject.getString("contact");
                        String address = jsonObject.getString("address");
                        String is_fav = jsonObject.getString("is_fav");
                        String business_type = jsonObject.getString("business_type");

                        tv_business_name.setText(""+name);
                        tv_business_address.setText(""+address);
                        tv_business_contact.setText(""+contact);
                        tv_business_description.setText(""+description);


                        Picasso.with(img_profile_pic.getContext())
                                  .load(""+sharedPreferences.getString("selected_image", ""))
                                     .into(img_profile_pic);


                        Toast.makeText(BusinessDetail_Activity.this,"business_type = "+business_type,Toast.LENGTH_SHORT).show();

                        JSONArray business_timing = jsonObject.getJSONArray("business_timing");

                        textViews = new TextView[business_timing.length()];

                        for(int i = 0; i < business_timing.length(); i++) {
                            JSONObject c = business_timing.getJSONObject(i);
                            String is_selected = c.getString("is_selected");
                            String operating_day_per = c.getString("operating_day_per");
                            String operating_day = c.getString("operating_day");
                            String from_time = c.getString("from_time");
                            String from_time_per = c.getString("from_time_per");
                            String to_time = c.getString("to_time");
                            String to_time_per = c.getString("to_time_per");
                            String final_time_per = c.getString("final_time_per");
                            String final_time = c.getString("final_time");

                            if (is_selected.equals("1")){
                                textViews[i] = (TextView)findViewById(textview_from[i]);
                                textViews[i].setText(""+from_time_per);

                                textViews[i] = (TextView)findViewById(textview_to[i]);
                                textViews[i].setText(""+to_time_per);
                            }else {

                                textViews[i] = (TextView)findViewById(textview_from[i]);
                                textViews[i].setVisibility(View.INVISIBLE);

                                textViews[i] = (TextView)findViewById(textview_to[i]);
                                textViews[i].setVisibility(View.INVISIBLE);

                                textViews[i] = (TextView)findViewById(textview_dash[i]);
                                textViews[i].setText(""+final_time);
                            }

                            //Toast.makeText(BusinessDetail_Activity.this,"final_time = "+final_time,Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);

    }
}
