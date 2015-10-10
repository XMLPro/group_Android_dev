package com.example.suzuki.memoprot001;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;

public class ImageViewList extends ListActivity {
//    static final String[] data = {"time", "image", DrawNoteDBHelper.DB_NAME};
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.imageviewlist);

        // インスタンス作成
        DrawNoteDBHelper help = new DrawNoteDBHelper(this);
        // 読み書き出来るように開く
        db = help.getWritableDatabase();
        // レコードを検索してカーソルを作成
        Cursor cursor = db.query(DrawNoteDBHelper.SAVE_PHOTO_TABLE,
                new String[]{"_id", DrawNoteDBHelper.COLUMN_FILE_NAME,
                        DrawNoteDBHelper.COLUMN_PHOTO_BINARY_DATA}, null, null, null, null, null);
        // カーソルから値を取り出す
        while (cursor.moveToNext()) {
            // BLOBをbyte[]で受け取る.
            byte blob[] = cursor
                    .getBlob(cursor
                            .getColumnIndex
                                    (DrawNoteDBHelper.COLUMN_PHOTO_BINARY_DATA));
            // byte[]をビットマップに変換しImageViewとして表示
            Bitmap hyouji = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        }



        // カーソルクローズ
        cursor.close();
        // DBクローズ
        db.close();
    }

//端末の戻るボタンで戻る
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }
}