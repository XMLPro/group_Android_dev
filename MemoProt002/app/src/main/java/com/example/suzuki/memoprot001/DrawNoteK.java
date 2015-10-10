package com.example.suzuki.memoprot001;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * ?ｽ?ｽ?ｽC?ｽ?ｽ?ｽN?ｽ?ｽ?ｽX?ｽﾌ抵ｿｽ`
 */
public class DrawNoteK extends ActionBarActivity {
    DrawNoteView view;
    public Intent i;

    //新画像表示
    private static final int REQUEST_GALLERY = 0;
    //画像ビュー用の数字
    public int G;

    //?ｽA?ｽN?ｽV?ｽ?ｽ?ｽ?ｽ?ｽo?ｽ[?ｽ?ｽﾏ更?ｽ?ｽ?ｽ驍ｽ?ｽﾟの撰ｿｽ?ｽ?ｽ
    int change = 0;
    int color = 0;

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

        Intent intent = getIntent();
        G = intent.getIntExtra("G", 0);
    }

    public void change(){
        //mainActivityから画像プレビューで呼ばれた時の処理
        if (G == 2) {
            G = 0;
            toView();
        }
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

    /**
     * ?ｽ?ｽ?ｽj?ｽ?ｽ?ｽ[?ｽ?ｽ?ｽN?ｽ?ｽ?ｽb?ｽN?ｽ?ｽ?ｽ黷ｽ?ｽ?ｽ?ｽﾌイ?ｽx?ｽ?ｽ?ｽg
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.black:
                color = 0;
                break;
            case R.id.red:
                color = 1;
                break;
            case R.id.green:
                color = 2;
                break;
            case R.id.blue:
                color = 3;
                break;
            case R.id.images:
                toView();
                break;
            //↓画像削除↓
            case R.id.imageDelete:
                i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_PICK);
                startActivityForResult(i, 1);
                break;
            case R.id.action_eraser:
                if (change == 0) {
                    change = 1;
                    //?ｽA?ｽN?ｽV?ｽ?ｽ?ｽ?ｽ?ｽo?ｽ[?ｽ?ｽ?ｽﾄ表?ｽ?ｽ?ｽ?ｽ?ｽ?ｽﾖ撰ｿｽinvalidateOptionsMenu()
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
                saveToFile(view.saveToFile());
                Toast.makeText(this, getString(R.string.Save), Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    //画像プレビューへ移行させる関数toView
    public void toView() {
        i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_PICK);
        startActivityForResult(i, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //画像プレビュー選択時
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                // WindowManagerのインスタンス取得
                WindowManager wm = getWindowManager();
                // Displayの広さ取得 Actionbarの大きさぶんy軸に-240
                Display disp = wm.getDefaultDisplay();
                int width = disp.getWidth();
                int height = disp.getHeight() - 240;

                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                //大きさをフィットさせる
                Bitmap img2 = Bitmap.createScaledBitmap(img, width, height, false);
                in.close();
                // 画面を消して選択した画像で塗る
                view.readImage(img2);
                Toast.makeText(this, getString(R.string.openM), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
        //画像削除選択時
        else if (requestCode == 1) {
            try {
                getContentResolver().delete(data.getData(), null, null);
                Toast.makeText(this, getString(R.string.deleteM), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }

    //保存
    public void saveToFile(Bitmap bmp) {
        final String SAVE_DIR = "/MyPhoto/";
        File file = new File(Environment.getExternalStorageDirectory().getPath() + SAVE_DIR);
        try {
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            throw e;
        }

        Date mDate = new Date();
        SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = fileNameDate.format(mDate) + ".jpg";
        String AttachName = file.getAbsolutePath() + "/" + fileName;

        try {
            FileOutputStream out = new FileOutputStream(AttachName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // save index
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * ?ｽ`?ｽ?ｽN?ｽ?ｽ?ｽX?ｽﾌ抵ｿｽ`
     */
    class DrawNoteView extends android.view.View {

        Bitmap bmp = null;
        Canvas bmpCanvas;
        Point oldpos = new Point(-1, -1);
        Paint paint = new Paint();

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

        public void readImage(Bitmap bmp) {
            bmpCanvas.drawBitmap(bmp, 0, 0, paint);
        }

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
            change();
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

            if (change == 0) {
                // ?ｽ`?ｽ鞫ｮ?ｽ?ｽ?ｽ?ｽﾝ抵ｿｽ
                paint.setColor(Color.BLUE);
                // ?ｽ`?ｽ鞫ｮ?ｽ?ｽ?ｽ?ｽﾝ抵ｿｽ
                if (color == 0) {
                    paint.setColor(Color.BLACK);
                } else if (color == 1) {
                    paint.setColor(Color.RED);
                } else if (color == 2) {
                    paint.setColor(Color.GREEN);
                } else if (color == 3) {
                    paint.setColor(Color.BLUE);
                }
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(8);
            } else if (change == 1) {
                paint.setColor(Color.WHITE);
//                paint.setStyle(Paint.Style.FILL);
//                paint.setStrokeWidth(50);
                paint.setStyle(Paint.Style.FILL);
                bmpCanvas.drawCircle(oldpos.x, oldpos.y, 50, paint);
//                bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
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