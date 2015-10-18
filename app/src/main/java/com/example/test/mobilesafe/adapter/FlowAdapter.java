package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.TrafficStats;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.util.DecimalFormater;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by test on 10/17/2015.
 */
public class FlowAdapter extends BaseAdapter{
    private Context context;
    private List<Drawable> icons;
    private List<String> names;
    private List<String> dFlows;
    private List<String> uFlows;

    public FlowAdapter(Context context) {
        this.context = context;

        List<Drawable> icons1 = new ArrayList<>();
        List<String> names1 = new ArrayList<>();
        List<String> downloadFlows = new ArrayList<>();
        List<String> uploadFlows = new ArrayList<>();

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.GET_UNINSTALLED_PACKAGES);

        List<ApplicationInfo> infos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);



/*
        for (ResolveInfo info : list) {
            String name = (String) info.loadLabel(pm);
            names1.add(name);

            Drawable icon = info.loadIcon(pm);
            icons1.add(icon);


        }
*/
        for (ApplicationInfo info : infos) {
            String name = (String) info.loadLabel(pm);
            names1.add(name);

            Drawable drawable = info.loadIcon(pm);
            icons1.add(drawable);

            int uid = info.uid;
            long downloadFlow = TrafficStats.getUidRxBytes(uid) + TrafficStats.getUidRxPackets(uid);
            long uploadFlow = TrafficStats.getUidTxBytes(uid) + TrafficStats.getUidTxPackets(uid);

            if (downloadFlow < 0 & uploadFlow < 0) {
/*                downloadFlows.add("nothing");
                uploadFlows.add("nothing");*/
            } else {
                downloadFlows.add(DecimalFormater.getNumber(downloadFlow));
                uploadFlows.add(DecimalFormater.getNumber(uploadFlow));
            }
        }

        this.icons = icons1;
        this.names = names1;

        this.dFlows = downloadFlows;
        this.uFlows = uploadFlows;
    }

    @Override
    public int getCount() {
        return dFlows.size();
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
            viewHolder.downloadFlow = (TextView) view.findViewById(R.id.tv_ifs_mobile_flow);
            viewHolder.uploadFlow = (TextView) view.findViewById(R.id.tv_ifs_wifi_flow);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        /*if (!dFlows.get(position).equals("nothing")) {
            viewHolder.appIcon.setImageDrawable(icons.get(position));
            viewHolder.appName.setText(names.get(position));
            viewHolder.downloadFlow.setText(dFlows.get(position));
            viewHolder.uploadFlow.setText(uFlows.get(position));
        } else {
            icons.remove(position);
            names.remove(position);
            dFlows.remove(position);
            uFlows.remove(position);
        }*/
        viewHolder.appIcon.setImageDrawable(icons.get(position));
        viewHolder.appName.setText(names.get(position));
        viewHolder.downloadFlow.setText(dFlows.get(position));
        viewHolder.uploadFlow.setText(uFlows.get(position));
        return view;
    }

    private static class ViewHolder {
        private ImageView appIcon;
        private TextView appName;
        private TextView downloadFlow;
        private TextView uploadFlow;
    }
}
