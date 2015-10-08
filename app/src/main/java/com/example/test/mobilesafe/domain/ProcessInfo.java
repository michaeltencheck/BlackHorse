package com.example.test.mobilesafe.domain;

import android.graphics.drawable.Drawable;
import android.widget.CheckBox;

/**
 * Created by test on 10/8/2015.
 */
public class ProcessInfo {
    private String name;
    private String memory;
    private Drawable icon;
    private CheckBox checkBox;

    public ProcessInfo(String name, String memory, Drawable icon, CheckBox checkBox) {
        this.name = name;
        this.memory = memory;
        this.icon = icon;
        this.checkBox = checkBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }
}
