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
import android.text.Html;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.AddNewSession;
import info.androidhive.materialdesign.fragments.SessionDetailFragment;
import info.androidhive.materialdesign.lists.Class_Session_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.lists.Pop_List;

/**
 * Created by hp on 2/16/2018.
 */

public class Class_Session_List_Adapter extends BaseAdapter {

    private Activity mContext;
    static private List<Class_Session_List> classSessionLists = null;

    private LayoutInflater inflater=null;/**/
    private ArrayList<ManagmentStaff_List> arraylist;
    private SparseBooleanArray mSelectedItemsIds;
    private FragmentActivity fragmentActivity;

    private String id,online_status;

    ArrayList<Pop_List> pop_lists =new ArrayList();

    ListView listView;

    Pop_List_Adapter pop_list_adapter;

    public Class_Session_List_Adapter(Activity context, List<Class_Session_List> class_session_lists) {
        // TODO Auto-generated constructor stub
        this.mContext = context;
        this.classSessionLists = class_session_lists;
        //inflater = LayoutInflater.from(this.mContext);
        this.inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private class ViewHolder {
    TextView tv_class_name,tv_duration;
    ConstraintLayout layout_class_session;
    }

    @Override
    public int getCount() {
        return classSessionLists.size();
    }

    @Override
    public Class_Session_List getItem(int position) {
        return classSessionLists.get(position);
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


        final Class_Session_List item = getItem(position);

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.list_view_row_session, parent, false);

            convertView.setTag(holder);
        }

        else{
            holder = (ViewHolder) convertView.getTag();
        }

        fragmentActivity = (FragmentActivity) mContext;


        holder.tv_class_name = convertView.findViewById(R.id.tv_class_name);
        holder.tv_class_name.setText(item.getTeacher_name());

        holder.tv_duration  = convertView.findViewById(R.id.tv_duration);
        holder.tv_duration.setText(""+item.getTo_date_per()+" الی "+""+item.getFrom_date_per());

        JSONArray session_day_list = item.getSession_day_list();

        pop_lists.clear();

        for(int j = 0; j < session_day_list.length(); j++) {
            JSONObject d = null;
            try {
                d = session_day_list.getJSONObject(j);

            final String teacher_name      = d.getString("teacher_name");
            String operating_day           = d.getString("operating_day");
            final String operating_day_per = d.getString("operating_day_per");
            String from_hour               = d.getString("from_hour");
            final String from_hour_per     = d.getString("from_hour_per");
            String duration                = d.getString("duration");
            final String duration_per      = d.getString("duration_per");
            final String student_gender    = d.getString("student_gender");
            String seats                   = d.getString("seats");
            final String seats_per         = d.getString("seats_per");
            String cost                    = d.getString("cost");
            final String cost_per          = d.getString("cost_per");

                Pop_List obj = new Pop_List
                        (""+operating_day_per,""+duration_per,""+from_hour_per);

                pop_lists.add(obj);


            /* Toast.makeText(getActivity(),"value from = "+cost_per,Toast.LENGTH_SHORT).show();*/
            System.out.println("cost_per = "+cost_per);

                holder.layout_class_session = convertView.findViewById(R.id.layout_class_session);
                holder.layout_class_session.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        //alertDialogShow_requestreject(mContext,teacher_name,seats_per,cost_per,student_gender);

                        Bundle bundle = new Bundle();
                        bundle.putString("session_id"    ,""+item.getSession_id());
                        bundle.putString("teacher_name"  ,""+teacher_name);
                        bundle.putString("seats_per"     ,""+seats_per);
                        bundle.putString("cost_per"      ,""+cost_per);
                        bundle.putString("student_gender",""+student_gender);
                        bundle.putString("class_name",""+item.getClassname_temp());
                        bundle.putString("class_id",""+item.getClassid_temp());
                        bundle.putString("duration"      ,""+holder.tv_duration.getText().toString());

                        SessionDetailFragment tab1 = new SessionDetailFragment();
                        tab1.setArguments(bundle);

                        fragmentActivity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frame_layout, tab1)
                                .addToBackStack(null)   // this will be it to the back stack
                                .commit();

                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        pop_list_adapter = new Pop_List_Adapter(mContext,pop_lists);

       /* String activity_detail = "<b>" + "New Appt: " + "</b> " +item.getBooked_by()+" for a service "+item.getService_name()+" on "+item.getEnd_event()+" with "+item.getStaff_name();
        holder.tv_detail.setText(Html.fromHtml(activity_detail));*/

        return convertView;
    }


    public void alertDialogShow_requestreject
            (final Context context, String teacher_name, String seats_per, String cost_per,String student_gender) {

        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.popup_session_detail,
                null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        alertDialogBuilder.setView(promptsView);


        final AlertDialog d = alertDialogBuilder.show();
        d.getWindow().setBackgroundDrawableResource(android.R.color.white);


        TextView tv_session_date = promptsView.findViewById(R.id.tv_session_date);
                 tv_session_date.setText(""+holder.tv_duration.getText().toString());


        TextView tv_teacher_name = promptsView.findViewById(R.id.tv_teacher_name);
        tv_teacher_name.setText(""+teacher_name);

        TextView tv_cost = promptsView.findViewById(R.id.tv_cost);
        tv_cost.setText(""+cost_per);

        TextView tv_sites = promptsView.findViewById(R.id.tv_sites);
        tv_sites.setText(""+seats_per);

        TextView tv_gender = promptsView.findViewById(R.id.tv_gender);
        tv_gender.setText(""+student_gender);

        listView       = promptsView.findViewById(R.id.list_view);
        listView.setAdapter(pop_list_adapter);

//        ImageView img_close = promptsView.findViewById(R.id.img_close);
//        img_close.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                d.dismiss();
//            }
//        });


    }

}
