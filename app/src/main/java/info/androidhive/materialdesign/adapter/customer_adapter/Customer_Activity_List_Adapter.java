package info.androidhive.materialdesign.adapter.customer_adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class_scroll;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_scroll;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.fragments.BookNowFragment;
import info.androidhive.materialdesign.fragments.CreateBusinessAccountFragment;
import info.androidhive.materialdesign.fragments.EditBusinessFragment;
import info.androidhive.materialdesign.fragments.customer_work.Customer_ActivitiesFragment;
import info.androidhive.materialdesign.lists.Business_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.lists.customer_list.Customer_Activity_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 2/16/2018.
 */

public class Customer_Activity_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Customer_Activity_List> customerActivityLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<Customer_Activity_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    SharedPreferences sharedPreferences;

    public Customer_Activity_List_Adapter(Activity context, List<Customer_Activity_List> customer_activity_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.customerActivityLists = customer_activity_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {

        TextView tv_date,tv_business,tv_service,tv_staff,tv_description,tv_cost,tv_ranking;

        RatingBar ratingBar;

        LinearLayout layoutranking,layoutrankingbutton,layoutdotss;
    }

    @Override
    public int getCount() {
        return customerActivityLists.size();
    }

    @Override
    public Customer_Activity_List getItem(int position) {
        return customerActivityLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final Customer_Activity_List item = getItem(position);

        fragmentActivity = (FragmentActivity) mContext;

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_customer_activity, parent, false);


            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.layoutranking = convertView.findViewById(R.id.layoutranking);
        holder.layoutranking.setVisibility(View.GONE);

        holder.layoutrankingbutton = convertView.findViewById(R.id.layoutrankingbutton);
        holder.layoutrankingbutton.setVisibility(View.VISIBLE);

        holder.layoutdotss = convertView.findViewById(R.id.layoutdotss);
        holder.layoutdotss.setVisibility(View.GONE);

        holder.tv_date = convertView.findViewById(R.id.tv_date);
        holder.tv_date.setText(""+item.getEnd_event());
        holder.tv_business= convertView.findViewById(R.id.tv_business);
        holder.tv_business.setText(""+item.getBusiness_name());
        holder.tv_service = convertView.findViewById(R.id.tv_service);
        holder.tv_service.setText(""+item.getService_name());
        holder.tv_staff   = convertView.findViewById(R.id.tv_staff);
        holder.tv_staff.setText(""+item.getStaff_name());
        holder.tv_description = convertView.findViewById(R.id.tv_description);
        holder.tv_description.setText(""+item.getDescription());
        holder.tv_cost    = convertView.findViewById(R.id.tv_cost);
        holder.tv_cost.setText(""+item.getAmount());
        holder.tv_ranking = convertView.findViewById(R.id.tv_ranking);

        holder.ratingBar = convertView.findViewById(R.id.ratingBar);

        if (item.getRated().equals("no") && item.getRating().equals("rate now")){

            holder.tv_ranking.setText("رتبه بندی");

            holder.tv_ranking.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    alertDialogShow_ranking(mContext,position,item,holder);
                }
            });

        }else if (item.getRated().equals("no") && item.getRating().equals("not yet")){

            holder.layoutdotss.setVisibility(View.VISIBLE);
            holder.layoutranking.setVisibility(View.GONE);
            holder.layoutrankingbutton.setVisibility(View.GONE);

        }else if (item.getRated().equals("yes")){

            //Toast.makeText(mContext,"rated = "+item.getRated(),Toast.LENGTH_SHORT).show();
            //Toast.makeText(mContext,"rating = "+item.getRating(),Toast.LENGTH_SHORT).show();
            holder.ratingBar.setRating(Float.parseFloat(""+item.getRating()));

            holder.ratingBar.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });

            holder.ratingBar.setFocusable(false);

            holder.layoutrankingbutton.setVisibility(View.GONE);
            holder.layoutranking.setVisibility(View.VISIBLE);

        }

        return convertView;
    }


    public void alertDialogShow_ranking(final Context context, final int position, final Customer_Activity_List item, final ViewHolder holder) {

        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(R.layout.custom_prompt_ranking,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setView(promptsView);

        final AlertDialog d = alertDialogBuilder.show();
        d.getWindow().setBackgroundDrawableResource(android.R.color.white);

        //call the api here

        //fetch_rating_detail.php?user_id=&business_id=&service_id=&staff_id=
        String url    =  mContext.getResources().getString(R.string.url)+"fetch_rating_detail.php";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+item.getBus_id()+
                    "&service_id="+item.getService_id()+
                    "&staff_id="+item.getStaff_id();
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params = "user_id="+sharedPreferences.getString("user_id", "")+
                    "&business_id="+item.getBus_id()+
                    "&service_id="+item.getService_id()+
                    "&staff_id="+item.getStaff_id();
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        String business_name = jsonObject.getString("business_name");
                        String image = jsonObject.getString("image");
                        String name = jsonObject.getString("name");
                        String phone = jsonObject.getString("phone");
                        String expertise = jsonObject.getString("expertise");
                        String email = jsonObject.getString("email");
                        String service_name = jsonObject.getString("service_name");

                        TextView tv_title = promptsView.findViewById(R.id.tv_title);
                        tv_title.setText("امتیاز برای "+business_name);

                        TextView tv_service_name = promptsView.findViewById(R.id.tv_service_name);
                        tv_service_name.setText(""+name);

                        TextView tv_service_phone = promptsView.findViewById(R.id.tv_service_phone);
                        tv_service_phone.setText(""+phone);

                        TextView tv_service_expertise = promptsView.findViewById(R.id.tv_service_expertise);
                        tv_service_expertise.setText(""+expertise);

                        TextView tv_service_email = promptsView.findViewById(R.id.tv_service_email);
                        tv_service_email.setText(""+email);

                        TextView tv_service = promptsView.findViewById(R.id.tv_service);
                        tv_service.setText("سرویس : "+service_name);

                        ImageView imageView = promptsView.findViewById(R.id.imageView);
                        Picasso.with(imageView.getContext()).load(""+image).into(imageView);

                        final RatingBar ratingBar = promptsView.findViewById(R.id.ratingBar);
                        ratingBar.setOnRatingBarChangeListener( new RatingBar.OnRatingBarChangeListener() {
                            @Override
                            public void onRatingChanged( final RatingBar ratingBar, final float rating, final boolean fromUser ) {
                                if ( fromUser ) {
                                    ratingBar.setRating((float) Math.ceil(rating));
                                }
                            }
                        });

                        final EditText ed_feedback = promptsView.findViewById(R.id.ed_feedback);


                        Button btn_submit = promptsView.findViewById(R.id.btn_submit);
                        btn_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


//                        Toast.makeText(mContext,"rating = "+Math.round(ratingBar.getRating()),Toast.LENGTH_SHORT).show();
//
//                        Toast.makeText(mContext,"feedback  = "+ ed_feedback.getText().toString(),Toast.LENGTH_SHORT).show();

                        //Call the api here


                                //http://192.168.100.14/monshiapp/app/rating_submit.php
                                //user_id=&business_id=&service_id=&staff_id=&customer_id=&appointment_id=&stars=&role=&feedback=
                                String url    =  mContext.getResources().getString(R.string.url)+"rating_submit.php";

                                String params = "";
                                if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                                    //spinner_business_list.setVisibility(View.GONE);
                                    params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                            "&role="+sharedPreferences.getString("user_role", "")+
                                            "&business_id="+item.getBus_id()+
                                            "&service_id="+item.getService_id()+
                                            "&staff_id="+item.getStaff_id()+
                                            "&customer_id="+item.getCustomer_id()+
                                            "&appointment_id="+item.getAppt_id()+
                                            "&stars="+Math.round(ratingBar.getRating())+
                                            "&feedback="+ed_feedback.getText().toString();
                                }else {
                                    //spinner_business_list.setVisibility(View.VISIBLE);
                                    params =   "user_id="+sharedPreferences.getString("user_id", "")+
                                            "&role="+sharedPreferences.getString("user_role", "")+
                                            "&business_id="+item.getBus_id()+
                                            "&service_id="+item.getService_id()+
                                            "&staff_id="+item.getStaff_id()+
                                            "&customer_id="+item.getCustomer_id()+
                                            "&appointment_id="+item.getAppt_id()+
                                            "&stars="+Math.round(ratingBar.getRating())+
                                            "&feedback="+ed_feedback.getText().toString();
                                }

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {

                                                d.dismiss();
                                                holder.layoutrankingbutton.setVisibility(View.GONE);
                                                holder.layoutranking.setVisibility(View.VISIBLE);
                                              //  holder.ratingBar.setRating(ratingBar.getRating());


                                                Customer_ActivitiesFragment tab1= new Customer_ActivitiesFragment();
                                                fragmentActivity.getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.frame_layout, tab1)
                                                        .addToBackStack(null)   // this will be it to the back stack
                                                        .commit();

                                            }
                                            else{

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                                webRequestCall.execute(url, "POST", params);


                        //================

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

        //===================

    }
}
