package com.example.test.mobilesafe.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.AppInfoAdapter;
import com.example.test.mobilesafe.domain.AppInfo;
import com.example.test.mobilesafe.engine.AppInfoAssist;

import java.util.List;

public class AppManagerAct extends AppCompatActivity {
    private static final int GET_ALL_APPINFOS = 200;
    private ListView lv_appInfos;
    private AppInfoAdapter appInfoAdapter;
    private List<AppInfo> appInfos;
    private static AppInfo appInfo = null;
    private PopupWindow popupWindow = null;
    private LinearLayout ll;
    private boolean scroll = false;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_ALL_APPINFOS:
                    appInfoAdapter = new AppInfoAdapter(getApplicationContext(), appInfos);
                    lv_appInfos.setAdapter(appInfoAdapter);
                    ll.setVisibility(View.INVISIBLE);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);

        lv_appInfos = (ListView) findViewById(R.id.lv_ama_appInfo);
        ll = (LinearLayout) findViewById(R.id.ll_ama);

        new Thread(new Runnable() {
            @Override
            public void run() {
                AppInfoAssist appInfoAssist = new AppInfoAssist(getApplicationContext());
                appInfos = appInfoAssist.getAppInfos();
                Message message = new Message();
                message.what = GET_ALL_APPINFOS;
                handler.sendMessage(message);
            }
        }).start();

        lv_appInfos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupDismiss();
                appInfo = appInfos.get(position);
                /*TextView textView = new TextView(getApplicationContext());
                textView.setText(appInfo.getAppName());
                Drawable drawable = new ColorDrawable(Color.CYAN);
                textView.setBackground(drawable);*/

                int[] arrayOf = new int[2];
                view.getLocationOnScreen(arrayOf);
                int i = arrayOf[0] + 60;
                int j = arrayOf[1];

                popupWindow = new PopupWindow(textView, 60, 60);
                popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, i, j);
            }
        });

        lv_appInfos.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                popupDismiss();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                popupDismiss();
            }
        });
    }

    public void popupDismiss() {
        if (popupWindow != null) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_app_manager, menu);
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
