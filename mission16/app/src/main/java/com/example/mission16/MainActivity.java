package com.example.mission16;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    WebView webView;
    EditText urlInput;
    ImageView handleButton;
    RelativeLayout urlLayout;

    Animation translateUpAnim;
    Animation translateDownAnim;

    boolean isUrlInputOpen = false;

    boolean loadRequested = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);

        webView.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished() called");

                if (loadRequested) {
                    urlLayout.startAnimation(translateUpAnim);
                    loadRequested = false;
                }
            }
        });

        urlInput = findViewById(R.id.urlInput);
        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = urlInput.getText().toString().trim();
                if (urlStr.length() < 1) {
                    Toast.makeText(getApplicationContext(), "사이트 주소를 먼저 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!urlStr.startsWith("http://")) {
                    urlStr = "http://" + urlStr;
                    urlInput.setText(urlStr);
                }

                loadRequested = true;
                webView.loadUrl(urlStr);
            }
        });

        handleButton = findViewById(R.id.handleButton);
        handleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButton.startAnimation(translateUpAnim);
            }
        });

        urlLayout = findViewById(R.id.urlLayout);

        translateUpAnim = AnimationUtils.loadAnimation(this, R.anim.translate_up);
        translateDownAnim = AnimationUtils.loadAnimation(this, R.anim.translate_down);

        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateUpAnim.setAnimationListener(animListener);
        translateDownAnim.setAnimationListener(animListener);

        isUrlInputOpen = true;
    }

    private void showHandleButton() {
        handleButton.setVisibility(View.VISIBLE);
        urlLayout.setVisibility(View.GONE);

        isUrlInputOpen = false;
    }

    private void showUrlInput() {
        handleButton.setVisibility(View.GONE);
        urlLayout.setVisibility(View.VISIBLE);

        isUrlInputOpen = true;
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            if (isUrlInputOpen) {
                showHandleButton();
            } else {
                showUrlInput();
            }
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }
}