package info.androidhive.materialdesign.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.MailTo;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterBookAppointment_non_login;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class BookAppt_NonLogin extends AppCompatActivity {

    String[] services; int services_position;
    String[] staff   ; int staff_position;
    public static int fragment_no;
    SharedPreferences sharedPreferences;

    private Toolbar mToolbar;

    CircleImageView imageView_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appt_non_login);

        String title = getString(R.string.title_messages);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        imageView_menu = mToolbar.findViewById(R.id.img_profile);
        imageView_menu.setVisibility(View.GONE);

        ImageView img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       /* setSupportActionBar(mToolbar);*/
      /*  if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }*/

        /*if (getArguments() != null) {
            services = getArguments().getStringArray("services");
            services_position = getArguments().getInt("service_position");

            staff = getArguments().getStringArray("staff");
            staff_position = getArguments().getInt("staff_position");
        }*/


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("جزئیات"));//DetailFragment
        tabLayout.addTab(tabLayout.newTab().setText("مشتری"));//SelectCustomerFragment

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterBookAppointment_non_login adapter = new PagerAdapterBookAppointment_non_login
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
                alertDialogShow_login(BookAppt_NonLogin.this);
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
                                SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                SavePreferences("isLogin","yes");

                                if (jsonObject.getString("user_status"   ).equals("a")){
                                    fragment_no = 2;
                                }

                                Toast.makeText(context,jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();

                                //Intent intent = new Intent(BookAppt_NonLogin.this,MainActivity.class);
                                //startActivity(intent);
                                //finish();
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
