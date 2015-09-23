package com.example.test.mobilesafe.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.db.BlackNumberDAO;

import java.util.List;

public class ContactGuardActvity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "ContactGuardActvity";
    private ListView listView;
    private BlackNumberDAO dao;
    private Button button;
    private List<String> numbers;
    private ArrayAdapter<String> adapter;

    /*@Override
    protected void onResume() {
        super.onResume();
        numbers = dao.findAll();
        String num = numbers.get(numbers.size()-1);
        Log.i(TAG, num);
        listView.setAdapter(new ArrayAdapter(this, R.layout.blacklist_item,
                R.id.tv_cg_blacklist_item, numbers));
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_guard_actvity);

        listView = (ListView) findViewById(R.id.lv_cg_blacklist);
        button = (Button) findViewById(R.id.bt_cg_add);
        button.setOnClickListener(this);

        dao = new BlackNumberDAO(this);
        long number = 13888881000l;

        for (int i = 0; i < 66; i++) {
            dao.add(number + i + "");
        }

        numbers = dao.findAll();

        adapter = new ArrayAdapter<>(this, R.layout.blacklist_item,
                R.id.tv_cg_blacklist_item, numbers);

        /*listView.setAdapter(new ArrayAdapter(this, R.layout.blacklist_item,
                R.id.tv_cg_blacklist_item, numbers));*/
        listView.setAdapter(adapter);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_cg_add:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("请输入号码");
                final EditText editText = new EditText(this);
                builder.setView(editText);
                builder.setCancelable(false);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*String newNumber = editText.getText().toString().trim();
                        if (TextUtils.isEmpty(newNumber)) {
                            Toast.makeText(getApplicationContext(), "号码不能为空", Toast.LENGTH_LONG).show();
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.shake2);
                            editText.startAnimation(animation);
                        } else {
                            dao.add(newNumber);
                        }*/
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newNumber = editText.getText().toString().trim();
                        if (!TextUtils.isEmpty(newNumber)) {
                            dao.add(newNumber);
                            numbers.clear();
                            numbers.addAll(dao.findAll());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                                    R.anim.shake2);
                            editText.startAnimation(animation);
                            Toast.makeText(getApplicationContext(),"号码不能为空",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
