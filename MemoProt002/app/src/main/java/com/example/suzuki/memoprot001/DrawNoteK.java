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
 * ?ｽ?ｽ?ｽC?ｽ?ｽ?ｽN?ｽ?ｽ?ｽX?ｽﾌ抵ｿｽ`
 */
public class DrawNoteK extends ActionBarActivity {
    DrawNoteView view;
    /** データベース */
    private SQLiteDatabase db;;

    //?ｽA?ｽN?ｽV?ｽ?ｽ?ｽ?ｽ?ｽo?ｽ[?ｽ?ｽﾏ更?ｽ?ｽ?ｽ驍ｽ?ｽﾟの撰ｿｽ?ｽ?ｽ
    int change = 0;

    /**
     * ?ｽA?ｽv?ｽ?ｽ?ｽﾌ擾ｿｽ?ｽ?ｽ
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        //?ｽ?ｽ?ｽG?ｽ`?ｽ?ｽ?ｽ^?ｽC?ｽg?ｽ?ｽ?ｽ\?ｽ?ｽ
        setTitle(getString(R.string.draw));

        // ?ｽ`?ｽ?ｽN?ｽ?ｽ?ｽX?ｽ?ｽﾝ抵ｿｽ
        view = new DrawNoteView(getApplication());
        setContentView(view);
    }

    /**
     * ?ｽ?ｽ?ｽj?ｽ?ｽ?ｽ[?ｽﾌ撰ｿｽ?ｽ?ｽ?ｽC?ｽx?ｽ?ｽ?ｽg
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.draw, menu);

        //?ｽ?ｽ?ｽ?ｽ?ｽS?ｽ?ｽ?ｽ{?ｽ^?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ黷ｽ?ｽ?ｽ?ｽﾉボ?ｽ^?ｽ?ｽ?ｽﾌマ?ｽ[?ｽN?ｽ?ｽ?ｽ?ｽ?ｽM?ｽﾉ変ゑｿｽ?ｽ?ｽ
        //?ｽﾏ撰ｿｽchange?ｽ?ｽ0?ｽﾈゑｿｽ?ｽ?ｽ?ｽ?ｽS?ｽ?ｽ?ｽA1?ｽﾈら鉛?ｽM?ｽﾉマ?ｽ[?ｽN?ｽ?ｽﾏ更
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
        // インスタンス作成
        DrawNoteDBHelper helper = new DrawNoteDBHelper(this);
        // 読み書き出来るように開く
        db = helper.getWritableDatabase();
        // レコードの一括DELETE
        db.delete(DrawNoteDBHelper.SAVE_PHOTO_TABLE, null, null);

        // レコード1設定 ByteArrayOutputStreamをbyte[]に変換し格納
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
        // ?ｽ鞫懶ｿｽ?ｽ?ｽt?ｽ@?ｽC?ｽ?ｽ?ｽﾉ擾ｿｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ
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

    //?ｽ[?ｽ?ｽ?ｽ?ｽ?ｽﾌ戻ゑｿｽ{?ｽ^?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ黷ｽ?ｽﾆゑｿｽ?ｽﾌ擾ｿｽ?ｽ?ｽ
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ?ｽ_?ｽC?ｽA?ｽ?ｽ?ｽO?ｽ\?ｽ?ｽ?ｽﾈど難ｿｽ?ｽ?ｽﾌ擾ｿｽ?ｽ?ｽ?ｽ?ｽ?ｽs?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ鼾?ｿｽﾍゑｿｽ?ｽ?ｽ?ｽﾉ記?ｽq
                    // ?ｽe?ｽN?ｽ?ｽ?ｽX?ｽ?ｽdispatchKeyEvent()?ｽ?ｽ?ｽﾄび出?ｽ?ｽ?ｽ?ｽ?ｽ?ｽtrue?ｽ?ｽﾔゑｿｽ?ｽﾆ戻ゑｿｽ{?ｽ^?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽﾉなゑｿｽ
                    finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * ?ｽ`?ｽ?ｽN?ｽ?ｽ?ｽX?ｽﾌ抵ｿｽ`
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
         * ?ｽ?ｽﾊサ?ｽC?ｽY?ｽ?ｽ?ｽﾏ更?ｽ?ｽ?ｽ黷ｽ?ｽ?ｽ
         */
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bmpCanvas = new Canvas(bmp);
            bmpCanvas.drawColor(Color.WHITE);
        }

        /**
         * ?ｽ`?ｽ?ｽC?ｽx?ｽ?ｽ?ｽg
         */
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(bmp, 0, 0, null);
        }

        /**
         * ?ｽ^?ｽb?ｽ`?ｽC?ｽx?ｽ?ｽ?ｽg
         */
        public boolean onTouchEvent(MotionEvent event) {
            // ?ｽ`?ｽ?ｽﾊ置?ｽﾌ確?ｽF
            Point cur = new Point((int) event.getX(), (int) event.getY());
            if (oldpos.x < 0) {
                oldpos = cur;
            }

            Paint paint = new Paint();
            if (change == 0) {
                // ?ｽ`?ｽ鞫ｮ?ｽ?ｽ?ｽ?ｽﾝ抵ｿｽ
                paint.setColor(Color.BLUE);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(8);
            } else if (change == 1) {
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(100);
            }
            // ?ｽ?ｽ?ｽ`?ｽ?ｽ
            bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
            oldpos = cur;
            // ?ｽw?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽ繧ｰ?ｽ?ｽ?ｽ?ｽ?ｽ?ｽW?ｽ?ｽ?ｽ?ｽ?ｽZ?ｽb?ｽg
            if (event.getAction() == MotionEvent.ACTION_UP) {
                oldpos = new Point(-1, -1);
            }
            invalidate();
            return true;
        }

    }
}