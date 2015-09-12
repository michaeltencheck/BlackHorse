package com.example.test.testdeviceadmin.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyDeviceAdmin extends DeviceAdminReceiver {
    public MyDeviceAdmin() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i("MyDeviceAdmin", "MyDeviceAdmin done");
    }
}
