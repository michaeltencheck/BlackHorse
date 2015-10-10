package com.example.test.mobilesafe.domain;

import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

/**
 * Created by test on 10/8/2015.
 */
public class ProcessInfo {
    private String name;
    private int memory;
    private int pid;
    private String packageName;
    private Drawable icon;
    private boolean isChecked;

    public ProcessInfo(String name, int memory, int pid, String packageName, Drawable icon, boolean isChecked) {
        this.name = name;
        this.memory = memory;
        this.pid = pid;
        this.packageName = packageName;
        this.icon = icon;
        this.isChecked = isChecked;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
