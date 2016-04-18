package com.example.boshen.socialself;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

import java.util.ArrayList;
import java.util.List;

public class qr_scanner extends AppCompatActivity implements ZBarScannerView.ResultHandler  {
    private ZBarScannerView mScannerView;
    private ArrayList<Integer> mSelectedIndices;
    public final static String EXTRA_MESSAGE = "com.example.boshen.socialself.MESSAGE";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setupFormats();
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.app_name))
                .setCancelable(false)
                .setMessage(rawResult.getContents() + " " + rawResult.getBarcodeFormat())
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .show();

        Intent intent = new Intent(this, view_medias.class);
        String message = rawResult.getContents();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void setupFormats() {
        List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        if(mSelectedIndices == null || mSelectedIndices.isEmpty()) {
            mSelectedIndices = new ArrayList<Integer>();
            for(int i = 0; i < BarcodeFormat.ALL_FORMATS.size(); i++) {
                mSelectedIndices.add(i);
            }
        }

        for(int index : mSelectedIndices) {
            formats.add(BarcodeFormat.ALL_FORMATS.get(index));
        }
        if(mScannerView != null) {
            mScannerView.setFormats(formats);
        }
    }

}