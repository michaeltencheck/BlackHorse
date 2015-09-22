package com.example.test.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.db.BlackNumberDAO;

import java.util.List;

public class ContactGuardActvity extends AppCompatActivity {
    private ListView listView;
    private BlackNumberDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_guard_actvity);

        listView = (ListView) findViewById(R.id.lv_cg_blacklist);
        dao = new BlackNumberDAO(this);
        long number = 13888881000l;

        for (int i = 0; i < 66; i++) {
            dao.add(number + i + "");
        }

        List<String> numbers = dao.findAll();

        listView.setAdapter(new ArrayAdapter(this, R.layout.blacklist_item,
                R.id.tv_cg_blacklist_item, numbers));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_guard_actvity, menu);
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
