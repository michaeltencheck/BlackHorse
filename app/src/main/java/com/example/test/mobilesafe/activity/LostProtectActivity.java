package com.example.test.mobilesafe.activity;

import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.receiver.MyDeviceAdminReceiver;
import com.example.test.mobilesafe.util.MD5Encode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LostProtectActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LostProtectActivity";
    private SharedPreferences sp;
    private EditText pwd;
    private EditText pwdConfirm;
    private EditText pwdInput;
    private TextView safeNumber;
    private CheckBox cb_protected;
    private Button reWizard;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_protect);
        sp = getSharedPreferences("config", MODE_PRIVATE);
        DevicePolicyManager manager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        ComponentName name = new ComponentName(this, MyDeviceAdminReceiver.class);
        if (!manager.isAdminActive(name)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, name);
            startActivity(intent);
        }


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
        dialog = new Dialog(this);
        View view = View.inflate(this, R.layout.pwd_setup, null);
        dialog.setContentView(view);
        dialog.setTitle("请输入密码");
        pwdInput = (EditText) view.findViewById(R.id.et_pwdInput);
        Button bt_ok = (Button) view.findViewById(R.id.bt_pwdOk);
        Button bt_cancel = (Button) view.findViewById(R.id.bt_pwdCancel);
        bt_ok.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        dialog.show();
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

        switch (v.getId()) {
            case R.id.bt_ok:
                String pwdText = pwd.getText().toString().trim();
                String pwdConfirmText = pwdConfirm.getText().toString().trim();
                if ("".equals(pwdText) || "".equals(pwdConfirmText)) {
                    Log.i(TAG, "密码不能为空");
                    Toast.makeText(this, "密码不能为空，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }else if (pwdText.equals(pwdConfirmText)) {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("password", MD5Encode.MD5Encoding(pwdConfirmText));
                    editor.commit();
                    if (isPwdSetUp()) {

                    } else {

                    }
                } else {
                    Log.i(TAG, "密码不同，请重新输入");
                    Toast.makeText(this, "密码不同，请重新输入", Toast.LENGTH_LONG).show();
                    pwd.setText("");
                    pwdConfirm.setText("");
                    return;
                }
                break;
            case R.id.bt_cancel:
                dialog.dismiss();
                break;
            case R.id.bt_pwdOk:
                String pwd = pwdInput.getText().toString().trim();
                String pwdMD5 = MD5Encode.MD5Encoding(pwd);
                String pwdOrigin = sp.getString("password", "");
                boolean finishWizard = sp.getBoolean("finishWizard", false);
                if (pwdMD5.equals(pwdOrigin) && finishWizard) {
                    Log.i(TAG, "进入防盗界面");
                    dialog.dismiss();
                    setContentView(R.layout.activity_lost_protect);

                    safeNumber = (TextView) findViewById(R.id.tv_safe_number);
                    cb_protected = (CheckBox) findViewById(R.id.cb_whether_protectd);
                    reWizard = (Button) findViewById(R.id.bt_rewizard);

                    String number = sp.getString("safeNumber", "");
                    safeNumber.setText(number);
                    cb_protected.setChecked(true);
                    reWizard.setOnClickListener(this);


                }else if (pwdMD5.equals(pwdOrigin) && !finishWizard) {
                    Log.i(TAG, "进入设置向导界面");
                    Intent intent = new Intent(LostProtectActivity.this, SetUpwizard.class);
                    this.startActivity(intent);
                    Toast.makeText(this, "进入防盗界面", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                } else {
                    pwdInput.setText("");
                    Log.i(TAG, "密码不正确，请重新输入");
                    Toast.makeText(this, "密码不正确，请重新输入", Toast.LENGTH_LONG).show();
                    return;
                }

                break;
            case R.id.bt_pwdCancel:
                dialog.dismiss();
                break;
            case R.id.bt_rewizard:
                Intent intent = new Intent(this, SetUpwizard.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_translate, R.anim.anim_out_translate);
            default:
                break;
        }
    }

    private boolean isSetUp() {

        return false;
    }
}
