package com.example.test.mobilesafe.activity;

import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.adapter.FlowAdapter;
import com.example.test.mobilesafe.util.DecimalFormater;

public class FlowStatisticActivity extends AppCompatActivity {
    private LinearLayout layout;
    private ListView listView;
    private TextView mobile, wifi;
    private FlowAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0) {
                long mobileFlow = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileRxPackets()
                        + TrafficStats.getMobileTxBytes() + TrafficStats.getMobileTxPackets();
                long wifiFlow = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalRxPackets()
                        + TrafficStats.getTotalTxBytes() + TrafficStats.getTotalTxPackets()-mobileFlow;

                mobile.setText(DecimalFormater.getNumber(mobileFlow));
                wifi.setText(DecimalFormater.getNumber(wifiFlow));

                listView.setAdapter(adapter);
                layout.setVisibility(View.INVISIBLE);
            }else if (msg.what == 1) {
                long mobileFlow = TrafficStats.getMobileRxBytes() + TrafficStats.getMobileRxPackets()
                        + TrafficStats.getMobileTxBytes() + TrafficStats.getMobileTxPackets();
                long wifiFlow = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalRxPackets()
                        + TrafficStats.getTotalTxBytes() + TrafficStats.getTotalTxPackets()-mobileFlow;

                mobile.setText(DecimalFormater.getNumber(mobileFlow));
                wifi.setText(DecimalFormater.getNumber(wifiFlow));
                listView.setAdapter(adapter);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_statistic);

        layout = (LinearLayout) findViewById(R.id.ll_afs_progress);
        listView = (ListView) findViewById(R.id.lv_afs_flowDetail);

        mobile = (TextView) findViewById(R.id.tv_afs_mobileFlow);
        wifi = (TextView) findViewById(R.id.tv_afs_wifiFlow);

        getAdapter();
    }

    public void getAdapter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter = new FlowAdapter(getApplicationContext());
                handler.sendEmptyMessage(0);
            }
        }).start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        new Thread(new Runnable() {
            @Override
            public void run() {
                adapter = new FlowAdapter(getApplicationContext());
                handler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flow_statistic, menu);
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
