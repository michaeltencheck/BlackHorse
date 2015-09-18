package com.example.test.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

public class MoveDisplayActivity extends AppCompatActivity implements View.OnTouchListener{
    private TextView introduction1;
    private TextView introduction2;
    private ImageView displayLocation;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int dx;
    private int dy;
    private int l;
    private int r;
    private int t;
    private int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_display);

        introduction1 = (TextView) findViewById(R.id.tv_md_explaination1);
        introduction2 = (TextView) findViewById(R.id.tv_md_explaination2);
        displayLocation = (ImageView) findViewById(R.id.iv_md_display);

        displayLocation.setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_move_display, menu);
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
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.iv_md_display:
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = (int) event.getRawX();
                        startY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        endX = (int) event.getRawX();
                        endY = (int) event.getRawY();
                        dx = endX - startX;
                        dy = endY - startY;
                        l = displayLocation.getLeft();
                        r = displayLocation.getRight();
                        t = displayLocation.getTop();
                        b = displayLocation.getBottom();
                        if (t > 900) {
                            introduction1.setVisibility(View.VISIBLE);
                            introduction2.setVisibility(View.INVISIBLE);
                        } else if (t < 300) {
                            introduction1.setVisibility(View.INVISIBLE);
                            introduction2.setVisibility(View.VISIBLE);
                        }
                        displayLocation.layout(l + dx, t + dy, r + dx, b + dy);
                        startX = endX;
                        startY = endY;
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return true;
    }
}
