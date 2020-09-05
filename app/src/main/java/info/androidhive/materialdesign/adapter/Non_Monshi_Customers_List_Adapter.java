package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.lists.Monshi_Customers_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Non_Monshi_Customers_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Monshi_Customers_List> monshiCustomersLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public static Monshi_Customers_List item;

    public Non_Monshi_Customers_List_Adapter(Activity context, List<Monshi_Customers_List> monshi_customers_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.monshiCustomersLists = monshi_customers_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
    TextView tv_name;
    }

    @Override
    public int getCount() {
        return monshiCustomersLists.size();
    }

    @Override
    public Monshi_Customers_List getItem(int position) {
        return monshiCustomersLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Non_Monshi_Customers_List_Adapter.ViewHolder holder;
                item = getItem(position);
        notifyDataSetChanged();

        if (convertView == null) {

            holder = new Non_Monshi_Customers_List_Adapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_monshi_customer, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (Non_Monshi_Customers_List_Adapter.ViewHolder) convertView.getTag();
        }

        holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name) ;
        holder.tv_name.setText(""+item.getName());



        return convertView;
    }
}
