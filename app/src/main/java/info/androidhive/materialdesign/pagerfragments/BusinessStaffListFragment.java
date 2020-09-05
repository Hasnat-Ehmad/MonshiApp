package info.androidhive.materialdesign.pagerfragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MangementStaff_List_Adapter;
import info.androidhive.materialdesign.lists.ManagmentStaff_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity.scrollView_BusinessPublicDetail_Activity;
import static info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity.viewPager;
import static info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class.scrollView;

public class BusinessStaffListFragment extends Fragment {

    ArrayList<ManagmentStaff_List> managmentStaff_lists =new ArrayList();

    ListView listView;

    MangementStaff_List_Adapter mangementStaff_list_adapter;
    SharedPreferences sharedPreferences;
    TextView tv_label_dashboard;
    Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_business_staff_list, container, false);
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        listView = (ListView) rootView.findViewById(R.id.list_view);

        activity = getActivity();

        tv_label_dashboard = rootView.findViewById(R.id.tv_label_dashboard);
        tv_label_dashboard.setVisibility(View.GONE);

        ImageView img_add = rootView.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);

        //http://192.168.100.14/monshiapp/app/staff_listing.php
        String url    =  getResources().getString(R.string.url)+"staff_listing.php";
        String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("selected_business", "")+
                "&selected_lat="+sharedPreferences.getString("selected_lat", "")+"&selected_lng="+sharedPreferences.getString("selected_lng", "")
                ;

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray staff_listing = jsonObject.getJSONArray("staff_listing");

//                        scrollView_BusinessPublicDetail_Activity.smoothScrollTo(0, 0);

                        for(int i = 0; i < staff_listing.length(); i++) {
                            JSONObject c = staff_listing.getJSONObject(i);
                            String id    = c.getString("id");
                            String user_id = c.getString("user_id");
                            String business_id = c.getString("business_id");
                            String username    = c.getString("username");
                            String full_name   = c.getString("full_name");
                            String role = c.getString("role");
                            String expertise = c.getString("expertise");
                            String experience = c.getString("experience");
                            String email = c.getString("email");
                            String mobile = c.getString("mobile");
                            String image = c.getString("image");

                            ManagmentStaff_List obj = new ManagmentStaff_List
                                    (""+id,""+username,""+email,""+mobile,"",""+image);

                            managmentStaff_lists.add(obj);

                        }

                        if (getActivity()!=null){
                            mangementStaff_list_adapter = new MangementStaff_List_Adapter(getActivity(),managmentStaff_lists);
                            listView.setAdapter(mangementStaff_list_adapter);
                            setDynamicHeight(listView);
                            //viewPager.setMinimumHeight(listView.getHeight());
                            //viewPager.notify();
                            //Toast.makeText(getActivity(),listView.getHeight()+" Staff listview",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);


        //listView.setPadding(0,0,0,20);

       /* DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        Toast.makeText(getActivity(),height+" - "+width+" Staff list",Toast.LENGTH_SHORT).show();*/

        return rootView;
    }

    public void setDynamicHeight(ListView listView) {

        ViewGroup.LayoutParams layoutParams_temp = listView.getLayoutParams();
        layoutParams_temp.height = 0;
        listView.setLayoutParams(layoutParams_temp);
        listView.requestLayout();

        //Checkout_List_Adapter adapter = listView.getAdapter();
        //check adapter if null
        if (mangementStaff_list_adapter.getCount() == 0) {
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mangementStaff_list_adapter.getCount(); i++) {
            View listItem = mangementStaff_list_adapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams layoutParams = listView.getLayoutParams();
        layoutParams.height = 300 * mangementStaff_list_adapter.getCount();//(listView.getDividerHeight() * (mangementStaff_list_adapter.getCount() - 1));
        listView.setLayoutParams(layoutParams);
        listView.requestLayout();
    }

}
