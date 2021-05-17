package com.example.mission08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Button toMenuButton3 = findViewById(R.id.toMenuButton3);
        toMenuButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("message", "result message is OK");

                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        Button toLoginButton3 = findViewById(R.id.toLoginButton3);
        toLoginButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("titleMsg", "로그인 화면");
                startActivity(intent);
            }
        });
    }
}