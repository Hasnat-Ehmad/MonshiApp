package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.fragments.AddNewCustomerFragment;
import info.androidhive.materialdesign.lists.Customer_List;
import info.androidhive.materialdesign.lists.Fav_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 2/16/2018.
 */

public class Fav_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Fav_List> favouriteList = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    SharedPreferences sharedPreferences;

    public Fav_List_Adapter(Activity context, List<Fav_List> fav_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.favouriteList = fav_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {

        TextView tv_fav_name,tv_fav_des,tv_cust_phone;

        ImageView img_delet;
        CircleImageView circleImageView;
        ToggleButton toggle_favorite;

        ConstraintLayout layout_fav;

    }

    @Override
    public int getCount() {
        return favouriteList.size();
    }

    @Override
    public Fav_List getItem(int position) {
        return favouriteList.get(position);
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
        final Fav_List item = getItem(position);

        if (convertView == null) {



            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_favourite, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (ViewHolder) convertView.getTag();
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());

        fragmentActivity = (FragmentActivity) mContext;

        holder.toggle_favorite = convertView.findViewById(R.id.toggle_favorite);
        holder.layout_fav = convertView.findViewById(R.id.layout_fav);
        holder.layout_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Bundle bundle = new Bundle();
                bundle.putString("id"     , ""+item.getId());
                bundle.putString("name"   , ""+item.getName());
                bundle.putString("email"  , ""+item.getEmail());
                bundle.putString("mobile" , ""+item.getNumber());
                bundle.putString("address", ""+item.getCustomer_address());
                bundle.putString("image"  , ""+item.getCustomer_image());

                AddNewCustomerFragment tab1= new AddNewCustomerFragment();
                tab1.setArguments(bundle);
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();*/


            }
        });

        holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.circleImageView);
        Picasso.with(holder.circleImageView.getContext()).load(""+item.getImage()).into(holder.circleImageView);

        holder.tv_fav_name = (TextView) convertView.findViewById(R.id.tv_fav_name);
        holder.tv_fav_name.setText(""+item.getName());

        holder.tv_fav_des = (TextView) convertView.findViewById(R.id.tv_fav_des);
        holder.tv_fav_des.setText(""+item.getDescription());

        holder.toggle_favorite.setChecked(true);

        holder.toggle_favorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked) {
                    new AlertDialog.Builder(mContext,R.style.AlertDialogCustom)
                            .setTitle(mContext.getResources().getString(R.string.str_remove_frvt_title))
                            .setMessage(mContext.getResources().getString(R.string.str_remove_from_fvt))
                            .setPositiveButton(mContext.getResources().getString(R.string.str_yes), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //http://192.168.100.14/monshiapp/app/add_fav.php
                                    String url = mContext.getResources().getString(R.string.url) + "add_fav.php";

                                    String params = "user_id=" + sharedPreferences.getString("user_id", "") +
                                            "&fav_id=" + item.getFav_id() +
                                            "&type="+item.getType();


                                    WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                        @Override
                                        public void TaskCompletionResult(String result) {

                                            try {

                                                JSONObject jsonObject = new JSONObject(result);
                                                if (jsonObject.getString("status").equals("200")) {

                                                    favouriteList.remove(position);
                                                    notifyDataSetChanged();
                                                } else {
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
                            .setNegativeButton(mContext.getResources().getString(R.string.str_no), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    holder.toggle_favorite.setChecked(true);
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });
        /* holder.tv_cust_phone = (TextView) convertView.findViewById(R.id.tv_cust_phone);
        holder.tv_cust_phone.setText(""+item.getNumber());*/

        /*holder.img_delet = convertView.findViewById(R.id.img_delet);
        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(mContext)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to remove this from favourites?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
//                              Delete Customer API
//http://192.168.100.14/monshiapp/app/delete_customer.php?cust_id=
                                String url    =  mContext.getResources().getString(R.string.url)+"delete_customer.php";

                                String params = "cust_id="+item.getId();

                                WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                    @Override
                                    public void TaskCompletionResult(String result) {

                                        try {

                                            JSONObject jsonObject = new JSONObject(result);
                                            if(jsonObject.getString("status").equals("200")) {

                                                Toast.makeText(mContext,jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();

                                                favouriteList.remove(position);
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
        });*/

        return convertView;
    }
}
