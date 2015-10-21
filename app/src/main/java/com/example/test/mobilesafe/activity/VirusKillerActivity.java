package com.example.test.mobilesafe.activity;

import android.content.res.AssetManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VirusKillerActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView textView;
    private ProgressBar progressBar;
    private Button button;
    private boolean isClick;
    private AnimationDrawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_virus_killer);

        imageView = (ImageView) findViewById(R.id.iv_avk_picture);
        textView = (TextView) findViewById(R.id.tv_avk_scan);
        progressBar = (ProgressBar) findViewById(R.id.pb_avk_progress);
        button = (Button) findViewById(R.id.bt_avk_scan);
        isClick = false;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isClick) {
                    drawable.start();
                    isClick = true;
                }
            }
        });


        imageView.setBackgroundResource(R.drawable.anim_scan);
        drawable = (AnimationDrawable) imageView.getBackground();

        downloadDb();
    }

    private void downloadDb() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String state = Environment.getExternalStorageState();
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/antivirus.db";
                File file = new File(path);
                File file1 = new File(getApplicationContext().getFilesDir(), "antivirus.db");
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    if (!file.exists()) {
                        AssetManager am = getAssets();
                        InputStream is;
                        try {
                            is = am.open("antivirus.db");
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int len = 0;
                            while ((len = is.read()) != -1) {
                                fos.write(bytes, 0, len);
                            }
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    if (!file1.exists()) {
                        AssetManager am = getAssets();
                        InputStream is;
                        try {
                            is = am.open("antivirus.db");
                            FileOutputStream fos = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int len = 0;
                            while ((len = is.read()) != -1) {
                                fos.write(bytes, 0, len);
                            }
                            fos.flush();
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
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
