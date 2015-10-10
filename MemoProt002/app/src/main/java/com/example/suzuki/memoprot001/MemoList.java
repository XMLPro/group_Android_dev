package com.example.suzuki.memoprot001;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MemoList extends ListActivity {

    protected int color;
    public PaintDrawable paintDrawable;

    static final String[] cols = {"title", "memo", "id",};
    private MemoDBHelper memos;
    private BaseAdapter adapter;
    private ListView listView;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.memolist);
        getBackgroundColor();
//        showMemos(getMemoList());
        showMemos();

        //デバック処理_start
        memos = new MemoDBHelper(this);
        SQLiteDatabase db = memos.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM memoDB", null);
        cursor.moveToFirst();
        String memoTitles;
        for (int i = 0; i < cursor.getCount(); i++) {
            //取得したレコードの0番目のカラムを取得 -> 0番目のカラムはメモのタイトル
            memoTitles = "title: "
                    + cursor.getString(cursor.getColumnIndex("title"))
                    + ", id: "
                    + cursor.getString(cursor.getColumnIndex("id"))
                    + ", memo: "
                    + cursor.getString(cursor.getColumnIndex("memo"))
                    + ", date: "
                    + cursor.getString(cursor.getColumnIndex("date"));

            //次のレコードにカーソルを移す
            cursor.moveToNext();
            Log.d("memoTitle", memoTitles);
            //デバック処理_end
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        TextView t = (TextView) v.findViewById(R.id.list_item_id);
        Log.d("onListItemClick(): ", ": v.findViewById(R.id.list_item_id): " + t.getText());
        memos = new MemoDBHelper(this);
        SQLiteDatabase db = memos.getWritableDatabase();
        Cursor cursor = db.query("memoDB", cols, "id='" + t.getText() + "'", null, null, null, null);
        startManagingCursor(cursor);
        int idx = cursor.getColumnIndex("memo");
        Log.d("onListItemClick()", "idx:" + idx);
        cursor.moveToFirst();
//        Log.d("onListItemClick()", "getString(idx):" + cursor);
        Intent i = new Intent();
        if (cursor.getCount() > 0)
            i.putExtra("text", cursor.getString(idx));
        else {
            Log.e("onListItemClick():", "cursor.getCount()=" + cursor.getCount());
            i.putExtra("text", "error");
        }
        setResult(RESULT_OK, i);
        memos.close();
        finish();
    }

    private void showMemos() {
        listView = (ListView) findViewById(android.R.id.list);
        adapter = new MemoListAdapter(this);
        listView.setAdapter(adapter);
    }
//    private void showMemos(String[] list) {
//        listView = (ListView) findViewById(android.R.id.list);
//        adapter = new MemoListAdapter(this);
//        listView.setAdapter(adapter);
//    }

    private String[] getMemoList() {
        memos = new MemoDBHelper(this);
        SQLiteDatabase db = memos.getReadableDatabase();
        //memoDBテーブルから全てのタイトルのデータを持ったカーソルを取得
        Cursor cursor = db.rawQuery("SELECT title, id FROM memoDB", null);
        cursor.moveToFirst();
        Log.d("getCount: ", "cursor: " + cursor.getCount());
        String[] memoTitles = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            //取得したレコードの0番目のカラムを取得 -> 0番目のカラムはメモのタイトル
            Log.d("getString: ", "cursor: " + cursor.getString(0));
            memoTitles[i] = cursor.getString(0) + "\n" + cursor.getString(1);

            //次のレコードにカーソルを移す
            cursor.moveToNext();
        }
        db.close();
        return memoTitles;
    }

    private void getBackgroundColor() {
        Intent intent = getIntent();
        color = intent.getIntExtra("Color", 0);
        switch (color) {
            case 1:
                paintDrawable = new PaintDrawable(Color.DKGRAY);
                break;
            case 2://��
                paintDrawable = new PaintDrawable(Color.rgb(255, 51, 51));
                break;
            case 3://��
                paintDrawable = new PaintDrawable(Color.rgb(51, 204, 255));
                break;
            case 4://��
                paintDrawable = new PaintDrawable(Color.rgb(0, 255, 102));
                break;
            case 5://�s���N
                paintDrawable = new PaintDrawable(Color.rgb(255, 153, 204));
                break;
            case 6://��
                paintDrawable = new PaintDrawable(Color.rgb(255, 153, 0));
                break;
            case 7://��
                paintDrawable = new PaintDrawable(Color.rgb(255, 102, 204));
                break;
            default:
                paintDrawable = new PaintDrawable(Color.WHITE);
        }
        getWindow().setBackgroundDrawable(paintDrawable);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("color", color);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
            }
        }
        return true;
    }
}
