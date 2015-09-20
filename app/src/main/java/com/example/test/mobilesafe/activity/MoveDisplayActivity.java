package com.example.test.mobilesafe.activity;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

public class MoveDisplayActivity extends AppCompatActivity implements View.OnTouchListener{
    private static final String TAG = "MoveDisplayActivity";
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
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_move_display);

        introduction1 = (TextView) findViewById(R.id.tv_md_explaination1);
        introduction2 = (TextView) findViewById(R.id.tv_md_explaination2);

        displayLocation = (ImageView) findViewById(R.id.iv_md_display);

        editor = getSharedPreferences("config", MODE_PRIVATE).edit();
        sp = getSharedPreferences("config", MODE_PRIVATE);

        int lastX = sp.getInt("lastX", 0);
        int lastY = sp.getInt("lastY", 0);
        Log.i(TAG, "x = " + lastX);
        Log.i(TAG, "y = " + lastY);
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) displayLocation.getLayoutParams();
        p.leftMargin = lastX;
        p.topMargin = lastY;
        displayLocation.setLayoutParams(p);


       /* displayLocation.layout(displayLocation.getLeft() + lastX, displayLocation.getTop() + lastY,
                displayLocation.getRight() + lastX, displayLocation.getBottom() + lastY);*/

        displayLocation.setOnTouchListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_move_display, menu);
        return true;
    }

    /*@Override
    *//*protected void onResume() {
        super.onResume();
        int lastX = sp.getInt("lastX", 0);
        int lastY = sp.getInt("lastY", 0);
        Log.i(TAG, "x = " + lastX);
        Log.i(TAG, "y = " + lastY);
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) displayLocation.getLayoutParams();
        p.leftMargin = lastX;
        p.topMargin = lastY;
        displayLocation.setLayoutParams(p);
    }*/

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

                        if (t < 300) {
                            introduction1.setVisibility(View.INVISIBLE);
                            introduction2.setVisibility(View.VISIBLE);
                        }else if (t > 900) {
                            introduction1.setVisibility(View.VISIBLE);
                            introduction2.setVisibility(View.INVISIBLE);
                        }

                        displayLocation.layout(l + dx, t + dy, r + dx, b + dy);
                        startX = endX;
                        startY = endY;

                        break;
                    case MotionEvent.ACTION_UP:
                        int reX = (int) event.getX();
                        int reY = (int) event.getY();
                        editor.putInt("lastX", startX-reX);
                        editor.putInt("lastY", startY-reY-28);
                        editor.commit();
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
