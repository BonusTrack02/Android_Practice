package com.example.mission19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();

    TextView textView;
    EditText editText;

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);

        webView = findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlStr = editText.getText().toString().trim();

                RequestThread thread = new RequestThread(urlStr);
                thread.start();
            }
        });
    }

    class RequestThread extends Thread {
        String urlStr;

        public RequestThread(String str) {
            urlStr = str;
        }

        public void run() {
            try {
                final String output = request(urlStr);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(output);
                        webView.loadData(output, "text/html", "UTF8");
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        private String request(String urlStr) {
            StringBuilder output = new StringBuilder();
            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                if (connection != null) {
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod("GET");
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    boolean redirect = false;

                    int status = connection.getResponseCode();
                    if (status != HttpURLConnection.HTTP_OK) {
                        if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER)
                            redirect = true;
                    }
                    System.out.println("Response Code ... " + status);

                    if (redirect) {
                        String newUrl = connection.getHeaderField("Location");
                        String cookies = connection.getHeaderField("Set-Cookie");

                        connection = (HttpURLConnection) new URL(newUrl).openConnection();
                        connection.setRequestProperty("Cookie", cookies);

                        System.out.println("Redirect to URL : " + newUrl);
                    }

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line = null;
                    while (true) {
                        line = reader.readLine();
                        if (line == null) {
                            break;
                        }
                        output.append(line + "\n");
                    }

                    reader.close();
                    connection.disconnect();
                }
            } catch (Exception ex) {
                Log.e("MainActivity", "Exception in processing response.", ex);
                ex.printStackTrace();
            }

            return output.toString();
        }
    }
}