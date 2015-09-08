package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.test.mobilesafe.R;

public class SetUpwizard3 extends AppCompatActivity implements View.OnClickListener{
    private CheckBox cb_protection;
    private Button next;
    private Button previous;
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_upwizard3);

        cb_protection = (CheckBox) findViewById(R.id.cb_protection);
        next = (Button) findViewById(R.id.bt_next);
        previous = (Button) findViewById(R.id.bt_previous);
        checked = false;

        cb_protection.setChecked(true);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        cb_protection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checked = true;
                } else {
                    checked = false;
                    Toast.makeText(SetUpwizard3.this, "保护没有开启，可能会遭受攻击", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_upwizard3, menu);
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
            case R.id.bt_previous:
                Intent intent = new Intent(this, SetUpwizard2.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.bt_next:
                if (checked) {
                    Intent intent1 = new Intent(this, LostProtectActivity.class);
                    finish();
                    startActivity(intent1);
                    overridePendingTransition(R.anim.anim_in_translate, R.anim.anim_out_translate);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("友情提示");
                    builder.setMessage("目前暂未开启手机保护功能，是否开启该功能");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cb_protection.setChecked(true);
                            finish();
                            Intent intent2 = new Intent(SetUpwizard3.this, LostProtectActivity.class);
                            finish();
                            startActivity(intent2);
                            overridePendingTransition(R.anim.anim_in_translate, R.anim.anim_out_translate);
                        }
                    });
                    builder.create().show();
                }
                break;
        }
    }
}
