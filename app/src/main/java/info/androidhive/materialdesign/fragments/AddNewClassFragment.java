package info.androidhive.materialdesign.fragments;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Add_Class_Session_List_Adapter;
import info.androidhive.materialdesign.adapter.Class_Session_List_Adapter;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.adapter.NewServiceStaff_List_Adapter;
import info.androidhive.materialdesign.lists.Add_Class_Session_List;
import info.androidhive.materialdesign.lists.Class_Session_List;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.lists.ManagmentService_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.fragments.ClassListFragment.new_class_id;


public class AddNewClassFragment extends Fragment {

    ArrayList<ManagmentService_List> managmentService_lists =new ArrayList();//using previous classes

    NewServiceStaff_List_Adapter newServiceStaff_list_adapter;
    SharedPreferences sharedPreferences;

    ArrayList<Add_Class_Session_List> add_class_session_lists =new ArrayList();

    ListView listView;

    Add_Class_Session_List_Adapter addClassSessionListAdapter;

    EditText ed_class_name,ed_class_description;

    Spinner spinner_pay_online;

    Button btn_submit,btn_cancel;
    ImageView add_session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_new_class, container, false);

//        Toast.makeText(getActivity(),"onCreate",Toast.LENGTH_LONG).show();

        btn_submit = rootView.findViewById(R.id.btn_submit);

        btn_cancel = rootView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getFragmentManager().popBackStack();
            }
        });

        spinner_pay_online = rootView.findViewById(R.id.spinner_pay_online);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.service_payonline_yes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_pay_online.setAdapter(adapter);

        ed_class_name        = rootView.findViewById(R.id.ed_class_name);
        ed_class_description = rootView.findViewById(R.id.ed_class_description);

        add_session  = rootView.findViewById(R.id.add_session);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        listView = (ListView) rootView.findViewById(R.id.list_view);

//        Bundle b = getArguments();
//        if (!b.isEmpty()){
//            new_class_id = b.getString("id");
//        }
//        b.clear();

        //Toast.makeText(getActivity(),"new_class_id = "+new_class_id,Toast.LENGTH_SHORT).show();
        Log.d("TAG", "File...:::: new_class_id =" + new_class_id);
        if (!new_class_id.equals("")){

            add_session.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ed_class_name.getText().toString().equals("")){
                        ed_class_name.setError("Empty");
                    }else {
                        String role = "";
                        if (spinner_pay_online.getSelectedItem().toString().equals("yes") ||
                                spinner_pay_online.getSelectedItem().toString().equals("no")) {

                            if (spinner_pay_online.getSelectedItem().toString().equals("yes")) {
                                role = "1";
                            } else {
                                role = "0";
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("class_name_with_id",ed_class_name.getText().toString());
                            bundle.putString("class_payment_with_id",role);
                            bundle.putString("class_id",new_class_id);
                            bundle.putString("class_note",""+ed_class_description.getText().toString());

                            AddNewSession addNewSession = new AddNewSession();
                            addNewSession.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_layout, addNewSession);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else {
                            Toast.makeText(getActivity(),getString(R.string.str_online_payment),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });


            //Class info detail
            //http://192.168.100.14/monshiapp/app/class_info_detail.php?user_id=1&bus_id=1&class_id=22
            String url    =  getResources().getString(R.string.url)+"class_info_detail.php";

            String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                    "&class_id="+new_class_id ;

            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                @Override
                public void TaskCompletionResult(String result) {

                    try {

                        JSONObject jsonObject = new JSONObject(result);
                        if(jsonObject.getString("status").equals("200")) {

                            add_class_session_lists.clear();

                            String class_name = jsonObject.getString("class_name");
                            String role       = jsonObject.getString("role");
                            String description= jsonObject.getString("description");

                            ed_class_name.setText(""+class_name);
                            ed_class_description.setText(""+description);

                            if (role.equals("0")){
                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.service_payonline_yes, android.R.layout.simple_spinner_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_pay_online.setAdapter(adapter);

                                spinner_pay_online.setSelection(2);
                            }else {
                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.service_payonline_yes, android.R.layout.simple_spinner_item);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinner_pay_online.setAdapter(adapter);
                                spinner_pay_online.setSelection(1);
                            }

                            JSONArray session_listing = jsonObject.getJSONArray("session_listing");

                            for(int i = 0; i < session_listing.length(); i++) {
                                JSONObject c = session_listing.getJSONObject(i);

                                String session_id    = c.getString("session_id");
                                String s_classid     = c.getString("s_classid" );
                                String s_staff_id   = c.getString("s_staff_id");
                                String booking_total = c.getString("booking_total");
                                String s_slot = c.getString("s_slot");
                                String s_cost = c.getString("s_cost");
                                String date   = c.getString("date");

                                Add_Class_Session_List obj = new Add_Class_Session_List
                                        (""+session_id,""+s_classid,""+s_staff_id,""+booking_total,
                                                ""+s_slot,""+s_cost,""+date);

                                add_class_session_lists.add(obj);

                                //Toast.makeText(getActivity(),"user_type = "+user_type,Toast.LENGTH_SHORT).show();

                            }
                            if (getActivity()!=null){
                                addClassSessionListAdapter = new Add_Class_Session_List_Adapter(getActivity(),add_class_session_lists);

                                listView.setAdapter(addClassSessionListAdapter);
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

 /*

$user_id //compulsory
$business_id //compulsory

$class_id //compulsory incase of edit
$class_name //compulsory
$class_note
$roll //1 for yes , 0 for no ---- compulsory

$cost //compulsory if session_add=yes
$gender //compulsory if session_add=yes male,female,both
$staff_id //compulsory if session_add=yes
$session_add //if session add yes else no compulsory

$from_date //compulsory if session_add=yes
$to_date //compulsory if session_add=yes
$from_time //compulsory if session_add=yes , separated array
$service_duration //compulsory if session_add=yes , separated array
$check_list //compulsory if session_add=yes , separated array

             */

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String role = "";
                    if (ed_class_name.getText().toString().equals("")){
                        ed_class_name.setError("Empty");

                    }else {

                        if (spinner_pay_online.getSelectedItem().toString().equals("yes") || spinner_pay_online.getSelectedItem().toString().equals("no")){

                            if (spinner_pay_online.getSelectedItem().toString().equals("yes")){
                                role = "1";
                            }else {
                                role = "0";
                            }

                            final String finalRole = role;

                            //http://192.168.100.14/monshiapp/app/add_edit_class.php
                            String url    =  getResources().getString(R.string.url)+"add_edit_class.php";
                            String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                                    "&class_id="+new_class_id+
                                    "&class_name="+ed_class_name.getText().toString()+
                                    "&class_note="+ed_class_description.getText().toString()+
                                    "&session_add=no"+
                                    "&roll="+ finalRole
                                    ;

                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {

                                            Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_LONG).show();
                                            getActivity().getSupportFragmentManager().popBackStack();

                                        }
                                        else{

                                            Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_LONG).show();
                                            getActivity().getSupportFragmentManager().popBackStack();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            webRequestCall.execute(url, "POST", params);

                        }else {
                            Toast.makeText(getActivity(),"Select online payment",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });


        }
        else {

            /*

$user_id //compulsory
$business_id //compulsory

$class_id //compulsory incase of edit
$class_name //compulsory
$class_note
$roll //1 for yes , 0 for no ---- compulsory

$cost //compulsory if session_add=yes
$gender //compulsory if session_add=yes male,female,both
$staff_id //compulsory if session_add=yes
$session_add //if session add yes else no compulsory

$from_date //compulsory if session_add=yes
$to_date //compulsory if session_add=yes
$from_time //compulsory if session_add=yes , separated array
$service_duration //compulsory if session_add=yes , separated array
$check_list //compulsory if session_add=yes , separated array

             */


            add_session.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ed_class_name.getText().toString().equals("")){
                        ed_class_name.setError("Empty");
                    }
                    else {
                        String role = "";
                        if (spinner_pay_online.getSelectedItem().toString().equals("yes") ||
                                spinner_pay_online.getSelectedItem().toString().equals("no")) {

                            if (spinner_pay_online.getSelectedItem().toString().equals("yes")) {
                                role = "1";
                            } else {
                                role = "0";
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("class_name_no_id",ed_class_name.getText().toString());
                            bundle.putString("class_payment_no_id",role);
                            bundle.putString("class_id","");
                            bundle.putString("class_note",""+ed_class_description.getText().toString());

                            AddNewSession addNewSession = new AddNewSession();
                            addNewSession.setArguments(bundle);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_layout, addNewSession);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                        }else {
                            Toast.makeText(getActivity(),getString(R.string.str_online_payment),Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });

            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String role = "";
                    if (ed_class_name.getText().toString().equals("")){
                        ed_class_name.setError("Empty");

                    }else {

                        if (spinner_pay_online.getSelectedItem().toString().equals("yes") ||
                                spinner_pay_online.getSelectedItem().toString().equals("no")){

                            if (spinner_pay_online.getSelectedItem().toString().equals("yes")){
                                role = "1";
                            }else {
                                role = "0";
                            }

                            final String finalRole = role;
                            //http://192.168.100.14/monshiapp/app/add_edit_class.php
                            String url    =  getResources().getString(R.string.url)+"add_edit_class.php";
                            String params =  "user_id="+sharedPreferences.getString("user_id", "")+
                                    "&business_id="+sharedPreferences.getString("selected_business", "")+
                                    "&class_name="+ed_class_name.getText().toString()+
                                    "&class_note="+ed_class_description.getText().toString()+
                                    "&session_add=no"+
                                    "&roll="+ finalRole
                                    ;

                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {

                                            Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_LONG).show();
                                            getActivity().getSupportFragmentManager().popBackStack();

                                        }
                                        else{
                                            Toast.makeText(getActivity(),""+jsonObject.getString("status_alert"),Toast.LENGTH_LONG).show();
                                            getActivity().getSupportFragmentManager().popBackStack();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            webRequestCall.execute(url, "POST", params);

                        }else {
                            Toast.makeText(getActivity(),"Select online payment",Toast.LENGTH_LONG).show();
                        }
                    }

                }
            });

        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
  //      Toast.makeText(getActivity(),"onResume",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        new_class_id = "";
    //    Toast.makeText(getActivity(),"onDestroy",Toast.LENGTH_LONG).show();
    }
}
