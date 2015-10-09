package com.example.suzuki.memoprot001;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * ?�?�?�C?�?�?�N?�?�?�X?�̒�`
 */
public class DrawNoteK extends ActionBarActivity {
    DrawNoteView view;
    /** �f�[�^�x�[�X */
    private SQLiteDatabase db;;

    //?�A?�N?�V?�?�?�?�?�o?�[?�?�ύX?�?�?�邽?�߂̐�?�?�
    int change = 0;

    /**
     * ?�A?�v?�?�?�̏�?�?�
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        //?�?�?�G?�`?�?�?�^?�C?�g?�?�?�\?�?�
        setTitle(getString(R.string.draw));

        // ?�`?�?�N?�?�?�X?�?�ݒ�
        view = new DrawNoteView(getApplication());
        setContentView(view);
    }

    /**
     * ?�?�?�j?�?�?�[?�̐�?�?�?�C?�x?�?�?�g
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.draw, menu);

        //?�?�?�?�?�S?�?�?�{?�^?�?�?�?�?�?�?�?�?�ꂽ?�?�?�Ƀ{?�^?�?�?�̃}?�[?�N?�?�?�?�?�M?�ɕς�?�?�
        //?�ϐ�change?�?�0?�Ȃ�?�?�?�?�S?�?�?�A1?�Ȃ牔?�M?�Ƀ}?�[?�N?�?�ύX
        if (change == 1) {
            MenuItem one = menu.findItem(R.id.action_eraser);
            one.setIcon(android.R.drawable.ic_menu_edit);
        }
        super.onCreateOptionsMenu(menu);
        return true;
    }

    String fname;
    String dir = getFilesDir().getAbsolutePath();
    File fout = new File(dir);

    public void savetofile(Bitmap bmp) {
/*
        // �C���X�^���X�쐬
        DrawNoteDBHelper helper = new DrawNoteDBHelper(this);
        // �ǂݏ����o����悤�ɊJ��
        db = helper.getWritableDatabase();
        // ���R�[�h�̈ꊇDELETE
        db.delete(DrawNoteDBHelper.SAVE_PHOTO_TABLE, null, null);

        // ���R�[�h1�ݒ� ByteArrayOutputStream��byte[]�ɕϊ����i�[
        ContentValues values = new ContentValues();
        values.put(DrawNoteDBHelper.COLUMN_FILE_NAME, "card_01.png");
        values.put(DrawNoteDBHelper.COLUMN_PHOTO_BINARY_DATA,
                photo1.toByteArray());

        EditText et = (EditText) this.findViewById(R.id.editText);
        String title;
        String memo = et.getText().toString();

        if (memo.trim().length() > 0) {
            if (memo.indexOf("\n") == -1) {
                title = memo.substring(0, Math.min(memo.length(), 20));
            } else {
                title = memo.substring(0, Math.min(memo.indexOf("\n"), 20));
            }

            String ts = DateFormat.getDateTimeInstance().format(new Date());
            MemoDBHelper memos = new MemoDBHelper(this);
            SQLiteDatabase db = memos.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", title + "\n" + ts);
            values.put("memo", memo);
            db.insertOrThrow("memoDB", null, values);
            memos.close();
        }
        Toast.makeText(this, getString(R.string.Save), Toast.LENGTH_SHORT).show();
        */


        Log.v("dir;", dir);
//        if (!fout.exists()) {
        boolean f =
                fout.mkdirs();
        if (f) {
            System.out.println("eee:ok");
        } else {
            System.out.println("eee:no");
        }
//        }
        Date d = new Date();
        fname = fout.getAbsolutePath() + "/";
        fname += "picture";
//                String.format("%4d%02d%02d-%02d%02d%02d.png",
//                (1900 + d.getYear()), 1 + d.getMonth(), d.getDate(),
//                d.getHours(), d.getMi....nutes(), d.getSeconds());
        Log.v("dir;fname", fname);
        // ?�摜�?�?�t?�@?�C?�?�?�ɏ�?�?�?�?�?�?�
        try {
            FileOutputStream out = new FileOutputStream(fname);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.e("eee", "ok");

            FileInputStream i = new FileInputStream(fout.getAbsolutePath() + "/");
            Bitmap bm = BitmapFactory.decodeStream(i);
            ((ImageView)findViewById(R.id.view2)).setImageBitmap(bm);

        } catch (Exception e) {
            Log.e("eee", String.valueOf(e));
        }


    }

    //?�[?�?�?�?�?�̖߂�{?�^?�?�?�?�?�?�?�?�?�ꂽ?�Ƃ�?�̏�?�?�
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ?�_?�C?�A?�?�?�O?�\?�?�?�ȂǓ�?�?�̏�?�?�?�?�?�s?�?�?�?�?�?�?��?��͂�?�?�?�ɋL?�q
                    // ?�e?�N?�?�?�X?�?�dispatchKeyEvent()?�?�?�Ăяo?�?�?�?�?�?�true?�?�Ԃ�?�Ɩ߂�{?�^?�?�?�?�?�?�?�?�ɂȂ�
                    finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * ?�`?�?�N?�?�?�X?�̒�`
     */
    class DrawNoteView extends android.view.View {
        Bitmap bmp = null;
        Canvas bmpCanvas;
        Point oldpos = new Point(-1, -1);

        public DrawNoteView(Context c) {
            super(c);
            setFocusable(true);
        }

        public void clearDrawList() {
            bmpCanvas.drawColor(Color.WHITE);
            invalidate();
        }

        public Bitmap saveToFile() {
            return bmp;
        }

        /**
         * ?�?�ʃT?�C?�Y?�?�?�ύX?�?�?�ꂽ?�?�
         */
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bmpCanvas = new Canvas(bmp);
            bmpCanvas.drawColor(Color.WHITE);
        }

        /**
         * ?�`?�?�C?�x?�?�?�g
         */
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(bmp, 0, 0, null);
        }

        /**
         * ?�^?�b?�`?�C?�x?�?�?�g
         */
        public boolean onTouchEvent(MotionEvent event) {
            // ?�`?�?�ʒu?�̊m?�F
            Point cur = new Point((int) event.getX(), (int) event.getY());
            if (oldpos.x < 0) {
                oldpos = cur;
            }

            Paint paint = new Paint();
            if (change == 0) {
                // ?�`?�摮?�?�?�?�ݒ�
                paint.setColor(Color.BLUE);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(8);
            } else if (change == 1) {
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(100);
            }
            // ?�?�?�`?�?�
            bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
            oldpos = cur;
            // ?�w?�?�?�?�?�?�?�グ?�?�?�?�?�?�W?�?�?�?�?�Z?�b?�g
            if (event.getAction() == MotionEvent.ACTION_UP) {
                oldpos = new Point(-1, -1);
            }
            invalidate();
            return true;
        }

    }
}