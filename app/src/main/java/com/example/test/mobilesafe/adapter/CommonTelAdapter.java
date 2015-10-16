package com.example.test.mobilesafe.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.test.mobilesafe.R;

import java.io.File;

/**
 * Created by test on 10/15/2015.
 */
public class CommonTelAdapter extends BaseExpandableListAdapter{
    private Context context;
    private SQLiteDatabase db;
    private File file;

    public CommonTelAdapter(Context context, File file) {
        this.context = context;
        this.db = SQLiteDatabase.openDatabase(file.getAbsolutePath(), null, SQLiteDatabase.OPEN_READONLY);
        this.file = file;
    }

    @Override
    public int getGroupCount() {
        int groupCount = 0;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select count(*) from classlist", null);
            if (cursor.moveToNext()) {
                groupCount = cursor.getInt(0);
            }
            cursor.close();
        }
        return groupCount;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        int childrenCount = 0;
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select count(*) from table"+(groupPosition+1), null);
            if (cursor.moveToNext()) {
                childrenCount = cursor.getInt(0);
            }
            cursor.close();
        }
        return childrenCount;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
/*        TextView textView = new TextView(context);
        textView.setText("              " + "我在马路边" + groupPosition);
        return textView;*/
        View view;
        GroupViewHolder viewHolder;
        if (convertView == null) {
            view = View.inflate(context, R.layout.common_num_group, null);
            viewHolder = new GroupViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.tv_cng_group);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (GroupViewHolder) view.getTag();
        }
        String name = "";
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select name from classlist where idx=?", new String[]{groupPosition + 1 + ""});
            if (cursor.moveToNext()) {
                name = cursor.getString(0);
            }
            cursor.close();
        }
        viewHolder.textView.setText("       " + name);
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        TextView textView = new TextView(context);
        textView.setText("              " + "捡到" + childPosition+"分钱");
        return textView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private static class GroupViewHolder {
        private TextView textView;
    }
}
