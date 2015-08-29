package com.example.test.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.test.mobilesafe.activity.LostProtectActivity;

public class LostProtectReceiver extends BroadcastReceiver {
    public LostProtectReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String number = getResultData();
        Log.i("aaaaa", number);
        if ("111".equals(number)) {
            Intent intent1 = new Intent(context, LostProtectActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);
            setResultData(null);
        }
    }
}
