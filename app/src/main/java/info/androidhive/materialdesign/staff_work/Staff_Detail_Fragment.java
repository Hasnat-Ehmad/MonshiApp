package info.androidhive.materialdesign.staff_work;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import info.androidhive.materialdesign.R;

import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.service_amount;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.service_position;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.service_time;
import static info.androidhive.materialdesign.pagerfragments.BusinessBookAppointmentFragment.services;


public class Staff_Detail_Fragment extends Fragment {
    Spinner spinner_service;
    public static EditText ed_description;
    Button btn_countinue;
    SharedPreferences sharedPreferences;

    TextView tv_label_continous_booking,tv_label_time,tv_minute_service,tv_amount_service;

    ToggleButton toggle_repeat;
    LinearLayout layout_repeat,layout_dummy_lines;

    Spinner spinner_recursive_duration,spinner_recursive_booking;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_staff_detail, container, false);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        spinner_service = (Spinner) rootView.findViewById(R.id.spinner_service);
        setSpinner_service();

        ed_description = (EditText) rootView.findViewById(R.id.ed_description);

        //=====


        spinner_recursive_duration = rootView.findViewById(R.id.spinner_recursive_duration);//1 to 7
        spinner_recursive_booking  = rootView.findViewById(R.id.spinner_recursive_booking);//1 to 4

        tv_label_continous_booking = rootView.findViewById(R.id.tv_label_continous_booking);
        toggle_repeat = rootView.findViewById(R.id.toggle_repeat);

        layout_repeat = rootView.findViewById(R.id.layout_repeat);
        layout_dummy_lines = rootView.findViewById(R.id.layout_dummy_lines);

        tv_label_time = rootView.findViewById(R.id.tv_label_time);

        tv_minute_service = rootView.findViewById(R.id.tv_minute_service);
        tv_minute_service.setText(""+service_time[service_position]);
        tv_amount_service = rootView.findViewById(R.id.tv_amount_service);
        tv_amount_service.setText(""+service_amount[service_position]);

        SavePreferences("recursive_check"        ,"no");

        SavePreferences("recursive_duration"        ,"");
        SavePreferences("recursive_booking"        ,"1");


        toggle_repeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Toast.makeText(getActivity(),isChecked+"",Toast.LENGTH_SHORT).show();
                if (isChecked) {

                    SavePreferences("recursive_check"        ,"yes");

                    SavePreferences("recursive_duration"        ,""+(spinner_recursive_duration.getSelectedItemPosition()+1));
                    SavePreferences("recursive_booking"        ,""+(spinner_recursive_booking.getSelectedItemPosition()+1));

                    tv_label_time.setText("روز");

//                    Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                                                        ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_duration = " +sharedPreferences.getString("recursive_duration", "")
//                                                        ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_booking = " +sharedPreferences.getString("recursive_booking", "")
//                                                         ,Toast.LENGTH_SHORT).show();

                    layout_repeat.setVisibility(View.VISIBLE);
                    layout_dummy_lines.setVisibility(View.VISIBLE);
                } else {

                    SavePreferences("recursive_check"        ,"no");

                    SavePreferences("recursive_duration"        ,"");
                    SavePreferences("recursive_booking"        ,"1");

//                    Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                            ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_duration = " +sharedPreferences.getString("recursive_duration", "")
//                            ,Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"recursive_booking = " +sharedPreferences.getString("recursive_booking", "")
//                            ,Toast.LENGTH_SHORT).show();

                    layout_repeat.setVisibility(View.GONE);
                    layout_dummy_lines.setVisibility(View.GONE);
                }
            }
        });

        spinner_recursive_duration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SavePreferences("recursive_duration"        ,""+(position+1));
//
//                Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                        ,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),"recursive_duration = " +sharedPreferences.getString("recursive_duration", "")
//                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_recursive_booking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SavePreferences("recursive_booking"        ,""+(position+1));

                if (position+1==1){
                    tv_label_time.setText("روز");
                }
                if (position+1==2){
                    tv_label_time.setText("هفته");
                }
                if (position+1==3){
                    tv_label_time.setText("ماه");
                }
                if (position+1==4){
                    tv_label_time.setText("سال");
                }

//                Toast.makeText(getActivity(),"recursive_check = "+sharedPreferences.getString("recursive_check", "")
//                        ,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(),"recursive_booking = " +sharedPreferences.getString("recursive_booking", "")
//                        ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //====


        btn_countinue = rootView.findViewById(R.id.btn_countinue);
        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = (ViewPager) getActivity().findViewById(
                        R.id.pager);

                viewPager.setCurrentItem(1);
            }
        });

        return rootView;

    }

    void setSpinner_service(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.my_spinner_style, services) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                return v;
            }

            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                //((TextView) v).setBackgroundResource(R.drawable.grey_border);
                return v;
            }
        };

        spinner_service.setAdapter(adapter);

        spinner_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                service_position = position;
                // Toast.makeText(getActivity().getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinner_service.setSelection(service_position);
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
