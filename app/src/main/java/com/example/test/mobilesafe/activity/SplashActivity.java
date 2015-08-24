package com.example.test.mobilesafe.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.domain.UpdateInfo;
import com.example.test.mobilesafe.engine.HttpUtil;
import com.example.test.mobilesafe.engine.UpdateInfoparser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashActivity extends AppCompatActivity {
    private LinearLayout ll_splash;
    private TextView tv_splash_version;
    private URL url;
    private InputStream inputStream;
    private UpdateInfo updateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        ll_splash = (LinearLayout) findViewById(R.id.ll_splash);

        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(5000);
        ll_splash.startAnimation(animation);

        tv_splash_version.setText("Version " + getVersionName());

        String path = "http://localhost:8080";
        try {
            url = new URL(path);
            inputStream = HttpUtil.connectWebsite(url);
            UpdateInfoparser updateInfoparser = new UpdateInfoparser();
            try {
                updateInfo = updateInfoparser.parseUpdateInfo(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        String version = updateInfo.getVersion();
        Log.d("aaaa", version);
    }

    private String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), 0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "版本号未知";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
