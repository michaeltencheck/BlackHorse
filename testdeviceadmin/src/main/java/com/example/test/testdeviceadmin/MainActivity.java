package com.example.test.testdeviceadmin;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.test.testdeviceadmin.receiver.MyDeviceAdmin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button lockScreem;
    private Button alarm;
    private DevicePolicyManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lockScreem = (Button) findViewById(R.id.bt_lockScreem);
        alarm = (Button) findViewById(R.id.bt_alarm);

        lockScreem.setOnClickListener(this);
        alarm.setOnClickListener(this);

        manager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName name = new ComponentName(this, MyDeviceAdmin.class);
        if (!manager.isAdminActive(name)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, name);
            startActivity(intent);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            case R.id.bt_lockScreem:
                manager.resetPassword("111", 0);
                manager.lockNow();
                break;
            case R.id.bt_alarm:
                MediaPlayer player = MediaPlayer.create(this, R.raw.sleepaway);
                player.setVolume(1.0f,1.0f);
                player.start();
                break;
        }
    }
}
