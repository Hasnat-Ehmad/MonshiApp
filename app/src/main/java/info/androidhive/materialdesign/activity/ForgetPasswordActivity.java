package info.androidhive.materialdesign.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

public class ForgetPasswordActivity extends AppCompatActivity {

    ImageView img_back;

    private Toolbar mToolbar;
    int verifyTypw = 0;

    EditText ed_email_address;
    TextView tv_label_login;
    Button btn_submit;
    Spinner spinner_code_method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        img_back = mToolbar.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ed_email_address = findViewById(R.id.ed_email_address);
        tv_label_login = findViewById(R.id.tv_label_login);
        btn_submit = findViewById(R.id.btn_submit);

        spinner_code_method = findViewById(R.id.spinner_code_method);

        tv_label_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed_email_address.getText().toString().equals(""))
                    {
                        ed_email_address.setError("Empty");
                    }
                else
                    {
                        if (spinner_code_method.getSelectedItem().toString().equals("موبائل"))
                        {
                            verifyTypw= 1;
                        }

                    //http://192.168.100.14/monshiapp/app/forgot_password_app.php?email=
                    String url = getResources().getString(R.string.url) + "forgot_password_app.php";
                    String params = "email=" + ed_email_address.getText().toString()+"&verify=" + verifyTypw;
                    WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                        @Override
                        public void TaskCompletionResult(String result) {

                            try {

                                JSONObject jsonObject = new JSONObject(result);
                                if (jsonObject.getString("status").equals("200")) {
                                    ed_email_address.setText("");
                                    Toast.makeText(ForgetPasswordActivity.this,""+jsonObject.getString("status_alert"   ),Toast.LENGTH_SHORT).show();
                                    //finish();

                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this,""+jsonObject.getString("status_alert"   ),Toast.LENGTH_SHORT).show();
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
}
