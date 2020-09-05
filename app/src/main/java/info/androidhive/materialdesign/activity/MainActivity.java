package info.androidhive.materialdesign.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.customer_work.Customer_Account_Fragment;
import info.androidhive.materialdesign.fragments.customer_work.Customer_ActivitiesFragment;
import info.androidhive.materialdesign.fragments.customer_work.Customer_favorate_Fragment;
import info.androidhive.materialdesign.fragments.BillHistoryFragment;
import info.androidhive.materialdesign.fragments.BookNowFragment;
import info.androidhive.materialdesign.fragments.BusinessListFragment;
import info.androidhive.materialdesign.fragments.CalendarFragment;
import info.androidhive.materialdesign.fragments.CreateBusinessAccountFragment;
import info.androidhive.materialdesign.fragments.CustomerListFragment;
import info.androidhive.materialdesign.fragments.DashboardFragment;
import info.androidhive.materialdesign.fragments.ManagementServiceFragment;
import info.androidhive.materialdesign.fragments.ManagementStaffFragment;
import info.androidhive.materialdesign.staff_work.StaffScreensActivity;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {



    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    String [] item,item_id;
    public static Spinner spinner_business_list;
    SharedPreferences sharedPreferences;

    TextView tv_app_name;

    int fragment_no=2;

    View sperator_1;


    @Override
    protected void onResume() {
        super.onResume();

        if (sharedPreferences.getString("isLogin" ,""  ).equals("yes")){

            if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                //staff bookappointment activity here
                Intent intent = new Intent(MainActivity.this, StaffScreensActivity.class);
                startActivity(intent);
                finish();

            }

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        spinner_business_list = (Spinner) findViewById(R.id.spinner_business_list);

        sperator_1 = findViewById(R.id.sperator_1);


        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);

    /*    setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);*/

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);




        if (!sharedPreferences.getString("isLogin" ,""  ).equals("yes")){
            displayView_non_login(0);

        }else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){

            spinner_business_list.setVisibility(View.GONE);
            sperator_1.setVisibility(View.GONE);
            displayView_recp(0);
        }else if (sharedPreferences.getString("user_role" ,""  ).equals("4")){

            spinner_business_list.setVisibility(View.GONE);
            sperator_1.setVisibility(View.GONE);
            displayView_cust(1);
        }else {

            spinner_business_list.setVisibility(View.VISIBLE);
            // display the first navigation drawer view on app launch
            //http://192.168.100.14/monshiapp/app/business_listing.php?user_id=1
            String url    =  getResources().getString(R.string.url)+"business_listing.php";
            String params = "" ;/*+sharedPreferences.getString("user_id", "")*/;

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getString("status").equals("200")) {
                            JSONArray business_listing = jsonObject.getJSONArray("business_listing");

                            item = new String[business_listing.length()];
                            item_id = new String[business_listing.length()];

                            for(int i = 0; i < business_listing.length(); i++) {
                                JSONObject c = business_listing.getJSONObject(i);
                                String id = c.getString("id");
                                String userID = c.getString("userID");
                                String name = c.getString("name");
                                String state = c.getString("state");
                                String state_name = c.getString("state_name");
                                String state_name_per = c.getString("state_name_per");
                                String city = c.getString("city");
                                String city_name = c.getString("city_name");
                                String city_name_per = c.getString("city_name_per");
                                String description = c.getString("description");
                                String contact = c.getString("contact");
                                String address = c.getString("address");

                                String business_type = c.getString("business_type");
                                String category = c.getString("category");
                                String category_name = c.getString("category_name");
                                String rating = c.getString("rating");

                                String sub_cat = c.getString("sub_cat");
                                String key_staff = c.getString("key_staff");
                                String key_services = c.getString("key_services");
                                String business_id = c.getString("business_id");

                                String image = c.getString("image");

                                item[i]=name;
                                item_id[i]=id;

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                    R.layout.my_spinner_style, item) {

                                public View getView(int position, View convertView, ViewGroup parent) {
                                    View v = super.getView(position, convertView, parent);

                                    return v;
                                }

                                public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                    View v =super.getDropDownView(position, convertView, parent);
                                    // ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                    return v;
                                }
                            };

                            spinner_business_list.setAdapter(adapter);

                            spinner_business_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    SavePreferences("business_id"        , item_id[position]);
                                    displayView(fragment_no);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
          getMenuInflater().inflate(R.menu.menu_main_non_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.item1:
                alertDialogShow_login(MainActivity.this);
                //Toast.makeText(getApplicationContext(),"Login",Toast.LENGTH_LONG).show();
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(),"Logging Out",Toast.LENGTH_LONG).show();
                SavePreferences("isLogin","");
                finish();
                return true;

            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDrawerItemSelected(View view, int position) {

        if (sharedPreferences.getString("isLogin" ,""  ).equals("yes")){
            if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                displayView_recp(position);// recp2 case
            }if (sharedPreferences.getString("user_role" ,""  ).equals("4")){
                displayView_cust(position);
            } else {
                displayView(position);//normal case
            }
        }else {
            displayView_non_login(position);
        }



    }

    @Override
    public void onimgClick() {
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new CreateBusinessAccountFragment();
                title = getString(R.string.title_home);
                fragment_no = 0;

                break;
            case 1:
                fragment = new BusinessListFragment();
                title = getString(R.string.title_friends);
                fragment_no = 1;
                break;
            case 2:
                fragment = new BookNowFragment();
                title = getString(R.string.title_messages);
                fragment_no = 2;
                break;
            case 3:
                fragment = new DashboardFragment();
                title = getString(R.string.title_home);
                fragment_no = 3;
                break;
            case 4:
                fragment = new ManagementStaffFragment();
                title = getString(R.string.title_friends);
                fragment_no = 4;
                break;
            case 5:
                fragment = new ManagementServiceFragment();
                title = getString(R.string.title_messages);
                fragment_no = 5;
                break;
            case 6:
                fragment = new CalendarFragment();
                title = getString(R.string.title_friends);
                fragment_no = 6;
                break;
            case 7:
                fragment = new CustomerListFragment();
                title = getString(R.string.title_messages);
                fragment_no = 7;
                break;
            case 8:
                fragment = new BillHistoryFragment();
                title = getString(R.string.title_messages);
                fragment_no = 8;
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            //getSupportActionBar().setTitle(title);

            tv_app_name.setText(""+title);
        }
    }

    private void displayView_recp(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new DashboardFragment();
                title = getString(R.string.title_home);
                fragment_no = 3;
                break;
            case 1:
                fragment = new ManagementStaffFragment();
                title = getString(R.string.title_friends);
                fragment_no = 4;
                break;
            case 2:
                fragment = new ManagementServiceFragment();
                title = getString(R.string.title_messages);
                fragment_no = 5;
                break;
            case 3:
                fragment = new CalendarFragment();
                title = getString(R.string.title_friends);
                fragment_no = 6;
                break;
            case 4:
                fragment = new CustomerListFragment();
                title = getString(R.string.title_messages);
                fragment_no = 7;
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            //getSupportActionBar().setTitle(title);

            tv_app_name.setText(""+title);
        }
    }

    private void displayView_cust(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new CreateBusinessAccountFragment();
                title = getString(R.string.title_home);
                fragment_no = 3;
                break;
            case 1:
                fragment = new BookNowFragment();
                title = getString(R.string.title_friends);
                fragment_no = 4;
                break;
            case 2:
                fragment = new Customer_favorate_Fragment();
                title = getString(R.string.title_messages);
                fragment_no = 5;
                break;
            case 3:
                fragment = new Customer_ActivitiesFragment();
                title = getString(R.string.title_friends);
                fragment_no = 6;
                break;
            case 4:
                fragment = new Customer_Account_Fragment();
                title = getString(R.string.title_messages);
                fragment_no = 7;
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            //getSupportActionBar().setTitle(title);

            tv_app_name.setText(""+title);
        }
    }

    private void displayView_non_login(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {

            case 0:
                fragment = new BookNowFragment();
                title = getString(R.string.title_messages);
                fragment_no = 2;
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            //getSupportActionBar().setTitle(title);

            tv_app_name.setText(""+title);
        }
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
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
                                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                                    //staff bookappointment activity here

                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                } else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                                    //staff bookappointment activity here

                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(MainActivity.this, StaffScreensActivity.class);
                                    startActivity(intent);
                                    finish();

                                } else if (jsonObject.getString("user_role"   ).equals("4")){
                                    //Customer bookappointment activity here
                                    /*Intent intent = new Intent(MainActivity.this, CustomerBookingActivity.class);
                                    startActivity(intent);*/

                                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

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


}