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
}
