package info.androidhive.materialdesign.webservice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import info.androidhive.materialdesign.R;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Hasnat Ahmad
 */
public class JSONParser_Update_Customer_recp {

    SharedPreferences sharedPreferences;

    /**
     * Upload Image
     *
     * @param sourceImageFile
     * @return
     */
    public JSONObject uploadImage(String sourceImageFile,
                                  String cust_id,  String bus_id, String name,String email,String mobilenumber,String address, Activity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        /*
      Upload URL of your folder with php file name...
      You will find this file in php_upload folder in this project
      You can copy that folder and paste in your htdocs folder...
     */
        String URL_UPLOAD_IMAGE = activity.getResources().getString(R.string.url) + "update_customer.php";
        try {

            /**
             * OKHTTP2
             */
//            RequestBody requestBody = new MultipartBuilder()
//                    .type(MultipartBuilder.FORM)
//                    .addFormDataPart("member_id", memberId)
//                    .addFormDataPart("file", "profile.png", RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
//                    .build();

            /**
             * OKHTTP3
             */
            File sourceFile = new File(sourceImageFile);

            Log.d("TAG", "File...::::" + sourceFile + " : " + sourceFile.exists());

            final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");

            String filename = sourceImageFile.substring(sourceImageFile.lastIndexOf("/")+1);

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("profile_pic", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    .addFormDataPart("cust_id", cust_id)
                    .addFormDataPart("bus_id",bus_id)
                    .addFormDataPart("name",name)
                    .addFormDataPart("email",email)
                    .addFormDataPart("mobilenumber",mobilenumber)
                    .addFormDataPart("address",address)
                    .build();

            Log.d("TAG", "File...:::: URL_UPLOAD_IMAGE1" + URL_UPLOAD_IMAGE);
            Log.d("TAG", "File...:::: cust_id="+ cust_id);
            Log.d("TAG", "File...:::: bus_id=" + bus_id);
            Log.d("TAG", "File...:::: name="   + name);
            Log.d("TAG", "File...:::: email="  + email);
            Log.d("TAG", "File...:::: mobilenumber=" + mobilenumber);
            Log.d("TAG", "File...:::: address="+ address);

            Request request = new Request.Builder()
                    .url(URL_UPLOAD_IMAGE)
                    .post(requestBody)
                    .build();


            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            Log.e("TAG", "Error: " + res);
            return new JSONObject(res);

        } catch (UnknownHostException | UnsupportedEncodingException e) {
            Log.e("TAG", "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("TAG", "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }
}
