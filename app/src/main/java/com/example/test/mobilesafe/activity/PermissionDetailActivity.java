package com.example.test.mobilesafe.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class PermissionDetailActivity extends AppCompatActivity {
    private static final String TAG = "PermissionDetail";
    private Intent intent;
    private ImageView imageView;
    private TextView textView;
    private String packageName;
    private String appName;
    private ScrollView scrollView;
    private PackageManager manager;
    private LinearLayout progress;
    private View view;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            scrollView.addView(view);
            textView.setText(appName);
            try {
                Drawable icon = manager.getApplicationIcon(packageName);
                imageView.setImageDrawable(icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            progress.setVisibility(View.INVISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_detail);

        imageView = (ImageView) findViewById(R.id.iv_apd_icon);
        textView = (TextView) findViewById(R.id.tv_apd_appName);
        scrollView = (ScrollView) findViewById(R.id.sv_apd_permission);
        progress = (LinearLayout) findViewById(R.id.ll_apd_progress);

        showPermissionDetails();

        intent = getIntent();
        packageName = intent.getStringExtra("packageName");
        appName = intent.getStringExtra("appName");

        manager = getPackageManager();
    }

    private void showPermissionDetails() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class clazz = getClass().getClassLoader().loadClass("android.widget.AppSecurityPermissions");
                    Log.i(TAG, "run is" + clazz.toString());
                    Constructor constructor = clazz.getConstructor(Context.class, String.class);
                    Object o = constructor.newInstance(getApplicationContext(), packageName);
//                    Method method = clazz.getDeclaredMethod("getPermissionsView", new Class[]{});
                    Method method = clazz.getDeclaredMethod("getPermissionsView", null);
                    view = (View) method.invoke(o, null);
                    handler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_permission_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
