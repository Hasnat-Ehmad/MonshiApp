package info.androidhive.materialdesign.adapter.customer_adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.BusinessDetail_Activity;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.lists.customer_list.Customer_frvt_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Customer_frvt_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Customer_frvt_List> customerFrvtLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;
    SharedPreferences sharedPreferences;

    public Customer_frvt_List_Adapter(Activity context, List<Customer_frvt_List> customer_frvt_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.customerFrvtLists = customer_frvt_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
    TextView tv_number,tv_name;
    ImageView img_profile_pic;
    LinearLayout layout_main;
    }

    @Override
    public int getCount() {
        return customerFrvtLists.size();
    }

    @Override
    public Customer_frvt_List getItem(int position) {
        return customerFrvtLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Customer_frvt_List_Adapter.ViewHolder holder;
        final Customer_frvt_List item = getItem(position);

        if (convertView == null) {

            holder = new Customer_frvt_List_Adapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_customer_frvt, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (Customer_frvt_List_Adapter.ViewHolder) convertView.getTag();
        }

        holder.tv_number =  convertView.findViewById(R.id.tv_number) ;
        holder.tv_number.setText(""+item.getNumber());

        holder.tv_name =  convertView.findViewById(R.id.tv_name) ;
        holder.tv_name.setText(""+item.getName());

        holder.img_profile_pic =  convertView.findViewById(R.id.img_profile_pic) ;
        Picasso.with(holder.img_profile_pic.getContext()).load(""+item.getImage()).into(holder.img_profile_pic);

        holder.layout_main = convertView.findViewById(R.id.layout_main) ;

        holder.layout_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.getType().equals("business")){

                    SavePreferences("selected_business" ,item.getBusiness_id());
                    SavePreferences("selected_image"    ,item.getImage());

                    Intent intent = new Intent(mContext, BusinessDetail_Activity.class);
                    mContext.startActivity(intent);

                }else
                    Toast.makeText(mContext,"Class page under progress",Toast.LENGTH_LONG).show();
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
