package info.androidhive.materialdesign.staff_work;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Staff_Activity_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Staff_Activity_List> staffActivityLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<Staff_Activity_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public Staff_Activity_List_Adapter(Activity context, List<Staff_Activity_List> staff_activity_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.staffActivityLists = staff_activity_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {

    TextView tv_date,tv_service,tv_customer_name,tv_description,tv_cost,tv_ranking;

    }

    @Override
    public int getCount() {
        return staffActivityLists.size();
    }

    @Override
    public Staff_Activity_List getItem(int position) {
        return staffActivityLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        final Staff_Activity_List item = getItem(position);

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_staff_activity, parent, false);

            convertView.setTag(holder);
        }


        else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date) ;
        holder.tv_date.setText(""+item.getJalali_date());

        holder.tv_service = (TextView) convertView.findViewById(R.id.tv_service) ;
        holder.tv_service.setText(""+item.getService_name());

        holder.tv_customer_name = (TextView) convertView.findViewById(R.id.tv_customer_name) ;
        holder.tv_customer_name.setText(""+item.getCustomer_name());

        holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description) ;
        holder.tv_description.setText(""+item.getDescription());

        holder.tv_cost = (TextView) convertView.findViewById(R.id.tv_cost) ;
        holder.tv_cost.setText(""+item.getAmount());

        holder.tv_ranking = (TextView) convertView.findViewById(R.id.tv_ranking);
        holder.tv_ranking.setText(""+item.getRating());

        return convertView;
    }
}
