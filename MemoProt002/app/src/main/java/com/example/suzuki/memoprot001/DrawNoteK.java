package com.example.suzuki.memoprot001;

import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * メインクラスの定義
 */
public class DrawNoteK extends ActionBarActivity {
    DrawNoteView view;

    //アクションバーを変更するための数字
    int change = 0;

    /**
     * アプリの初期化
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        //お絵描きタイトル表示
        setTitle(getString(R.string.draw));

        // 描画クラスを設定
        view = new DrawNoteView(getApplication());
        setContentView(view);
    }

    /**
     * メニューの生成イベント
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.draw, menu);

        //消しゴムボタンが押された時にボタンのマークを鉛筆に変える
        //変数changeが0なら消しゴム、1なら鉛筆にマークを変更
        if (change == 1) {
            MenuItem one = menu.findItem(R.id.action_eraser);
            one.setIcon(android.R.drawable.ic_menu_edit);
        }
        super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * メニューがクリックされた時のイベント
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_eraser:
                if (change == 0) {
                    change = 1;
                    //アクションバーを再表示する関数invalidateOptionsMenu()
                    invalidateOptionsMenu();
                } else if (change == 1) {
                    change = 0;
                    invalidateOptionsMenu();
                }
                break;
            case R.id.action_share:
                break;
            case R.id.action_delete:
                view.clearDrawList();
                break;
            case R.id.action_save:
                savetofile(view.saveToFile());
                Toast.makeText(this, getString(R.string.Save), Toast.LENGTH_SHORT).show();
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
        if (f) {
            System.out.println("eee:ok");
        } else {
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
            bmp.compress(Bitmap.CompressFormat.PNG, 200, out);
            out.flush();
            out.close();
            Log.e("eee", "ok");
        } catch (Exception e) {
            Log.e("eee", String.valueOf(e));
        }
    }

    //端末側の戻るボタンが押されたときの処理
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ダイアログ表示など特定の処理を行いたい場合はここに記述
                    // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返すと戻るボタンが無効になる
                    finish();
            }
        }
        return super.dispatchKeyEvent(event);
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
}