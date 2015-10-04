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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class MemoList extends ListActivity {

    //�w�i�p�̕ϐ��錾
    protected int color;
    public PaintDrawable paintDrawable;

    static final String[] cols = {"title", "memo", android.provider.BaseColumns._ID,};
    MemoDBHelper memos;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
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

        //�w�i�F���󂯎���Đݒ肷��
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

        showMemos(getMemos());
    }

    private Cursor getMemos() {
        memos = new MemoDBHelper(this);
        SQLiteDatabase db = memos.getReadableDatabase();
        Cursor cursor = db.query("memoDB", cols, null, null, null, null, null);
        startManagingCursor(cursor);
        return cursor;
    }

    private void showMemos(Cursor cursor) {
        if (cursor != null) {
            String[] members = {"title", "asdf"};

            ListView folder = (ListView) findViewById(R.id.list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_2, members);
            folder.setAdapter(adapter);
        }
        memos.close();
    }

    //�[�����̖߂�{�^���������ꂽ�Ƃ��̏���
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // �_�C�A���O�\���ȂǓ���̏������s�������ꍇ�͂����ɋL�q
                    // �e�N���X��dispatchKeyEvent()���Ăяo������true��Ԃ��Ɩ߂�{�^���������ɂȂ�
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
