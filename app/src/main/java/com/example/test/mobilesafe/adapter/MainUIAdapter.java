package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.util.zip.Inflater;

/**
 * Created by test on 8/28/2015.
 */
public class MainUIAdapter extends BaseAdapter{
    private static String[] names = {"手机防盗", "通讯卫士",
            "软件管理", "任务管理", "上网管理", "手机杀毒", "系统优化", "高级工具", "设置中心"};
    private static int[] picID = {R.drawable.widget01, R.drawable.widget02, R.drawable.widget03,
            R.drawable.widget04, R.drawable.widget05, R.drawable.widget06,
            R.drawable.widget07, R.drawable.widget08, R.drawable.widget01};
    private Context context;
    private LayoutInflater inflater;

    public MainUIAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.main_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_function);
        TextView textView = (TextView) view.findViewById(R.id.tv_functionText);
        imageView.setImageResource(picID[position]);
        textView.setText(names[position]);
        return view;
    }
}
