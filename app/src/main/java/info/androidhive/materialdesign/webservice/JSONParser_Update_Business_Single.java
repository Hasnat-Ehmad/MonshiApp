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
public class JSONParser_Update_Business_Single {

    SharedPreferences sharedPreferences;

    /**
     * Upload Image
     *
     * @param sourceImageFile
     * @return
     */
    public JSONObject uploadImage(String sourceImageFile,
                                  String user_id, String bus_id, String business_title, String business_id,String business_type,String contact,String address,String state,
                                  String city,String industry,String sub_cat,String description, String fname,String all_operating_day,String check_operating_day,
                                  String day_on_off, String from_hour,String to_hour, Activity activity) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        /*
      Upload URL of your folder with php file name...
      You will find this file in php_upload folder in this project
      You can copy that folder and paste in your htdocs folder...
     */
        String URL_UPLOAD_IMAGE = activity.getResources().getString(R.string.url) + "update_business.php";
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
                    .addFormDataPart("business_pic", filename, RequestBody.create(MEDIA_TYPE_PNG, sourceFile))
                    .addFormDataPart("user_id", user_id)
                    .addFormDataPart("bus_id", bus_id)
                    .addFormDataPart("business_title",URLEncoder.encode(business_title, "UTF-8"))
                    .addFormDataPart("business_id",URLEncoder.encode(business_id, "UTF-8"))
                    .addFormDataPart("business_type", business_type)
                    .addFormDataPart("contact",URLEncoder.encode(contact, "UTF-8"))
                    .addFormDataPart("address",URLEncoder.encode(address, "UTF-8"))
                    .addFormDataPart("state", state)
                    .addFormDataPart("city",city)
                    .addFormDataPart("industry", industry)
                    .addFormDataPart("sub_cat",sub_cat)
                    .addFormDataPart("description", URLEncoder.encode(description, "UTF-8"))
                    .addFormDataPart("user_name", URLEncoder.encode(fname, "UTF-8"))
                    .addFormDataPart("all_operating_day",all_operating_day)
                    .addFormDataPart("check_operating_day",check_operating_day)
                    .addFormDataPart("day_on_off", day_on_off)
                    .addFormDataPart("from_hour",from_hour)
                    .addFormDataPart("to_hour",to_hour)


                    .build();

            Log.d("TAG", "File...:::: URL_UPLOAD_IMAGE1" + URL_UPLOAD_IMAGE);
            Log.d("TAG", "File...:::: user_id = " + user_id);
            Log.d("TAG", "File...:::: bus_id = " + bus_id);
            Log.d("TAG", "File...:::: business_title = " + business_title);
            Log.d("TAG", "File...:::: business_id = " + business_id);
            Log.d("TAG", "File...:::: business_type = " + business_type);
            Log.d("TAG", "File...:::: contact = " + contact);
            Log.d("TAG", "File...:::: address = " + address);
            Log.d("TAG", "File...:::: state = " + state);
            Log.d("TAG", "File...:::: city = " + city);
            Log.d("TAG", "File...:::: industry = " + industry);
            Log.d("TAG", "File...:::: sub_cat = " + sub_cat);
            Log.d("TAG", "File...:::: user_id = " + user_id);
            Log.d("TAG", "File...:::: description = " + description);
            Log.d("TAG", "File...:::: all_operating_day = " + all_operating_day);
            Log.d("TAG", "File...:::: check_operating_day = " + check_operating_day);
            Log.d("TAG", "File...:::: day_on_off = " + day_on_off);
            Log.d("TAG", "File...:::: from_hour = " + from_hour);
            Log.d("TAG", "File...:::: to_hour = " + to_hour);


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
