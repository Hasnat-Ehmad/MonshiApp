package info.androidhive.materialdesign.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterBusinessList;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.staff_work.StaffScreensActivity;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;

public class BusinessPublicDetail_Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static int fragment_no;
    TextView tv_number,tv_category,tv_address,tv_description,tv_label_dashboard;

    ImageView img_profile_pic,img_back;

    public static ViewPager viewPager;
    private Toolbar mToolbar;
    public static ScrollView scrollView_BusinessPublicDetail_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_public_detail);



        String title = getString(R.string.title_messages);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        img_back = mToolbar.findViewById(R.id.img_back);
        /*tv_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        tv_title.setGravity(View.TEXT_ALIGNMENT_CENTER);*/
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

      /*  setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getApplicationContext());

        tv_label_dashboard= findViewById(R.id.tv_label_dashboard);
        tv_label_dashboard.setText(""+sharedPreferences.getString("selected_name", ""));

        tv_number= findViewById(R.id.tv_number);
        tv_number.setText(""+sharedPreferences.getString("selected_contact", ""));

        tv_category= findViewById(R.id.tv_category);
        tv_category.setText(""+sharedPreferences.getString("selected_category_name_per", ""));

        tv_address= findViewById(R.id.tv_address);
        tv_address.setText(""+sharedPreferences.getString("selected_address", ""));

        tv_description= findViewById(R.id.tv_description);
        tv_description.setText(""+sharedPreferences.getString("selected_description", ""));

        img_profile_pic = findViewById(R.id.img_profile_pic);
        Picasso.with(img_profile_pic.getContext()).load(""+sharedPreferences.getString("selected_image", "")).into(img_profile_pic);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.map));//BusinessBookAppointmentFragment
        tabLayout.addTab(tabLayout.newTab().setText(R.string.staff));//BusinessServiceListFragment
        tabLayout.addTab(tabLayout.newTab().setText(R.string.service));//BusinessStaffListFragment
        tabLayout.addTab(tabLayout.newTab().setText(R.string.book_appt));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterBusinessList adapter = new PagerAdapterBusinessList
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        int count = tabLayout.getTabCount();
        viewPager.setCurrentItem(3);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                alertDialogShow_login(BusinessPublicDetail_Activity.this);
                //Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(),"Signup",Toast.LENGTH_LONG).show();
                return true;

            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void alertDialogShow_login(final Context context) {


        //final JSONArray[] ticket_list = new JSONArray[1];
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_popup_login,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);




        final AlertDialog d = alertDialogBuilder.show();

        final EditText ed_username = (EditText) promptsView.findViewById(R.id.ed_username);
        final EditText ed_password = (EditText) promptsView.findViewById(R.id.ed_password);






        Button p_btn_login = (Button) promptsView.findViewById(R.id.p_btn_login);
        p_btn_login.setTransformationMethod(null);

        p_btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String url    =  getResources().getString(R.string.url)+"login.php";
                String params =  "username="+ed_username.getText().toString()+"&password="+ed_password.getText().toString();


                //System.out.println("URL = "+url_login+"?"+params);
                WebRequestCall webRequestCall_login = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {


                                SavePreferences("fname"        ,jsonObject.getString("fname"      ));
                                SavePreferences("lname"        ,jsonObject.getString("lname"));
                                SavePreferences("full_name"    ,jsonObject.getString("full_name" ));
                                SavePreferences("email"        ,jsonObject.getString("email"     ));
                                SavePreferences("phone"        ,jsonObject.getString("phone"     ));
                                SavePreferences("address"      ,jsonObject.getString("address"       ));
                                SavePreferences("user_id"      ,jsonObject.getString("user_id"       ));
                                SavePreferences("user_status"  ,jsonObject.getString("user_status"   ));
                                SavePreferences("user_role"    ,jsonObject.getString("user_role"     ));

                                SavePreferences("isLogin","yes");

                                if (jsonObject.getString("user_role"   ).equals("1")){
                                    Intent intent = new Intent(BusinessPublicDetail_Activity.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                                    //staff bookappointment activity here

                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(BusinessPublicDetail_Activity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                } else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                                    //staff bookappointment activity here

                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(BusinessPublicDetail_Activity.this, StaffScreensActivity.class);
                                    startActivity(intent);
                                     finish();

                                } else if (jsonObject.getString("user_role"   ).equals("4")){
                                    //Customer bookappointment activity here
                                    Intent intent = new Intent(BusinessPublicDetail_Activity.this, CustomerBookingActivity.class);
                                    startActivity(intent);
                                }

                                //Toast.makeText(context,jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                d.dismiss();
                                Toast.makeText(context,jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                               // Intent intent = new Intent(BusinessPublicDetail_Activity.this,MainActivity.class);
                               // startActivity(intent);
                               // finish();
                                d.dismiss();
                            }else{
                                //login_form_layout.setVisibility(View.VISIBLE);
                                Toast.makeText(context,jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                ed_username.setError(getResources().getString(R.string.enter_valid_username));
                                ed_password.setError(getResources().getString(R.string.enter_valid_password));
                                ed_username.requestFocus();
                            }

                        } catch (final JSONException e) {
                            //login_form_layout.setVisibility(View.VISIBLE);
                            //Log.e(TAG, "Json parsing error: " + e.getMessage());

                        }
                    }
                });
                webRequestCall_login.execute(url,"POST",params);



                /*if(haveNetworkConnection()){}else{
                    //showSnack(false);
                }*/

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
