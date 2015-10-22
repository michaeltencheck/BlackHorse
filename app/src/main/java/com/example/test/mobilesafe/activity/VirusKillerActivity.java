package com.example.test.mobilesafe.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.util.MD5Encode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Signature;
import java.util.ArrayList;
import java.util.List;

public class VirusKillerActivity extends AppCompatActivity {
    private static final String TAG = "VirusKillerActivity";
    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;
    private Button button;
    private boolean isClick;
    private AnimationDrawable drawable;
    private AssetManager am;
    private SQLiteDatabase database;
    private TextView pro;
    private TextView detail;
    private List<PackageInfo> virus;
    private List<PackageInfo> infos;
    private int count;
    private String path;
    private String state;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
                    button.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
                    Log.i(TAG, "handleMessage "+database.getPath());
                    button.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virus_killer);

        imageView = (ImageView) findViewById(R.id.iv_avk_picture);
        textView = (TextView) findViewById(R.id.tv_avk_scan);
        progressBar = (ProgressBar) findViewById(R.id.pb_avk_progress);
        pro = (TextView) findViewById(R.id.tv_avk_progress);
        button = (Button) findViewById(R.id.bt_avk_scan);
        virus = new ArrayList<>();
        isClick = false;
        state = Environment.getExternalStorageState();
        am = getAssets();
        count = 0;
        detail = (TextView) findViewById(R.id.tv_avk_detail);
        PackageManager pm = getPackageManager();
        infos = pm.getInstalledPackages
                (PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_SIGNATURES);
        progressBar.setMax(infos.size());

        if (state.equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/antivirus.db";
        } else {
            path = this.getFilesDir() + "/antivirus.db";
        }

//        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        imageView.setBackgroundResource(R.drawable.anim_scan);
        drawable = (AnimationDrawable) imageView.getBackground();

        downloadDb();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    drawable.start();
                    isClick = true;
                    List<String> strs = new ArrayList<String>();

                    Cursor c = database.rawQuery("select md5 from datable", null);
                    while (c.moveToNext()) {
                        String str = c.getColumnName(0);
                        strs.add(str);
                    }
                    Log.i(TAG, "onClick " + strs.size());
                    c.close();
                    int progress = 0;
                    for (PackageInfo info : infos) {
                        android.content.pm.Signature[] signatures = info.signatures;
//                        String si = signatures.toString();
                        String si = signatures[0].toCharsString();
//                        Log.i(TAG, "onClick "+si);
                        String md5 = MD5Encode.MD5Encoding(si);
                        if (strs.contains(md5)) {
                            count++;
                            virus.add(info);
                        }
                        Log.i(TAG, "onClick " + info.packageName);
                        textView.setText("正在扫描" + info.packageName);
                        progress++;
                        progressBar.setProgress(progress);
                        pro.setText(progress + "/" + infos.size());
                    }
                    textView.setText("扫描完毕");
                    if (count == 0) {
                        detail.setText("您的系统安全，没有找到病毒");
                    } else {
                        detail.setText("共找到"+count+"个病毒");
                    }
                    isClick = false;
                    drawable.stop();
                }
            }
        });


    }

    private void downloadDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(path);
                if (!file.exists()) {
                    try {
                        InputStream is = am.open("antivirus.db");
                        FileOutputStream fos = new FileOutputStream(file);
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read()) != -1) {
                            fos.write(bytes, 0, len);
                        }
                        fos.flush();
                        fos.close();
                        Log.i(TAG, "run "+file.getTotalSpace());
                        handler.sendEmptyMessage(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    handler.sendEmptyMessage(1);
                } }}).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_virus_killer, menu);
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
