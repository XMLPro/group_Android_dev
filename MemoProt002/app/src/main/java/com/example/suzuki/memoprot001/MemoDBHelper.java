package com.example.suzuki.memoprot001;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MemoDBHelper extends SQLiteOpenHelper {

    static final String name = "memos.db";
    static final int version = 1;
    static final SQLiteDatabase.CursorFactory factory = null;

    private SQLiteDatabase DB;

    public MemoDBHelper(Context context) {
        super(context, name, factory, version);
        //データベースを初期化したいときにこれをコメントアウト
//        context.deleteDatabase(name);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE memoDB (id INTEGER PRIMARY KEY AUTOINCREMENT, title Text, memo TEXT, date TEXT, path TEXT);";
//        String sql = "CREATE TABLE memoDB (" + android.provider.BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, title Text, memo TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void deleteRecode(String id) {
        this.DB = super.getWritableDatabase();

//      String sql = "DELETE FROM memoDB WHERE " + android.provider.BaseColumns._ID + "=" + (id);
        //上のsqlと同じ意味
//        DB.beginTransaction();
        try {
            int a = DB.delete("memoDB", "id=?", new String[]{id});
//            int a = DB.delete("memoDB", null,null);//これは成功する
//            DB.setTransactionSuccessful();
            if(a == -1)
                Log.d("deleteRecode()", "failed");
            else
                Log.d("deleteRecode()", "Successful");
        }finally {
//            DB.endTransaction();
            DB.close();
        }
    }
}
