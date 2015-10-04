package com.example.test.mobilesafe.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.AppLockerAdapter;
import com.example.test.mobilesafe.db.AppLockerDAO;
import com.example.test.mobilesafe.domain.AppInfo;
import com.example.test.mobilesafe.engine.AppInfoAssist;

import java.util.List;

public class AppLockerActivity extends AppCompatActivity {
    private static final int GET_DATA_SUCCESS = 222;
    private AppLockerAdapter appLockerAdapter;
    private List<AppInfo> appInfos;
    private List<String> appLocked;
    private AppInfoAssist appInfoAssist;
    private AppLockerDAO appLockerDAO;
    private ListView listView;
    private ImageView appStatus;
    private AppInfo selected;
    private TranslateAnimation animation;
    private LinearLayout linearLayout;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == GET_DATA_SUCCESS) {
                appLockerAdapter = new AppLockerAdapter(AppLockerActivity.this, appInfos, appLocked);
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
        appLockerDAO = new AppLockerDAO(this);
        appLocked = appLockerDAO.findAll();
        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.3f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(300);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected = appInfos.get(position);
                appStatus = (ImageView) view.findViewById(R.id.iv_ala_locker);
                if (appLocked.contains(selected.getPackageName())) {
                    appLockerDAO.delete(selected.getPackageName());
                    appLocked.remove(selected.getPackageName());
                    view.startAnimation(animation);
                    appStatus.setImageResource(R.drawable.unlock);
                } else {
                    appLockerDAO.add(selected.getPackageName());
                    appLocked.add(selected.getPackageName());
                    view.startAnimation(animation);
                    appStatus.setImageResource(R.drawable.lock);
                }

            }
        });

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
