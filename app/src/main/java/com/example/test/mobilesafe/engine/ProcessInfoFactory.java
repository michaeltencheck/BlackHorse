package com.example.test.mobilesafe.engine;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Debug;

import com.example.test.mobilesafe.domain.ProcessInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 10/8/2015.
 */
public class ProcessInfoFactory {
    private Context context;
    private ActivityManager am;
    private PackageManager pm;

    public ProcessInfoFactory(Context context) {
        this.context = context;
        this.am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.pm = context.getPackageManager();
    }

    public List<ProcessInfo> getProcessInfos() throws PackageManager.NameNotFoundException {
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        List<ProcessInfo> processInfos = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo info : list) {
            int pID = info.pid;
            String packageName = info.processName;
            ApplicationInfo applicationInfo =
                    pm.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            Drawable drawable = applicationInfo.loadIcon(pm);
            String appName = applicationInfo.name;
            Debug.MemoryInfo[] memoryInfos = am.getProcessMemoryInfo(new int[]{pID});
            Debug.MemoryInfo memoryInfo = memoryInfos[0];
            int memory = memoryInfo.getTotalPrivateDirty();
            ProcessInfo processInfo = new ProcessInfo(appName, memory, pID, drawable, false);
            processInfos.add(processInfo);
        }
        return processInfos;
    }
}
