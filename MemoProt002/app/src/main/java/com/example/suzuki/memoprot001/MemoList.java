package com.example.suzuki.memoprot001;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MemoList extends ListActivity {

    //îwåiópÇÃïœêîêÈåæ
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

        //îwåiêFÇéÛÇØéÊÇ¡Çƒê›íËÇ∑ÇÈ
        Intent intent = getIntent();
        color = intent.getIntExtra("Color",0);
        switch(color) {
            case 1:
                paintDrawable = new PaintDrawable(Color.DKGRAY);
                break;
            case 2:
                paintDrawable = new PaintDrawable(Color.RED);
                break;
            case 3:
                paintDrawable = new PaintDrawable(Color.BLUE);
                break;
            case 4:
                paintDrawable = new PaintDrawable(Color.GREEN);
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


}
