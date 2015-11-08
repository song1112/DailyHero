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

import com.dailyhero.R;

import java.util.Calendar;

/**
 * Created by song on 2015/11/5.
 */
public class AddTaskActivity extends Activity {
    EditText addTaskName, deadline;
    TextView taskName;
    FloatingActionButton confirm;

    final String TAG = "AddTaskActivity";
    static String task; //task name
    TaskDB tdb;
    SQLiteDatabase db;

    public AddTaskActivity() {
    }

    public AddTaskActivity(String t) {

        task = t;
        Log.i(TAG, "初始化：" + task);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tdb = new TaskDB(this);
        db = tdb.getWritableDatabase();


        if (task == "緊急任務") {
            setContentView(R.layout.add_todo_task);
            addTaskName = (EditText) findViewById(R.id.todoTaskName);
            confirm = (FloatingActionButton) findViewById(R.id.addTodo);

            // todo: version 2 function:
            //            deadline = (EditText) findViewById(R.id.deadline);
            //設定監聽器呼叫日期選擇器
//            deadline.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        getDatePickerDialog();
//                        Log.i(TAG, "action_down");
//                        return true;
//                    }
//                    return false;
//                }
//            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
               //     addTask(String.valueOf(addTaskName.getText()), addTaskName.getText().toString());
                    addTask(String.valueOf(addTaskName.getText()));
                }
            });

        } else {
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

            addTaskName = (EditText) findViewById(R.id.addTaskName);
            confirm = (FloatingActionButton) findViewById(R.id.addTask);
            taskName = (TextView) findViewById(R.id.taskName);
            taskName.setText("新增"+task);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String times="";

                    // todo: version 2 function:
//                    for(int i=0;i<week.length;i++) {
//                        if(week[i].isChecked())
//                            times+=week[i].getText().toString()+"/";
//                    }
                   // addTask(addTaskName.getText().toString(),times);
                    addTask(addTaskName.getText().toString());
                }
            });
        }


    }

    private void addTask(String field1) {
        String field2="coming soon";
        // 判斷不為空
        if (!(field1.equals("")) && !(field2.equals(""))) {
            // 檢查任務名稱是否重複
            if (!isDuplicate()) {
                ContentValues args = new ContentValues();
                args.put("TASK_TYPE", task);
                args.put("TASK_NAME", field1);
                args.put("TIME", field2);
                long rowid = db.insert("TASK", null, args);
                Log.i(TAG, "inserted, id=" + rowid);
                Toast.makeText(this, "新增成功", Toast.LENGTH_SHORT).show();
                db.close();

                Intent i = new Intent();
                i.setClass(AddTaskActivity.this, MainActivity.class);
                startActivity(i);
            }
            else {
                Toast.makeText(this, "任務名稱重複了", Toast.LENGTH_SHORT).show();
            }
        } else
            Toast.makeText(this, "有欄位尚未填寫", Toast.LENGTH_SHORT).show();
    }

    // todo: version 2 function:
//    //建立一個DatePickerDialog並取得日期
//    public void getDatePickerDialog() {
//        Calendar calendar = Calendar.getInstance();
//        //建立DatePickerDialog取得日期
//        new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year,
//                                  int monthOfYear, int dayOfMonth) {
//
//                deadline.setText(year + "/" + (monthOfYear + 1) + "/" + dayOfMonth);
//            }
//        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
//    }

    //檢查任務名稱是否有重複
    private boolean isDuplicate() {

        Cursor cursor = db.query("TASK", new String[] { "_ID", "TASK_TYPE", "TASK_NAME" }, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                // 如果任務型態相同下，任務名稱又相同，則代表重複，傳回true
                if(cursor.getString(1).equals(task) && cursor.getString(2).equals(addTaskName.getText().toString())) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }





}
