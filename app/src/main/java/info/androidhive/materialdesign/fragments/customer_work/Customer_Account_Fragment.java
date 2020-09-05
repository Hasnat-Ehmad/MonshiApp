package info.androidhive.materialdesign.fragments.customer_work;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.webservice.JSONParser_Update_Customer;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.tv_title;


public class Customer_Account_Fragment extends Fragment {

    SharedPreferences sharedPreferences;
EditText ed_customer_name,ed_customer_password,ed_customer_fullname,ed_customer_email,ed_customer_mobile;
Button btn_submit,btn_addphoto;

ImageView img_photo;

String image_real_path="";

JSONParser_Update_Customer jsonParser_update_customer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
            Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            ImageView img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.VISIBLE);

            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        Objects.requireNonNull(getFragmentManager()).popBackStack();
                    }
                }
            });
        }else {

            Toolbar mToolbar;ImageView img_back,img_profile_tool_bar,img_notifications,img_filter;
            TextView tv_app_name;

            mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);

            tv_app_name = mToolbar.findViewById(R.id.tv_app_name);
            tv_app_name.setVisibility(View.GONE);
            tv_app_name.setText(getActivity().getResources().getString(R.string.app_name));

            img_profile_tool_bar = mToolbar.findViewById(R.id.img_profile);
            img_profile_tool_bar.setVisibility(View.GONE);

            img_notifications = mToolbar.findViewById(R.id.img_notifications);
            img_notifications.setVisibility(View.VISIBLE);

            img_filter = mToolbar.findViewById(R.id.img_filter);
            img_filter.setVisibility(View.GONE);

            img_back = mToolbar.findViewById(R.id.img_back);
            img_back.setVisibility(View.GONE);
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getFragmentManager().popBackStack();
                }
            });
        }

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_customer_account, container, false);
        //View rootView = inflater.inflate(R.layout.fragment_add_new_customer, container, false);



        img_photo = rootView.findViewById(R.id.img_photo);

        jsonParser_update_customer = new JSONParser_Update_Customer();

        ed_customer_name    = rootView.findViewById(R.id.ed_customer_name);
        ed_customer_password= rootView.findViewById(R.id.ed_customer_password);
        ed_customer_fullname= rootView.findViewById(R.id.ed_customer_fullname);
        ed_customer_email   = rootView.findViewById(R.id.ed_customer_email);
        ed_customer_mobile  = rootView.findViewById(R.id.ed_customer_mobile);

        ed_customer_name.setText(""+sharedPreferences.getString("user_name", ""));
        //ed_customer_password.setText("");
        ed_customer_fullname.setText(""+sharedPreferences.getString("full_name", ""));
        ed_customer_email.setText(""+sharedPreferences.getString("email", ""));
        ed_customer_mobile.setText(""+sharedPreferences.getString("phone", ""));

        Picasso.with(img_photo.getContext()).load(""+sharedPreferences.getString("person_image", "")).into(img_photo);

        btn_submit = rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

/*
                    $_REQUEST['user_id'];
                    $_REQUEST['username'];
                    $_REQUEST['fullname'];
                    $_REQUEST['password'];
                    $_POST['phone']
*/

                if (ed_customer_name.getText().toString().equals("")){
                    ed_customer_name.setError("Empty");
                }else if (ed_customer_fullname.getText().toString().equals("")){
                          ed_customer_fullname.setError("Empty");
                }else if (ed_customer_password.getText().toString().equals("")){
                          ed_customer_password.setError("Empty");
                }else if (ed_customer_mobile.getText().toString().equals("")){
                          ed_customer_mobile.setError("Empty");
                }else {

                    if (!image_real_path.equals("")){
                        update_profile(""+sharedPreferences.getString("user_id", ""),""+ed_customer_name.getText().toString(),
                                ""+ed_customer_fullname.getText().toString(),""+ed_customer_password.getText().toString(),""+ed_customer_mobile.getText().toString());
                    }else {

                        //http://192.168.100.14/monshiapp/app/Add_Customer.php
                        String url    =  getResources().getString(R.string.url)+"update_profile.php";

                        String params = "user_id="+sharedPreferences.getString("user_id", "")+
                                "&username="+ed_customer_name.getText().toString()+
                                "&fullname="+ed_customer_fullname.getText().toString()+
                                "&password="+ed_customer_password.getText().toString()+
                                "&phone="+ed_customer_mobile.getText().toString();


                        WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                            @Override
                            public void TaskCompletionResult(String result) {

                                try {

                                    JSONObject jsonObject = new JSONObject(result);
                                    if(jsonObject.getString("status").equals("200")) {
                                        Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();

                                        SavePreferences("full_name",ed_customer_name.getText().toString());
                                        SavePreferences("full_name",ed_customer_fullname.getText().toString());
                                        SavePreferences("email",ed_customer_email.getText().toString());
                                        SavePreferences("phone",ed_customer_mobile.getText().toString());
                                        SavePreferences("password" ,ed_customer_password.getText().toString());

                                        getFragmentManager().popBackStack();
                                    }
                                    else{
                                        Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
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

        btn_addphoto = rootView.findViewById(R.id.btn_addphoto);
        btn_addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissionForReadExtertalStorage()){

                    // 1. on Upload click call ACTION_GET_CONTENT intent
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    // 2. pick image only
                    intent.setType("image/*");
                    // 3. start activity
                    startActivityForResult(intent, 0);

                }else {
                    try {
                        requestPermissionForReadExtertalStorage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        return rootView;
    }

    public boolean checkPermissionForReadExtertalStorage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    int READ_STORAGE_PERMISSION_REQUEST_CODE=1;
    public void requestPermissionForReadExtertalStorage() throws Exception {
        try {
            ActivityCompat.requestPermissions((Activity) getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    READ_STORAGE_PERMISSION_REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == Activity.RESULT_OK && data != null) {
            String realPath;
            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(getActivity(), data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(getActivity(), data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(getActivity(), data.getData());


            setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(), realPath);
        }
    }

    private void setTextViews(int sdk, String uriPath, String realPath) {

        File file = new File(realPath);


        Uri uriFromPath = Uri.fromFile(file);

        // you have two ways to display selected image

        // ( 1 ) imageView.setImageURI(uriFromPath);

        // ( 2 ) imageView.setImageBitmap(bitmap);
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        img_photo.setImageBitmap(bitmap);

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:" + sdk);
        Log.d("HMKCODE", "URI Path:" + uriPath);
        Log.d("HMKCODE", "Real Path: " + realPath);

        image_real_path = file.getAbsolutePath();
    }

    private void update_profile(final String user_id, final String username, final String fullname, final String password, final String phone){

        //
        new AsyncTask<Void, Integer, Boolean>() {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Save Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Boolean doInBackground(Void... params) {
                try {
//String result = jsonParser.uploadImage(imgFile.getAbsolutePath(),getActivity()).toString();
//Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
//System.out.println("fdfdf"+imgFile.getAbsolutePath().toString());
                    JSONObject jsonObject;
//if(imgFile != null)
                    jsonObject =jsonParser_update_customer.uploadImage(""+image_real_path,""+user_id,""+username,""+fullname,
                            ""+password,""+phone,getActivity());
//else
//jsonObject =jsonParser_editProfile.uploadImage("",username,password,age,height,phone,weight,country,activity);

                    if (jsonObject != null) {
                        if(jsonObject.getString("status").equals("200")){

                            SavePreferences("person_image" ,jsonObject.getString("person_image" ));
                        }
                        return jsonObject.getString("status").equals("200");
                    }
                } catch (JSONException e) {
                    Log.i("TAG", "Error : " + e.getLocalizedMessage());
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
// if (progressDialog != null)
// progressDialog.dismiss();
                try
                {
                    if (aBoolean) {
//Toast.makeText(getActivity(), "Image Uploaded Successfully", Toast.LENGTH_LONG).show();

                        Toast.makeText(getActivity(),"Profile Saved!",Toast.LENGTH_SHORT).show();


                    }
                    else
                        Toast.makeText(getActivity(), "Failed To Save Data, Try Again!", Toast.LENGTH_LONG).show();


                } catch (Exception e)
                {
                    e.printStackTrace();
                }finally {

                    progressDialog.dismiss();
                }


//imagePath = "";
            }
        }.execute();

    }

    private void SavePreferences(String key, String value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

}

class RealPathUtil {

    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API19(Context context, Uri uri){
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(uri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = { MediaStore.Images.Media.DATA };

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{ id }, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }


    @SuppressLint("NewApi")
    public static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        String result = null;

        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if(cursor != null){
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }

    public static String getRealPathFromURI_BelowAPI11(Context context, Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }



}
