package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.AddNewClassFragment;
import info.androidhive.materialdesign.fragments.ClassSessionListFragment;
import info.androidhive.materialdesign.lists.ManagmentClass_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;

import static info.androidhive.materialdesign.fragments.ClassListFragment.new_class_id;

/**
 * Created by hp on 2/16/2018.
 */

public class MangementClass_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<ManagmentClass_List> managmentClassLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public MangementClass_List_Adapter(Activity context, List<ManagmentClass_List> managmentClass_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.managmentClassLists = managmentClass_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {

        TextView tv_classname,tv_description,tv_cost,tv_time;

        ConstraintLayout layout_class;

    }

    @Override
    public int getCount() {
        return managmentClassLists.size();
    }

    @Override
    public ManagmentClass_List getItem(int position) {
        return managmentClassLists.get(position);
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
        final ManagmentClass_List item = getItem(position);

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_managmentclass, parent, false);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;

        holder.layout_class = convertView.findViewById(R.id.layout_class);
        holder.layout_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* Toast.makeText(mContext,"click",Toast.LENGTH_SHORT).show();*/

                if (item.getScreen_check().equals("1")){
                    Bundle bundle = new Bundle();
                    bundle.putString("id",""+ item.getId());
                    bundle.putString("class_name",""+ item.getClassname());

                    ClassSessionListFragment tab1= new ClassSessionListFragment();
                    tab1.setArguments(bundle);

                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id",""+ item.getId());

                    new_class_id = item.getId();

                    AddNewClassFragment tab1= new AddNewClassFragment();
                    tab1.setArguments(bundle);

                    fragmentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_layout, tab1)
                            .addToBackStack(null)   // this will be it to the back stack
                            .commit();
                }

            }
        });

        holder.tv_classname = (TextView) convertView.findViewById(R.id.tv_class_name);
        holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
       // holder.tv_cost = (TextView) convertView.findViewById(R.id.tv_cost);
       // holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

        holder.tv_classname.setText(""+item.getClassname());
        holder.tv_description.setText(""+item.getDesc());
        //holder.tv_cost.setText(""+item.getAmount());
        //holder.tv_time.setText(""+item.getTime());


        return convertView;
    }
}
