package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 10/17/2015.
 */
public class FlowAdapter extends BaseAdapter{
    private Context context;
    private List<Drawable> icons;
    private List<String> names;
/*    private List<String> mobileFlows;
    private List<String> wifiFlows;*/

    public FlowAdapter(Context context) {
        this.context = context;

        List<Drawable> icons1 = new ArrayList<>();
        List<String> names1 = new ArrayList<>();
        List<String> mobileFlows = new ArrayList<>();
        List<String> wifiFlows = new ArrayList<>();

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.GET_UNINSTALLED_PACKAGES);

        for (ResolveInfo info : list) {
            String name = (String) info.loadLabel(pm);
            names1.add(name);

            Drawable icon = info.loadIcon(pm);
            icons1.add(icon);
        }

        this.icons = icons1;
        this.names = names1;
    }

    @Override
    public int getCount() {
        return icons.size();
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
        viewHolder.appIcon.setImageDrawable(icons.get(position));
        viewHolder.appName.setText(names.get(position));
        return view;
    }

    private static class ViewHolder {
        private ImageView appIcon;
        private TextView appName;
        private TextView moblieFlow;
        private TextView wifiFlow;
    }
}
