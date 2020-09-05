package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Class_Session_List_Adapter;
import info.androidhive.materialdesign.lists.Class_Session_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class ClassSessionListActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    ImageView img_back;

    private Toolbar mToolbar;

    ArrayList<Class_Session_List> class_session_lists =new ArrayList();

    ListView listView;

    Class_Session_List_Adapter class_session_list_adapter;

    String class_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_session_list);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras!=null){
            class_id = extras.getString("id");
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        listView = (ListView) findViewById(R.id.list_view);

        //http://192.168.100.14/monshiapp/app/class_session_list.php?business_id=1&class_id=83
        String url    =  getResources().getString(R.string.url)+"class_session_list.php";

        String params =  "business_id="+sharedPreferences.getString("selected_business", "")+
                "&class_id="+class_id ;

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    class_session_lists.clear();

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {
                        JSONArray session_list = jsonObject.getJSONArray("session_list");

                        for(int i = 0; i < session_list.length(); i++) {
                            JSONObject c = session_list.getJSONObject(i);
                            String session_id = c.getString("id");
                            String classname = c.getString("classname");
                            String to_date_per = c.getString("to_date_per");
                            String from_date_per = c.getString("from_date_per");
                            String teacher_name = c.getString("teacher_name");
                            String user_type = c.getString("user_type");

                            JSONArray session_day_list = c.getJSONArray("session_day_list");

                            Class_Session_List obj = new Class_Session_List
                                    (""+session_id,"جلسه "+i+1 ,""+classname , ""+class_id,""+to_date_per,""+from_date_per,""+teacher_name,session_day_list);

                            class_session_lists.add(obj);

                            //Toast.makeText(getActivity(),"user_type = "+user_type,Toast.LENGTH_SHORT).show();

                        }
                        if (ClassSessionListActivity.this != null){
                            class_session_list_adapter = new Class_Session_List_Adapter(ClassSessionListActivity.this,class_session_lists);

                            listView.setAdapter(class_session_list_adapter);
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

    }
}
