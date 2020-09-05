package info.androidhive.materialdesign.staff_work;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.materialdesign.R;


public class Account_Fragment extends Fragment {

    EditText ed_staff_name,ed_staff_address,ed_staff_username,ed_staff_email,ed_staff_contact;

    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.fragment_account, container, false);


        Toolbar mToolbar;
        ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);
        tv_app_name.setText(getActivity().getResources().getString(R.string.app_name));

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.GONE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);

        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);

        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.GONE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        ed_staff_name = rootView.findViewById(R.id.ed_staff_name);
        ed_staff_address = rootView.findViewById(R.id.ed_staff_address);
        ed_staff_username = rootView.findViewById(R.id.ed_staff_username);
        ed_staff_email = rootView.findViewById(R.id.ed_staff_email);
        ed_staff_contact = rootView.findViewById(R.id.ed_staff_contact);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        ed_staff_name.setText(""+sharedPreferences.getString("full_name", ""));
        ed_staff_email.setText(""+sharedPreferences.getString("email", ""));
        ed_staff_contact.setText(""+sharedPreferences.getString("phone", ""));
        ed_staff_address.setText(""+sharedPreferences.getString("address", ""));
        return rootView;
    }

}
