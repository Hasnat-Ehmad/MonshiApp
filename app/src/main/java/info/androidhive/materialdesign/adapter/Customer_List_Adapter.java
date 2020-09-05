package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.AddNewCustomerFragment;
import info.androidhive.materialdesign.fragments.EditBusinessFragment;
import info.androidhive.materialdesign.lists.Customer_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

/**
 * Created by hp on 2/16/2018.
 */

public class Customer_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Customer_List> customerLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public Customer_List_Adapter(Activity context, List<Customer_List> customer_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.customerLists = customer_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {

        TextView tv_cust_name,tv_email,tv_cust_phone;

        ImageView img_delet;

        ConstraintLayout layout_add_customer;

    }

    @Override
    public int getCount() {
        return customerLists.size();
    }

    @Override
    public Customer_List getItem(int position) {
        return customerLists.get(position);
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
        final Customer_List item = getItem(position);

        if (convertView == null) {



            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_customer, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;

        holder.layout_add_customer = convertView.findViewById(R.id.layout_add_customer);
        holder.layout_add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
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
                        .commit();


            }
        });

                holder.tv_cust_name = (TextView) convertView.findViewById(R.id.tv_cust_name);
        holder.tv_cust_name.setText(""+item.getName());
                /*holder.tv_email = (TextView) convertView.findViewById(R.id.tv_email);
        holder.tv_email.setText(""+item.getEmail());*/
                holder.tv_cust_phone = (TextView) convertView.findViewById(R.id.tv_cust_phone);
        holder.tv_cust_phone.setText(""+item.getNumber());

        holder.img_delet = convertView.findViewById(R.id.img_delet);
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

                                                customerLists.remove(position);
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

        return convertView;
    }
}
