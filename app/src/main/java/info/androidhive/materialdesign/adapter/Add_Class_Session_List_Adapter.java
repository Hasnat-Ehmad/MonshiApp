package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.AddNewSession;
import info.androidhive.materialdesign.fragments.EditBusinessFragment;
import info.androidhive.materialdesign.lists.Add_Class_Session_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Add_Class_Session_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Add_Class_Session_List> addClassSessionLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public Add_Class_Session_List_Adapter(Activity context, List<Add_Class_Session_List> add_class_session_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.addClassSessionLists = add_class_session_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
    TextView tv_date,tv_capacity,tv_cost;
    LinearLayout layout_session_info;
    ImageView img_delet;
    }

    @Override
    public int getCount() {
        return addClassSessionLists.size();
    }

    @Override
    public Add_Class_Session_List getItem(int position) {
        return addClassSessionLists.get(position);
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
        final Add_Class_Session_List item = getItem(position);

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_add_new_class_session, parent, false);


            convertView.setTag(holder);
        }


        else{
            holder = (ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;


        holder.layout_session_info = convertView.findViewById(R.id.layout_session_info);
        holder.layout_session_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("session_id",""+item.getSession_id());
                bundle.putString("class_id"  ,""+item. getS_classid());
                bundle.putString("s_staff_id"  ,""+item.getS_staff_id());


                AddNewSession tab1 = new AddNewSession();
                tab1.setArguments(bundle);

                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();


            }
        });


        holder.tv_date = convertView.findViewById(R.id.tv_date);
        holder.tv_date.setText(item.getDate());

        holder.tv_capacity = convertView.findViewById(R.id.tv_capacity);
        holder.tv_capacity.setText(item.getS_slot());

        holder.tv_cost = convertView.findViewById(R.id.tv_cost);
        holder.tv_cost.setText(item.getS_cost());

        holder.img_delet = convertView.findViewById(R.id.img_delet);
        holder.img_delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,"session delete",Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }
}
