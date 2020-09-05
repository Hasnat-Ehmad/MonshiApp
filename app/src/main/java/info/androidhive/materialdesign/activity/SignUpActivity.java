package info.androidhive.materialdesign.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.adapter.Dashboard_List_Adapter;
import info.androidhive.materialdesign.lists.Dashboard_List;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class SignUpActivity extends AppCompatActivity {

    EditText ed_username,ed_password,ed_confirm_password,ed_email,ed_code;

    Spinner spinner_code_method;

    Button btn_register;

    boolean dialog_box = false;

    ImageView img_back;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
        TextView tv_app_name;

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
        tv_app_name.setVisibility(View.VISIBLE);

        img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
        img_profile_tool_bar.setVisibility(View.INVISIBLE);

        img_notifications = mToolbar.findViewById(R.id.img_notifications);
        img_notifications.setVisibility(View.INVISIBLE);

        img_filter = mToolbar.findViewById(R.id.img_filter);
        img_filter.setVisibility(View.GONE);

        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });




        ed_username         = findViewById(R.id.ed_username);
        ed_password         = findViewById(R.id.ed_password);
        ed_confirm_password = findViewById(R.id.ed_confirm_password);
        ed_email            = findViewById(R.id.ed_email);
        ed_code             = findViewById(R.id.ed_code);
        ed_code.setVisibility(View.GONE);


        spinner_code_method = findViewById(R.id.spinner_code_method);

        spinner_code_method.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinner_code_method.getSelectedItem().toString().equals("موبائل")){

                    ed_code.setVisibility(View.VISIBLE);
                    ed_email.setVisibility(View.GONE);
                    dialog_box=true;

                }else {

                    ed_code.setVisibility(View.GONE);
                    ed_email.setVisibility(View.VISIBLE);
                    dialog_box=false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_username.getText().toString().equals("")){
                     ed_username.setError("Empty");

                }else if (ed_password.getText().toString().equals("")){
                     ed_password.setError("Empty");

                }else if (ed_confirm_password.getText().toString().equals("")){
                           ed_confirm_password.setError("Empty");

                } else  if (dialog_box) {

                    if (ed_code.getText().toString().equals("")) {
                        ed_code.setError("Empty");

                    } else {
                        //======================

//http://monshiapp.com/app/signup_app.php?username=hassanali&password=123456&cpassword=123456&email=evs.tester@gmail.com&verify=2
//&mobilenumber=090078601&verify=1
                        String url = getResources().getString(R.string.url) + "signup_app.php";

                        String params = "username="     +ed_username.getText().toString() +
                                        "&password="    +ed_password.getText().toString() +
                                        "&cpassword="   +ed_confirm_password.getText().toString() +
                                        "&mobilenumber="+ed_code.getText().toString() +
                                        "&verify=1";

                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if (jsonObject.getString("status").equals("200")) {


                                       final String user_id = jsonObject.getString("user_id");

                                        final AlertDialog.Builder alertbox = new AlertDialog.Builder(SignUpActivity.this);

                                        LinearLayout ll_alert_layout = new LinearLayout(SignUpActivity.this);
                                        ll_alert_layout.setOrientation(LinearLayout.VERTICAL);

                                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                                        layoutParams.setMargins(55, 0, 55, 0);

                                        final EditText ed_input = new EditText(SignUpActivity.this);
                                        ll_alert_layout.addView(ed_input, layoutParams);

                                        alertbox.setTitle("Verify Code");
                                        alertbox.setMessage("Enter your Code");

                                        //setting linear layout to alert dialog

                                        alertbox.setView(ll_alert_layout);

                                        alertbox.setNegativeButton("Resend Code",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(final DialogInterface arg0, int arg1) {

//Toast.makeText(SignUpActivity.this, "Resend Code", Toast.LENGTH_SHORT).show();
//http://192.168.100.14/monshiapp/app/resend_code_app.php?user_id=20&mobilenumber=03223565695

                                                        String url = getResources().getString(R.string.url) + "resend_code_app.php";

                                                        String params = "user_id=" + user_id+
                                                                        "mobilenumber=" + ed_code.getText().toString()
                                                                ;

                                                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                                            @Override
                                                            public void TaskCompletionResult(String result) {

                                                                try {

                                                                    JSONObject jsonObject = new JSONObject(result);
                                                                    if (jsonObject.getString("status").equals("200")) {
                                                                        Toast.makeText(getApplicationContext(),jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();

                                                                        arg0.cancel();

                                                                    } else {
                                                                        Toast.makeText(getApplicationContext(),jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                        webRequestCall.execute(url, "POST", params);


                                                        // will automatically dismiss the dialog and will do nothing

                                                    }
                                                });


                                        alertbox.setPositiveButton("Verify",
                                                new DialogInterface.OnClickListener() {

                                                    public void onClick(final DialogInterface arg0, int arg1) {

                                                        String input_text = ed_input.getText().toString();

                                                        //Toast.makeText(SignUpActivity.this, "Code = " + input_text, Toast.LENGTH_SHORT).show();

                                                        //http://192.168.100.14/monshiapp/app/verify_code_app.php?smscode=
                                                        String url = getResources().getString(R.string.url) + "verify_code_app.php";

                                                        String params = "smscode=" + input_text;

                                                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                                            @Override
                                                            public void TaskCompletionResult(String result) {

                                                                try {

                                                                    JSONObject jsonObject = new JSONObject(result);
                                                                    if (jsonObject.getString("status").equals("200")) {
                                                                        Toast.makeText(SignUpActivity.this,""+jsonObject.getString("status_alert"   ),Toast.LENGTH_SHORT).show();

                                                                        arg0.cancel();

                                                                    } else {
                                                                        Toast.makeText(SignUpActivity.this,""+jsonObject.getString("status_alert"   ),Toast.LENGTH_SHORT).show();

                                                                    }
                                                                } catch (JSONException e) {
                                                                    e.printStackTrace();
                                                                }
                                                            }
                                                        });
                                                        webRequestCall.execute(url, "POST", params);

                                                        // do your action with input string

                                                    }
                                                });
                                        alertbox.show();


                                    } else {

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        webRequestCall.execute(url, "POST", params);
                        //===========================
                    }

                } else {

                        if (ed_email.getText().toString().equals("")){
                            ed_email.setError("Empty");
                        }else {
// http://monshiapp.com/app/signup_app.php?username=hassanali&password=123456&cpassword=123456&email=evs.tester@gmail.com&verify=2
                            //&mobilenumber=090078601&verify=1
                            String url    =  getResources().getString(R.string.url)+"signup_app.php";

                            String  params =  "username="+ed_username.getText().toString()+
                                    "&password="+ed_password.getText().toString()+
                                    "&cpassword="+ed_confirm_password.getText().toString()+
                                    "&email="+ed_email.getText().toString()+
                                    "&verify=2"
                                    ;

                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {
                                            Toast.makeText(getApplicationContext(),jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),jsonObject.getString("status_alert"),Toast.LENGTH_SHORT).show();

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            webRequestCall.execute(url, "POST", params);

                        }
                    }
                }
        });
    }
}
