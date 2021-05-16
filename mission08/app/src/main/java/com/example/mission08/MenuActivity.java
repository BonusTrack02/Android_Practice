package com.example.mission08;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_CUSTOMER = 201;
    public static final int REQUEST_CODE_SALES = 202;
    public static final int REQUEST_CODE_PRODUCT = 203;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent receivedIntent = getIntent();
        String username = receivedIntent.getStringExtra("username");
        String password = receivedIntent.getStringExtra("password");

        Toast.makeText(this, "username : " + username + ", password : " + password, Toast.LENGTH_LONG).show();

        Button menu1Button = findViewById(R.id.menu1Button);
        menu1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CustomerActivity.class);
                intent.putExtra("titleMsg", "고객 관리 화면");
                startActivityForResult(intent, REQUEST_CODE_CUSTOMER);
            }
        });

        Button menu2Button = findViewById(R.id.menu2Button);
        menu2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SalesActivity.class);
                intent.putExtra("titleMsg", "매출 관리 화면");
                startActivityForResult(intent, REQUEST_CODE_SALES);
            }
        });

        Button menu3Button = findViewById(R.id.menu3Button);
        menu3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProductActivity.class);
                intent.putExtra("titleMsg", "상품 관리 화면");
                startActivityForResult(intent, REQUEST_CODE_PRODUCT);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (intent != null) {
            if (requestCode == REQUEST_CODE_CUSTOMER) {
                String message = intent.getStringExtra("message");

                if (message != null) {
                    Toast toast = Toast.makeText(getBaseContext(), "고객 관리 응답, result code : " + resultCode + ", message : " + message, Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            else if (requestCode == REQUEST_CODE_SALES) {
                String message = intent.getStringExtra("message");

                if (message != null) {
                    Toast toast = Toast.makeText(getBaseContext(), "매출 관리 응답, result code : " + resultCode + ", message : " + message, Toast.LENGTH_LONG);
                    toast.show();
                }
            }

            else if (requestCode == REQUEST_CODE_PRODUCT) {
                String message = intent.getStringExtra("message");

                if (message != null) {
                    Toast toast = Toast.makeText(getBaseContext(), "상품 관리 응답, result code : " + resultCode + ", message : " + message, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }
    }
}