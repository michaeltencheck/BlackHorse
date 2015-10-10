package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.mobilesafe.R;
import com.example.test.mobilesafe.domain.AppInfo;
import com.example.test.mobilesafe.domain.ProcessInfo;
import com.example.test.mobilesafe.util.DecimalFormater;

import java.util.List;

/**
 * Created by test on 9/28/2015.
 */
public class ProcessInfoAdapter extends BaseAdapter{
    private Context context;
    private List<ProcessInfo> customer, system;

    public ProcessInfoAdapter(Context context, List<ProcessInfo> customer, List<ProcessInfo> system) {
        this.context = context;
        this.customer = customer;
        this.system = system;
    }

    @Override
    public int getCount() {
        return customer.size() + system.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        if (position == 0) {
            String customerApp = "用户程序";
            return customerApp;
        }else if (position <= customer.size()) {
            return customer.get(position - 1);
        }else if (position == customer.size() + 1) {
            String systemApp = "系统程序";
            return systemApp;
        }else if (position > customer.size() + 1) {
            return system.get(position - customer.size() - 2);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        if (position == 0) {
            return -1;
        }else if (position <= customer.size()) {
            return position - 1;
        }else if (position == customer.size() + 1) {
            return -1;
        }else if (position > customer.size() + 1) {
            return position - 2;
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == 0) {
            TextView textView = new TextView(context);
            textView.setTextSize(30);
            textView.setText("用户程序");
            return textView;
        } else if (position <= customer.size()) {
            View view;
            ViewHolder viewHolder;
            if (convertView == null || convertView.getVerticalScrollbarPosition() == 0 ||
                    convertView.getVerticalScrollbarPosition() == customer.size() + 1) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.item_taskmanager, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_it_icon);
                viewHolder.name = (TextView) view.findViewById(R.id.tv_it_name);
                viewHolder.memory = (TextView) view.findViewById(R.id.tv_it_memory);
                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.cb_it_checkbox);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }

            if (customer.get(position - 1).getPackageName().equals(context.getPackageName())) {
                viewHolder.imageView.setImageDrawable(customer.get(position - 1).getIcon());
                viewHolder.name.setText(customer.get(position - 1).getName());
                viewHolder.memory.setText
                        ("使用内存: " + DecimalFormater.getKBNumber(customer.get(position - 1).getMemory()) + "");
                viewHolder.checkBox.setEnabled(false);
                return view;
            }
            viewHolder.imageView.setImageDrawable(customer.get(position - 1).getIcon());
            viewHolder.name.setText(customer.get(position - 1).getName());
            viewHolder.memory.setText
                    ("使用内存: " + DecimalFormater.getKBNumber(customer.get(position - 1).getMemory()) + "");
            viewHolder.checkBox.setChecked(customer.get(position - 1).isChecked());
            return view;
        } else if (position == customer.size() + 1) {
            TextView textView = new TextView(context);
            textView.setText("系统程序");
            textView.setTextSize(30);
            return textView;
        } else {
            View view;
            ViewHolder viewHolder;
            if (convertView == null || convertView.getVerticalScrollbarPosition() == 0 ||
                    convertView.getVerticalScrollbarPosition() == customer.size() + 1) {
                LayoutInflater inflater = LayoutInflater.from(context);
                view = inflater.inflate(R.layout.item_taskmanager, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.iv_it_icon);
                viewHolder.name = (TextView) view.findViewById(R.id.tv_it_name);
                viewHolder.memory = (TextView) view.findViewById(R.id.tv_it_memory);
                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.cb_it_checkbox);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.imageView.setImageDrawable(system.get(position - customer.size() - 2).getIcon());
            viewHolder.name.setText(system.get(position - customer.size() - 2).getName());
            viewHolder.memory.setText
                    ("使用内存: " + DecimalFormater.getKBNumber(system.get(position - customer.size() - 2).getMemory()) + "");
            viewHolder.checkBox.setChecked(system.get(position - customer.size() - 2).isChecked());
            return view;
        }
/*        if (position == 0) {
            TextView textView = new TextView(context);
            textView.setTextSize(30);
            textView.setText("用户程序");
            return textView;
        }*/
        /*View view;
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
        viewHolder.memory.setText
                ("使用内存: "+ DecimalFormater.getKBNumber(list.get(position).getMemory())+"");
//        viewHolder.memory.setText(list.get(position).getPid()+"");

        return view;*/
    }

    private static class ViewHolder {
        private ImageView imageView;
        private TextView name,memory;
        private CheckBox checkBox;
    }
}
