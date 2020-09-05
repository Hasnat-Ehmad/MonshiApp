package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.AddNewStaffActivity;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.fragments.AddNewServiceFragment;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.pagerfragmentcontiners.AddNewStaffFragment;
import info.androidhive.materialdesign.pagerfragments.AddStaffFragment;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by hp on 2/16/2018.
 */

public class MangementStaff_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<ManagmentStaff_List> managmentStaffLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;
    SharedPreferences sharedPreferences;

    public MangementStaff_List_Adapter(Activity context, List<ManagmentStaff_List> managmentStaff_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.managmentStaffLists = managmentStaff_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        TextView tv_username,tv_email,tv_number;
        ImageView img_delet;
        CircleImageView img_business_profile;
        ConstraintLayout layout_staff_list;
    }

    @Override
    public int getCount() {
        return managmentStaffLists.size();
    }

    @Override
    public ManagmentStaff_List getItem(int position) {
        return managmentStaffLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        fragmentActivity = (FragmentActivity) mContext;

        final MangementStaff_List_Adapter.ViewHolder holder;
        final ManagmentStaff_List item = getItem(position);

        if (convertView == null) {

            holder = new MangementStaff_List_Adapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_managmentstaff, parent, false);

            convertView.setTag(holder);
        }

        else{
            holder = (MangementStaff_List_Adapter.ViewHolder) convertView.getTag();
        }

        holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
        holder.tv_email    = (TextView) convertView.findViewById(R.id.tv_email);
        holder.tv_number   = (TextView) convertView.findViewById(R.id.tv_number);
        holder.img_business_profile = (CircleImageView) convertView.findViewById(R.id.img_business_profile);
        /*holder.img_delet   = (ImageView) convertView.findViewById(R.id.img_delet);



        if (sharedPreferences.getString("user_role" ,""  ).equals("1")||sharedPreferences.getString("user_role" ,""  ).equals("2")){
            //holder.img_delet.setVisibility(View.VISIBLE);
            holder.img_delet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    new AlertDialog.Builder(mContext)
                            .setTitle("Delete entry")
                            .setMessage("Are you sure you want to delete this entry?")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation



                                    //Delete staff API
                                    //http://192.168.100.14/monshiapp/app/delete_staff.php?staff_id=
                                    String url    =  mContext.getResources().getString(R.string.url)+"delete_staff.php";

                                    String params = "staff_id="+item.getId();

                                    WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                        @Override
                                        public void TaskCompletionResult(String result) {

                                            try {

                                                JSONObject jsonObject = new JSONObject(result);
                                                if(jsonObject.getString("status").equals("200")) {

                                                    Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();

                                                    managmentStaffLists.remove(position);
                                                    notifyDataSetChanged();
                                                    mContext.getFragmentManager().popBackStack();

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
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }
            });

        }else {
            holder.img_delet.setVisibility(View.GONE);
        }*/

        Picasso.with(holder.img_business_profile.getContext()).load(""+item.getImage()).into(holder.img_business_profile);

        holder.tv_username.setText(""+item.getUsername());

        holder.tv_email.setText(""+item.getEmail());
        holder.tv_number.setText(""+item.getMobile());

        holder.layout_staff_list = convertView.findViewById(R.id.layout_staff_list);
        holder.layout_staff_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* AddNewStaffFragment tab1 = new AddNewStaffFragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();*/

               //Toast.makeText(mContext,mContext.getClass().getSimpleName(),Toast.LENGTH_SHORT).show();
                if(mContext.getClass().getSimpleName().equals("MainActivity_bottom_view")){

                    alertDialogShow_staff_detail(mContext,position,item);
                }else{


                }
                /*Intent intent = new Intent(mContext, AddNewStaffActivity.class);


                SavePreferences("show_staff_detail" , "yes");
                SavePreferences("staff_id" , ""+item.getId() );

                mContext.startActivity(intent);*/

            }
        });

        /*final View finalConvertView = convertView;
        convertView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                finalConvertView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                finalConvertView.getHeight(); //height is ready
                //Toast.makeText(mContext,finalConvertView.getHeight()+"",Toast.LENGTH_SHORT).show();
            }
        });*/


        return convertView;
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void alertDialogShow_staff_detail(final Context context, final int position, final ManagmentStaff_List item) {



        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_prompt_crud_option,
                null);

        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context, android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setView(promptsView);


        final android.app.AlertDialog d = alertDialogBuilder.show();
        d.getWindow().setBackgroundDrawableResource(android.R.color.white);


        final TextView textView_label = promptsView.findViewById(R.id.tv_label_popup);
        textView_label.setText(mContext.getResources().getString(R.string.str_staff_action));


        Button p_btn_del = promptsView.findViewById(R.id.p_btn_del);
        p_btn_del.setText(mContext.getResources().getString(R.string.str_staff_delete));
        p_btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView textView_title = new TextView(mContext);
                textView_title.setText(mContext.getResources().getString(R.string.str_staff_delete));
                textView_title.setGravity(Gravity.START);
                textView_title.setPadding(20,10,20,10);
                textView_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                textView_title.setCompoundDrawablePadding(10);
                //textView_title.setCompoundDrawables(null,null,mContext.getResources().getDrawable(R.drawable.ic_warning_colored_24dp),null);
                textView_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning_colored_24dp, 0);

                final AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                        alert.setCustomTitle(textView_title);
                        //alert.setTitle(mContext.getResources().getString(R.string.str_staff_delete));
                        alert.setMessage(mContext.getResources().getString(R.string.confirm_staff_delete));



                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                alert.setPositiveButton(mContext.getResources().getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation

                                //Delete staff API
                                //http://192.168.100.14/monshiapp/app/delete_staff.php?staff_id=
                                String url    =  mContext.getResources().getString(R.string.url)+"delete_staff.php";

                                String params = "staff_id="+item.getId();

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {

                                                Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();

                                                managmentStaffLists.remove(position);
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



        Button p_btn_edit = promptsView.findViewById(R.id.p_btn_edit);
        p_btn_edit.setText(mContext.getResources().getString(R.string.str_edit_staff));
        p_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                SavePreferences("show_staff_detail" , "yes");
                SavePreferences("staff_id" , ""+item.getId() );

                AddNewStaffFragment tab1= new AddNewStaffFragment();

                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will add this in to the back stack
                        .commit();

//                Intent intent = new Intent(mContext, AddNewStaffActivity.class);
//                mContext.startActivity(intent);

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
