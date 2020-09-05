package info.androidhive.materialdesign.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
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
import info.androidhive.materialdesign.adapter.Class_Session_List_Adapter;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.circle_image.CircleImageView;
import info.androidhive.materialdesign.lists.Class_Session_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class ClassSessionListFragment extends Fragment {

    SharedPreferences sharedPreferences;

    ImageView img_back;

    private Toolbar mToolbar;

    ArrayList<Class_Session_List> class_session_lists =new ArrayList();

    ListView listView;

    Class_Session_List_Adapter class_session_list_adapter;

    String class_id,class_name;
    TextView tv_title;
    CircleImageView img_profile;

    TextView tv_label_week;

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_class_session_list, container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mToolbar.setVisibility(View.GONE);

//        mToolbar.setBackgroundColor(getActivity().getResources().getColor(R.color.darkgrey));
//
//        img_profile = (CircleImageView) mToolbar.findViewById(R.id.img_profile);
//        img_profile.setVisibility(View.GONE);
//
//        tv_title = mToolbar.findViewById(R.id.tv_app_name);
//
//        img_back = mToolbar.findViewById(R.id.img_back);
//        img_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getFragmentManager().popBackStack();
//            }
//        });

        tv_label_week = rootView.findViewById(R.id.tv_label_week);
        tv_label_week.setVisibility(View.GONE);

        if (getArguments()!=null){

            Bundle b = getArguments();
            class_id = b.getString("id");
            SavePreferences("class_id"        ,class_id);
            class_name = b.getString("class_name");
            //class_id= b.getString("class_name");
//            tv_title.setText(class_name+" sessions");

        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/class_session_list.php?business_id=1&class_id=83
        String url    =  getResources().getString(R.string.url)+"class_session_list.php";

        String params =  "business_id="+sharedPreferences.getString("selected_business", "")+
                         "&class_id="+class_id ;

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {

                        class_session_lists.clear();

                        JSONArray session_list = jsonObject.getJSONArray("session_list");

                        for(int i = 0; i < session_list.length(); i++) {
                            JSONObject c = session_list.getJSONObject(i);
                            String session_id = c.getString("id");
                            String classname = c.getString("classname");
                            String to_date_per = c.getString("to_date_per");
                            String from_date_per = c.getString("from_date_per");
                            String booked_up = c.getString("booked_up");
                            String teacher_name = c.getString("teacher_name");
                            String user_type = c.getString("user_type");

                            JSONArray session_day_list = c.getJSONArray("session_day_list");

                            Class_Session_List obj = new Class_Session_List
                                    (""+session_id,"جلسه "+i+1 ,""+classname, ""+class_id,""+to_date_per,""+from_date_per,""+teacher_name+", "+classname,session_day_list);


                            class_session_lists.add(obj);

                            //Toast.makeText(getActivity(),"user_type = "+user_type,Toast.LENGTH_SHORT).show();

                        }
                        if (getActivity()!=null){
                            class_session_list_adapter = new Class_Session_List_Adapter(getActivity(),class_session_lists);

                            listView.setAdapter(class_session_list_adapter);
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

        return rootView;
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
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
        listView.setLayoutParams(params);
    }
}
