package com.example.test.mobilesafe.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.ProcessInfoAdapter;
import com.example.test.mobilesafe.domain.ProcessInfo;
import com.example.test.mobilesafe.engine.ProcessInfoFactory;
import com.example.test.mobilesafe.util.DecimalFormater;

import java.util.ArrayList;
import java.util.List;

public class TasksManager extends AppCompatActivity {
    private static final String TAG = "TasksManager";
    private ActivityManager manager;
    private TextView processInfo, memoryInfo;
    private List<ActivityManager.RunningAppProcessInfo> list;
    private List<ApplicationInfo> list1;
    private List<String> pn, pn1;
    private ActivityManager.MemoryInfo men;
    private List<ProcessInfo> processInfos;
    private ProcessInfoAdapter adapter;
    private ListView listView;
    private PackageManager pm;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            processInfo.setText("正在运行的进程数为：" + getProcessInfo());
            memoryInfo.setText("系统剩余内存/总内存：" + getAvailableMemory() + "/" + getTotalMemory());
            listView.setAdapter(adapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_manager);

        listView = (ListView) findViewById(R.id.lv_tm_processDetail);

        manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        pm = this.getPackageManager();

        pn = new ArrayList<>();
        pn1 = new ArrayList<>();

        processInfo = (TextView) findViewById(R.id.tv_tm_processInfo);
        memoryInfo = (TextView) findViewById(R.id.tv_tm_memoryInfo);

        men = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(men);

        initProcessInfo();
    }

    private void initProcessInfo() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ProcessInfoFactory factory = new ProcessInfoFactory(getApplicationContext());
                try {
                    list1 = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                    for (ApplicationInfo applicationInfo : list1) {
                        String packageName = applicationInfo.packageName;
                        pn1.add(packageName);
                    }
                    list = manager.getRunningAppProcesses();
                    for (ActivityManager.RunningAppProcessInfo info : list) {
                        String packageName = info.processName;
                        if (pn1.contains(packageName)) {
                            pn.add(packageName);
                        }
                    }
                    processInfos = factory.getProcessInfos(list);
                    adapter = new ProcessInfoAdapter(getApplicationContext(), processInfos);
                    handler.sendEmptyMessage(0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private int getProcessInfo() {
        return pn.size();
    }

    private String getAvailableMemory() {
        long avaMemory = men.availMem;
        Log.i(TAG, "getAvailableMemory " + avaMemory);
        return DecimalFormater.getNumber(avaMemory);
    }

    private String getTotalMemory() {
        long totalMemory = men.totalMem;
        Log.i(TAG, "getTotalMemory " + totalMemory);
        return DecimalFormater.getNumber(totalMemory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tasks_manager, menu);
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
