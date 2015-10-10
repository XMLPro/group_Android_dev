package com.example.suzuki.memoprot001;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrawNoteDBHelper extends SQLiteOpenHelper {

    /**
     * �摜�Ǘ��e�[�u���� */
    public static final String SAVE_PHOTO_TABLE = "save_photo";
    /** �J������ �摜�t�@�C���� */
    public static final String COLUMN_FILE_NAME = "fname";
    /** �J������ �摜�o�C�i���f�[�^ */
    public static final String COLUMN_PHOTO_BINARY_DATA = "bdata";
    /** �A�N�Z�X����f�[�^�x�[�X�� */
    public static final String DB_NAME = "fraw.db";
    /** DB�̃o�[�W���� */
    static final int version = 1;
    /** create table�� */
    private static final String createTableString = "create table "
            + SAVE_PHOTO_TABLE + "(_id integer primary key autoincrement, "
            + COLUMN_FILE_NAME + " text, " + COLUMN_PHOTO_BINARY_DATA
            + " blob)";

    public DrawNoteDBHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableString);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(createTableString);
    }
}
