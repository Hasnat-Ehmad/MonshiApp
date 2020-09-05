package info.androidhive.materialdesign.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.json.JSONException;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.ChatFragment;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class ChatActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Intent intent = getIntent();
        String class_name = intent.getStringExtra("class_name");
        showFragment(class_name);


    }
    private void showFragment(String class_name)
    {
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//
//        ft.replace(R.id.frameLayout, ChatFragment.newInstance(getIntent().getExtras()));
//        ft.commit();
//        getFragmentManager()
//                .beginTransaction()
//                .replace(R.id.frameLayout, ft)
//                .commit();

        Fragment fragment  = ChatFragment.newInstance(getIntent().getExtras());

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction.commit();

    }


}
