package com.example.test.mobilesafe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.example.test.mobilesafe.engine.LocationInfo;

public class SMSLocationReceiver extends BroadcastReceiver {
    public SMSLocationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Object[] pdus = (Object[]) intent.getExtras().get("pdus");
        DevicePolicyManager manager = (DevicePolicyManager)
                context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        for (Object pdu : pdus) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[])pdu);
            String content = smsMessage.getMessageBody();
            Log.i("SMSLocationReceiver", content);
            String address = smsMessage.getOriginatingAddress();
            Log.i("SMSLocationReceiver", address);
            if ("#*location*#".equals(content)) {
                abortBroadcast();
                LocationInfo locationInfo = LocationInfo.getInstance(context);
                String location = locationInfo.getLocationInfo();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(address, null, location, null,null);
            } else if ("#*lockscreen*#".equals(content)) {
                manager.resetPassword("111", 0);
                manager.lockNow();
            }else if ("#*wipedata*#".equals(content)) {
                manager.wipeData(0);
            }
        }
    }
}
