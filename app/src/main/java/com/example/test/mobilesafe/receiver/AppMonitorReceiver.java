package com.example.test.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AppMonitorReceiver extends BroadcastReceiver {

    private static final String TAG = "AppMonitorReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "AFASDFASFJASFSF");
    }
}
