package com.example.test.testservice.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.test.testservice.interf.Change;

public class MyService extends Service {
    private static final String TAG = "MyService";
    private String str;
    private boolean flags;
    private int count;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        MyChange myChange = new MyChange();
        return myChange;
    }

    public class MyChange extends Binder implements Change {

        @Override
        public void changeBoolean() {
            if (flags == true) {
                changeB();
                Log.i(TAG, "changeBoolean false");
            } else {
                changeBTrue();
                Log.i(TAG, "changeBoolean true");
            }
        }

        @Override
        public void changeString(String something) {
            changeS(something);
        }
    }

    public void changeS(String something) {
        this.str = something;
    }

    public void changeB() {
        flags = false;
    }

    public void changeBTrue() {
        flags = true;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        flags = false;
        str = "1212";
        count = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!flags) {
                    try {
                        Log.i(TAG, str);
                        Thread.sleep(1111);
                        count++;
                        Log.i(TAG, "run is----->" + count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flags = true;
        Log.i(TAG, "onDestroy ");
    }
}
