package info.androidhive.materialdesign.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.MangementService_List_Adapter;
import info.androidhive.materialdesign.lists.ManagmentService_List;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;

/**
 * Created by Ravi on 29/07/15.
 */
public class ManagementClassFragment extends Fragment {

    ArrayList<ManagmentService_List> managmentService_lists =new ArrayList();

    ListView listView;

    MangementService_List_Adapter mangementService_list_adapter;

    ImageView img_add;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });


        // Inflate the layout for this fragment
        return rootView;
    }


}
