package com.example.test.mobilesafe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.test.mobilesafe.R;

public class SetUpwizard1 extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SetUpwizard1";
    private Button next;
    private Button previous;
    private CheckBox cb_bind;
    private Button bt_bind;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_upwizard1);
        next = (Button) findViewById(R.id.bt_next);
        previous = (Button) findViewById(R.id.bt_previous);

        sp = getSharedPreferences("config", MODE_PRIVATE);
        bt_bind = (Button) findViewById(R.id.bt_bind);
        cb_bind = (CheckBox) findViewById(R.id.cb_bind);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        bt_bind.setOnClickListener(this);

        cb_bind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cb_bind.setText("已经绑定");
                    setSimInfo();
                    Log.i(TAG, "aaaaaa");
                } else {
                    cb_bind.setText("暂未绑定");
                    clearSimInfo();
                }
            }
        });
        String sim = sp.getString("simNumber", "");
        if (sim != null && sim != "") {
            cb_bind.setText("已经绑定");
            cb_bind.setChecked(true);
        } else {
            cb_bind.setText("暂未绑定");
            cb_bind.setChecked(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_upwizard1, menu);
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
            case R.id.bt_next:
                Intent intent = new Intent(this, SetUpwizard2.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                finish();
                break;
            case R.id.bt_previous:
                Intent intent1 = new Intent(this, SetUpwizard.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                finish();
                break;
            case R.id.bt_bind:
                setSimInfo();
                cb_bind.setChecked(true);
                break;
            default:
                break;
        }
    }

    private void clearSimInfo() {
        String simNumber = "";
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("simNumber", simNumber);
        editor.commit();
        Log.i(TAG, "number is" + simNumber);
    }

    private void setSimInfo() {
        TelephonyManager telephonyManager =
                (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String simNumber = telephonyManager.getSimSerialNumber();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("simNumber", simNumber);
        editor.commit();
        Log.i(TAG, simNumber);
    }
}
