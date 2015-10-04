package com.example.test.mobilesafe.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.test.mobilesafe.db.AppLockerDAO;

import java.util.List;

public class AppMonitorService extends Service {
    private static final String TAG = "AppMonitorService";
    private List<String> appLocked;
    private AppLockerDAO appLockerDAO;

    public AppMonitorService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appLockerDAO = new AppLockerDAO(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    appLocked = appLockerDAO.findAll();
                    for (String app : appLocked) {
                        Log.i(TAG, app);
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
