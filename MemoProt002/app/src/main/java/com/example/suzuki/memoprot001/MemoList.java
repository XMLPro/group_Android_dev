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
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MemoList extends ListActivity {

    protected int color;
    public PaintDrawable paintDrawable;

    static final String[] cols = {"title", "memo", android.provider.BaseColumns._ID};
    MemoDBHelper memos;


    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.memolist);
        getBackgroundColor();
        showMemos(getMemoList());
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Log.d("onListItemClick(): ", "onListItemClick() returned: " + id);
        memos = new MemoDBHelper(this);
        SQLiteDatabase db = memos.getWritableDatabase();
        Cursor cursor = db.query("memoDB", cols, "_ID=" + String.valueOf(id), null, null, null, null);
        startManagingCursor(cursor);
        int idx = cursor.getColumnIndex("memo");
        cursor.moveToFirst();
        Intent i = new Intent();
        i.putExtra("text", cursor.getString(idx));
        setResult(RESULT_OK, i);
        memos.close();
        finish();
    }
<<<<<<< HEAD
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.memolist);
=======

    private void showMemos(String[] list) {
        ListView listView = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<String> adapter = new MemoListAdapter(this, R.layout.listitem, R.id.list_item, list);
        listView.setAdapter(adapter);
    }
>>>>>>> bfd3e61ab73c4b80a86dfb9ac88a539e5474cc76

    private String[] getMemoList() {
        memos = new MemoDBHelper(this);
        SQLiteDatabase db = memos.getReadableDatabase();
        //memoDBテーブルから全てのタイトルのデータを持ったカーソルを取得
        Cursor cursor = db.rawQuery("SELECT `title` FROM memoDB", null);
        cursor.moveToFirst();
        String[] memoTitles = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++) {
            //取得したレコードの0番目のカラムを取得 -> 0番目のカラムはメモのタイトル
//            Log.d("db.rawQuery: ", "cursor: " + cursor.getString(0));
            memoTitles[i] = cursor.getString(0);

            //次のレコードにカーソルを移す
            cursor.moveToNext();
        }

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
