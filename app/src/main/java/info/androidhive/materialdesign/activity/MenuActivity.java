package info.androidhive.materialdesign.activity;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.fragments.MenuFragment;

public class MenuActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener {

    public static Toolbar toolbar_menu;
    public static ImageView img_back;
    public static TextView tv_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        toolbar_menu = findViewById(R.id.toolbar);


        FragmentManager fm = getSupportFragmentManager();
        fm.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(getSupportFragmentManager().getBackStackEntryCount() == 0) finish();
            }
        });

        MenuFragment tab1= new MenuFragment();
        fm.beginTransaction()
                .replace(R.id.frame_layout, tab1)
                .addToBackStack(null)   // this will be it to the back stack
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
