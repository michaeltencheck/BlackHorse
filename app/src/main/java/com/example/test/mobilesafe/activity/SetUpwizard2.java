package com.example.test.mobilesafe.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.engine.ContactInfoService;

public class SetUpwizard2 extends AppCompatActivity implements View.OnClickListener{
    private Button next;
    private Button previous;
    private Button selectContact;
    private EditText contact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_upwizard2);

        next = (Button) findViewById(R.id.bt_next);
        previous = (Button) findViewById(R.id.bt_previous);

        selectContact = (Button) findViewById(R.id.bt_select_contact);
        contact = (EditText) findViewById(R.id.et_tel_number);

        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        selectContact.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_set_upwizard2, menu);
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
                Intent intent = new Intent(this, SetUpwizard3.class);
                finish();
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.bt_previous:
                Intent intent1 = new Intent(this, SetUpwizard1.class);
                finish();
                startActivity(intent1);
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
                break;
            case R.id.bt_select_contact:
                ContactInfoService contactInfoService = new ContactInfoService(this);
                contactInfoService.getContacts();
                break;
            default:
                break;
        }
    }
}
