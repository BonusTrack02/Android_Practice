package com.example.mission11;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "MyService";
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate 호출됨");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand 호출됨");

        if (intent != null) {
            String command = intent.getStringExtra("command");
            if (command != null) {
                if (command.equals("show")) {
                    String data = intent.getStringExtra("data");

                    sendToActivity(data);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void sendToActivity(String data) {
        Intent activityIntent = new Intent();
        activityIntent.setAction("com.example.broadcast.SHOW");
        activityIntent.putExtra("command", "show");
        activityIntent.putExtra("data", data);

        sendBroadcast(activityIntent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
