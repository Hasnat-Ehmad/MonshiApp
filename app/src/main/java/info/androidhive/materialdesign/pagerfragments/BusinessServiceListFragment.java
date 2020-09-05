package info.androidhive.materialdesign.pagerfragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MangementService_List_Adapter;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity.scrollView_BusinessPublicDetail_Activity;


public class BusinessServiceListFragment extends Fragment {

    ArrayList<ManagmentService_List> managmentService_lists =new ArrayList();

    ListView listView;

    MangementService_List_Adapter mangementService_list_adapter;
    SharedPreferences sharedPreferences;

    ImageView img_add;
    TextView tv_label_dashboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView =  inflater.inflate(R.layout.fragment_business_service_list_, container, false);
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        listView = (ListView) rootView.findViewById(R.id.list_view);


        tv_label_dashboard = rootView.findViewById(R.id.tv_label_dashboard);
        tv_label_dashboard.setVisibility(View.GONE);
        img_add = rootView.findViewById(R.id.img_add);
        img_add.setVisibility(View.GONE);

//http://192.168.100.14/monshiapp/app/service_listing.php
        String url    =  getResources().getString(R.string.url)+"service_listing.php";
        String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                "&business_id="+sharedPreferences.getString("selected_business", "");

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray staff_listing = jsonObject.getJSONArray("service_listing");

                        managmentService_lists.clear();
//                        scrollView_BusinessPublicDetail_Activity.smoothScrollTo(0, 0);

                        for(int i = 0; i < staff_listing.length(); i++) {
                            JSONObject c = staff_listing.getJSONObject(i);
                            String id = c.getString("id");
                            String user_id = c.getString("user_id");
                            String business_id = c.getString("business_id");
                            String servicename = c.getString("servicename");
                            String description = c.getString("description");
                            String amount = c.getString("amount");
                            String time = c.getString("time");
                            String buffer_time = c.getString("buffer_time");
                            String role = c.getString("role");


                            if(!id.equals("0")){
                                ManagmentService_List obj = new ManagmentService_List
                                        (""+id,""+servicename,""+amount,""+time,"","",""+description);


                                managmentService_lists.add(obj);
                            }


                        }

                        if (getActivity()!=null){
                            mangementService_list_adapter = new MangementService_List_Adapter(getActivity(),managmentService_lists);
                            listView.setAdapter(mangementService_list_adapter);
                            setListViewHeightBasedOnChildren(listView);
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

        //Toast.makeText(getActivity(),rootView.getHeight()+" Service List",Toast.LENGTH_SHORT).show();

        return rootView;
    }


    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //Toast.makeText(getActivity(),params.height +"",Toast.LENGTH_SHORT).show();
        listView.setLayoutParams(params);
    }

}
