package com.example.test.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.engine.AddressService;

public class ShowTelLocService extends Service {
    private static final String TAG = "ShowTelLocService";
    private MyPhoneListener listener;
    private TelephonyManager manager;
    private WindowManager windowManager;
    private TextView textView;
    private SharedPreferences sp;
    private View view;

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
        sp = getSharedPreferences("config", MODE_PRIVATE);
        manager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        listener = new MyPhoneListener();
        manager.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
    }

    private class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    String address = AddressService.getAddress(incomingNumber);
                    Log.i(TAG, address);
                    showLocation(address);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    if (textView != null) {
                        windowManager.removeView(textView);
                        textView = null;
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    if (textView != null) {
                        windowManager.removeView(textView);
                        textView = null;
                    }
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

    public void showLocation(String address) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        params.format = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        params.setTitle("Toast");

        /*textView = new TextView(this);
        textView.setText("号码归属地为: " + address);*/

        view = View.inflate(getApplicationContext(), R.layout.color_change, null);
        textView = (TextView) view.findViewById(R.id.tv_cc_location);

        int color = sp.getInt("toast_color", 0);
        switch (color) {
            case 0:
                view.setBackgroundColor(Color.GRAY);
                break;
            case 1:
                view.setBackgroundColor(Color.CYAN);
                break;
            case 2:
                view.setBackgroundColor(Color.BLUE);
                break;
            default:
                break;
        }

        windowManager.addView(textView, params);
    }
}
