package com.example.test.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.domain.Contact;
import com.example.test.mobilesafe.engine.ContactInfoService;

import java.util.List;

public class ContactInfoActivity extends AppCompatActivity {
    private ListView lv_contact;
    private List<Contact> contacts;
    private ContactInfoService contactInfoService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        contactInfoService = new ContactInfoService(this);

        lv_contact = (ListView) findViewById(R.id.lv_contact);
        contacts = contactInfoService.getContacts();

        lv_contact.setAdapter(new ContactsAdapter());

    }

    private class ContactsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return contacts.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Contact contact = contacts.get(position);
            LinearLayout linearLayout = new LinearLayout(ContactInfoActivity.this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            TextView tv_name = new TextView(ContactInfoActivity.this);
            TextView tv_number = new TextView(ContactInfoActivity.this);
            tv_name.setText("姓名 : " + contact.getName());
            tv_number.setText("号码 : " + contact.getNumber());
            linearLayout.addView(tv_name);
            linearLayout.addView(tv_number);
            return linearLayout;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
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
