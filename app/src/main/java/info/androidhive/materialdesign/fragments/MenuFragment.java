package info.androidhive.materialdesign.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.activity.LoginActivity;
import info.androidhive.materialdesign.activity.MenuActivity;
import info.androidhive.materialdesign.activity.NonLogin_Activity;
import info.androidhive.materialdesign.activity.SignUpActivity;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.service_notification;
import static info.androidhive.materialdesign.activity.MenuActivity.toolbar_menu;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SharedPreferences sharedPreferences;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentActivity fragmentActivity;
    private OnFragmentInteractionListener mListener;

    public MenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Objects.requireNonNull(getActivity()).getApplicationContext());


        View root =  inflater.inflate(R.layout.fragment_menu, container, false);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            TextView tv_app_name = toolbar_menu.findViewById(R.id.tv_app_name);
            tv_app_name.setText("MonshiApp");
            tv_app_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closefragment();
                }
            });
        }

        fragmentActivity = (FragmentActivity) getActivity();

        TextView tv_signin = root.findViewById(R.id.tv_signin);
        TextView tv_signup = root.findViewById(R.id.tv_signup);
        TextView tv_contact_us = root.findViewById(R.id.tv_contact_us);
        TextView tv_privacy_policy = root.findViewById(R.id.tv_privacy_policy);
        TextView tv_logout = root.findViewById(R.id.tv_logout);
        TextView tv_tutorial = root.findViewById(R.id.tv_tutorial);

        tv_signin.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SignUpActivity.class);
                Objects.requireNonNull(getActivity()).startActivity(intent);
            }
        });
        tv_contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact_us_Fragment tab1= new Contact_us_Fragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();
            }
        });
        tv_privacy_policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Privacy_Policy_Fragment tab1= new Privacy_Policy_Fragment();
                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, tab1)
                        .addToBackStack(null)   // this will be it to the back stack
                        .commit();
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();
                SavePreferences("fname"        ,"");
                SavePreferences("lname"        ,"");
                SavePreferences("full_name"    ,"");
                SavePreferences("email"        ,"");
                SavePreferences("phone"        ,"");
                SavePreferences("address"      ,"");
                SavePreferences("user_id"      ,"");
                SavePreferences("user_status"  ,"");
                SavePreferences("user_role"    ,"");
                SavePreferences("staff_user_id","");
                SavePreferences("class_id"     ,"");
                SavePreferences("session_id"   ,"");
                SavePreferences("last_notification_id","0");

                SavePreferences("isLogin"      ,"no");

               // Toast.makeText(getActivity(),"Logout",Toast.LENGTH_SHORT).show();


                //getActivity().stopService(service_notification);

                Intent intent = new Intent(getActivity(),NonLogin_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(intent);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Objects.requireNonNull(getActivity()).finish();
                }

            }
        });

        tv_tutorial.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //Tutorial page under progress
            }
        });

        if(!sharedPreferences.contains("isLogin") || sharedPreferences.getString("isLogin","").equals("no")){
            tv_logout.setVisibility(View.GONE);
            tv_signup.setVisibility(View.VISIBLE);
            tv_signin.setVisibility(View.VISIBLE);
        }else{
            tv_logout.setVisibility(View.VISIBLE);
            tv_signup.setVisibility(View.GONE);
            tv_signin.setVisibility(View.GONE);
        }


        return  root;
    }
    private void closefragment() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).finish();
            Objects.requireNonNull(getActivity()).getSupportFragmentManager().popBackStack();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Objects.requireNonNull(getActivity()).finish();
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
