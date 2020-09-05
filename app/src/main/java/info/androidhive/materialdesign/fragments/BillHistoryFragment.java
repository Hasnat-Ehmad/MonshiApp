package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.BillHistory_List_Adapter;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.BillHistory_List;
import info.androidhive.materialdesign.lists.Dashboard_List;

import static info.androidhive.materialdesign.activity.MainActivity.spinner_business_list;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;

public class BillHistoryFragment extends Fragment {

    ArrayList<BillHistory_List> billHistory_lists =new ArrayList();

    ListView listView;

    BillHistory_List_Adapter billHistory_list_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      /*  spinner_business_list.setVisibility(View.VISIBLE);*/
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bill_history, container, false);

        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        img_notifications.setVisibility(View.VISIBLE);

        listView = (ListView) rootView.findViewById(R.id.list_view);


        for (int i=0 ; i<10 ; i++){

            //addind data in Order_list from api
            BillHistory_List obj = new BillHistory_List
                    ("","","","","","");

            billHistory_lists.add(obj);
        }
        billHistory_list_adapter = new BillHistory_List_Adapter(getActivity(),billHistory_lists);

        listView.setAdapter(billHistory_list_adapter);


        return rootView;
    }


}
