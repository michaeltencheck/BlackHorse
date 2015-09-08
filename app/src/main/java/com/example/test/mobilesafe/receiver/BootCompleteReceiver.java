package com.example.test.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Log.i(TAG, "1111111");
        TelephonyManager telephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        String currentSimNumber = telephonyManager.getSimSerialNumber();
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        String simNumber = sp.getString("simNumber", "");
        if (!simNumber.equals(currentSimNumber)) {
            String safeNumber = sp.getString("safeNumber", "");
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(safeNumber, null,
                    "simcard number is different from default, may be stole", null, null);
            Toast.makeText(context, "simcard number is different from default, may be stole",
                    Toast.LENGTH_LONG).show();
        } else {
            Log.i(TAG, simNumber + "---" + currentSimNumber);
        }
    }
}
