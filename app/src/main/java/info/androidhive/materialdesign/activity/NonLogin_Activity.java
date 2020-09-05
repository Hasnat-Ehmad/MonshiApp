package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.BookNow_List_Adapter;
import info.androidhive.materialdesign.lists.BookNow_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class NonLogin_Activity extends AppCompatActivity {

    ArrayList<BookNow_List> bookNow_lists =new ArrayList();

    ListView listView;

    BookNow_List_Adapter bookNow_list_adapter;
    String [] states,states_id;
    String [] cities,city_id;
    String [] business_category,business_category_id;
    String [] business_sub_category,business_sub_category_id;

    Spinner spinner_business_state,spinner_business_city,
            spinner_business_category,spinner_business_sub_category;
    View view1,view2,view3,view4;

    TextView tv_no_business,tv_app_name;

    EditText ed_search;

    Button btn_search;
    Boolean filter_check = true;
    ImageView img_filter;
    ImageView img_profile;
    SharedPreferences sharedPreferences;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_login);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.VISIBLE);
        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);


        if(sharedPreferences.contains("isLogin")){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(Objects.equals(sharedPreferences.getString("isLogin", ""), "yes")){
                    Intent intent = new Intent(NonLogin_Activity.this,MainActivity_bottom_view.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            }
        }


        tv_app_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonLogin_Activity.this,NonLogin_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        tv_no_business = findViewById(R.id.tv_no_business);
        tv_no_business.setVisibility(View.GONE);

        img_profile = mToolbar.findViewById(R.id.img_profile);
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NonLogin_Activity.this,MenuActivity.class);
                startActivity(intent);
                //Intent intent = new Intent(NonLogin_Activity.this,LoginActivity.class);
                //startActivity(intent);
            }
        });


        listView = (ListView) findViewById(R.id.list_view);

        view1 = (View) findViewById(R.id.sperator_1);
        view2 = (View) findViewById(R.id.separator_2);
        view3 = (View) findViewById(R.id.separator_3);
        view4 = (View) findViewById(R.id.separator_4);


        spinner_business_state = (Spinner) findViewById(R.id.spinner_business_state);
        states();
        spinner_business_city  = (Spinner) findViewById(R.id.spinner_business_city);

        spinner_business_category  = (Spinner) findViewById(R.id.spinner_business_category);
        spinner_business_category();

        spinner_business_sub_category  = (Spinner) findViewById(R.id.spinner_business_sub_category);

        ed_search = findViewById(R.id.ed_search);
        btn_search = findViewById(R.id.btn_search);

        if(filter_check){

            view1.setVisibility(View.GONE);
            view2.setVisibility(View.GONE);
            view3.setVisibility(View.GONE);
            view4.setVisibility(View.GONE);

            ed_search.setVisibility(View.GONE);
            btn_search.setVisibility(View.GONE);

            spinner_business_state.setVisibility(View.GONE);
            spinner_business_city.setVisibility(View.GONE);
            spinner_business_category.setVisibility(View.GONE);
            spinner_business_sub_category.setVisibility(View.GONE);
        }

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filter_check){

                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.VISIBLE);

                    ed_search.setVisibility(View.VISIBLE);
                    btn_search.setVisibility(View.VISIBLE);

                    spinner_business_state.setVisibility(View.VISIBLE);
                    spinner_business_city.setVisibility(View.VISIBLE);
                    spinner_business_category.setVisibility(View.VISIBLE);
                    spinner_business_sub_category.setVisibility(View.VISIBLE);
                    filter_check = false;


                }else{

                    view1.setVisibility(View.GONE);
                    view2.setVisibility(View.GONE);
                    view3.setVisibility(View.GONE);
                    view4.setVisibility(View.GONE);

                    ed_search.setVisibility(View.GONE);
                    btn_search.setVisibility(View.GONE);

                    spinner_business_state.setVisibility(View.GONE);
                    spinner_business_city.setVisibility(View.GONE);
                    spinner_business_category.setVisibility(View.GONE);
                    spinner_business_sub_category.setVisibility(View.GONE);
                    filter_check = true;
                }
            }
        });


        getData("","","","","");//calling the api here

        /*img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NonLogin_Activity.this,"Filter",Toast.LENGTH_SHORT).show();
            }
        });*/
        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    String keyword=""+ URLEncoder.encode(ed_search.getText().toString(),"utf-8");
                           keyword=""+ ed_search.getText().toString();
                    getData("","","","",""+keyword);//calling the api here
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                single_state_id="";
                single_city_id="";
                single_category_id="";
                single_sub_category_id="";

                spinner_business_state.setSelection(0);
                spinner_business_city.setSelection(0);
                spinner_business_category.setSelection(0);
                spinner_business_sub_category.setSelection(0);

                ed_search.setText("");

                getData("","","","","");//calling the api here

            }
        });

    }

    String single_state_id="";

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
                                states   [i]  = getString(R.string.str_select_state);
                                states_id[i]  = RegionID;
                            }else {
                                states   [i]  = Region_per;
                                states_id[i]  = RegionID;
                            }
                        }

                        //if (getActivity()!=null){

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(NonLogin_Activity.this,
                                    R.layout.my_spinner_style, states) {

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

                            spinner_business_state.setAdapter(adapter);

                            spinner_business_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (!states_id[position].equals("0")){
                                        single_state_id = states_id[position];
                                        cities(states_id[position]);
//                                    getData(""+states_id[position],"","","","");//calling the api here


                                        if (!single_category_id.equals("")){

                                            getData(""+single_state_id,"",""+single_category_id,"","");//calling the api here
                                        }
                                        if (!single_sub_category_id.equals("")){

                                            getData(""+single_state_id,"",""+single_category_id,""+single_sub_category_id,"");//calling the api here

                                        } else {

                                            getData(""+states_id[position],"","","","");//calling the api here

                                        }

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {


                                }
                            });

                      //  }

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

    String single_city_id = "";

    public void cities(final String states_id){
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
                                cities   [i]  = getString(R.string.str_select_city);
                                city_id  [i]  = CityId;
                            }else {
                                cities [i]  = City_per;
                                city_id[i]  = CityId;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NonLogin_Activity.this,
                                R.layout.my_spinner_style, cities) {

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

                        spinner_business_city.setAdapter(adapter);

                        spinner_business_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!city_id[position].equals("0")){
                                    single_city_id = city_id[position];
                                    // getData(""+states_id,""+city_id[position],"","","");//calling the api here


                                    if (!single_category_id.equals("")){

                                        getData(""+single_state_id,""+single_city_id,""+single_category_id,"","");//calling the api here
                                    }
                                    if (!single_sub_category_id.equals("")){

                                        getData(""+single_state_id,""+single_city_id,""+single_category_id,""+single_sub_category_id,"");//calling the api here

                                    } else {

                                        getData(""+states_id,""+city_id[position],"","","");//calling the api here

                                    }



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

    String single_category_id = "";

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
                                business_category[i]  = getString(R.string.str_select_cat);
                                business_category_id[i]  = category_id;
                            }else {
                                business_category   [i]  = category_name_per;
                                business_category_id[i]  = category_id;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NonLogin_Activity.this,
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

                                    single_category_id = business_category_id[position];

                                    if (!single_state_id.equals("")){

                                        getData(""+single_state_id,"",""+business_category_id[position],"","");//calling the api here
                                    }
                                    if (!single_city_id.equals("")){

                                        getData(""+single_state_id,""+single_city_id,""+business_category_id[position],"","");//calling the api here

                                    } else {

                                        getData("","",""+business_category_id[position],"","");//calling the api here

                                    }


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

    String single_sub_category_id = "";

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

                        business_sub_category    = new String [business_subcat_listing.length()];
                        business_sub_category_id = new String [business_subcat_listing.length()];

                        for(int i = 0; i < business_subcat_listing.length(); i++) {
                            JSONObject c = business_subcat_listing.getJSONObject(i);
                            String category_id = c.getString("category_id");
                            String category_name = c.getString("category_name");
                            String category_name_per = c.getString("category_name_per");



                            if (i==0){
                                business_sub_category[i]  = getString(R.string.str_select_sub_cat);
                                business_sub_category_id[i]  = category_id;
                            }else {
                                business_sub_category   [i]  = category_name_per;
                                business_sub_category_id[i]  = category_id;
                            }
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(NonLogin_Activity.this,
                                R.layout.my_spinner_style, business_sub_category) {

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

                        spinner_business_sub_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (!business_sub_category_id[position].equals("0")){

                                    single_sub_category_id = business_sub_category_id[position];

                                    if (!single_state_id.equals("")){

                                        getData(""+single_state_id,"",""+id,""+business_sub_category_id[position],"");//calling the api here

                                    }
                                    if (!single_city_id.equals("")){

                                        getData(""+single_state_id,""+single_city_id,""+id,""+business_sub_category_id[position],"");//calling the api here

                                    }
                                    else {
                                        getData("","",""+id,""+business_sub_category_id[position],"");//calling the api here

                                    }
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

    public void getData(String states_id, String city_id , String category_id, String sub_category_id,String keyword){
        String params = "";

        Boolean check = false;

        if (states_id!=null && !states_id.equals("")){
            if (check){
                params += "&state="+states_id;
            }else {
                params += "state="+states_id;
            }

            check=true;
        }
        if (city_id!=null && !city_id.equals("")){
            if (check){
                params += "&city="+city_id;
            }else {
                params += "city="+city_id;
            }
            check=true;
        }
        if (category_id!=null && !category_id.equals("")){
            if (check){
                params += "&category="+category_id;
            }else {
                params += "category="+category_id;
            }
            check=true;
        }
        if (sub_category_id!=null && !sub_category_id.equals("")){
            if (check){
                params += "&sub_cat="+sub_category_id;
            }else {
                params += "sub_cat="+sub_category_id;
            }
            check=true;
        }


        if (keyword!=null && !keyword.equals("")){
            if (check){
                params += "&keyword="+keyword;
            }else {
                params += "keyword="+keyword;
            }
            check=true;
        }



        //http://192.168.100.14/monshiapp/app/business_listing.php
        String url = getResources().getString(R.string.url)+"business_listing.php";


        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) throws JSONException {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getString("status").equals("200")) {


                    JSONArray business_listing = jsonObject.getJSONArray("business_listing");

                    if (business_listing.length()<1) {
                        bookNow_lists.clear();
                        tv_no_business.setVisibility(View.VISIBLE);
                    }else {
                        bookNow_lists.clear();
                        tv_no_business.setVisibility(View.GONE);

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
                            String latitude = c.getString("latitude");
                            String longitude = c.getString("longitude");
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
                            String fav_id = c.getString("fav_id");
                            String isfav = c.getString("isfav");

                            BookNow_List obj = new BookNow_List
                                    (""+id,""+userID,""+name,""+state,""+state_name,""+state_name_per,
                                            ""+city, ""+city_name,""+city_name_per,""+description,""+contact,
                                            ""+address,""+latitude,""+longitude,""+business_type,""+category, ""+category_name_per,
                                            ""+category_name, ""+rating,""+sub_cat,""+key_staff,""+key_services,
                                            ""+business_id, ""+image,""+isfav,""+fav_id);

                            bookNow_lists.add(obj);

                        }
                        bookNow_list_adapter = new BookNow_List_Adapter(NonLogin_Activity.this,bookNow_lists);

                        listView.setAdapter(bookNow_list_adapter);
                    }




                } else if (jsonObject.getString("status").equals("300")) {
                    String status_alert = jsonObject.getString("status_alert");
                    Toast.makeText(NonLogin_Activity.this, "" + status_alert, Toast.LENGTH_SHORT).show();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);

    }
}
