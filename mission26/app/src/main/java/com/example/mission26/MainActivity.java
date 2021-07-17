package com.example.mission26;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

public class MainActivity extends AppCompatActivity implements AutoPermissionsListener {
    FrameLayout previewFrame;
    CameraSurfaceView cameraView;

    RelativeLayout iconLayout;
    ImageView icon1Image;
    ImageView icon2Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previewFrame = findViewById(R.id.previewFrame);
        cameraView = findViewById(R.id.cameraView);

        iconLayout = findViewById(R.id.iconLayout);
        icon1Image = findViewById(R.id.icon01Image);
        icon2Image = findViewById(R.id.icon02Image);

        Button showButton = findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconLayout.setVisibility(View.VISIBLE);
            }
        });

        Button hideButton = findViewById(R.id.hideButton);
        hideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iconLayout.setVisibility(View.INVISIBLE);
            }
        });

        icon1Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "VIPS 쿠폰 선택됨.", Toast.LENGTH_LONG).show();
            }
        });

        icon2Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "스타벅스 쿠폰 선택됨.", Toast.LENGTH_LONG).show();
            }
        });

        AutoPermissions.Companion.loadAllPermissions(this, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(this, requestCode, permissions, this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions denied : " + permissions.length, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {
        Toast.makeText(this, "permissions granted : " + permissions.length, Toast.LENGTH_LONG).show();
    }
}