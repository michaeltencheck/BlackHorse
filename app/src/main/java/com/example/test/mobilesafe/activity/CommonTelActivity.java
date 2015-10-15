package com.example.test.mobilesafe.activity;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.CommonTelAdapter;
import com.example.test.mobilesafe.adapter.ContactInfoAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CommonTelActivity extends AppCompatActivity {
    private ExpandableListView showCommonTel;
    private CommonTelAdapter commonTelAdapter;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_tel);

        showCommonTel = (ExpandableListView) findViewById(R.id.elv_act_commonTel);
        commonTelAdapter = new CommonTelAdapter(this);
        showCommonTel.setAdapter(commonTelAdapter);

        AssetManager manager = getAssets();

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/commonnum.db";
        file = new File(path);

        String state = Environment.getExternalStorageState();
        boolean isAvailable = state.equals(Environment.MEDIA_MOUNTED);
        if (isAvailable) {
            if (!file.exists()) {
                try {
                    InputStream is = manager.open("commonnum.db");
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] bytes = new byte[1024];
                    int len ;
                    while ((len = is.read(bytes)) != -1) {
                        fos.write(bytes,0,len);
                    }
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_common_tel, menu);
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
