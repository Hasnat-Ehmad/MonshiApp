package info.androidhive.materialdesign.pagerfragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import info.androidhive.materialdesign.R;

import static info.androidhive.materialdesign.fragments.CalendarFragment.service_position;
import static info.androidhive.materialdesign.fragments.CalendarFragment.services;
import static info.androidhive.materialdesign.fragments.CalendarFragment.staff;
import static info.androidhive.materialdesign.fragments.CalendarFragment.staff_position;


public class DetailFragment extends Fragment {

Spinner spinner_service,spinner_staff;
public static EditText ed_description;

Button btn_countinue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        btn_countinue = rootView.findViewById(R.id.btn_countinue);
        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              ViewPager  viewPager = (ViewPager) getActivity().findViewById(
                        R.id.pager);

                viewPager.setCurrentItem(1);

            }
        });

        spinner_service = (Spinner) rootView.findViewById(R.id.spinner_service);
        setSpinner_service();
        spinner_staff   = (Spinner) rootView.  findViewById(R.id.spinner_staff);
        setSpinner_staff();
        ed_description = (EditText) rootView.findViewById(R.id.ed_description);


        return rootView;
    }

    void setSpinner_staff(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.my_spinner_style, staff) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                return v;
            }

            public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                View v =super.getDropDownView(position, convertView, parent);
                ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                return v;
            }
        };

        spinner_staff.setAdapter(adapter);

        spinner_staff.setSelection(staff_position);

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
                ((TextView) v).setBackgroundResource(R.drawable.grey_border);
                return v;
            }
        };

        spinner_service.setAdapter(adapter);

        spinner_service.setSelection(service_position);
    }
}

