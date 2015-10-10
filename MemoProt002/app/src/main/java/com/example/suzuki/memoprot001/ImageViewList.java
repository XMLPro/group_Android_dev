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

        // �C���X�^���X�쐬
        DrawNoteDBHelper help = new DrawNoteDBHelper(this);
        // �ǂݏ����o����悤�ɊJ��
        db = help.getWritableDatabase();
        // ���R�[�h���������ăJ�[�\�����쐬
        Cursor cursor = db.query(DrawNoteDBHelper.SAVE_PHOTO_TABLE,
                new String[]{"_id", DrawNoteDBHelper.COLUMN_FILE_NAME,
                        DrawNoteDBHelper.COLUMN_PHOTO_BINARY_DATA}, null, null, null, null, null);
        // �J�[�\������l�����o��
        while (cursor.moveToNext()) {
            // BLOB��byte[]�Ŏ󂯎��.
            byte blob[] = cursor
                    .getBlob(cursor
                            .getColumnIndex
                                    (DrawNoteDBHelper.COLUMN_PHOTO_BINARY_DATA));
            // byte[]���r�b�g�}�b�v�ɕϊ���ImageView�Ƃ��ĕ\��
            Bitmap hyouji = BitmapFactory.decodeByteArray(blob, 0, blob.length);
        }



        // �J�[�\���N���[�Y
        cursor.close();
        // DB�N���[�Y
        db.close();
    }

//�[���̖߂�{�^���Ŗ߂�
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