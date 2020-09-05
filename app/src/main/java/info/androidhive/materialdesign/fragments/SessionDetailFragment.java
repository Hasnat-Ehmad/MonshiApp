package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
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
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.ChatActivity;
import info.androidhive.materialdesign.activity.ClassBookingActivity;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.Pop_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.Pop_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.BusinessPublicDetail_Activity_Class_scroll.business_id_static;


public class SessionDetailFragment extends Fragment {
    SharedPreferences sharedPreferences;

    ArrayList<Pop_List> pop_lists =new ArrayList();

    ListView listView;

    Pop_List_Adapter pop_list_adapter;

    ImageView img_book_now,img_chat;

    String user_type;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View rootView = inflater.inflate(R.layout.fragment_session_detail, container, false);
        View rootView = inflater.inflate(R.layout.popup_session_detail, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        String session_id="" , teacher_name = null,seats_per="",cost_per = null,student_gender="",class_id = "",class_name = "", duration = null;

        if (getArguments() != null) {

            Bundle b = getArguments();
            session_id = getArguments().getString("session_id");

            teacher_name = getArguments().getString("teacher_name");
            seats_per = getArguments().getString("seats_per");

            cost_per = getArguments().getString("cost_per");
            student_gender = getArguments().getString("student_gender");

            duration = getArguments().getString("duration");
            class_name = getArguments().getString("class_name");
            class_id = getArguments().getString("class_id");


        }

        img_book_now = rootView.findViewById(R.id.img_book_now);
        final String finalSession_id = session_id;
        final String finalClass_id = class_id;
        img_book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(getActivity(),"booking here",Toast.LENGTH_SHORT).show();

                Intent mIntent = new Intent(getActivity(), ClassBookingActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putString("session_id"    ,""+ finalSession_id);
                mBundle.putString("class_id"    ,""+ finalClass_id);
                SavePreferences("session_id",finalSession_id);

                mIntent.putExtras(mBundle);
                startActivity(mIntent);



                /* Bundle bundle = new Bundle();
                ClassBookingFragment tab1 = new ClassBookingFragment();
                tab1.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        // .addToBackStack(null)   // this will be it to the back stack
                        .commit();*/

            }
        });


        img_chat = rootView.findViewById(R.id.img_chat);//implementation inside the api call check user_type

        img_chat.setVisibility(View.GONE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if(Objects.equals(sharedPreferences.getString("user_id", ""), "") || !sharedPreferences.contains("user_id")){
//                img_chat.setVisibility(View.GONE);
//            }
//        }

        final String finalClass_name = class_name;



        TextView tv_session_date = rootView.findViewById(R.id.tv_session_date);
        tv_session_date.setText(""+duration);


        TextView tv_teacher_name = rootView.findViewById(R.id.tv_teacher_name);
        tv_teacher_name.setText(""+teacher_name);

        TextView tv_cost = rootView.findViewById(R.id.tv_cost);
        tv_cost.setText(""+cost_per);

        TextView tv_sites = rootView.findViewById(R.id.tv_sites);
        tv_sites.setText(""+seats_per);

        TextView tv_gender = rootView.findViewById(R.id.tv_gender);
        tv_gender.setText(""+student_gender);


        listView = (ListView) rootView.findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/session_detail.php?business_id=1&class_id=2&session_id=104
        String url    =  getResources().getString(R.string.url)+"session_detail.php";

        String params = "";
        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            //spinner_business_list.setVisibility(View.GONE);
            params =  "class_id="+finalClass_id+
                      "&business_id="+business_id_static+
                      "&session_id="+session_id+
                      "&user_id="+sharedPreferences.getString("user_id", "");
        }else {
            //spinner_business_list.setVisibility(View.VISIBLE);
            params =
                        "class_id="+finalClass_id+
                      "&business_id="+business_id_static+
                      "&session_id="+session_id+
                      "&user_id="+sharedPreferences.getString("user_id", "");
        }

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray session_list = jsonObject.getJSONArray("session_list");

                        pop_lists.clear();

                        for(int j = 0; j < session_list.length(); j++) {
                            JSONObject d = null;
                            try {
                                d = session_list.getJSONObject(j);

                                String classname = d.getString("classname");
                                String to_date_per = d.getString("to_date_per");
                                String from_date_per = d.getString("from_date_per");
                                String booked_up = d.getString("booked_up");
                                user_type = d.getString("user_type");

                                if (user_type.equals("unknown")){
                                    img_chat.setVisibility(View.GONE);
                                }else {
                                    img_chat.setVisibility(View.VISIBLE);

                                    img_chat.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                            //Toast.makeText(getActivity(),"chat under progress",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getActivity(),ChatActivity.class);
                                            intent.putExtra("class_name", finalClass_name);
                                            intent.putExtra("class_id", finalClass_id);
                                            getActivity().startActivity(intent);

                                        }
                                    });
                                }

                                JSONArray session_day_list = d.getJSONArray("session_day_list");

                                for(int k = 0; k < session_day_list.length(); k++) {
                                    JSONObject f = null;
                                    try {
                                        f = session_day_list.getJSONObject(k);

                                        final String teacher_name      = f.getString("teacher_name");
                                        String operating_day           = f.getString("operating_day");
                                        final String operating_day_per = f.getString("operating_day_per");
                                        String from_hour               = f.getString("from_hour");
                                        final String from_hour_per     = f.getString("from_hour_per");
                                        String duration                = f.getString("duration");
                                        final String duration_per      = f.getString("duration_per");
                                        final String student_gender    = f.getString("student_gender");
                                        String seats                   = f.getString("seats");
                                        final String seats_per         = f.getString("seats_per");
                                        String cost                    = f.getString("cost");
                                        final String cost_per          = f.getString("cost_per");

                                        Pop_List obj = new Pop_List
                                                (""+operating_day_per,""+duration_per,""+from_hour_per);

                                        pop_lists.add(obj);

                                        /* Toast.makeText(getActivity(),"value from = "+cost_per,Toast.LENGTH_SHORT).show();*/

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }


                                /* Toast.makeText(getActivity(),"value from = "+cost_per,Toast.LENGTH_SHORT).show();*/



                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        pop_list_adapter = new Pop_List_Adapter(getActivity(),pop_lists);

                        listView.setAdapter(pop_list_adapter);
                        setListViewHeightBasedOnChildren(listView);
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

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


}
