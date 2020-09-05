package info.androidhive.materialdesign.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.BookNow_List_Adapter;
import info.androidhive.materialdesign.lists.BookNow_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class LandingPage_Activity extends AppCompatActivity {

    ArrayList<BookNow_List> bookNow_lists =new ArrayList();

    ListView listView;

    BookNow_List_Adapter bookNow_list_adapter;
    String [] states,states_id;
    String [] cities,city_id;
    String [] business_category,business_category_id;

    Spinner spinner_business_state,spinner_business_city,
            spinner_business_category,spinner_business_sub_category;
    private Toolbar mToolbar;
    public static int fragment_no;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        String title = getString(R.string.title_messages);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(title);



        listView = (ListView) findViewById(R.id.list_view);
        spinner_business_state = (Spinner) findViewById(R.id.spinner_business_state);
        states();
        spinner_business_city  = (Spinner) findViewById(R.id.spinner_business_city);
        cities("0");
        spinner_business_category  = (Spinner) findViewById(R.id.spinner_business_category);
        spinner_business_category();

        spinner_business_sub_category  = (Spinner) findViewById(R.id.spinner_business_sub_category);
        spinner_business_sub_category("0");


        //http://192.168.100.14/monshiapp/app/business_listing.php
        String url = getResources().getString(R.string.url)+"business_listing.php";
        String params = "";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getString("status").equals("200")) {
                    JSONArray business_listing = jsonObject.getJSONArray("business_listing");

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
                        String category_name_per = c.getString("category_name_per");
                        String category_name = c.getString("category_name");
                        String rating = c.getString("rating");
                        String sub_cat = c.getString("sub_cat");
                        String key_staff = c.getString("key_staff");
                        String key_services = c.getString("key_services");
                        String business_id = c.getString("business_id");
                        String image = c.getString("image");
                        String isfav = c.getString("isfav");
                        String fav_id = c.getString("fav_id");

                        BookNow_List obj = new BookNow_List
                                (""+id,""+userID,""+name,""+state,""+state_name,""+state_name_per,
                                        ""+city, ""+city_name,""+city_name_per,""+description,""+contact,
                                        ""+address,"","",""+business_type,""+category, ""+category_name_per,
                                        ""+category_name, ""+rating,""+sub_cat,""+key_staff,""+key_services,
                                        ""+business_id, ""+image,""+isfav,""+fav_id);

                        bookNow_lists.add(obj);

                    }
                    bookNow_list_adapter = new BookNow_List_Adapter(LandingPage_Activity.this,bookNow_lists);

                    listView.setAdapter(bookNow_list_adapter);


                } else if (jsonObject.getString("status").equals("300")) {
                    String status_alert = jsonObject.getString("status_alert");
                    Toast.makeText(LandingPage_Activity.this, "" + status_alert, Toast.LENGTH_SHORT).show();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);

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
                alertDialogShow_login(LandingPage_Activity.this);
                //Intent intent = new Intent(LandingPage_Activity.this,LoginActivity.class);
                //startActivity(intent);
                return true;
            case R.id.item2:
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;

            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void states(){
        //http://192.168.100.14/monshiapp/app/state_list.php
        String url    =  getResources().getString(R.string.url)+"state_list.php";
        String params =  "";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray state_listing = jsonObject.getJSONArray("state_listing");

                        states    = new String [state_listing.length()];
                        states_id = new String [state_listing.length()];

                        for(int i = 0; i < state_listing.length(); i++) {
                            JSONObject c = state_listing.getJSONObject(i);
                            String RegionID = c.getString("RegionID");
                            String Region = c.getString("Region");
                            String Region_per = c.getString("Region_per");



                            if (i==0){
                                states   [i]  = "انتخاب استان";
                                states_id[i]  = RegionID;
                            }else {
                                states   [i]  = Region_per;
                                states_id[i]  = RegionID;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LandingPage_Activity.this,
                                R.layout.my_spinner_style, states) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                //((TextView) v).setGravity(Gravity.CENTER);
                                return v;
                            }
                        };

                        spinner_business_state.setAdapter(adapter);

                        spinner_business_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!states_id[position].equals("0")){
                                    cities(states_id[position]);
                                }

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

    public void cities(String states_id){
        //http://192.168.100.14/monshiapp/app/city_list.php
        String url    =  getResources().getString(R.string.url)+"city_list.php";
        String params =  "state="+states_id;

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray city_listing = jsonObject.getJSONArray("city_listing");

                        cities    = new String [city_listing.length()];
                        city_id = new String [city_listing.length()];

                        for(int i = 0; i < city_listing.length(); i++) {
                            JSONObject c = city_listing.getJSONObject(i);
                            String CityId = c.getString("CityId");
                            String City = c.getString("City");
                            String City_per = c.getString("City_per");



                            if (i==0){
                                cities   [i]  = "انتخاب شهر";
                                city_id  [i]  = CityId;
                            }else {
                                cities [i]  = City_per;
                                city_id[i]  = CityId;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LandingPage_Activity.this,
                                R.layout.my_spinner_style, cities) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);


                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_city.setAdapter(adapter);




                    }
                    else{
                        cities    = new String [1];
                        city_id = new String [1];
                        cities   [0]  = "انتخاب شهر";
                        city_id  [0]  = "0";
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LandingPage_Activity.this,
                                R.layout.my_spinner_style, cities) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);


                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_city.setAdapter(adapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);

    }

    public void spinner_business_category(){
        //http://192.168.100.14/monshiapp/app/business_category_list.php
        String url    =  getResources().getString(R.string.url)+"business_category_list.php";
        String params =  "";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_cat_listing = jsonObject.getJSONArray("business_cat_listing");

                        business_category    = new String [business_cat_listing.length()];
                        business_category_id = new String [business_cat_listing.length()];

                        for(int i = 0; i < business_cat_listing.length(); i++) {
                            JSONObject c = business_cat_listing.getJSONObject(i);
                            String category_id = c.getString("category_id");
                            String category_name = c.getString("category_name");
                            String category_name_per = c.getString("category_name_per");



                            if (i==0){
                                business_category[i]  = "نوع کسب و کار";
                                business_category_id[i]  = category_id;
                            }else {
                                business_category   [i]  = category_name_per;
                                business_category_id[i]  = category_id;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LandingPage_Activity.this,
                                R.layout.my_spinner_style, business_category) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_category.setAdapter(adapter);

                        spinner_business_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!business_category_id[position].equals("0")){
                                    spinner_business_sub_category(business_category_id[position]);
                                }

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

    public void spinner_business_sub_category(String id){
        // http://192.168.100.14/monshiapp/app/business_subcategory_list.php
        String url    =  getResources().getString(R.string.url)+"business_subcategory_list.php";
        String params =  "category="+id;

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_subcat_listing = jsonObject.getJSONArray("business_subcat_listing");

                        business_category    = new String [business_subcat_listing.length()];
                        business_category_id = new String [business_subcat_listing.length()];

                        for(int i = 0; i < business_subcat_listing.length(); i++) {
                            JSONObject c = business_subcat_listing.getJSONObject(i);
                            String category_id = c.getString("category_id");
                            String category_name = c.getString("category_name");
                            String category_name_per = c.getString("category_name_per");



                            if (i==0){
                                business_category[i]  = "کسب و کار زیر شاخه";
                                business_category_id[i]  = category_id;
                            }else {
                                business_category   [i]  = category_name_per;
                                business_category_id[i]  = category_id;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LandingPage_Activity.this,
                                R.layout.my_spinner_style, business_category) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_sub_category.setAdapter(adapter);


                    }
                    else{

                        business_category    = new String [1];
                        business_category_id = new String [1];

                        business_category[0]  = "کسب و کار زیر شاخه";
                        business_category_id[0]  = "0";
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(LandingPage_Activity.this,
                                R.layout.my_spinner_style, business_category) {

                            public View getView(int position, View convertView, ViewGroup parent) {
                                View v = super.getView(position, convertView, parent);

                                return v;
                            }

                            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                                View v =super.getDropDownView(position, convertView, parent);
                                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                                return v;
                            }
                        };

                        spinner_business_sub_category.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);
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

                                Intent intent = new Intent(LandingPage_Activity.this,MainActivity.class);
                                startActivity(intent);
                                finish();
                                //Toast.makeText(context,jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
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
