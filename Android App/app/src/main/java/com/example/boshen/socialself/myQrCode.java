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

//this activity displays the user's personal QR code for other users to scan (and add the user)
public class myQrCode extends AppCompatActivity {


    //initialize variables
    SharedPreferences userPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout file
        setContentView(R.layout.activity_my_qr_code);

        //get size of the screen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //assign variable to UI field
        ImageView QrCode = (ImageView) findViewById(R.id.imageView);

        //make a square
        if(width < height){
            height = width;
        }
        else{
            width = height;
        }

        //resize the image view to be a square that fits on the screen
        RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(width,height);
        QrCode.setLayoutParams(parms);

        //get user Id, to turn into a qr code
        userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String id = userPref.getString("id", " ");

        //URL to generate the QR code from the user's ID, and download it
        String MY_URL_STRING = "http://chart.apis.google.com/chart?chs=300x300&cht=qr&chld=|0&chl=id="+id;

        //download the image and display it in the imageview
        new DownloadImageTask(QrCode).execute(MY_URL_STRING);

    }

    //async class to download an image from the URL
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
