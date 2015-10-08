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
import com.example.test.mobilesafe.domain.ProcessInfo;

import java.util.List;

/**
 * Created by test on 9/28/2015.
 */
public class ProcessInfoAdapter extends BaseAdapter{
    private Context context;
    private List<ProcessInfo> list;

    public ProcessInfoAdapter(Context context, List<ProcessInfo> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            view = inflater.inflate(R.layout.item_taskmanager, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_it_icon);
            viewHolder.name = (TextView) view.findViewById(R.id.tv_it_name);
            viewHolder.memory = (TextView) view.findViewById(R.id.tv_it_memory);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageDrawable(list.get(position).getIcon());
        viewHolder.name.setText(list.get(position).getName());
        viewHolder.memory.setText(list.get(position).getMemory()+"");

        return view;
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView name,memory;
    }
}
