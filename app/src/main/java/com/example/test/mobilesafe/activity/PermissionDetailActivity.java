package com.example.test.mobilesafe.activity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class PermissionDetailActivity extends AppCompatActivity {
    private static final String TAG = "PermissionDetail";
    private Intent intent;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout layout;
    private View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_detail);

        imageView = (ImageView) findViewById(R.id.iv_apd_icon);
        textView = (TextView) findViewById(R.id.tv_apd_appName);
        layout = (LinearLayout) findViewById(R.id.ll_apd_permission);

        showPermissionDetails();



        intent = getIntent();
        String packageName = intent.getStringExtra("packageName");
        String appName = intent.getStringExtra("appName");

        textView.setText(appName);
        PackageManager manager = getPackageManager();
        try {
            Drawable icon = manager.getApplicationIcon(packageName);
            imageView.setImageDrawable(icon);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void showPermissionDetails() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class clazz = getClass().getClassLoader().loadClass("android.widget.AppSecurityPermissions");
                    Log.i(TAG, "run is" + clazz.toString());
                    Constructor constructor = clazz.getConstructor(Context.class, String.class);
                    Object o = constructor.newInstance(getApplicationContext(), "com.example.test.mobilesafe");
//                    Method method = clazz.getDeclaredMethod("getPermissionsView", new Class[]{});
                    Method method = clazz.getDeclaredMethod("getPermissionsView", null);
                    View view = (View) method.invoke(o, null);
                    setContentView(view);
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
