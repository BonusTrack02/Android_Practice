package com.example.mission08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CustomerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        Button toMenuButton1 = findViewById(R.id.toMenuButton1);
        toMenuButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("message", "result message is OK");

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button toLoginButton1 = findViewById(R.id.toLoginButton1);
        toLoginButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("titleMsg", "로그인 화면");
                startActivity(intent);
            }
        });
    }
}