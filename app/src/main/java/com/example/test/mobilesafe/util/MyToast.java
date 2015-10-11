package com.example.test.mobilesafe.util;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mobilesafe.R;

import javax.xml.datatype.Duration;

/**
 * Created by test on 10/11/2015.
 */
public class MyToast {

    public static void showToast(Context context,String s,int iconId) {
        Toast toast = new Toast(context);
        View v = View.inflate(context, R.layout.item_toast, null);
        ImageView iv = (ImageView) v.findViewById(R.id.iv_itx_icon);
        TextView tv = (TextView) v.findViewById(R.id.tv_itx_text);
        iv.setImageResource(iconId);
        tv.setText(s);
        toast.setView(v);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
