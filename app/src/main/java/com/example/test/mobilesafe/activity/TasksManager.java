package com.example.test.mobilesafe.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.ShareActionProvider;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.ProcessInfoAdapter;
import com.example.test.mobilesafe.domain.ProcessInfo;
import com.example.test.mobilesafe.engine.ProcessInfoFactory;
import com.example.test.mobilesafe.receiver.BackgroundKiller;
import com.example.test.mobilesafe.util.DecimalFormater;
import com.example.test.mobilesafe.util.MyToast;

import java.security.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TasksManager extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TasksManager";
    private ActivityManager manager;
    private TextView processInfo, memoryInfo;
    private List<ActivityManager.RunningAppProcessInfo> list;
    private List<ApplicationInfo> list1;
    private List<String> pn, pn1;
    private ActivityManager.MemoryInfo men;
    private ShareActionProvider provider;
    private Button clear, setting;
    private LinearLayout linearLayout;
    private List<ProcessInfo> customerProcessInfos;
    private List<ProcessInfo> systemProcessInfos;
    private LinearLayout progressBar;
    private RelativeLayout relativeLayout;
    private WindowManager.LayoutParams wl;
    private ProcessInfoAdapter adapter;
    private ActivityManager activityManager;
    private ListView listView;
    private Intent sendIntent;
    private PackageManager pm;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showSummary();
            listView.setAdapter(adapter);
            progressBar.setVisibility(View.INVISIBLE);
        }
    };

    private void showSummary() {
        processInfo.setText("正在运行的进程数为：" + getProcessInfo(customerProcessInfos,systemProcessInfos));
        memoryInfo.setText("系统剩余内存/总内存：" + getAvailableMemory() + "/" + getTotalMemory());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_manager);

        listView = (ListView) findViewById(R.id.lv_tm_processDetail);
        progressBar = (LinearLayout) findViewById(R.id.ll_tm_progressBar);

        clear = (Button) findViewById(R.id.bt_tm_clear);
        setting = (Button) findViewById(R.id.bt_tm_setting);
        linearLayout = (LinearLayout) findViewById(R.id.ll_tm_allApps);

        relativeLayout = (RelativeLayout) getLayoutInflater()
                .inflate(R.layout.activity_tasks_manager, null);

/*        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> list = manager.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo info : list) {
                    String packageName = info.processName;
                    Log.i(TAG, "onReceive " + packageName);
                    manager.killBackgroundProcesses(packageName);
            }
        }};*/

        BackgroundKiller killer = new BackgroundKiller();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(killer, filter);

        clear.setOnClickListener(this);
        setting.setOnClickListener(this);

        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);


        manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        pm = this.getPackageManager();

        sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        sendIntent.setType("text/plain");

        pn = new ArrayList<>();
        pn1 = new ArrayList<>();

        processInfo = (TextView) findViewById(R.id.tv_tm_processInfo);
        memoryInfo = (TextView) findViewById(R.id.tv_tm_memoryInfo);


        initProcessInfo();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == customerProcessInfos.size() + 1) {

                } else {
                    ProcessInfo processInfo = (ProcessInfo) listView.getItemAtPosition(position);
                    if (!processInfo.getPackageName().equals(getApplication().getPackageName())) {
                        if (processInfo.isChecked()) {
                            processInfo.setIsChecked(false);
                            adapter.notifyDataSetChanged();
                        } else {
                            processInfo.setIsChecked(true);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }/*else if (position >= customerProcessInfos.size() + 2) {
                    ProcessInfo processInfo =
                            (ProcessInfo) listView.getItemAtPosition(position - customerProcessInfos.size() - 2);
                    if (processInfo.isChecked()) {
                        processInfo.setIsChecked(false);
                        checkBox.setChecked(false);
                    } else {
                        processInfo.setIsChecked(true);
                        checkBox.setChecked(true);
                    }
                }*/
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0 & position != customerProcessInfos.size() + 1) {
                    Intent intent = new Intent(getApplicationContext(), PermissionDetailActivity.class);
                    ProcessInfo processInfo = (ProcessInfo) listView.getItemAtPosition(position);
                    String packageName = processInfo.getPackageName();
                    String appName = processInfo.getName();
                    intent.putExtra("appName", appName);
                    intent.putExtra("packageName", packageName);

                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                    layoutParams.dimAmount = 1.0f;
                    getWindow().setAttributes(layoutParams);

                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in_translate, R.anim.anim_out_translate);



/*                    ImageView imageView = new ImageView(getApplicationContext());
                    Drawable drawable = new ColorDrawable(Color.CYAN);
                    imageView.setImageDrawable(drawable);
                    linearLayout.setVisibility(View.INVISIBLE);
                    linearLayout.addView(imageView);*/
/*                    RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater()
                            .inflate(R.layout.activity_tasks_manager, null);
                    WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
                    WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) relativeLayout.getLayoutParams();
                    layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    layoutParams.dimAmount = 0.3f;
                    wm.updateViewLayout(relativeLayout, layoutParams);*/
                }
                return false;
            }
        });
    }

    private void getMemInfo() {
        men = new ActivityManager.MemoryInfo();
        manager.getMemoryInfo(men);
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
                    customerProcessInfos = factory.getCustomerProcessInfos(list, pn);
                    systemProcessInfos = factory.getSystemProcessInfos(list, pn);
                    adapter = new ProcessInfoAdapter
                            (getApplicationContext(), customerProcessInfos, systemProcessInfos);
                    handler.sendEmptyMessage(0);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void killBackground() {
        int count = 0;
        long memories = 0;
        for (ProcessInfo info : customerProcessInfos) {
            if (info.isChecked()) {
                String packageName = info.getPackageName();
                activityManager.killBackgroundProcesses(packageName);
                count++;
                memories += info.getMemory();
            }
        }
        for (ProcessInfo info : systemProcessInfos) {
            if (info.isChecked()) {
                String packageName = info.getPackageName();
                activityManager.killBackgroundProcesses(packageName);
                count++;
                memories += info.getMemory();
            }
        }
        Toast.makeText(this,
                "此次操作总共为您清理" + count + "个后台进程，释放" + DecimalFormater.getKBNumber(memories) + "内存空间", Toast.LENGTH_SHORT).show();
/*        MyToast.showToast(this,"此次操作总共为您清理" + count + "个后台进程，释放" + DecimalFormater.getKBNumber(memories) + "内存空间",R.drawable.icon5);*/
    }

    private int getProcessInfo(List list1,List list2) {
        return list1.size() + list2.size();
    }

    private String getAvailableMemory() {
        getMemInfo();
        long avaMemory = men.availMem;
        Log.i(TAG, "getAvailableMemory " + avaMemory);
        return DecimalFormater.getNumber(avaMemory);
    }

    private String getTotalMemory() {
        getMemInfo();
        long totalMemory = men.totalMem;
        Log.i(TAG, "getTotalMemory " + totalMemory);
        return DecimalFormater.getNumber(totalMemory);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tasks_manager, menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        provider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        if (provider != null) {
            provider.setShareIntent(sendIntent);
        }
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
            case R.id.bt_tm_clear:
                killBackground();
                refreshCustomer();
                refreshSystem();
                adapter.notifyDataSetChanged();
                showSummary();
                break;
            default:
                break;
        }
    }

    public void refreshCustomer() {
        Iterator<ProcessInfo> iterator = customerProcessInfos.iterator();
        while (iterator.hasNext()) {
            ProcessInfo info = iterator.next();
            if (info.isChecked()) {
                iterator.remove();
                break;
            }
        }
    }

    public void refreshSystem() {
        Iterator<ProcessInfo> iterator = systemProcessInfos.iterator();
        while (iterator.hasNext()) {
            ProcessInfo info = iterator.next();
            if (info.isChecked()) {
                iterator.remove();
            }
        }
    }
}
