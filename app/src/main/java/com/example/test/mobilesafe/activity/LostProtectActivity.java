package com.example.test.mobilesafe.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.test.mobilesafe.R;

public class LostProtectActivity extends AppCompatActivity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_protect);
        sp = getSharedPreferences("config", MODE_PRIVATE);


        if (isPwdSetUp()) {
            showPwdSetUp();
        } else {
            showPwdNotSetUp();
        }

    }

    private void showPwdNotSetUp() {
        /*Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setContentView(R.layout.pwd_not_setup);
        dialog.show();*/
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("设置密码");
        dialog.setContentView(R.layout.pwd_not_setup);
        dialog.show();

    }

    private void showPwdSetUp() {
    }

    private boolean isPwdSetUp() {
        String password = sp.getString("password", null);
        if (password == null) {
            return false;
        }else if (password == "") {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lost_protect, menu);
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
