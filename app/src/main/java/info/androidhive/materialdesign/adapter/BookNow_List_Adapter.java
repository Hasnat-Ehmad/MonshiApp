package info.androidhive.materialdesign.adapter;

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
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class_scroll;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_scroll;
import info.androidhive.materialdesign.fragments.CreateBusinessAccountFragment;
import info.androidhive.materialdesign.fragments.EditBusinessFragment;
import info.androidhive.materialdesign.lists.BookNow_List;
import info.androidhive.materialdesign.lists.Business_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 2/16/2018.
 */

public class BookNow_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<BookNow_List> bookNowLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;
    SharedPreferences sharedPreferences;

    private String id,online_status;

    public static ArrayList<Boolean> positionArray;

    public BookNow_List_Adapter(Activity context, List<BookNow_List> bookNow_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.bookNowLists = bookNow_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        positionArray = new ArrayList<Boolean>(bookNow_lists.size());
        for (int i = 0; i < bookNow_lists.size(); i++) {
            positionArray.add(false);
        }
    }

    private class ViewHolder {

    TextView  tv_business_name,tv_business_description;
    RatingBar ratingBar;
    ImageView img_business_profile;
    TextView  tv_business_category;
    TextView  tv_business_city;
    ToggleButton toggle_favorite;
    ConstraintLayout layout_detail;

    }

    @Override
    public int getCount() {
        return bookNowLists.size();
    }

    @Override
    public BookNow_List getItem(int position) {
        return bookNowLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

      final  ViewHolder holder;
      final  BookNow_List item = getItem(position);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_booknow, null, false);


            holder.tv_business_name = (TextView) convertView.findViewById(R.id.tv_business_name);
            holder.tv_business_description = (TextView) convertView.findViewById(R.id.tv_business_description);
            holder.tv_business_category = (TextView) convertView.findViewById(R.id.tv_business_category);
            holder.tv_business_city = (TextView) convertView.findViewById(R.id.tv_business_city);

            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            holder.img_business_profile = (ImageView) convertView.findViewById(R.id.img_business_profile);
            holder.toggle_favorite = (ToggleButton) convertView.findViewById(R.id.toggle_favorite);

            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }

        //Toast.makeText(mContext,item.getIsfav(),Toast.LENGTH_SHORT).show();

        if(item.getIsfav().equals("no")){
             holder.toggle_favorite.setChecked(false);
            //holder.img_fav.setBackgroundResource(R.drawable.heart_icon);
        }else{
            holder.toggle_favorite.setChecked(true);
            //holder.img_fav.setBackgroundResource(R.drawable.heart_icon_selected);
            //holder.img_fav.setImageResource(R.drawable.heart_icon_selected);
        }

       fragmentActivity = (FragmentActivity) mContext;
        holder.layout_detail = (ConstraintLayout) convertView.findViewById(R.id.layout_detail);
        holder.layout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.getBusiness_type().equals("class")){
                    //Toast.makeText(mContext,"Class page under progress",Toast.LENGTH_LONG).show();


                    SavePreferences("selected_business"         ,item.getId());
                    SavePreferences("selected_name"             ,item.getName());
                    SavePreferences("selected_contact"          ,item.getContact());
                    SavePreferences("selected_address"          ,item.getAddress());
                    SavePreferences("selected_category_name_per",item.getCategory_name_per());
                    SavePreferences("selected_description"      ,item.getDescription());
                    SavePreferences("selected_image"            ,item.getImage());
                    SavePreferences("selected_lat"              ,item.getLatitude());
                    SavePreferences("selected_lng"              ,item.getLongitude());

                    item.getBusiness_type();

                    Intent intent = new Intent(mContext, BusinessPublicDetail_Activity_Class_scroll.class);
                    mContext.startActivity(intent);

                }else {

                    SavePreferences("selected_business"         ,item.getId());
                    SavePreferences("selected_name"             ,item.getName());
                    SavePreferences("selected_contact"          ,item.getContact());
                    SavePreferences("selected_address"          ,item.getAddress());
                    SavePreferences("selected_category_name_per",item.getCategory_name_per());
                    SavePreferences("selected_description"      ,item.getDescription());
                    SavePreferences("selected_image"            ,item.getImage());
                    SavePreferences("selected_lat"              ,item.getLatitude());
                    SavePreferences("selected_lng"              ,item.getLongitude());

                    Intent intent = new Intent(mContext, BusinessPublicDetail_Activity_scroll.class);
                    mContext.startActivity(intent);



                }


                /*BusinessDetailFragment tab1= new BusinessDetailFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();*/

            }
        });


        holder.toggle_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://192.168.100.14/monshiapp/app/add_fav.php
                String url    =  mContext.getResources().getString(R.string.url)+"add_fav.php";

                String params = "";
                if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                    //spinner_business_list.setVisibility(View.GONE);
                    params =  "user_id="+sharedPreferences.getString("user_id", "")+
                            "&fav_id="+item.getId()+
                            "&type=business"
                    ;
                }else {
                    //spinner_business_list.setVisibility(View.VISIBLE);
                    params =  "user_id="+sharedPreferences.getString("user_id", "")+
                            "&fav_id="+item.getId()+
                            "&type=business"
                    ;
                }


                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                    @Override
                    public void TaskCompletionResult(String result) {

                        try {

                            JSONObject jsonObject = new JSONObject(result);
                            if(jsonObject.getString("status").equals("200")) {

                                if (jsonObject.getString("fav_check").equals("true")){

                                    bookNowLists.get(position).setIsfav("yes");


                                }else {
                                    bookNowLists.get(position).setIsfav("no");
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
        });

        holder.tv_business_name.setText(""+item.getName());
        holder.tv_business_description.setText(""+item.getDescription());
        holder.ratingBar.setRating(Float.parseFloat(""+item.getRating()));
        holder.tv_business_category.setText(""+item.getCategory_name_per());
        holder.tv_business_city.setText(""+item.getCity_name_per()+" , "+item.getState_name_per());

        Picasso.with(holder.img_business_profile.getContext()).load(""+item.getImage()).into(holder.img_business_profile);
        holder.ratingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // TODO perform your action here
                }
                return true;
            }
        });

        return convertView;
    }
    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


}
/* @SuppressLint("SetTextI18n")
    public void alertDialogShow_favrt_dialog(final Context context, final int position, boolean ischeked, final BookNow_List item, final ViewHolder holder) {



        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_prompt_favort_option,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setView(promptsView);


        final AlertDialog d = alertDialogBuilder.show();
        d.getWindow().setBackgroundDrawableResource(android.R.color.white);

        final TextView txt_dia = promptsView.findViewById(R.id.txt_dia);

        int frvt_api_call=0;


        Button btn_yes = promptsView.findViewById(R.id.btn_yes);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        Button btn_no = promptsView.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                d.dismiss();

            }
        });

    }*/