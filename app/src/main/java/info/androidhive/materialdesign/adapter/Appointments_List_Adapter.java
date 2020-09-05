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
import android.text.Html;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class_scroll;
import info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_scroll;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.fragments.CreateBusinessAccountFragment;
import info.androidhive.materialdesign.fragments.EditBusinessFragment;
import info.androidhive.materialdesign.fragments.UpdateAppoimentFragment;
import info.androidhive.materialdesign.lists.Appointments_List;
import info.androidhive.materialdesign.lists.Business_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 2/16/2018.
 */

public class Appointments_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Appointments_List> appointmentsLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    SharedPreferences sharedPreferences;

    public Appointments_List_Adapter(Activity context, List<Appointments_List> appointments_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.appointmentsLists = appointments_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
    TextView tv_title,tv_time_start,tv_end_time,tv_staff,tv_customer,tv_business,tv_service;

        CircleImageView img_business_profile;
        LinearLayout layout_main;
    }

    @Override
    public int getCount() {
        return appointmentsLists.size();
    }

    @Override
    public Appointments_List getItem(int position) {
        return appointmentsLists.get(position);
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
        final Appointments_List item = getItem(position);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_appointments, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;

        holder.layout_main = convertView.findViewById(R.id.layout_main);
        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialogShow_bus_detail(mContext,position,item);
            }
        });

//        holder.tv_title = convertView.findViewById(R.id.tv_title);
//        holder.tv_title.setText(""+item.getTitle());

        holder.tv_business = convertView.findViewById(R.id.tv_business);
        holder.tv_business.setText(""+item.getBusiness());

        holder.tv_service = convertView.findViewById(R.id.tv_service);
        holder.tv_service.setText(""+item.getService());

        holder.tv_staff = convertView.findViewById(R.id.tv_staff);
        holder.tv_staff.setText(""+item.getStaff());

        holder.tv_customer = convertView.findViewById(R.id.tv_customer);
        holder.tv_customer.setText(""+item.getCustomer());

        holder.tv_time_start = convertView.findViewById(R.id.tv_time_start);
        holder.tv_time_start.setText(""+item.geteStart());

//        holder.tv_end_time = convertView.findViewById(R.id.tv_end_time);
//        holder.tv_end_time.setText(""+item.geteEnd());

        holder.img_business_profile = (CircleImageView) convertView.findViewById(R.id.img_business_profile);
        Picasso.with(holder.img_business_profile.getContext()).load(""+item.getBusiness_image()).into(holder.img_business_profile);

        return convertView;
    }

    public void alertDialogShow_bus_detail(final Context context, final int position, final Appointments_List item) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_prompt_appointment_option,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setView(promptsView);


        final AlertDialog d = alertDialogBuilder.show();
        d.getWindow().setBackgroundDrawableResource(android.R.color.white);

        Button p_btn_edit = promptsView.findViewById(R.id.p_btn_edit);
        p_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                bundle.putString("id"   ,item.getId());
                bundle.putString("start",item.getStart());
                bundle.putString("end"  ,item.geteEnd());
                bundle.putString("business_id",item.getBusiness_id());
                bundle.putString("service_id" ,item.getService_id());
                bundle.putString("service_time",item.getService_time());
                bundle.putString("staff_id"   ,item.getStaff_id());
                bundle.putString("user_id"    ,item.getUser_id());
                bundle.putString("customer_id",item.getCustomer_id());

                UpdateAppoimentFragment tab1= new UpdateAppoimentFragment();
                tab1.setArguments(bundle);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();

                d.dismiss();
            }
        });

        Button p_btn_del = promptsView.findViewById(R.id.p_btn_del);
        p_btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView textView_title = new TextView(mContext);
                textView_title.setText(mContext.getResources().getString(R.string.str_appointment_delete));
                textView_title.setGravity(Gravity.START);
                textView_title.setPadding(20,10,20,10);
                textView_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                textView_title.setCompoundDrawablePadding(10);
                //textView_title.setCompoundDrawables(null,null,mContext.getResources().getDrawable(R.drawable.ic_warning_colored_24dp),null);
                textView_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning_colored_24dp, 0);

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setCustomTitle(textView_title);
                //alert.setTitle(mContext.getResources().getString(R.string.str_staff_delete));
                alert.setMessage(mContext.getResources().getString(R.string.confirm_appointment_delete));



                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                alert.setPositiveButton(mContext.getResources().getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation

                        //Delete staff API
                        //http://192.168.100.14/monshiapp/app/delete_staff.php?staff_id=
                        String url    =  mContext.getResources().getString(R.string.url)+"delete_appointment.php";

                       String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                "&business_id="+item.getBusiness_id()+
                                "&id="+item.getId()+
                                "&service_id="+item.getService_id()+
                                "&staff_id="+item.getStaff_id()+
                                "&customer_id="+item.getCustomer_id()+
                                "&role="+sharedPreferences.getString("user_role", "")
                                ;

                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {

                                        Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();

                                        appointmentsLists.remove(position);
                                        notifyDataSetChanged();
                                        d.dismiss();

                                    }
                                    else{
                                        Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        webRequestCall.execute(url, "POST", params);

                    }
                });

                // A null listener allows the button to dismiss the dialog and take no further action.
                alert.setNegativeButton(context.getResources().getString(R.string.str_no), null);
                //alert.setIcon(android.R.drawable.ic_dialog_alert);
                //alert.setView(view);
                alert.show();

                /*alert.setOnShowListener(new DialogInterface.OnShowListener() {
                    @TargetApi(Build.VERSION_CODES.KITKAT)
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onShow(DialogInterface dlg) {
                        Objects.requireNonNull(alert.getWindow()).getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL); // set title and message direction to RTL
                    }
                });*/

                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(alert.getWindow()).getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                    }
                }*/
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