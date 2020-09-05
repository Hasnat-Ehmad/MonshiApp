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
import info.androidhive.materialdesign.lists.Pop_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Pop_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Pop_List> popLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public Pop_List_Adapter(Activity context, List<Pop_List> pop_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.popLists = pop_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
    TextView tv_day,tv_duration;
    }

    @Override
    public int getCount() {
        return popLists.size();
    }

    @Override
    public Pop_List getItem(int position) {
        return popLists.get(position);
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
        final Pop_List item = getItem(position);

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.row_session_detail_list, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (ViewHolder) convertView.getTag();
        }


        holder.tv_day = (TextView) convertView.findViewById(R.id.tv_day);
        holder.tv_day.setText(""+item.getOperating_day_per());

        holder.tv_duration = (TextView) convertView.findViewById(R.id.tv_duration);
        holder.tv_duration.setText(""+item.getFrom_hour_per()+" ("+item.getDuration_per()+") ");

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
