package com.dailyhero;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    TaskExpandableListAdapter listAdapter;
    ExpandableListView expListView;


    List<String> taskType; // 任務型態
    HashMap<String, List<String>> taskItem; // 任務項目
    AddTaskActivity addTaskActivity;
    Intent intent;
    FloatingActionButton addTaskBtn, addBrBtn, addTodoBtn;
    TextView hero_point,hero_sign;
    SharedPreferences data; // 儲存point用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addTaskBtn = (FloatingActionButton)findViewById(R.id.add_main_task);
        addBrBtn = (FloatingActionButton)findViewById(R.id.add_br_task);
        addTodoBtn = (FloatingActionButton)findViewById(R.id.add_todo_task);
        hero_point = (TextView)findViewById(R.id.hero_point);
        hero_sign = (TextView)findViewById(R.id.hero_sign);
        data = getSharedPreferences("DATA", 0);
        int point = data.getInt("POINT",0);

        // 依據傳入的point來獲取稱號
        DesignationList designation = new DesignationList(point);
        hero_sign.setText(designation.getDesigntion());
        hero_point.setText("Point:" + point);

        // 新增任務
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent();
                addTaskActivity = new AddTaskActivity("主線任務");
                intent.setClass(MainActivity.this,addTaskActivity.getClass());
                startActivity(intent);
            }
        });

        addBrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                addTaskActivity = new AddTaskActivity("支線任務");
                intent.setClass(MainActivity.this,addTaskActivity.getClass());
                startActivity(intent);
            }
        });

        addTodoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                addTaskActivity = new AddTaskActivity("緊急任務");
                intent.setClass(MainActivity.this,addTaskActivity.getClass());
                startActivity(intent);
            }
        });


        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData(); //取得任務資料
        listAdapter = new TaskExpandableListAdapter(this, taskType, taskItem);
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        taskType = new ArrayList<String>();
        taskItem = new HashMap<String, List<String>>();

        // addGroupHeader
        taskType.add("主線任務");
        taskType.add("支線任務");
        taskType.add("緊急任務");


        List<String> mainTask = new ArrayList<String>();
        List<String> brTask = new ArrayList<String>();
        List<String> todoTask = new ArrayList<String>();

        // 取得儲存的任務
        TaskDB tdb = new TaskDB(this);
        SQLiteDatabase db = tdb.getWritableDatabase();
        Cursor cursor = db.query("TASK", new String[] { "_ID",
                "TASK_TYPE", "TASK_NAME" }, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // 把任務按照任務型態分類
                if(cursor.getString(1).equals("主線任務"))
                    mainTask.add(cursor.getString(2));
                if(cursor.getString(1).equals("支線任務"))
                    brTask.add(cursor.getString(2));
                if(cursor.getString(1).equals("緊急任務"))
                    todoTask.add(cursor.getString(2));

            } while (cursor.moveToNext());

        }

        taskItem.put(taskType.get(0), mainTask); // Group, Child
        taskItem.put(taskType.get(1), brTask);
        taskItem.put(taskType.get(2), todoTask);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // 移到説明畫面
            Intent intent = new Intent(MainActivity.this,ExplainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
