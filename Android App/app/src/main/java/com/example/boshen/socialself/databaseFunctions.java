package com.example.boshen.socialself;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

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

    //url to make POST request to get a user
    private static String url_get = "http://boshencui.com/getuser.php";
    //url to make a POST request to add/edit a user
    private static String url_post = "http://boshencui.com/postuser.php";

    //function that sends the POST request, returns a string that is the JSON response
    static String HttpRequest(String url, String sendData){

        try{
            //converts string (either url_get or url_post) to a URL object
            URL Url = new URL(url);

            //open HTTP connection
            HttpURLConnection urlConnection = (HttpURLConnection)Url.openConnection();
            urlConnection.setDoOutput(true);

            //write the data to send to the URL
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
            new OutputStreamWriter(os, "UTF-8"));
            writer.write(sendData);
            writer.flush();
            writer.close();
            os.close();

            //receive the response from the server as a stringbuilder, then convert to string
            InputStream is = urlConnection.getInputStream();
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            line = total.toString();

            //return the response
            return line;
        }
        catch(Exception e){
            //this should never happen
            Log.d("debug - error", e.getMessage());
        }
        //if request fails at any point, return null
        return null;
    }

    //asyc class to load the user that is currently logged in based on their id
    static class loaduser extends AsyncTask<String, Void, String>{

        SharedPreferences userPref;
        SharedPreferences.Editor userPrefEditor;

        protected String doInBackground(String... args){

            String id = userPref.getString("id", "");
            String sendData = "id=" + id;

            return HttpRequest(url_get, sendData);
        }


    }

    //async class to load a user based on id
    static class getuser extends AsyncTask<String, Void, String>{
        String sendData;

        protected String doInBackground(String... Args){
            return HttpRequest(url_get, sendData);
        }
    }

    //async class to update/create a user, based on the data sent
    static class postuser extends AsyncTask<String, Void, String> {

        SharedPreferences userPref;
        SharedPreferences.Editor userPrefEditor;
        String sendData="";

        protected String doInBackground(String... Args){

            Log.d("debug-dbFn values", sendData);
            return HttpRequest(url_post, sendData);
        }
    }
}
