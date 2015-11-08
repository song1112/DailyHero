package com.dailyhero;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by song on 2015/11/4.
 */
public class TaskExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _taskHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    TaskDB tdb;
    SQLiteDatabase db;
    EditTaskActivity editTaskActivity;

    public TaskExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._taskHeader = listDataHeader;
        this._listDataChild = listChildData;
        tdb = new TaskDB(_context);
        db = tdb.getWritableDatabase();
    }

    @Override
    public int getGroupCount() {
        return this._taskHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._taskHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._taskHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._taskHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        // 取得標頭(任務型態)
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        // 設定title
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    //在這裡做子資料操作的部分
    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String taskType = _taskHeader.get(groupPosition); //任務型態
        final String taskItem = (String) getChild(groupPosition, childPosition); //任務名稱

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.task_list_item, null);
        }

        final CheckBox task = (CheckBox) convertView.findViewById(R.id.lblListItem);
        task.setText(taskItem);
        TextView edit = (TextView) convertView.findViewById(R.id.edit);
        TextView del = (TextView)convertView.findViewById(R.id.del);



        // 當任務被打勾
        task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 如果被打勾了
                if(task.isChecked()) {
                    SharedPreferences data;
                    data = _context.getSharedPreferences("DATA",0);
                    // 判斷是哪種類型的任務
                    int point = data.getInt("POINT",0); //取得儲存的point
                    int p=0; // 要增加的oint
                    if(taskType.equals("主線任務")) {
                        p=30;
                        point+=p;
                        task.setClickable(false); //讓任務不可以再被點擊變更
                        db.delete("TASK", "TASK_NAME='" + taskItem + "'", null); //完成就移除該任務
                    }
                    if(taskType.equals("支線任務")) {
                        p=10;
                        point+=p;
                        task.setClickable(false);
                        db.delete("TASK", "TASK_NAME='" + taskItem + "'", null);
                    }
                    if(taskType.equals("緊急任務")) {
                        p=50;
                        point+=p;
                        task.setClickable(false);
                        db.delete("TASK", "TASK_NAME='" + taskItem + "'", null);
                    }

                    data.edit().putInt("POINT",point).commit(); //儲存point

                    // 彈出對話框
                    AlertDialog.Builder dialog = new AlertDialog.Builder(_context);
                    dialog.setTitle("恭喜完成任務！");
                    dialog.setMessage("獲得Point:" + p);
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            Intent intent = new Intent(_context, MainActivity.class);
                            // 讓使用者按下返回鍵不會回到上一頁
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            _context.startActivity(intent);
                        }
                    });
                    dialog.show();

                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Cursor cursor = db.query("TASK", new String[] { "_ID",
                        "TASK_TYPE", "TASK_NAME", "TIME" }, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        // 比較任務型態是否相等
                        if(taskType.equals(cursor.getString(1))) {
                            // 比較任務名稱是否相等
                            if(taskItem.equals(cursor.getString(2))) {
                                // 傳入任務型態、任務名稱、unfinished
                                editTaskActivity = new EditTaskActivity(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                                db.close();
                                Intent intent = new Intent(_context, editTaskActivity.getClass());
                                _context.startActivity(intent);
                            }
                        }
                    } while (cursor.moveToNext());

                }

            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刪除
                db.delete("TASK", "TASK_NAME='" + taskItem + "'", null);
                db.close();
                //刪除後在轉發到主頁面
                Intent intent = new Intent(_context, MainActivity.class);
                //讓使用者按下返回鍵不會回到上一頁
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                _context.startActivity(intent);


            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
