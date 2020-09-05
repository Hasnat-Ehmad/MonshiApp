package info.androidhive.materialdesign.adapter;

/**
 * Created by Ravi on 29/07/15.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.model.NavDrawerItem;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    SharedPreferences sharedPreferences;


    int[] myImageList = new int[]{
            R.drawable.ic_fiber_new_white_24dp,R.drawable.ic_view_list_black_24dp,
            R.drawable.ic_home_white_24dp,R.drawable.ic_dashboard_black_24dp,
            R.drawable.ic_face_black_24dp,R.drawable.ic_room_service_black_24dp,
            R.drawable.ic_perm_contact_calendar_white_24dp,R.drawable.ic_person_white_24dp,
            R.drawable.ic_monetization_on_black_24dp
    };

    int[] myImageList_recp = new int[]{
            R.drawable.ic_dashboard_black_24dp,
            R.drawable.ic_face_black_24dp,R.drawable.ic_room_service_black_24dp,
            R.drawable.ic_perm_contact_calendar_white_24dp,R.drawable.ic_person_white_24dp,

    };

    int[] myImageList_customer = new int[]{
            R.drawable.ic_fiber_new_white_24dp,
            R.drawable.ic_home_white_24dp,R.drawable.ic_favorite_border_white_24dp,
            R.drawable.ic_view_list_black_24dp,R.drawable.ic_person_white_24dp,

    };


    int[] myImageList_non_login = new int[]{
            R.drawable.ic_home_white_24dp

    };

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);

        if (sharedPreferences.getString("isLogin" ,""  ).equals("yes")){
            if (sharedPreferences.getString("user_role" ,""  ).equals("2")){
                holder.title.setText(current.getTitle());
                holder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, myImageList_recp[position], 0);
                holder.title.setCompoundDrawablePadding(15);

            } if (sharedPreferences.getString("user_role" ,""  ).equals("4")){
                holder.title.setText(current.getTitle());
                holder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, myImageList_customer[position], 0);
                holder.title.setCompoundDrawablePadding(15);
            } else {
                holder.title.setText(current.getTitle());
                holder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, myImageList[position], 0);
                holder.title.setCompoundDrawablePadding(15);

            }

        }else {

            holder.title.setText(current.getTitle());
            holder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, myImageList_non_login[position], 0);
            holder.title.setCompoundDrawablePadding(15);

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            //title.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_person_black_24dp, 0, 0, 0);
        }
    }
}
