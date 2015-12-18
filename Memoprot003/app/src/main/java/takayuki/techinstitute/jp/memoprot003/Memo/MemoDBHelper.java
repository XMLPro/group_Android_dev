package takayuki.techinstitute.jp.memoprot003.Memo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Owner on 2015/12/12.
 */
public class MemoDBHelper extends SQLiteOpenHelper {
    static final String name = "memos.db";
    static final int version = 1;
    private SQLiteDatabase DB;

    public MemoDBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE memoDB (id INTEGER PRIMARY KEY AUTOINCREMENT, title Text, memo TEXT);";
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
