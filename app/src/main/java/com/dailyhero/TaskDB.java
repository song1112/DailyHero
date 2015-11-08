package com.dailyhero;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by song on 2015/11/5.
 */
public class TaskDB extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME="Task";
    private Context taskItem;
    // 建立資料表
    private static final String CREATE_DDL =
            "CREATE TABLE TASK ("
                    + "_ID INTEGER PRIMARY KEY,"
                    + "TASK_TYPE TEXT,"
                    + "TASK_NAME TEXT,"
                    + "TIME TEXT";
    // 刪除資料表
    private static final String DELETE_DDL =
            "DROP TABLE IF EXISTS TASK;";

    public TaskDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public TaskDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        taskItem = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DDL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_DDL);
        db.execSQL(CREATE_DDL);
    }
}
