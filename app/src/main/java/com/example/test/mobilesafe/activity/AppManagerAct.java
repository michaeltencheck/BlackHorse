package com.example.test.mobilesafe.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.AppInfoAdapter;
import com.example.test.mobilesafe.domain.AppInfo;
import com.example.test.mobilesafe.engine.AppInfoAssist;

import java.util.List;

public class AppManagerAct extends AppCompatActivity implements View.OnClickListener{
    private static final int GET_ALL_APPINFOS = 200;
    private static final String TAG = "AppManagerAct";
    private ListView lv_appInfos;
    private AppInfoAdapter appInfoAdapter;
    private List<AppInfo> appInfos;
    private PopupWindow popupWindow = null;
    private View popupwindowView;
    private LinearLayout ll;
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
                /*TextView textView = new TextView(getApplicationContext());
                textView.setText(appInfo.getAppName());
                Drawable drawable = new ColorDrawable(Color.CYAN);
                textView.setBackground(drawable);*/
                Animation animation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
                animation.setDuration(300);
                popupwindowView = View.inflate(AppManagerAct.this, R.layout.popupwindow_item, null);
                popupwindowView.setTag(position);

                LinearLayout ll_start = (LinearLayout) popupwindowView.findViewById(R.id.ll_ama_start);
                LinearLayout ll_uninstall = (LinearLayout) popupwindowView.findViewById(R.id.ll_ama_uninstall);
                LinearLayout ll_share = (LinearLayout) popupwindowView.findViewById(R.id.ll_ama_share);

                ll_start.setOnClickListener(AppManagerAct.this);
                ll_uninstall.setOnClickListener(AppManagerAct.this);
                ll_share.setOnClickListener(AppManagerAct.this);

                int[] arrayOf = new int[2];
                view.getLocationOnScreen(arrayOf);
                int i = arrayOf[0] + 60;
                int j = arrayOf[1];

//                popupWindow = new PopupWindow(textView, 60, 60);
                popupWindow = new PopupWindow(popupwindowView, 242, 80);
                Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
                popupWindow.setBackgroundDrawable(drawable);
                popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, i, j);
                popupwindowView.startAnimation(animation);
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

    @Override
    public void onClick(View v) {
        int position = (int) popupwindowView.getTag();
        AppInfo appInfo = appInfos.get(position);
        String packageName = appInfo.getPackageName();
        /*PackageInfo packageInfo = getPackageManager()
                .getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES).get(position);*/

        switch (v.getId()) {
            case R.id.ll_ama_start:
                try {
//                  packageInfo所含内容过多，需要flags修饰过滤，GET_UNINSTALLED_PACKAGES，即
//                  把有数据目录存在的package返回，GET_ACTIVITIES即返回package的所有act
                    PackageInfo packageInfo = getPackageManager().getPackageInfo
                            (packageName, PackageManager.GET_ACTIVITIES);
                    ActivityInfo[] activityInfos = packageInfo.activities;
                    if (activityInfos.length > 0) {
                        Intent intent = new Intent();
                        intent.setClassName(packageName, activityInfos[0].name);
                        startActivity(intent);
                        popupWindow.dismiss();
                    } else {
                        Log.i(TAG, "该程序无法启动");
                        Toast.makeText(this, "该程序无法启动", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, "该程序无法启动");
                    Toast.makeText(this, "该程序无法启动", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.ll_ama_uninstall:
                break;
            case R.id.ll_ama_share:
                Intent intentShare = new Intent();
                intentShare.setAction(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_SUBJECT, "分享");
                intentShare.putExtra(Intent.EXTRA_TEXT, "强烈推荐一个应用：" + appInfo.getAppName());
                startActivity(intentShare);
                popupWindow.dismiss();
                break;
        }
    }
}
