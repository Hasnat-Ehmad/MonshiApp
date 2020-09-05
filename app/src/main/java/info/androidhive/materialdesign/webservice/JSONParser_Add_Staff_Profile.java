package info.androidhive.materialdesign.webservice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
public class JSONParser_Add_Staff_Profile {

    SharedPreferences sharedPreferences;

    /**
     * Upload Image
     *
     * @param sourceImageFile
     * @return
     */
    public JSONObject uploadImage(String sourceImageFile,
                                  String user_id,String business_id,String username,String password,String fullname,String roll,String expertise,
                                  String experience_years,String email,
                                  String mobilenumber,String all_operating_day,String check_operating_day,String day_on_off,String from_hour,
                                  String to_hour,String service_list,Activity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        /*
      Upload URL of your folder with php file name...
      You will find this file in php_upload folder in this project
      You can copy that folder and paste in your htdocs folder...
     */
        String URL_UPLOAD_IMAGE = activity.getResources().getString(R.string.url) + "add_staff.php";
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
                    .addFormDataPart("user_id", user_id)
                    .addFormDataPart("business_id",business_id)
                    .addFormDataPart("username",URLEncoder.encode(username, "UTF-8"))
                    .addFormDataPart("password",URLEncoder.encode(password, "UTF-8"))
                    .addFormDataPart("fullname",URLEncoder.encode(fullname, "UTF-8"))
                    .addFormDataPart("roll",roll)
                    .addFormDataPart("expertise",URLEncoder.encode(expertise, "UTF-8"))
                    .addFormDataPart("experience_years",URLEncoder.encode(experience_years, "UTF-8"))
                    .addFormDataPart("email",URLEncoder.encode(email, "UTF-8"))
                    .addFormDataPart("mobilenumber",URLEncoder.encode(mobilenumber, "UTF-8"))
                    .addFormDataPart("all_operating_day",all_operating_day)
                    .addFormDataPart("check_operating_day",check_operating_day)
                    .addFormDataPart("day_on_off",day_on_off)
                    .addFormDataPart("from_hour",from_hour)
                    .addFormDataPart("to_hour",to_hour)
                    .addFormDataPart("service_list",service_list)
                    .build();

            Log.d("TAG", "File...:::: URL_UPLOAD_IMAGE1" + URL_UPLOAD_IMAGE);
            Log.d("TAG", "File...:::: user_id" + user_id);
            Log.d("TAG", "File...:::: business_id" + business_id);
            Log.d("TAG", "File...:::: username" + username);
            Log.d("TAG", "File...:::: password" + password);
            Log.d("TAG", "File...:::: roll" + roll);
            Log.d("TAG", "File...:::: expertise" + expertise);
            Log.d("TAG", "File...:::: experience_years" + experience_years);
            Log.d("TAG", "File...:::: email" + email);
            Log.d("TAG", "File...:::: mobilenumber" + mobilenumber);
            Log.d("TAG", "File...:::: all_operating_day" + all_operating_day);
            Log.d("TAG", "File...:::: check_operating_day" + check_operating_day);
            Log.d("TAG", "File...:::: day_on_off" + day_on_off);
            Log.d("TAG", "File...:::: from_hour" + from_hour);
            Log.d("TAG", "File...:::: to_hour" + to_hour);
            Log.d("TAG", "File...:::: service_list" + service_list);

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
