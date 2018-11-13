package com.example.soyoung.shopping;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyMS";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived() 호출됨.");

        String from = remoteMessage.getFrom();
        Map<String, String> resData = remoteMessage.getData();
        String command = resData.get("command");
        String type = resData.get("type");
        String data = resData.get("data");

        Log.v(TAG, "from : " + from + ", command : " + command + ", type : " + type + ", data : " + data);

        sendToActivity(getApplicationContext(), from, command, type, data);
    }

    private void sendToActivity(Context context, String from, String command, String type, String data) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("command", command);
        intent.putExtra("type", type);
        intent.putExtra("data", data);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

        context.startActivity(intent);
    }

}