package com.example.boshen.socialself;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Boshen on 4/21/2016.
 */
public class databaseFunctions{

    private static String url_get = "http://boshencui.com/getuser.php";
    private static String url_post = "http://boshencui.com/postuser.php";

    static String HttpRequest(String url, String sendData){

        try{
            URL Url = new URL(url);

            HttpURLConnection urlConnection = (HttpURLConnection)Url.openConnection();

            urlConnection.setDoOutput(true);

            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(os, "UTF-8"));
            writer.write(sendData);
            writer.flush();
            writer.close();
            os.close();

            InputStream is = urlConnection.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            line = total.toString();

            Log.d("debug - result:", line);

            return line;
        }
        catch(Exception e){
            Log.d("debug - error", e.getMessage());
        }
        return null;
    }

    static class loaduser extends AsyncTask<String, Void, String>{

        SharedPreferences userPref;
        SharedPreferences.Editor userPrefEditor;

        protected String doInBackground(String... args){

            String id = userPref.getString("id", "");
            String sendData = "id=" + id;

            return HttpRequest(url_get, sendData);
        }


    }

    static class getuser extends AsyncTask<String, Void, String>{
        String sendData;

        protected String doInBackground(String... Args){
            return HttpRequest(url_get, sendData);
        }
    }

    static class postuser extends AsyncTask<String, Void, String> {

        SharedPreferences userPref;
        SharedPreferences.Editor userPrefEditor;
        String sendData="";

        protected String doInBackground(String... Args){

            Log.d("debug-dbFn values", sendData);
            return HttpRequest(url_post, sendData);
        }
    }


//    String userToString(){
//         return "id: " + id + "fb: "  + fb_name  + "insta: "  + insta_name  + "twitter: " + twitter_name + "linkedin: " + linkedin_name + "snapchat: " + sc_name +"email: " + email + "phone number: "  + phone;
//    }


}
