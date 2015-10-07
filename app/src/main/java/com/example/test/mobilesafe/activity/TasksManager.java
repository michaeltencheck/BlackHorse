package com.example.test.mobilesafe.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.util.List;

public class TasksManager extends AppCompatActivity {
    private ActivityManager manager;
    private TextView processInfo, memoryInfo;
    private List<ActivityManager.RunningAppProcessInfo> list;
    private ActivityManager.MemoryInfo men;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_manager);

        manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);

        processInfo = (TextView) findViewById(R.id.tv_tm_processInfo);
        memoryInfo = (TextView) findViewById(R.id.tv_tm_memoryInfo);

        processInfo.setText("正在运行的进程数为：" + getProcessInfo());

        men = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(men);

    }

    private int getProcessInfo() {
        list = manager.getRunningAppProcesses();
        return list.size();
    }

    private String getAvailableMemory() {
        long avaMemory = men.availMem;
        return null;
    }

    private String getTotalMemory() {
        long totalMemory = men.totalMem;
        return null;
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
