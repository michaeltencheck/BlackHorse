package com.example.test.mobilesafe.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.engine.DownloadFileTask;

import java.io.File;

public class HighCastActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int ERROR = 10;
    private static final String TAG = "HighCastActivity";
    private static final int SUCCEED = 20;
    private Button numberLocation;
    private String path;
    private ProgressDialog pd;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ERROR:
                    Toast.makeText(getApplicationContext(), "下载失败", Toast.LENGTH_LONG).show();
                    break;
                case SUCCEED:
                    Toast.makeText(getApplicationContext(),"下载成功", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_cast);

        numberLocation = (Button) findViewById(R.id.bt_numberLocation);
        numberLocation.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_cast, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_numberLocation:
                if (isDBExist()) {
                    Intent intent = new Intent(this, TelNumLocationActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in_translate, R.anim.anim_out_translate);
                } else {
                    pd = new ProgressDialog(this);
                    pd.setMessage("正在下载");
                    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    pd.show();
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        new Thread(){
                            @Override
                            public void run() {
                                String downloadPath = getResources().getString(R.string.address_db_url);
                                try {
                                    DownloadFileTask.getFile(downloadPath, path, pd);
                                    pd.dismiss();
                                    Message msg = new Message();
                                    msg.what = SUCCEED;
                                    handler.sendMessage(msg);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    pd.dismiss();
                                    Message message = new Message();
                                    message.what = ERROR;
                                    handler.sendMessage(message);
                                }
                            }
                        }.start();
                    }
                }

                break;
        }
    }

    public boolean isDBExist() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/address.db";
        File file = new File(path);
        return file.exists();
    }
}
