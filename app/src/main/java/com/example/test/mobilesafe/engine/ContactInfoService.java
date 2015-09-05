package com.example.test.mobilesafe.engine;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.Window;

import com.example.test.mobilesafe.domain.Contact;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 9/5/2015.
 */
public class ContactInfoService {
    private static final String TAG = "ContactInfoService";
    private List<Contact> contacts = new ArrayList<Contact>();
    private Context context;

    public ContactInfoService(Context context) {
        this.context = context;
    }

    public List<Contact> getContacts() {
        ContentResolver resolver = context.getContentResolver();
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            Log.i(TAG, id);
            String name = cursor.getString(cursor.getColumnIndex("display_name"));
            Log.i(TAG, name);
        }
        return null;
    }
}
