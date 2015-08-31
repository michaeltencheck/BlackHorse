package com.example.test.mobilesafe.activity;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.mobilesafe.R;

public class LostProtectActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LostProtectActivity";
    private SharedPreferences sp;
    private EditText pwd;
    private EditText pwdConfirm;
    private Dialog dialog;

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
        dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("设置密码");
        View view = View.inflate(this, R.layout.pwd_not_setup, null);
        pwd = (EditText) view.findViewById(R.id.et_pwd);
        pwdConfirm = (EditText) view.findViewById(R.id.et_pwdConfirm);
        Button ok = (Button) view.findViewById(R.id.bt_ok);
        Button cancel = (Button) view.findViewById(R.id.bt_cancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(view);
//        dialog.setContentView(R.layout.pwd_not_setup);
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

    @Override
    public void onClick(View v) {
        String pwdText = pwd.getText().toString().trim();
        String pwdConfirmText = pwdConfirm.getText().toString().trim();
        switch (v.getId()) {
            case R.id.bt_ok:
                if ("".equals(pwdText) || "".equals(pwdConfirmText)) {
                    Log.i(TAG, "密码不能为空");
                    Toast.makeText(this, "密码不能为空，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }else if (pwdText.equals(pwdConfirmText)) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", pwdConfirmText);
                    editor.commit();
                } else {
                    Log.i(TAG, "密码不同，请重新输入");
                    Toast.makeText(this, "密码不同，请重新输入", Toast.LENGTH_LONG).show();
                    pwd.setText("");
                    pwdConfirm.setText("");
                    return;
                }
            case R.id.bt_cancel:
                dialog.dismiss();
        }
    }
}
