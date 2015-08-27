package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.domain.UpdateInfo;
import com.example.test.mobilesafe.engine.DownloadFileTask;
import com.example.test.mobilesafe.engine.UpdateInfoService;
import com.example.test.mobilesafe.engine.UpdateInfoServiceByUpdateInfo;

import java.io.File;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private LinearLayout ll_splash;
    private TextView tv_splash_version;
    private SharedPreferences sp;
    private ProgressDialog progressDialog;
    private UpdateInfo updateInfo;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isNeedUpdate(getVersionName())) {
                showDialog();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("downloading, please wait");
        tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
        ll_splash = (LinearLayout) findViewById(R.id.ll_splash);

        Animation animation = new AlphaAnimation(0f, 1f);
        animation.setDuration(5000);
        ll_splash.startAnimation(animation);

        tv_splash_version.setText("Version " + getVersionName());

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    sleep(3333);
                    handler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();



        /*if (isNeedUpdateByUpdateInfo(getVersionName())) {
            showDialog2();
        }*/

    }

    private void install(File file) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        finish();
        startActivity(intent);
    }

    private UpdateInfo updateInfoThread() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                UpdateInfoServiceByUpdateInfo updateInfoServiceByUpdateInfo =
                        new UpdateInfoServiceByUpdateInfo(getApplicationContext());
                try {
                    updateInfo = updateInfoServiceByUpdateInfo.getUpdateInfo(R.string.apkurl);
                    Log.i("dddd", updateInfo.getVersion());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return updateInfo;
    }

    private class DownloadThread implements Runnable {
        private String path;
        private String filePath;

        public DownloadThread(String path, String filePath) {
            this.path = path;
            this.filePath = filePath;
        }

        @Override
        public void run() {
            try {
                File file = DownloadFileTask.getFile(path, filePath);
                progressDialog.dismiss();
                Log.i(TAG, "thread running");
                install(file);
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Log.i(TAG, "downloading mistake");
                loadMainUI();
            }
        }
    }

    private void loadMainUI() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.icon5);
        builder.setTitle("升级提醒");
        builder.setMessage(updateInfo.getDesciption());
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "正在下载" + updateInfo.getApkurl());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadMainUI();
            }
        });
        builder.create().show();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.icon5);
        builder.setTitle("升级提醒");
        builder.setMessage(sp.getString("description", ""));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG, "正在下载" + sp.getString("apkurl", ""));
                String path = getApplicationContext().getResources().getString(R.string.apkdownload);
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
                Log.i(TAG, filePath);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    progressDialog.show();
                    DownloadThread downloadThread = new DownloadThread(path, filePath + "/mobilesafe.apk");
                    new Thread(downloadThread).start();
                    Log.i(TAG, "download successful");
                } else {
                    Log.i(TAG, "can not store");
                    loadMainUI();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadMainUI();
            }
        });
        builder.create().show();
    }

    private boolean isNeedUpdateByUpdateInfo(String localVersion) {
        updateInfoThread();
        Log.i("aaaa", updateInfo.getVersion());
        if (localVersion.equals(updateInfo.getVersion())) {
            return false;
        } else {
            return true;
        }
    }

    private boolean isNeedUpdate(String versionName)  {
        UpdateInfoService service = new UpdateInfoService(this);

        try {
            service.getUpdateInfo(R.string.apkurl);
            sp = PreferenceManager.getDefaultSharedPreferences(this);
            String serviceVersion = sp.getString("version", "");
            if (versionName.equals(serviceVersion)) {
                loadMainUI();
                return false;

            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadMainUI();
        return false;
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
