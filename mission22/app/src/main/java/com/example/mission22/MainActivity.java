package com.example.mission22;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnDatabaseCallback {
    private static final String TAG = "MainActivity";

    Toolbar toolbar;

    Fragment1 fragment1;
    Fragment2 fragment2;

    BookDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("입력"));
        tabs.addTab(tabs.newTab().setText("조회"));

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.d("MainActivity", "선택된 탭 : " + position);

                Fragment selected = null;
                if (position == 0) {
                    selected = fragment1;
                } else if (position == 1) {
                    selected = fragment2;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (database != null) {
            database.close();
            database = null;
        }

        database = BookDatabase.getInstance(this);
        boolean isOpen = database.open();
        if (isOpen) {
            Log.d(TAG, "Book database is open.");
        } else {
            Log.d(TAG, "Book database is not open.");
        }
    }

    @Override
    protected void onDestroy() {
        if (database != null) {
            database.close();
            database = null;
        }
        super.onDestroy();
    }

    @Override
    public void insert(String name, String author, String contents) {
        database.insertRecord(name, author, contents);
        Toast.makeText(getApplicationContext(), "책 정보를 추가했습니다.", Toast.LENGTH_LONG).show();
    }

    @Override
    public ArrayList<BookInfo> selectAll() {
        ArrayList<BookInfo> result = database.selectAll();
        Toast.makeText(getApplicationContext(), "책 정보를 조회했습니다.", Toast.LENGTH_LONG).show();

        return result;
    }
}