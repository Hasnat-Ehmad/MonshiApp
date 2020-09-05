package info.androidhive.materialdesign.fragments;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import info.androidhive.materialdesign.R;
import info.androidhive.materialdesign.webservice.JSONParser_Add_Customer_recp;
import info.androidhive.materialdesign.webservice.JSONParser_Update_Customer_recp;
import info.androidhive.materialdesign.webservice.TaskDelegate;
import info.androidhive.materialdesign.webservice.WebRequestCall;

import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_back;
import static info.androidhive.materialdesign.activity.MainActivity_bottom_view.img_notifications;

public class AddNewCustomerFragment extends Fragment {
    SharedPreferences sharedPreferences;
    Button btn_submit;

    EditText ed_customer_name,ed_customer_email,ed_customer_number,ed_customer_address;

    ImageView img_photo;

    int api_call_check;
    String customer_id;

    Button btn_addphoto;
    int REQUEST_GET_SINGLE_FILE=1;

    JSONParser_Update_Customer_recp jsonParser_add_update_customer_recp;
    JSONParser_Add_Customer_recp jsonParser_add_customer_recp;

    String image_real_path="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        img_notifications.setVisibility(View.VISIBLE);

        img_back.setVisibility(View.VISIBLE);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        jsonParser_add_update_customer_recp = new JSONParser_Update_Customer_recp();
        jsonParser_add_customer_recp = new JSONParser_Add_Customer_recp();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_new_customer, container, false);
        img_photo = rootView.findViewById(R.id.img_photo);
        //spinner_business_list.setVisibility(View.GONE);

        btn_addphoto = rootView.findViewById(R.id.btn_addphoto);

        btn_addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        ed_customer_name = (EditText) rootView.findViewById(R.id.ed_customer_name);
        ed_customer_email = (EditText) rootView.findViewById(R.id.ed_customer_email);
        ed_customer_number = (EditText) rootView.findViewById(R.id.ed_customer_number);
        ed_customer_address = (EditText) rootView.findViewById(R.id.ed_customer_address);



        if (getArguments() != null) {

            api_call_check=1;

            customer_id = getArguments().getString("id");

            ed_customer_name.setText(""+getArguments().getString("name"));
            ed_customer_email.setText(""+getArguments().getString("email"));
            ed_customer_number.setText(""+getArguments().getString("mobile"));
            ed_customer_address.setText(""+getArguments().getString("address"));

            if (getArguments().getString("image")!=null && !getArguments().getString("image").equals("") )
            Picasso.with(img_photo.getContext()).load(""+getArguments().getString("image")).into(img_photo);

        }



        btn_submit = (Button) rootView.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (api_call_check==1){

// http://192.168.100.14/monshiapp/app/update_customer.php?
// cust_id=2
// &bus_id=2
// &name=customer%20ahmad
// &email=customer_ahmad@gmail.com
// &mobilenumber=55455454545
// &address=customer%20address

                    //http://192.168.100.14/monshiapp/app/Add_Customer.php
                    String url    =  getResources().getString(R.string.url)+"update_customer.php";

                    String params;
                    if (sharedPreferences.getString("user_role" ,""  ).equals("1")){

                        if (!image_real_path.equals("")){
                            update_profile(""+customer_id,""+sharedPreferences.getString("selected_business", ""),""+ed_customer_name.getText().toString(),
                                    ""+ ed_customer_email.getText().toString(),""+ed_customer_number.getText().toString(),""+ed_customer_address.getText().toString());
                        }else {

                            params = "cust_id="+customer_id+
                                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                                    "&name="  +ed_customer_name.getText().toString()+
                                    "&email=" +ed_customer_email.getText().toString()+
                                    "&mobilenumber="+ed_customer_number.getText().toString()+
                                    "&address="+ed_customer_address.getText().toString();

                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {
                                            Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
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

                    }else {

                        if (!image_real_path.equals("")){
                            update_profile(""+customer_id,""+sharedPreferences.getString("business_id", ""),""+ed_customer_name.getText().toString(),
                                    ""+ ed_customer_email.getText().toString(),""+ed_customer_number.getText().toString(),""+ed_customer_address.getText().toString()
                            );
                        }else {

                            params = "cust_id="+customer_id+
                                    "&bus_id="+sharedPreferences.getString("business_id", "")+
                                    "&name="+ed_customer_name.getText().toString()+
                                    "&email="+ed_customer_email.getText().toString()+
                                    "&mobilenumber="+ed_customer_number.getText().toString()+
                                    "&address="+ed_customer_address.getText().toString();

                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {
                                            Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
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

                }else {

                    //http://192.168.100.14/monshiapp/app/Add_Customer.php
                    String url    =  getResources().getString(R.string.url)+"Add_Customer.php";

                    String params;
                    if (sharedPreferences.getString("user_role" ,""  ).equals("1")){
                        if (!image_real_path.equals("")){
                            Add_profile(""+customer_id,""+sharedPreferences.getString("selected_business", ""),""+ed_customer_name.getText().toString(),
                                    ""+ ed_customer_email.getText().toString(),""+ed_customer_number.getText().toString(),""+ed_customer_address.getText().toString()
                            );
                        }else {

                            params = "cust_id="+customer_id+
                                    "&bus_id="+sharedPreferences.getString("selected_business", "")+
                                    "&name="  +ed_customer_name.getText().toString()+
                                    "&email=" +ed_customer_email.getText().toString()+
                                    "&mobilenumber="+ed_customer_number.getText().toString()+
                                    "&address="+ed_customer_address.getText().toString();

                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {
                                            Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
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
                    }else {

                        if (!image_real_path.equals("")){
                            update_profile(""+customer_id,""+sharedPreferences.getString("business_id", ""),""+ed_customer_name.getText().toString(),
                                    ""+ ed_customer_email.getText().toString(),""+ed_customer_number.getText().toString(),""+ed_customer_address.getText().toString()
                            );
                        }else {

                            params = "cust_id="+customer_id+
                                    "&bus_id="+sharedPreferences.getString("business_id", "")+
                                    "&name="+ed_customer_name.getText().toString()+
                                    "&email="+ed_customer_email.getText().toString()+
                                    "&mobilenumber="+ed_customer_number.getText().toString()+
                                    "&address="+ed_customer_address.getText().toString();

                            WebRequestCall webRequestCall = new WebRequestCall(new TaskDelegate() {
                                @Override
                                public void TaskCompletionResult(String result) {

                                    try {

                                        JSONObject jsonObject = new JSONObject(result);
                                        if(jsonObject.getString("status").equals("200")) {
                                            Toast.makeText(getActivity(),jsonObject.getString("status_alert") , Toast.LENGTH_SHORT).show();
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
                realPath = RealPathUtil_recep.getRealPathFromURI_BelowAPI11(getActivity(), data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil_recep.getRealPathFromURI_API11to18(getActivity(), data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil_recep.getRealPathFromURI_API19(getActivity(), data.getData());


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


    private void update_profile(final String customer_id, final String bus_id, final String name, final String email, final String mobilenumber, final String address){

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
                    jsonObject =jsonParser_add_update_customer_recp.uploadImage(""+image_real_path,""+customer_id,""+bus_id,""+name,
                            ""+email,""+mobilenumber,""+address,getActivity());
//else
//jsonObject =jsonParser_editProfile.uploadImage("",username,password,age,height,phone,weight,country,activity);

                    if (jsonObject != null) {
                        if(jsonObject.getString("status").equals("200")){


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
                        getFragmentManager().popBackStack();

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
    private void Add_profile(final String customer_id, final String bus_id, final String name, final String email, final String mobilenumber, final String address){

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
                    jsonObject =jsonParser_add_update_customer_recp.uploadImage(""+image_real_path,""+customer_id,""+bus_id,""+name,
                            ""+email,""+mobilenumber,""+address,getActivity());
//else
//jsonObject =jsonParser_editProfile.uploadImage("",username,password,age,height,phone,weight,country,activity);

                    if (jsonObject != null) {
                        if(jsonObject.getString("status").equals("200")){


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
                        getFragmentManager().popBackStack();

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


}
class RealPathUtil_recep {

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

