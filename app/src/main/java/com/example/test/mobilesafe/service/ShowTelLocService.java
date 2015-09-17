package com.example.test.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.test.mobilesafe.engine.AddressService;

public class ShowTelLocService extends Service {
    private static final String TAG = "ShowTelLocService";
    private MyPhoneListener listener;
    private TelephonyManager manager;

    public ShowTelLocService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.


        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyPhoneListener();
        manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
    }

    private class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    String address = AddressService.getAddress(incomingNumber);
                    Log.i(TAG, address);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        manager.listen(listener, PhoneStateListener.LISTEN_NONE);
        listener = null;
    }
}
