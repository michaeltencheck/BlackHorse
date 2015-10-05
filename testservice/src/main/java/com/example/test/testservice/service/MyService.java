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
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Mychange mychange = new Mychange();
        return mychange;
    }

    private class Mychange extends Binder implements Change {

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
        public void changeString() {
            changeS();
        }
    }

    public void changeS() {
        str = "bbbb";
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
        flags = true;
        str = "1212";
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (flags) {
                    try {
                        Log.i(TAG, str);
                        Thread.sleep(1111);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
