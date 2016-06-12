package com.example.boshen.socialself;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;

public class myQrCode extends AppCompatActivity {


    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qr_code);

        Display display = getWindowManager().getDefaultDisplay();

        ImageView QrCode = (ImageView) findViewById(R.id.imageView);

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if(width < height){
            height = width;
        }
        else{
            width = height;
        }

        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
        QrCode.setLayoutParams(parms);

        userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String id = userPref.getString("id", " ");

        String MY_URL_STRING = "http://chart.apis.google.com/chart?chs=300x300&cht=qr&chld=|0&chl=id="+id;

        new DownloadImageTask(QrCode).execute(MY_URL_STRING);

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);

        }
    }
}
