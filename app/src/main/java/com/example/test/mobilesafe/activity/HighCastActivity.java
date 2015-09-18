package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.engine.DownloadFileTask;
import com.example.test.mobilesafe.service.ShowTelLocService;

import java.io.File;

public class HighCastActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int ERROR = 10;
    private static final String TAG = "HighCastActivity";
    private static final int SUCCEED = 20;
    private Button numberLocation;
    private String path;
    private TextView textView;
    private Intent intent;
    private SharedPreferences.Editor editor;
    private CheckBox checkBox;
    private int color;
    private RelativeLayout relativeLayout;
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

        editor = getSharedPreferences("config", MODE_PRIVATE).edit();

        relativeLayout = (RelativeLayout) findViewById(R.id.rl_hc_colorChange);
        relativeLayout.setOnClickListener(this);

        numberLocation = (Button) findViewById(R.id.bt_numberLocation);
        numberLocation.setOnClickListener(this);

        textView = (TextView) findViewById(R.id.tv_hc_showTelLocation);

        intent = new Intent(this, ShowTelLocService.class);

        checkBox = (CheckBox) findViewById(R.id.cb_hc_showTelLocation);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(intent);
                    textView.setText("来电显示归属地已开启");
                } else {
                    stopService(intent);
                    textView.setText("来电显示归属地未开启");
                }
            }
        });
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
            case R.id.rl_hc_colorChange:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("选择颜色");
                builder.setCancelable(false);
                String[] items = new String[]{"天空灰", "活力橙", "卫士蓝"};
                builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                editor.putInt("toast_color", 0);
                                break;
                            case 1:
                                editor.putInt("toast_color", 1);
                                break;
                            case 2:
                                editor.putInt("toast_color", 2);
                                break;
                            default:
                                break;
                        }
                    }
                });
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editor.commit();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
        }
    }

    public boolean isDBExist() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/address.db";
        File file = new File(path);
        return file.exists();
    }
}
