package info.androidhive.materialdesign.adapter;

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
import info.androidhive.materialdesign.lists.Notification_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Notification_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Notification_List> notificationLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public Notification_List_Adapter(Activity context, List<Notification_List> notification_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.notificationLists = notification_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
    TextView tv_date,tv_service,tv_staff,tv_reservedby,tv_ranking,tv_unranking,tv_detail;
    }

    @Override
    public int getCount() {
        return notificationLists.size();
    }

    @Override
    public Notification_List getItem(int position) {
        return notificationLists.get(position);
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
        final Notification_List item = getItem(position);

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_notification, parent, false);

            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_detail = convertView.findViewById(R.id.tv_detail) ;
        holder.tv_detail.setText(""+item.getNotification());

        /*holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date) ;
        holder.tv_date.setText(""+item.getEnd_event());

        holder.tv_service = (TextView) convertView.findViewById(R.id.tv_service) ;
        holder.tv_service.setText(""+item.getService_name());

        holder.tv_staff = (TextView) convertView.findViewById(R.id.tv_staff) ;
        holder.tv_staff.setText(""+item.getStaff_name());

        holder.tv_reservedby = (TextView) convertView.findViewById(R.id.tv_reservedby) ;
        holder.tv_reservedby.setText(""+item.getBooked_by());

        holder.tv_ranking = (TextView) convertView.findViewById(R.id.tv_ranking) ;
        holder.tv_ranking.setText(""+item.getRating());

        holder.tv_unranking = (TextView) convertView.findViewById(R.id.tv_unranking) ;
        holder.tv_unranking.setText(""+item.getIs_rated());*/

        return convertView;
    }
}
