package com.example.suzuki.memoprot001;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;


/**
 * メインクラスの定義
 */
public class DrawNoteK extends Activity {
    DrawNoteView view;
    private static final int MENU_CLEAR = 0;
    private static final int MENU_SAVE = 1;

    /**
     * アプリの初期化
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 描画クラスを設定
        view = new DrawNoteView(getApplication());
        setContentView(view);
    }

    /**
     * メニューの生成イベント
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_CLEAR, 0, "Clear");
        menu.add(0, MENU_SAVE, 0, "Save");
        return true;
    }

    /**
     * メニューがクリックされた時のイベント
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_CLEAR:
                view.clearDrawList();
                break;
            case MENU_SAVE:
                savetofile(view.saveToFile());
                break;
        }
        return true;
    }

    public void savetofile(Bitmap bmp) {
        String dir = getFilesDir().getAbsolutePath();
        File fout = new File(dir);
        Log.v("dir;", dir);
//        if (!fout.exists()) {
           boolean f = fout.mkdirs();
        if(f){
            System.out.println("eee:ok");
        }else {
            System.out.println("eee:no");
        }
//        }
        Date d = new Date();
        String fname = fout.getAbsolutePath() + "/";
        fname += "picture";
//                String.format("%4d%02d%02d-%02d%02d%02d.png",
//                (1900 + d.getYear()), 1 + d.getMonth(), d.getDate(),
//                d.getHours(), d.getMinutes(), d.getSeconds());
        Log.v("dir;fname", fname);
        // 画像をファイルに書き込む
        try {
            FileOutputStream out = new FileOutputStream(fname);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.e("eee", "ok");
        } catch (Exception e) {
            Log.e("eee", String.valueOf(e));
        }
    }
}

/**
 * 描画クラスの定義
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
     * 画面サイズが変更された時
     */
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bmpCanvas = new Canvas(bmp);
        bmpCanvas.drawColor(Color.WHITE);
    }

    /**
     * 描画イベント
     */
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, 0, 0, null);
    }

    /**
     * タッチイベント
     */
    public boolean onTouchEvent(MotionEvent event) {
        // 描画位置の確認
        Point cur = new Point((int) event.getX(), (int) event.getY());
        if (oldpos.x < 0) {
            oldpos = cur;
        }
        // 描画属性を設定
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(4);
        // 線を描画
        bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
        oldpos = cur;
        // 指を持ち上げたら座標をリセット
        if (event.getAction() == MotionEvent.ACTION_UP) {
            oldpos = new Point(-1, -1);
        }
        invalidate();
        return true;
    }
}