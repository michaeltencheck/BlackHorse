package com.example.test.mobilesafe.engine;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.test.mobilesafe.domain.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 9/28/2015.
 */
public class AppInfoAssist {
    private Context context;

    public AppInfoAssist(Context context) {
        this.context = context;
    }

    public List<AppInfo> getAppInfos() {
        List<AppInfo> appInfos = new ArrayList<>();
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos =
                packageManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (PackageInfo packageInfo : packageInfos) {
            String packageName = packageInfo.packageName;
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            Drawable icon = applicationInfo.loadIcon(packageManager);
            String appName = applicationInfo.loadLabel(packageManager).toString();
            boolean isSystemApp;
            if (filterApp(applicationInfo)) {
                isSystemApp = false;
            } else {
                isSystemApp = true;
            }
            AppInfo appInfo = new AppInfo(appName, packageName, icon, isSystemApp);
            appInfos.add(appInfo);
        }

        return appInfos;
    }

    public boolean filterApp(ApplicationInfo info) {
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
        }else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
