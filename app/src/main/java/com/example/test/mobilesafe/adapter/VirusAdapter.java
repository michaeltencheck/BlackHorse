package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.util.List;

/**
 * Created by test on 10/22/2015.
 */
public class VirusAdapter extends BaseAdapter{
    private Context context;
    private List<PackageInfo> packageInfos;

    public VirusAdapter(Context context, List<PackageInfo> packageInfos) {
        this.context = context;
        this.packageInfos = packageInfos;
    }

    @Override
    public int getCount() {
        return packageInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.item_virus_killer, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_ivk_icon);
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_ivk_name);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        Drawable drawable = packageInfos.get(position).applicationInfo.loadIcon(context.getPackageManager());
        String name = (String) packageInfos.get(position).applicationInfo.loadLabel(context.getPackageManager());
        viewHolder.imageView.setImageDrawable(drawable);
        viewHolder.textView.setText(name);
        return view;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
