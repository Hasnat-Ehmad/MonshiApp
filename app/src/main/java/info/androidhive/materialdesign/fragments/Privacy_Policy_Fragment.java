package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;
import static info.androidhive.materialdesign.activity.MenuActivity.toolbar_menu;


public class Privacy_Policy_Fragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*tv_title.setText(getString(R.string.str_privacy_policy));

        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });*/

        //commented by hassan
//        TextView tv_app_name = toolbar_menu.findViewById(R.id.tv_app_name);
//        tv_app_name.setText("Privacy Policy");
//        tv_app_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    Objects.requireNonNull(getFragmentManager()).popBackStack();
//                }
//            }
//        });

        Toolbar mToolbar;
        ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.GONE);
        tv_app_name.setText(getActivity().getResources().getString(R.string.app_name));

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.INVISIBLE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.VISIBLE);


        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.INVISIBLE);

        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });



        View rootView = inflater.inflate(R.layout.fragment_privacy_policy, container, false);

        final TextView tv_privacy_policy = rootView.findViewById(R.id.tv_privacy_policy);

        //http://192.168.100.14/monshiapp/app/cms.php?type=4 //privacy policy
        String url    =  getResources().getString(R.string.url)+"cms.php";

        String params = "type=4";

        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) {

                try {

                    JSONObject jsonObject = new JSONObject(result);
                    if(jsonObject.getString("status").equals("200")) {

                        String privacy_detail = jsonObject.getString("privacy_detail");

                        tv_privacy_policy.setText(Html.fromHtml(""+privacy_detail));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        webRequestCall.execute(url, "POST", params);


        return rootView;
    }


}
