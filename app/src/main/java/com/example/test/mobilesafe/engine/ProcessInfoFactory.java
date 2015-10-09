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

    public List<ProcessInfo> getCustomerProcessInfos
            (List<ActivityManager.RunningAppProcessInfo> list,List<String> list1)
            throws PackageManager.NameNotFoundException {
        List<ProcessInfo> processInfos = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo info : list) {
            int pID = info.pid;
            String packageName = info.processName;
            if (list1.contains(packageName)) {
                ApplicationInfo applicationInfo =
                        pm.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                AppInfoAssist appInfoAssist = new AppInfoAssist(context);
                if (appInfoAssist.filterApp(applicationInfo)) {
                    Drawable icon = applicationInfo.loadIcon(pm);
                    String name = applicationInfo.loadLabel(pm).toString();
                    Debug.MemoryInfo[] memoryInfos = am.getProcessMemoryInfo(new int[]{pID});
                    Debug.MemoryInfo memoryInfo = memoryInfos[0];
                    int memory = memoryInfo.getTotalPrivateDirty();
                    ProcessInfo processInfo = new ProcessInfo(name, memory, pID, icon, false);
                    processInfos.add(processInfo);
                }
            }
            /*ApplicationInfo applicationInfo =
                    pm.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);*/
            /*List<ApplicationInfo> applicationInfos = pm.getInstalledApplications
                    (PackageManager.GET_UNINSTALLED_PACKAGES);
            for (ApplicationInfo info1 : applicationInfos) {
                if (packageName.equals(info1.packageName)) {
                    Drawable drawable = info1.loadIcon(pm);
                    String appName = info1.loadLabel(pm).toString();
                    Debug.MemoryInfo[] memoryInfos = am.getProcessMemoryInfo(new int[]{pID});
                    Debug.MemoryInfo memoryInfo = memoryInfos[0];
                    int memory = memoryInfo.getTotalPrivateDirty();
                    ProcessInfo processInfo = new ProcessInfo(appName, memory, pID, drawable, false);
                    processInfos.add(processInfo);
                }
            }*/
        }
        return processInfos;
    }

    public List<ProcessInfo> getSystemProcessInfos
            (List<ActivityManager.RunningAppProcessInfo> list,List<String> list1)
            throws PackageManager.NameNotFoundException {
        List<ProcessInfo> processInfos = new ArrayList<>();
        for (ActivityManager.RunningAppProcessInfo info : list) {
            int pID = info.pid;
            String packageName = info.processName;
            if (list1.contains(packageName)) {
                ApplicationInfo applicationInfo =
                        pm.getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
                AppInfoAssist appInfoAssist = new AppInfoAssist(context);
                if (!appInfoAssist.filterApp(applicationInfo)) {
                    Drawable icon = applicationInfo.loadIcon(pm);
                    String name = applicationInfo.loadLabel(pm).toString();
                    Debug.MemoryInfo[] memoryInfos = am.getProcessMemoryInfo(new int[]{pID});
                    Debug.MemoryInfo memoryInfo = memoryInfos[0];
                    int memory = memoryInfo.getTotalPrivateDirty();
                    ProcessInfo processInfo = new ProcessInfo(name, memory, pID, icon, false);
                    processInfos.add(processInfo);
                }
            }
        }
        return processInfos;
    }
}
