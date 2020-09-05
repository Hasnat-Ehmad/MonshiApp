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
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class_scroll;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_scroll;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.fragments.CreateBusinessAccountFragment;
import info.androidhive.materialdesign.fragments.EditBusinessFragment;
import info.androidhive.materialdesign.lists.Business_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.pagerfragmentcontiners.BusinessDetailFragment;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 2/16/2018.
 */

public class Business_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Business_List> businessLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;
    SharedPreferences sharedPreferences;

    private String id,online_status;

    public Business_List_Adapter(Activity context, List<Business_List> business_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.businessLists = business_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        LinearLayout layout_detail;
        CircleImageView circleImageView;
        TextView tv_bus_title,tv_city,tv_bus_phone,tv_address;
    }

    @Override
    public int getCount() {
        return businessLists.size();
    }

    @Override
    public Business_List getItem(int position) {
        return businessLists.get(position);
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
        final Business_List item = getItem(position);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_businesslist, parent, false);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;
        holder.layout_detail = (LinearLayout) convertView.findViewById(R.id.layout_detail);
        holder.layout_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShow_bus_detail(mContext,position,item);
            }
        });

        holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.circleImageView);
        Picasso.with(holder.circleImageView.getContext()).load(""+item.getImage()).into(holder.circleImageView);

        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*BusinessDetailFragment tab1= new BusinessDetailFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_body, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();*/
                SavePreferences("selected_business"         ,item.getId());
                SavePreferences("selected_name"             ,item.getName());
                SavePreferences("selected_contact"          ,item.getContact());
                SavePreferences("selected_address"          ,item.getAddress());
                SavePreferences("selected_category_name_per",item.getCategory_name_per());
                SavePreferences("selected_description"      ,item.getDescription());
                SavePreferences("selected_image"            ,item.getImage());
                SavePreferences("selected_lat"              ,item.getLatitude());
                SavePreferences("selected_lng"              ,item.getLongitude());
//                EditBusinessFragment tab1= new EditBusinessFragment();
//                fragmentActivity.getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.frame_layout, tab1)
//                        .addToBackStack(null)   // this will be it to the back stack
//                        .commit();
                alertDialogShow_bus_detail(mContext,position,item);
            }
        });


        holder.tv_bus_title = (TextView)convertView.findViewById(R.id.tv_bus_title);
        holder.tv_bus_title.setText(""+item.getName());
        holder.tv_bus_phone = (TextView)convertView.findViewById(R.id.tv_bus_phone);
        holder.tv_bus_phone.setText(""+item.getContact());
        /*holder.tv_city = (TextView)convertView.findViewById(R.id.tv_city);
          holder.tv_city.setText(""+item.getCity());
          holder.tv_address = (TextView)convertView.findViewById(R.id.tv_address);
          holder.tv_address.setText(""+item.getAddress());*/
        return convertView;
    }
    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void alertDialogShow_bus_detail(final Context context, final int position, final Business_List item) {



        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_prompt_business_option,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setView(promptsView);


        final AlertDialog d = alertDialogBuilder.show();
        d.getWindow().setBackgroundDrawableResource(android.R.color.white);



        Button p_btn_bus_del = promptsView.findViewById(R.id.p_btn_bus_del);
        p_btn_bus_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView textView_title = new TextView(mContext);
                textView_title.setText(mContext.getResources().getString(R.string.str_business_delete));
                textView_title.setGravity(Gravity.START);
                textView_title.setPadding(20,10,20,10);
                textView_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                textView_title.setCompoundDrawablePadding(10);
                //textView_title.setCompoundDrawables(null,null,mContext.getResources().getDrawable(R.drawable.ic_warning_colored_24dp),null);
                textView_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning_colored_24dp, 0);

                final android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(mContext);
                alert.setCustomTitle(textView_title);
                //alert.setTitle(mContext.getResources().getString(R.string.str_staff_delete));
                alert.setMessage(mContext.getResources().getString(R.string.confirm_business_delete));



                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                alert.setPositiveButton(mContext.getResources().getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        //Delete staff API
                        //http://192.168.100.14/monshiapp/app/delete_staff.php?staff_id=
                        String url    =  mContext.getResources().getString(R.string.url)+"delete_business.php";

                        String params = "bus_id="+item.getId();
                        //Toast.makeText(mContext,"Delete process under progress", Toast.LENGTH_SHORT).show();

                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {

                                        Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();

                                        businessLists.remove(position);
                                        notifyDataSetChanged();
                                        d.dismiss();

                                    }
                                    else{
                                        d.dismiss();
                                        Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        webRequestCall.execute(url, "POST", params);
                        //d.dismiss();
                    }
                });

                // A null listener allows the button to dismiss the dialog and take no further action.
                alert.setNegativeButton(context.getResources().getString(R.string.str_no), null);
                //alert.setIcon(android.R.drawable.ic_dialog_alert);
                //alert.setView(view);
                alert.show();


            }
        });



        Button p_btn_edit_bus = promptsView.findViewById(R.id.p_btn_edit_bus);
        p_btn_edit_bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                bundle.putString("business_id",""+item.getId());
                bundle.putString("latitude",item.getLatitude());
                bundle.putString("longitude",item.getLongitude());
                bundle.putString("state",item.getState_name_per());
                bundle.putString("city",item.getCity_name_per());

                CreateBusinessAccountFragment tab1= new CreateBusinessAccountFragment();
                tab1.setArguments(bundle);

                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();

                SavePreferences("selected_business"         ,item.getId());
                SavePreferences("selected_name"             ,item.getName());
                SavePreferences("selected_contact"          ,item.getContact());
                SavePreferences("selected_address"          ,item.getAddress());
                SavePreferences("selected_category_name_per",item.getCategory_name_per());
                SavePreferences("selected_description"      ,item.getDescription());
                SavePreferences("selected_image"            ,item.getImage());
                SavePreferences("selected_business_type"    ,item.getBusiness_type());

                d.dismiss();

            }
        });



        Button p_btn_bus_detail = promptsView.findViewById(R.id.p_btn_bus_detail);
        p_btn_bus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditBusinessFragment tab1= new EditBusinessFragment();

                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();

                SavePreferences("selected_business"         ,item.getId());
                SavePreferences("selected_name"             ,item.getName());
                SavePreferences("selected_contact"          ,item.getContact());
                SavePreferences("selected_address"          ,item.getAddress());
                SavePreferences("selected_category_name_per",item.getCategory_name_per());
                SavePreferences("selected_description"      ,item.getDescription());
                SavePreferences("selected_image"            ,item.getImage());
                SavePreferences("selected_business_type"    ,item.getBusiness_type());

                d.dismiss();

            }
        });


        Button p_btn_book = promptsView.findViewById(R.id.p_btn_book);
        p_btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //d.dismiss();
                SavePreferences("selected_business"         ,item.getId());
                SavePreferences("selected_name"             ,item.getName());
                SavePreferences("selected_contact"          ,item.getContact());
                SavePreferences("selected_address"          ,item.getAddress());
                SavePreferences("selected_category_name_per",item.getCategory_name_per());
                SavePreferences("selected_description"      ,item.getDescription());
                SavePreferences("selected_image"            ,item.getImage());
                SavePreferences("selected_business_type"    ,item.getBusiness_type());
                if(item.getBusiness_type().equals("service")){

                    Intent intent = new Intent(mContext, BusinessPublicDetail_Activity_scroll.class);
                    mContext.startActivity(intent);
                }else{

                    Intent intent = new Intent(mContext, BusinessPublicDetail_Activity_Class_scroll.class);
                    mContext.startActivity(intent);
                }


            }
        });

        Button p_btn_close = promptsView.findViewById(R.id.p_btn_close);
        p_btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

    }
}
