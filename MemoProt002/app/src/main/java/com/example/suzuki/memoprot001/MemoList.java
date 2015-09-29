package com.example.suzuki.memoprot001;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MemoList extends ListActivity {

    //背景用の変数宣言
    protected int color;
    public PaintDrawable paintDrawable;

    static final String[] cols = {"title", "memo", android.provider.BaseColumns._ID,};
    MemoDBHelper memos;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l, v, position, id);

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

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.memolist);

        //背景色を受け取って設定する
        Intent intent = getIntent();
        color = intent.getIntExtra("Color",0);
        switch(color) {
            case 1:
                paintDrawable = new PaintDrawable(Color.DKGRAY);
                break;
            case 2://赤
                paintDrawable = new PaintDrawable(Color.rgb(255,51,51));
                break;
            case 3://青
                paintDrawable = new PaintDrawable(Color.rgb(51,204,255));
                break;
            case 4://緑
                paintDrawable = new PaintDrawable(Color.rgb(0,255,102));
                break;
            case 5://ピンク
                paintDrawable = new PaintDrawable(Color.rgb(255,153,204));
                break;
            case 6://橙
                paintDrawable = new PaintDrawable(Color.rgb(255,153,0));
                break;
            case 7://紫
                paintDrawable = new PaintDrawable(Color.rgb(255,102,204));
                break;
            default:
                paintDrawable = new PaintDrawable(Color.WHITE);
        }
        getWindow().setBackgroundDrawable(paintDrawable);

        showMemos(getMemos());
    }

    private Cursor getMemos(){
        memos = new MemoDBHelper(this);
        SQLiteDatabase db = memos.getReadableDatabase();
        Cursor cursor = db.query("memoDB", cols, null, null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showMemos(Cursor cursor){
        if(cursor != null){
            String[] from = {"title"};
            int[] to = {android.R.id.text1};
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor, from, to);
            setListAdapter(adapter);
        }
        memos.close();
    }

    //端末側の戻るボタンが押されたときの処理
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ダイアログ表示など特定の処理を行いたい場合はここに記述
                    // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返すと戻るボタンが無効になる
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
