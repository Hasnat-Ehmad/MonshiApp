package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.AddNewServiceFragment;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 2/16/2018.
 */

public class MangementService_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<ManagmentService_List> managmentServiceLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public MangementService_List_Adapter(Activity context, List<ManagmentService_List> managmentService_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.managmentServiceLists = managmentService_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {

        TextView tv_username,tv_description,tv_cost,tv_time;
        ConstraintLayout layout_service_list;

        ImageView img_delet,img_forward;

    }

    @Override
    public int getCount() {
        return managmentServiceLists.size();
    }

    @Override
    public ManagmentService_List getItem(int position) {
        return managmentServiceLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final MangementService_List_Adapter.ViewHolder holder;
        final ManagmentService_List item = getItem(position);

        if (convertView == null) {

            holder = new MangementService_List_Adapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_managmentservice, parent, false);
            convertView.setTag(holder);

        }else{
            holder = (MangementService_List_Adapter.ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;

        /*holder.img_delet = convertView.findViewById(R.id.img_delet);
        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });*/


        holder.layout_service_list = convertView.findViewById(R.id.layout_service_list);
        holder.layout_service_list.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            //Toast.makeText(mContext,mContext.getClass().getSimpleName(),Toast.LENGTH_SHORT).show();
            if(mContext.getClass().getSimpleName().equals("MainActivity_bottom_view")){

                alertDialogShow_service_detail(mContext,position,item);
                /*Bundle bundle = new Bundle();
                bundle.putString("service_id", ""+item.getId());
                AddNewServiceFragment tab1= new AddNewServiceFragment();
                tab1.setArguments(bundle);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();*/

            }else{

            }
        }
    });

        holder.tv_username = (TextView) convertView.findViewById(R.id.tv_username);
        holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
        holder.tv_cost = (TextView) convertView.findViewById(R.id.tv_cost);
        holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

        holder.tv_username.setText(""+item.getServicename());
        holder.tv_description.setText(""+item.getDescription());
        holder.tv_cost.setText(""+item.getAmount());
        holder.tv_time.setText(""+item.getTime());

        return convertView;
    }

    public void alertDialogShow_service_detail(final Context context, final int position, final ManagmentService_List item) {



        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.custom_prompt_crud_option,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setView(promptsView);


        final AlertDialog d = alertDialogBuilder.show();
        d.getWindow().setBackgroundDrawableResource(android.R.color.white);


        TextView textView_label = promptsView.findViewById(R.id.tv_label_popup);
        textView_label.setText(mContext.getResources().getString(R.string.str_service_action));


        Button p_btn_del = promptsView.findViewById(R.id.p_btn_del);
        p_btn_del.setText(mContext.getResources().getString(R.string.str_service_delete));
        p_btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView_title = new TextView(mContext);
                textView_title.setText(mContext.getResources().getString(R.string.str_service_delete));
                textView_title.setGravity(Gravity.START);
                textView_title.setPadding(20,10,20,10);
                textView_title.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                textView_title.setCompoundDrawablePadding(10);

                //textView_title.setCompoundDrawables(null,null,mContext.getResources().getDrawable(R.drawable.ic_warning_colored_24dp),null);
                textView_title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning_colored_24dp, 0);
                new AlertDialog.Builder(mContext)
                        .setCustomTitle(textView_title)
                        .setMessage(mContext.getResources().getString(R.string.confirm_service_delete))
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(mContext.getResources().getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
//                                Delete service API
//                                http://192.168.100.14/monshiapp/app/delete_service.php?service_id=
                                String url    =  mContext.getResources().getString(R.string.url)+"delete_service.php";
                                String params = "service_id="+item.getId();
                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {
                                                Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
                                                managmentServiceLists.remove(position);
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
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(mContext.getResources().getString(R.string.str_no), null)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });



        Button p_btn_edit = promptsView.findViewById(R.id.p_btn_edit);
        p_btn_edit.setText(mContext.getResources().getString(R.string.str_edit_service));
        p_btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.dismiss();
                Bundle bundle = new Bundle();
                bundle.putString("service_id", ""+item.getId());

                AddNewServiceFragment tab1= new AddNewServiceFragment();
                tab1.setArguments(bundle);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();

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
