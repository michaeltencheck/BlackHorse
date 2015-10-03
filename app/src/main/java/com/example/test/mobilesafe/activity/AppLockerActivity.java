package com.example.test.mobilesafe.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.AppLockerAdapter;
import com.example.test.mobilesafe.domain.AppInfo;
import com.example.test.mobilesafe.engine.AppInfoAssist;

import java.util.List;

public class AppLockerActivity extends AppCompatActivity {
    private static final int GET_DATA_SUCCESS = 222;
    private AppLockerAdapter appLockerAdapter;
    private List<AppInfo> appInfos;
    private AppInfoAssist appInfoAssist;
    private ListView listView;
    private LinearLayout linearLayout;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_DATA_SUCCESS) {
                appLockerAdapter = new AppLockerAdapter(AppLockerActivity.this, appInfos);
                listView.setAdapter(appLockerAdapter);
                linearLayout.setVisibility(View.INVISIBLE);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_locker);

        appInfoAssist = new AppInfoAssist(this);
        listView = (ListView) findViewById(R.id.lv_ala_appInfo);
        linearLayout = (LinearLayout) findViewById(R.id.ll_ala);

        initAppInfo();
    }

    private void initAppInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                appInfos = appInfoAssist.getAppInfos();
                Message msg = new Message();
                msg.what = GET_DATA_SUCCESS;
                handler.sendMessage(msg);
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_locker, menu);
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
