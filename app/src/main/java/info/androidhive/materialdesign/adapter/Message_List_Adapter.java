package info.androidhive.materialdesign.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.fragments.SessionDetailFragment;
import info.androidhive.materialdesign.lists.Class_Session_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.lists.Message_List;
import info.androidhive.materialdesign.lists.Pop_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Message_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Message_List> message_lists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<Message_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;
    private static final int TYPES_COUNT = 2;
    private static final int TYPE_LEFT = 0;
    private static final int TYPE_RIGHT = 1;

    public Message_List_Adapter(Activity context, List<Message_List> messageLists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.message_lists = messageLists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private class ViewHolder {
        TextView chatTV, timeTV;
        ImageView messageStatusIV;
        CircleImageView chatIV;
    }

    @Override
    public int getViewTypeCount() {
        return TYPES_COUNT;
    }

    @Override
    public int getItemViewType (int position) {
        if (getItem(position).getSender().equals("other")) {
            return TYPE_LEFT;
        }
        return TYPE_RIGHT;
    }

    @Override
    public int getCount() {
        return message_lists.size();
    }

    @Override
    public Message_List getItem(int position) {
        return message_lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder holder;

    @SuppressLint({"ResourceType", "SetTextI18n"})
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final Message_List item = getItem(position);

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (getItemViewType (position) == TYPE_LEFT) {
                convertView = inflater.inflate(R.layout.item_messsage_incoming, parent, false);
            }else{
                convertView = inflater.inflate(R.layout.item_messsage_outgoing, parent, false);
            }



            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;

        //Toast.makeText(mContext,item.getSender(),Toast.LENGTH_SHORT).show();
            holder.chatTV = (TextView) convertView.findViewById(R.id.chatTV);
            holder.timeTV = (TextView) convertView.findViewById(R.id.timeTV);
            //messageStatusIV = (ImageView) v.findViewById(messageStatusIV);
        if (getItemViewType (position) == TYPE_LEFT) {
            holder.chatIV = (CircleImageView) convertView.findViewById(R.id.chatIV);
            Picasso.with(holder.chatIV.getContext()).load(""+item.getImage()).into(holder.chatIV);
        }





        //Toast.makeText(mContext,item.getSender(),Toast.LENGTH_SHORT).show();
        holder.chatTV.setText(item.getMessage());
        holder.timeTV.setText(item.getDate_time());
        /*holder.tv_class_name = convertView.findViewById(R.id.tv_class_name);
        holder.tv_class_name.setText(item.getTeacher_name());

        holder.tv_duration  = convertView.findViewById(R.id.tv_duration);
        holder.tv_duration.setText(""+item.getTo_date_per()+" الی "+""+item.getFrom_date_per());*/

        return convertView;
    }

}
