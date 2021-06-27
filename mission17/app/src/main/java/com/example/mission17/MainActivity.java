package com.example.mission17;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    FrameLayout container;
    Animation translateIn;
    Animation translateOut;

    CustomerInfoView view;
    CustomerInfoView view2;

    int selected = 1;

    boolean running = false;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);

        view = new CustomerInfoView(this);
        view.setName("김준수");
        view.setMobile("010-1000-1000");
        view.setAddress("서울시");
        container.addView(view);

        view2 = new CustomerInfoView(this);
        view2.setName("이희선");
        view2.setMobile("010-2000-2000");
        view2.setAddress("인천시");
        container.addView(view2);

        translateIn = AnimationUtils.loadAnimation(this, R.anim.translate_in);
        translateOut = AnimationUtils.loadAnimation(this, R.anim.translate_out);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationThread thread = new AnimationThread();
                thread.start();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });
    }

    class AnimationThread extends Thread {
        public void run() {
            running = true;
            while (running) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (selected == 0) {
                            view.startAnimation(translateIn);
                            view2.startAnimation(translateOut);
                        } else if (selected == 1) {
                            view.startAnimation(translateOut);
                            view2.startAnimation(translateIn);
                        }
                    }
                });

                selected += 1;
                if (selected > 1) {
                    selected = 0;
                }

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }
    }
}