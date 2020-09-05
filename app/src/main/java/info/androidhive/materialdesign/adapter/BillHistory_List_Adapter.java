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

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.lists.BillHistory_List;
import info.androidhive.materialdesign.lists.Customer_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;

/**
 * Created by hp on 2/16/2018.
 */

public class BillHistory_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<BillHistory_List> billHistoryLists = null;

    private LayoutInflater inflater=null;/**/
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public BillHistory_List_Adapter(Activity context, List<BillHistory_List> billHistory_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.billHistoryLists = billHistory_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {

    }

    @Override
    public int getCount() {
        return billHistoryLists.size();
    }

    @Override
    public BillHistory_List getItem(int position) {
        return billHistoryLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final BillHistory_List_Adapter.ViewHolder holder;

        if (convertView == null) {



            holder = new BillHistory_List_Adapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_billhistory, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (BillHistory_List_Adapter.ViewHolder) convertView.getTag();
        }

        return convertView;
    }
}
