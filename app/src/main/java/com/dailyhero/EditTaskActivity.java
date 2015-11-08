package com.dailyhero;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dailyhero.AddTaskActivity;

import java.util.Calendar;

/**
 * Created by song on 2015/11/7.
 */
public class EditTaskActivity extends Activity {
    EditText addTaskName, deadline;
    TextView taskName;
    FloatingActionButton confirm;
    final String TAG = "EditTaskActivity";
    static String task,tast_name,tast_time; //task type,name,time
    TaskDB tdb;
    SQLiteDatabase db;


    public EditTaskActivity() {    }

    public EditTaskActivity(String t,String t_name, String t_time) {
        task = t;
        Log.i(TAG, "初始化：" + task);
        tast_name = t_name;
        tast_time = t_time;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (task.equals("緊急任務")) {

            setContentView(R.layout.add_todo_task);
            addTaskName = (EditText) findViewById(R.id.todoTaskName);
            //deadline = (EditText) findViewById(R.id.deadline);
            confirm = (FloatingActionButton) findViewById(R.id.addTodo);
            //設定傳過來的預設值
            addTaskName.setText(tast_name); //設定任務名稱為傳過來的名稱
//            deadline.setText(tast_time);
            taskName = (TextView) findViewById(R.id.addTodoName);
            taskName.setText("修改"+task);
            // todo: version 2 function:
//            deadline.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        //呼叫日期選擇器並設定
//                        Calendar calendar = Calendar.getInstance();
//                        //建立DatePickerDialog取得日期
//                        new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
//                            @Override
//                            public void onDateSet(DatePicker view, int year,
//                                                  int monthOfYear, int dayOfMonth) {
//                                deadline.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
//                            }
//                        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
//                        Log.i(TAG, "action_down");
//                        Log.i(TAG, "action_down");
//                        return true;
//                    }
//                    return false;
//                }
//            });
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //editTast(addTaskName.getText().toString(),deadline.getText().toString());
                    editTast(addTaskName.getText().toString());
                }
            });
        }
        else {

            setContentView(R.layout.add_task);
            // todo: version 2 function:
//            final CheckBox week[] = new CheckBox[7];
//            week[0] = (CheckBox) findViewById(R.id.sum);
//            week[1] = (CheckBox) findViewById(R.id.mon);
//            week[2] = (CheckBox) findViewById(R.id.tue);
//            week[3] = (CheckBox) findViewById(R.id.wed);
//            week[4] = (CheckBox) findViewById(R.id.thu);
//            week[5] = (CheckBox) findViewById(R.id.fri);
//            week[6] = (CheckBox) findViewById(R.id.sat);

//            String day[] = tast_time.split("/");
//            for(int i=0;i<day.length;i++) {
//                switch (day[i]){
//                    case "日":
//                        week[0].setChecked(true);
//                        break;
//                    case "一":
//                        week[1].setChecked(true);
//                        break;
//                    case "＝":
//                        week[2].setChecked(true);
//                        break;
//                    case "三":
//                        week[3].setChecked(true);
//                        break;
//                    case "四":
//                        week[4].setChecked(true);
//                        break;
//                    case "五":
//                        week[5].setChecked(true);
//                        break;
//                    case "六":
//                        week[6].setChecked(true);
//                        break;
//
//                }
//            }

            addTaskName = (EditText) findViewById(R.id.addTaskName);
            addTaskName.setText(tast_name);
            Log.i(TAG, "主r支"+tast_name);
            confirm = (FloatingActionButton) findViewById(R.id.addTask);
            taskName = (TextView) findViewById(R.id.taskName);
            taskName.setText("修改"+task);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //todo: version 2 function:
//                    String times="";
//                    for(int i=0;i<week.length;i++) {
//                        if(week[i].isChecked())
//                            times+=week[i].getText().toString()+"/";
//                    }
//                    editTast(addTaskName.getText().toString(),times);
                    editTast(addTaskName.getText().toString());
                }
            });
        }
    }

    private void editTast(String field1) {
        String field2 = "coming soon";
        tdb = new TaskDB(this);
        Log.i(TAG, " editTast");
        db = tdb.getWritableDatabase();
        // 如果欄位不為空
        if (!(field1.equals("")) && !(field2.equals(""))) {
            // 檢查重複
            if(!isDuplicate()) {
                ContentValues values = new ContentValues();
                values.put("TASK_NAME", field1);
                values.put("TIME", field2);
                db.update("TASK", values, "TASK_NAME='" + tast_name + "'", null);
                db.close();
                Log.i(TAG, "修改成功");
                Intent i = new Intent();
                i.setClass(EditTaskActivity.this, MainActivity.class);
                startActivity(i);
            }
            else {
                Toast.makeText(this, "任務名稱重複了", Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(this, "有欄位尚未填寫", Toast.LENGTH_SHORT).show();
    }

    //檢查任務名稱是否有重複
    private boolean isDuplicate() {

        Cursor cursor = db.query("TASK", new String[] { "_ID",
                "TASK_TYPE", "TASK_NAME" }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                //如果和原來的任務名稱一樣就不認定重複
                if(cursor.getString(1).equals(task) && addTaskName.getText().toString().equals(tast_name)) {
                    Log.i(TAG,"和原來的一樣");
                    return false;
                }
                if(cursor.getString(1).equals(task) && cursor.getString(2).equals(addTaskName.getText().toString())) {
                    Log.i(TAG,"重複了");
                    Log.i(TAG,cursor.getString(2));
                    Log.i(TAG,addTaskName.getText().toString());
                    return true;
                }

            } while (cursor.moveToNext());

        }
        return false;
    }
}
