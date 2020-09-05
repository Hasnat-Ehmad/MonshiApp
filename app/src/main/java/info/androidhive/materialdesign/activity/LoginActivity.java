package info.androidhive.materialdesign.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.service.Notification_Service;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class LoginActivity extends AppCompatActivity {


    Button btn_login;
    SharedPreferences sharedPreferences;
    EditText ed_username,ed_password;


    String finish="  ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_password = (EditText) findViewById(R.id.ed_password);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null){
            finish = bundle.getString("finish");

            Toast.makeText(LoginActivity.this,finish,Toast.LENGTH_SHORT).show();
        }

        TextView tv_label_text_2 = (TextView) findViewById(R.id.tv_label_text_2);
        String activity_detail = "<span style='color:#00688B'><b>"+"ثبت نام"+"</b> </span>حساب کاربری ندارید؟ ";
        tv_label_text_2.setText(Html.fromHtml(activity_detail));

        tv_label_text_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);

            }
        });

        TextView tv_label_forget_password = findViewById(R.id.tv_label_forget_password);
        tv_label_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ed_username.getText().toString().equals("")){
                    ed_username.setError("empty");
                }
                else if (ed_password.getText().toString().equals("")){
                    ed_password.setError("empty");
                }

                else {

                    //http://192.168.100.14/monshiapp/app/login.php?username=owner1&password=123456
                    String url    =  getResources().getString(R.string.url)+"login.php";
                    String params =  "username="+ed_username.getText().toString()+"&password="+ed_password.getText().toString();

                    //Toast.makeText(getApplicationContext(),""+params,Toast.LENGTH_SHORT).show();
                    WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                        @Override
                        public void TaskCompletionResult(String result) {

                            try {

                                JSONObject jsonObject = new JSONObject(result);
                                if(jsonObject.getString("status").equals("200")) {

                                    //Toast.makeText(getApplicationContext(),""+jsonObject.getString("status"),Toast.LENGTH_SHORT).show();

                                    SavePreferences("fname"        ,jsonObject.getString("fname"        ));
                                    SavePreferences("lname"        ,jsonObject.getString("lname"        ));
                                    SavePreferences("full_name"    ,jsonObject.getString("full_name"    ));
                                    SavePreferences("email"        ,jsonObject.getString("email"        ));
                                    SavePreferences("phone"        ,jsonObject.getString("phone"        ));
                                    SavePreferences("address"      ,jsonObject.getString("address"      ));
                                    SavePreferences("user_id"      ,jsonObject.getString("user_id"      ));
                                    SavePreferences("user_status"  ,jsonObject.getString("user_status"  ));
                                    SavePreferences("user_role"    ,jsonObject.getString("user_role"    ));
                                    SavePreferences("user_name"    ,jsonObject.getString("user_name"    ));
                                    SavePreferences("person_image" ,jsonObject.getString("person_image" ));
                                    SavePreferences("staff_user_id",jsonObject.getString("staff_user_id"));
                                    SavePreferences("isLogin"      ,                            "yes"           );

                                    boolean check_login = true;

                                    if (jsonObject.getString("user_role"   ).equals("1")){

                                        if (finish.equals("finish")){
                                            check_login = false;
                                            finish();
                                           // Toast.makeText(LoginActivity.this,finish,Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (jsonObject.getString("user_role"   ).equals("2")){

                                        SavePreferences("business_id"  ,jsonObject.getString("business_id"   ));

                                        if (finish.equals("finish")){
                                            check_login = false;
                                            finish();
                                            //Toast.makeText(LoginActivity.this,finish,Toast.LENGTH_SHORT).show();
                                        }

                                    } else if (jsonObject.getString("user_role"   ).equals("3")){
                                        SavePreferences("business_id"  ,jsonObject.getString("business_id"   ));

                                        if (finish.equals("finish")){
                                            check_login = false;
                                            finish();

                                            //Toast.makeText(LoginActivity.this,finish,Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (jsonObject.getString("user_role"   ).equals("4")){

                                        if (finish.equals("finish")){
                                            check_login = false;
                                            finish();
                                            //Toast.makeText(LoginActivity.this,finish,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    if (check_login){
                                        Intent intent = new Intent(LoginActivity.this,MainActivity_bottom_view.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                        finish();
                                        startActivity(intent);
                                    }

                                }
                                else{
                                    Toast.makeText(LoginActivity.this,""+jsonObject.getString("status_alert"   ),Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    webRequestCall.execute(url, "POST", params);
                }
            }
        });
      }


    @Override
    protected void onResume() {
        super.onResume();

        int PERMISSION_ALL = 0;
        String[] PERMISSIONS = {
                //android.Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
               // android.Manifest.permission.BLUETOOTH,
               // android.Manifest.permission.BLUETOOTH_ADMIN,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
              //  android.Manifest.permission.RECORD_AUDIO
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(context,"loop",Toast.LENGTH_SHORT).show();
                    return true;
                }
            }
        }
        return true;
    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}
