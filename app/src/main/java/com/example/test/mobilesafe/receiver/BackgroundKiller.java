package com.example.test.mobilesafe.receiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.util.List;

public class BackgroundKiller extends BroadcastReceiver {
    private static final String TAG = "BackgroundKiller";

    public BackgroundKiller() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "onReceive asfasdfasf");
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : list) {
            String packageName = info.processName;
            Log.i(TAG, "onReceive " + packageName);
            manager.killBackgroundProcesses(packageName);
        }
    }
}
