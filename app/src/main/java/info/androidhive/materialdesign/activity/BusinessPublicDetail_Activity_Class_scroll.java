package info.androidhive.materialdesign.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import info.androidhive.materialdesign.PagerFragmentAdapter.PagerAdapterBusinessList_class;
import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessClassListFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessInfoFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessServiceListFragment;
import info.androidhive.materialdesign.pagerfragments.BusinessStaffListFragment;
import info.androidhive.materialdesign.staff_work.StaffScreensActivity;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class BusinessPublicDetail_Activity_Class_scroll extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {

    SharedPreferences sharedPreferences;
    public static int fragment_no;
    TextView tv_number,tv_category,tv_address,tv_description,tv_label_dashboard;

    ImageView img_profile_pic,img_back;

    private Toolbar mToolbar;

    public static ScrollView scrollView;
    public static String business_id_static = "";

    Button btn_class_list_fragment,btn_staff_list_fragment,btn_location_fragment;

    SliderLayout sliderLayout;

    HashMap<String, String> HashMapForURL;

    String gallery_images="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_public_detail_class_scroll);

        scrollView = findViewById(R.id.scrollView);



        String title = getString(R.string.title_messages);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        img_back = mToolbar.findViewById(R.id.img_back);
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

//        img_profile_pic = findViewById(R.id.img_profile_pic);
//        Picasso.with(img_profile_pic.getContext()).load(""+sharedPreferences.getString("selected_image", "")).into(img_profile_pic);

        business_id_static = sharedPreferences.getString("selected_business", "");
        //Toast.makeText(getApplicationContext(),sharedPreferences.getString("selected_business", ""),Toast.LENGTH_SHORT).show();
        /*TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);

        tabLayout.addTab(tabLayout.newTab().setText(R.string.map));//class list fragment
        tabLayout.addTab(tabLayout.newTab().setText(R.string.str_teacher_list));//staff list fragment
        tabLayout.addTab(tabLayout.newTab().setText(R.string.str_class_list));//Business Info

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapterBusinessList_class adapter = new PagerAdapterBusinessList_class
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
        });*/

       /* btn_class_list_fragment = findViewById(R.id.btn_class_list_fragment);

        btn_class_list_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_class_list_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                btn_staff_list_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_location_fragment  .setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                BusinessClassListFragment addNewSession = new BusinessClassListFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(BusinessPublicDetail_Activity_Class.this).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout_1, addNewSession)
//                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }

            }
        });

        btn_staff_list_fragment = findViewById(R.id.btn_staff_list_fragment);

        btn_staff_list_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_staff_list_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                btn_class_list_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_location_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                BusinessStaffListFragment addNewSession = new BusinessStaffListFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(BusinessPublicDetail_Activity_Class.this).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout_1, addNewSession)
//                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }

            }
        });
        btn_location_fragment   = findViewById(R.id.btn_location_fragment);
        btn_location_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_location_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                btn_class_list_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_staff_list_fragment.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                BusinessInfoFragment addNewSession = new BusinessInfoFragment();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(BusinessPublicDetail_Activity_Class.this).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout_1, addNewSession)
//                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }
            }
        });*/

        final Button btn_class_list = findViewById(R.id.btn_class_list);
        final Button btn_staff_list = findViewById(R.id.btn_staff_list);
        final Button btn_bus_info = findViewById(R.id.btn_bus_info);

        btn_class_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BusinessClassListFragment tab3 = new BusinessClassListFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab3)
                       // .addToBackStack(null)   // this will be it to the back stack
                        .commit();


                btn_class_list.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_staff_list.setBackgroundColor(getResources().getColor(R.color.grey_light));
                btn_bus_info.setBackgroundColor(getResources().getColor(R.color.grey_light));

            }
        });

        btn_staff_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BusinessStaffListFragment tab1 = new BusinessStaffListFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        //.addToBackStack(null)   // this will be it to the back stack
                        .commit();

                btn_staff_list.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_class_list.setBackgroundColor(getResources().getColor(R.color.grey_light));
                btn_bus_info.setBackgroundColor(getResources().getColor(R.color.grey_light));
            }
        });

        btn_bus_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BusinessInfoFragment tab0 = new BusinessInfoFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab0)
                        //.addToBackStack(null)   // this will be it to the back stack
                        .commit();
                btn_bus_info.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                btn_class_list.setBackgroundColor(getResources().getColor(R.color.grey_light));
                btn_staff_list.setBackgroundColor(getResources().getColor(R.color.grey_light));
            }
        });


        btn_class_list.performClick();


        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        //http://192.168.100.14/monshiapp/app/get_business_gallery_image.php?user_id=1&bus_id=28
        String url_image    =  getResources().getString(R.string.url)+"get_business_gallery_image.php";

        String params_image = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params_image =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&bus_id="+sharedPreferences.getString("selected_business", "");
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params_image =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&bus_id="+sharedPreferences.getString("selected_business", "");
        }

        WebRequestCall webRequestCall_image = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray business_image_list = jsonObject.getJSONArray("business_image_list");

                        TextSliderView first_image = new TextSliderView(BusinessPublicDetail_Activity_Class_scroll.this);

                        first_image
                                .description("")
                                .image(sharedPreferences.getString("selected_image", ""))
                                .setScaleType(BaseSliderView.ScaleType.Fit)
                                .setOnSliderClickListener(BusinessPublicDetail_Activity_Class_scroll.this);

                        first_image.bundle(new Bundle());

                        first_image.getBundle()
                                .putString("extra","duumy");

                        sliderLayout.addSlider(first_image);


                        for(int i = 0; i < business_image_list.length(); i++) {
                            JSONObject c = business_image_list.getJSONObject(i);

                            String id = c.getString("id");
                            String image= c.getString("image");

                            TextSliderView textSliderView = new TextSliderView(BusinessPublicDetail_Activity_Class_scroll.this);

                            textSliderView
                                    .description("")
                                    .image(image)
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                                    .setOnSliderClickListener(BusinessPublicDetail_Activity_Class_scroll.this);

                            textSliderView.bundle(new Bundle());

                            textSliderView.getBundle()
                                    .putString("extra","duumy");

                            sliderLayout.addSlider(textSliderView);

                        }

                        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

                        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

                        sliderLayout.setCustomAnimation(new DescriptionAnimation());

                        sliderLayout.setDuration(3000);

                        sliderLayout.addOnPageChangeListener(BusinessPublicDetail_Activity_Class_scroll.this);


                    }
                    else{
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall_image.execute(url_image, "POST", params_image);


      /*  int[] textureArrayWin = {
                R.drawable.pic1,
                R.drawable.pic2,
                R.drawable.pic3,
        };

        sliderLayout = (SliderLayout)findViewById(R.id.slider);

        if(textureArrayWin==null){
            //Toast.makeText(getApplicationContext(), "No image", Toast.LENGTH_SHORT).show();
            sliderLayout.setVisibility(View.GONE);
        }else {


            String[] items = gallery_images.split(",");

            for (String item : items) {

                // System.out.println("item = " + item);
                //Toast.makeText(getApplicationContext(),""+item,Toast.LENGTH_SHORT).show();


            }

            AddImagesUrlOnline(gallery_images);
            for(int i=0;i<textureArrayWin.length;i++){

                TextSliderView textSliderView = new TextSliderView(BusinessPublicDetail_Activity_Class_scroll.this);

                textSliderView
                        .description("")
                        .image(textureArrayWin[i])
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(BusinessPublicDetail_Activity_Class_scroll.this);

                textSliderView.bundle(new Bundle());

                textSliderView.getBundle()
                        .putString("extra","hassan");

                sliderLayout.addSlider(textSliderView);
            }
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

            sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

            sliderLayout.setCustomAnimation(new DescriptionAnimation());

            sliderLayout.setDuration(3000);

            sliderLayout.addOnPageChangeListener(BusinessPublicDetail_Activity_Class_scroll.this);
        }*/


    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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
                alertDialogShow_login(BusinessPublicDetail_Activity_Class_scroll.this);
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
                                    Intent intent = new Intent(BusinessPublicDetail_Activity_Class_scroll.this,MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                                    //staff bookappointment activity here

                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(BusinessPublicDetail_Activity_Class_scroll.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();

                                } else if (sharedPreferences.getString("user_role" ,""  ).equals("3")){
                                    //staff bookappointment activity here

                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id" ));
                                    SavePreferences("staff_name"   ,jsonObject.getString("staff_name" ));
                                    SavePreferences("business_id"  ,jsonObject.getString("business_id" ));
                                    SavePreferences("business_name",jsonObject.getString("business_name" ));

                                    Intent intent = new Intent(BusinessPublicDetail_Activity_Class_scroll.this, StaffScreensActivity.class);
                                    startActivity(intent);
                                     finish();

                                } else if (jsonObject.getString("user_role"   ).equals("4")){
                                    //Customer bookappointment activity here
                                    Intent intent = new Intent(BusinessPublicDetail_Activity_Class_scroll.this, CustomerBookingActivity.class);
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void AddImagesUrlOnline(final String image){

        HashMapForURL = new HashMap<String, String>();
        String[] items = image.split(",");

        for (String item : items) {

            //System.out.println("item = " + item);
            //Toast.makeText(getApplicationContext(),"ddd-"+item,Toast.LENGTH_SHORT).show();
            HashMapForURL.put(item, item);


        }

        //       HashMapForURL.put("1", "http://200.200.200.7/bus_transportation/media/bus_images/89791326_1565311470.jpg");
        //      HashMapForURL.put("2", "http://200.200.200.7/bus_transportation/media/bus_images/123449236_2024334850.jpg");
        //    HashMapForURL.put("3", "http://200.200.200.7/bus_transportation/media/bus_images/2077752852_924937946.jpg");
        //  HashMapForURL.put("4", "http://200.200.200.7/bus_transportation/media/bus_images/1111477203_223020415.jpg");
        //HashMapForURL.put("5", "http://200.200.200.7/bus_transportation/media/bus_images/1297821117_1012482141.jpg");

    }
}
