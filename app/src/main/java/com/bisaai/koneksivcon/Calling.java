package com.bisaai.koneksivcon;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bisaai.koneksivcon.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class Calling extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 101;
    private ActivityMainBinding mBinding;
    private PermissionRequest myRequest;
    private String TAG = "TEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasCameraPermission = checkSelfPermission(Manifest.permission.CAMERA);
            Log.d(TAG, "has camera permission: " + hasCameraPermission);
            int hasRecordPermission = checkSelfPermission(Manifest.permission.RECORD_AUDIO);
            Log.d(TAG, "has record permission: " + hasRecordPermission);
            int hasAudioPermission = checkSelfPermission(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            Log.d(TAG, "has audio permission: " + hasAudioPermission);
            List<String> permissions = new ArrayList<>();
            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (hasRecordPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (hasAudioPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.MODIFY_AUDIO_SETTINGS);
            }

            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]),111);

            }
        }


        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        WebView webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);

        // Tiga baris di bawah ini agar laman yang dimuat dapat
        // melakukan zoom.
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        // Baris di bawah untuk menambahkan scrollbar di dalam WebView-nya
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedHttpError (WebView view,
                 WebResourceRequest request,
                 WebResourceResponse errorResponse){
                super.onReceivedHttpError(view, request, errorResponse);
                //This method only appeared in 6.0
                int statusCode = errorResponse.getStatusCode();
                System.out.println("onReceivedHttpError code = " + statusCode);
                if (404 == statusCode || 500 == statusCode || 403 == statusCode) {
                    finish();
                }
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    request.grant(request.getResources());
                }
            }



        });

        webView.loadUrl(url);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}