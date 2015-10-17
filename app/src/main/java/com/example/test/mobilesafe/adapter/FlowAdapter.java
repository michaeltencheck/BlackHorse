package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

/**
 * Created by test on 10/17/2015.
 */
public class FlowAdapter extends BaseAdapter{
    private Context context;

    public FlowAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 30;
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
            view = View.inflate(context, R.layout.item_flow_statistic, null);
            viewHolder = new ViewHolder();
            viewHolder.appIcon = (ImageView) view.findViewById(R.id.iv_ifs_appIcon);
            viewHolder.appName = (TextView) view.findViewById(R.id.tv_ifs_appName);
            viewHolder.moblieFlow = (TextView) view.findViewById(R.id.tv_ifs_mobile_flow);
            viewHolder.wifiFlow = (TextView) view.findViewById(R.id.tv_ifs_wifi_flow);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        return view;
    }

    private static class ViewHolder {
        private ImageView appIcon;
        private TextView appName;
        private TextView moblieFlow;
        private TextView wifiFlow;
    }
}
