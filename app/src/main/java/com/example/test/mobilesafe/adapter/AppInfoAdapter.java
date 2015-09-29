package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.domain.AppInfo;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by test on 9/28/2015.
 */
public class AppInfoAdapter extends BaseAdapter{
    private Context context;
    private List<AppInfo> appInfos;

    public AppInfoAdapter(Context context, List<AppInfo> appInfos) {
        this.context = context;
        this.appInfos = appInfos;
    }

    @Override
    public int getCount() {
        return appInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.appinfo_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_ama_icon);
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_ama_appName);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageDrawable(appInfos.get(position).getIcon());
        viewHolder.textView.setText(appInfos.get(position).getAppName());

        return view;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView textView;
    }
}
