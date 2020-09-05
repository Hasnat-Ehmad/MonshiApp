package info.androidhive.materialdesign.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Message_List_Adapter;
import info.androidhive.materialdesign.lists.Message_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;


/**
 * Created by vihaan on 22/05/17.
 */

public class ChatFragment extends Fragment {
    SharedPreferences sharedPreferences;
    ArrayList<Message_List> message_ArrayList =new ArrayList();
    Message_List_Adapter message_list_adapter;
    String class_name;
    public static ChatFragment newInstance(Bundle bundle) {
        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


Handler mhander;
    Runnable mrunnable;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        class_name = bundle.getString("class_name");
        //Toast.makeText(getActivity(),class_name,Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        ImageView img_profile = toolbar.findViewById(R.id.img_profile);
        img_profile.setVisibility(View.GONE);

        TextView tv_app_name = toolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setText(class_name+" "+getString(R.string.str_discussion));


        ImageView img_back = toolbar.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        loadChat("first");
        //loadChats();
    }

    private void initViews() {
        initMessageBar();
        initListView();
    }


    private FloatingActionButton mFabButton;
    private EditText mEditText;

    private void initMessageBar() {
        mEditText = (EditText) getView().findViewById(R.id.editText);
        mEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    showSendButton();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                   // showAudioButton();
                }
            }
        });


        mFabButton = (FloatingActionButton) getView().findViewById(R.id.floatingButton);
        mFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String tag = (String) mFabButton.getTag();
                Log.d("fab tag", tag);
                if (tag.equalsIgnoreCase(SEND_IMAGE)) {
                    onSendButtonClicked();
                }

            }
        });
    }

    private ListView mListView;

    private void initListView() {
        mListView = (ListView) getView().findViewById(R.id.chatsListView);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        //linearLayoutManager.setStackFromEnd(true);
        //mRecyclerView.setLayoutManager(linearLayoutManager);
    }


    private static final String SEND_IMAGE = "send_image";
    String last_id = "0";

    private void showSendButton() {
        mFabButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.input_send));
        mFabButton.setTag(SEND_IMAGE);
    }

    private void onSendButtonClicked() {
        String message = mEditText.getText().toString();
        mEditText.setText("");
        Log.d("send msg", message);
        if(!message.equals(""))
            writeTextMessage(message);
        else
            Toast.makeText(getActivity(),"Message must not be empty",Toast.LENGTH_SHORT).show();
    }

    private void loadChat(final String data_check){
        String url = getResources().getString(R.string.url)+"recieve_message.php";
        String params = "last_id="+last_id+"&class_id="+sharedPreferences.getString("class_id", "")+"&user_id="+sharedPreferences.getString("user_id", "");
        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) throws JSONException {

                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getString("status").equals("200")) {

                    //message_ArrayList.clear();
                    JSONArray jsonArray = jsonObject.getJSONArray("message_listing");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        last_id = c.getString("id");
                        String image = c.getString("image");
                        String name = c.getString("name");
                        String date_time = c.getString("date_time");
                        String message = c.getString("message");
                        String sender = c.getString("sender");


                        Message_List message_list = new Message_List(""+image,""+name,""+date_time,""+message,""+sender);
                        message_ArrayList.add(message_list);
                    }

                    if(data_check.equals("first")){
                        message_list_adapter = new Message_List_Adapter(getActivity(),message_ArrayList);

                        if (getActivity()!=null)
                        mListView.setAdapter(message_list_adapter);

                        int SPLASH_TIME_OUT = 2000;
                        final int[] i = {0};
                        final Handler handler = new Handler();
                        Runnable runnable1  = null;
                        final Runnable finalRunnable = runnable1;
                        runnable1 = new Runnable() {

                            /*
                             * Showing splash screen with a timer. This will be useful when you
                             * want to show case your app logo / company
                             */

                            @Override
                            public void run() {
                                mListView.smoothScrollToPosition(message_list_adapter.getCount() - 1);

                                if(i[0] > 0){
                                    handler.removeCallbacks(finalRunnable);
                                }
                                i[0]++;
                            }
                        };
                        handler.postDelayed(runnable1, SPLASH_TIME_OUT);
                    }else{

                        message_list_adapter.notifyDataSetChanged();
                        mListView.smoothScrollToPosition(message_list_adapter.getCount() - 1);
                    }
                    //Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.str_valid_business_id),Toast.LENGTH_SHORT).show();

                }
            }
        });
        webRequestCall.execute(url, "POST", params);


        int SPLASH_TIME_OUT = 5000;
       mhander = new Handler();
       mrunnable = new Runnable() {

           /*
            * Showing splash screen with a timer. This will be useful when you
            * want to show case your app logo / company
            */

           @Override
           public void run() {
               loadChat("second");
           }
       };
              mhander.postDelayed(mrunnable, SPLASH_TIME_OUT);

    }

    private void writeTextMessage(final String message){

        String sender_type = "";
        if(sharedPreferences.getString("user_role", "").equals("1")){
            sender_type = "teacher";
        }else{
            sender_type = "student";
        }

        String url = getResources().getString(R.string.url)+"send_message.php";
        String params = "class_id="+sharedPreferences.getString("class_id", "")+"&sender_id="+sharedPreferences.getString("user_id", "")+"&sender_type="+sender_type+"&msg="+message;
        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
            @Override
            public void TaskCompletionResult(String result) throws JSONException {


                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getString("status").equals("200")) {

                    String image ="";
                    String name = "";
                    String date_time =jsonObject.getString("date_time");
                    String message1 = jsonObject.getString("message");
                    String sender = "own";

                    Message_List message_list = new Message_List(""+image,""+name,""+date_time,""+message1,""+sender);

                    //message_ArrayList.add(message_list);
                    //message_list_adapter.notifyDataSetChanged();
                    //mListView.smoothScrollToPosition(message_list_adapter.getCount()-1);
                }


            }
        });
        webRequestCall.execute(url,"POST",params);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mhander.removeCallbacks(mrunnable);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mhander.removeCallbacks(mrunnable);
    }
}
