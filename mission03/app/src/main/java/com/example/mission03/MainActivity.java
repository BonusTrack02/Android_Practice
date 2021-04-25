package com.example.mission03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageView1;
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveImageUp();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveImageDown();
            }
        });

        moveImageUp();
    }
    private void moveImageUp() {
        imageView1.setImageResource(R.drawable.beach);
        imageView2.setImageResource(0);

        imageView1.invalidate();
        imageView2.invalidate();
    }

    private void moveImageDown() {
        imageView1.setImageResource(0);
        imageView2.setImageResource(R.drawable.beach);

        imageView1.invalidate();
        imageView2.invalidate();
    }
}