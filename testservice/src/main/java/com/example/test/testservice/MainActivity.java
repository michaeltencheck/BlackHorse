package com.example.test.testservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.test.testservice.activity.PreMoblieSafeActivity;
import com.example.test.testservice.activity.PreMoblieSafeActivityFragment;
import com.example.test.testservice.interf.Change;
import com.example.test.testservice.service.MyService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private Button button, button1,button2, button3, button4, button5;
    private Intent intent;
//    private Change myChange;
    private MyConnection myConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this, MyService.class);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);

        button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(this);

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);

        button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);

        button4 = (Button) findViewById(R.id.button4);
        button4.setOnClickListener(this);

        button5 = (Button) findViewById(R.id.button5);
        button5.setOnClickListener(this);

        myConnection = new MyConnection();



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
            case R.id.button:
//                bindService(intent, new MyConnection(), BIND_AUTO_CREATE);
                startService(intent);
                Log.i(TAG, "onClick button");
                break;
            case R.id.button1:
//                myChange.changeBoolean();
                stopService(intent);
                Log.i(TAG, "onClick button1");
                break;
            case R.id.button2:
//                myChange.changeString();
//                myChange.changeString("button2");
                bindService(intent, myConnection, BIND_AUTO_CREATE);
                Log.i(TAG, "onClick button2");
                break;
            case R.id.button3:
//                stopService(intent);
//                myChange.changeString("button3");
                Log.i(TAG, "onClick unbindService");
                break;
            case R.id.button4:
//                myChange.changeString("button4");
                unbindService(myConnection);
                Log.i(TAG, "onClick button4");
                break;
            case R.id.button5:
                Intent intent = new Intent(this, PreMoblieSafeActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class MyConnection implements ServiceConnection {
        Change myChange;
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            myChange = (MyService.MyChange) service;
            myChange = (Change) service;
            myChange.changeString("onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myChange.changeString("onServiceDisconnected");
        }
    }
}
