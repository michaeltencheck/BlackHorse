package com.example.test.mobilesafe.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.engine.AddressService;

import java.io.File;

public class TelNumLocationActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_number;
    private TextView tv_numberLocation;
    private Button bt_queryNumLocation;
    private Button bt_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tel_num_location);

        et_number = (EditText) findViewById(R.id.et_number);
        tv_numberLocation = (TextView) findViewById(R.id.tv_numberLocation);
        bt_queryNumLocation = (Button) findViewById(R.id.bt_queryNumLocation);
        bt_clear = (Button) findViewById(R.id.bt_clear);

        bt_queryNumLocation.setOnClickListener(this);
        bt_clear.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tel_num_location, menu);
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
            case R.id.bt_queryNumLocation:
                query();
                break;
            case R.id.bt_clear:
                et_number.setText("");
                break;
            default:
                break;
        }
    }

    public void query() {
        String number = et_number.getText().toString().trim();
        if (TextUtils.isEmpty(number)) {
//            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
//            et_number.setAnimation(shake);
            Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake2);
            et_number.startAnimation(shake);
            Toast.makeText(this, "aaaaaa", Toast.LENGTH_LONG).show();
        } else {
            /*AddressService addressService = new AddressService();
            String address = addressService.getAddress(number);*/
            String address = AddressService.getAddress(number);
            tv_numberLocation.setText(address);
        }
    }
}
