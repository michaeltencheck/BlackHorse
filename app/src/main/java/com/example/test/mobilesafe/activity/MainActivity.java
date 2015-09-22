package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.MainUIAdapter;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "MainActivity";
    private GridView gv_main;
    private MainUIAdapter adapter;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gv_main = (GridView) findViewById(R.id.gv_main);
        adapter = new MainUIAdapter(this);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        gv_main.setAdapter(adapter);
        gv_main.setOnItemClickListener(this);
        gv_main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {
                if (position == 0) {
                    final EditText et = new EditText(MainActivity.this);
                    et.setHint("点击输入新名称");
                    AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                    ab.setTitle("更改名称");
                    ab.setMessage("请输入新名称");
                    ab.setView(et);
                    ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String text = et.getText().toString().trim();
                            if ("".equals(text)) {
                                Toast.makeText(MainActivity.this,
                                        "名称不能为空，请重新输入", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                sp.edit().putString("changed name", text).commit();
                                TextView tv = (TextView) view.findViewById(R.id.tv_functionText);
                                tv.setText(text);
                            }
                        }
                    });
                    ab.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    ab.create().show();
                }
                return false;
            }
        });
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i(TAG, "点击位置是" + position);
        switch (position) {
            case 0:
                Log.i(TAG, "进入手机防盗");
                Intent intent = new Intent(this, LostProtectActivity.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(this, ContactGuardActvity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_in_translate, R.anim.anim_out_translate);
                break;
            case 7:
                Intent intent7 = new Intent(this, HighCastActivity.class);
                finish();
                startActivity(intent7);
                overridePendingTransition(R.anim.anim_in_translate, R.anim.anim_out_translate);
                break;
        }
    }
}
