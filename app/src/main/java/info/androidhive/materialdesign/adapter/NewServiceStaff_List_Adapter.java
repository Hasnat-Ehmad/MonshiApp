package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.lists.Business_List;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;

/**
 * Created by hp on 2/16/2018.
 */

public class NewServiceStaff_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<ManagmentService_List> managmentServiceLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    public static ArrayList<Boolean> positionArray;

    public NewServiceStaff_List_Adapter(Activity context, List<ManagmentService_List> managmentService_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.managmentServiceLists = managmentService_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        positionArray = new ArrayList<Boolean>(managmentService_lists.size());
        for (int i = 0; i < managmentService_lists.size(); i++) {
            positionArray.add(false);
        }
    }

    private class ViewHolder {
    ImageView img_pic;
    TextView tv_staff_name;
    ToggleButton toggle_btn;
    }

    @Override
    public int getCount() {
        return managmentServiceLists.size();
    }

    @Override
    public ManagmentService_List getItem(int position) {
        return managmentServiceLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final NewServiceStaff_List_Adapter.ViewHolder holder;
        final ManagmentService_List item = getItem(position);

        if (convertView == null) {



            holder = new NewServiceStaff_List_Adapter.ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_newservicestaff, parent, false);

            convertView.setTag(holder);
        }

        else{
            holder = (NewServiceStaff_List_Adapter.ViewHolder) convertView.getTag();
        }
        holder.img_pic= (ImageView) convertView.findViewById(R.id.img_pic);
        Picasso.with(holder.img_pic.getContext()).load(""+item.getImage()).into(holder.img_pic);

        holder.tv_staff_name = (TextView) convertView.findViewById(R.id.tv_staff_name);
        holder.tv_staff_name.setText(""+item.getServicename());

        holder.toggle_btn =  convertView.findViewById(R.id.toggle_btn);

        if (item.getSelect().equals("yes")){

            holder.toggle_btn.setChecked(true);

            positionArray.set(position,true);

            holder.toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked ){
                        System.out.println(position+"--- :)");
                        positionArray.set(position, true);

                    }else
                        positionArray.set(position, false);
                }
            });

        }else {

            holder.toggle_btn.setFocusable(false);
            holder.toggle_btn.setChecked(positionArray.get(position));

            positionArray.set(position,false);

            holder.toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked ){
                        System.out.println(position+"--- :)");
                        positionArray.set(position, true);

                    }else
                        positionArray.set(position, false);
                }
            });

        }

        return convertView;
    }
}
