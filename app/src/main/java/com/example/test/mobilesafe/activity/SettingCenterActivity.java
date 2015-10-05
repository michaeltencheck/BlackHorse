package com.example.test.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.service.AppMonitorService;

public class SettingCenterActivity extends AppCompatActivity {
    private CheckBox checkBox;
    private Intent appMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_center);

        appMonitor = new Intent(this, AppMonitorService.class);
        /*appMonitor = new Intent();
        appMonitor.setClassName(this.getApplication().getPackageName(), AppMonitorService.class.getName());*/

        checkBox = (CheckBox) findViewById(R.id.cb_sc_appLocker);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(appMonitor);
                } else {
                    stopService(appMonitor);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting_center, menu);
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
