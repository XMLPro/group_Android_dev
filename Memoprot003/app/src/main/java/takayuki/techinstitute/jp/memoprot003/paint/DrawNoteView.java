package takayuki.techinstitute.jp.memoprot003.paint;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Owner on 2015/12/08.
 */
class DrawNoteView extends android.view.View {

    Bitmap bmp = null;
    Canvas bmpCanvas;
    Point oldpos = new Point(-50, -50);
    Paint paint = new Paint();
    BitmapList bitmapList = new BitmapList();

    public DrawNoteView(Context c) {
        super(c);
        setFocusable(true);
    }

    public DrawNoteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        paint.setColor(Color.BLACK);
    }

    public DrawNoteView(Context cont, AttributeSet attr) {
        super(cont, attr);
    }

    public void clearDrawList() {
        bmpCanvas.drawColor(Color.WHITE);
       // bitmapList.addBitmap(bmp.copy(Bitmap.Config.ARGB_8888, true));
        invalidate();
    }


    public void readImage(Bitmap bmp) {
        bmpCanvas.drawBitmap(bmp, 0, 0, paint);
    }

    public void setColor(int color){
        paint.setColor(color);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bmpCanvas = new Canvas(bmp);
        bmpCanvas.drawColor(Color.WHITE);
        bitmapList.append(bmp.copy(Bitmap.Config.ARGB_8888, true));
    }

    public void saveToFile() {
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
        ContentResolver contentResolver = getContext().getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void undo() {
        Bitmap bitmap =bitmapList.undo();
        bmpCanvas.drawBitmap(bitmap,0,0,paint);
        invalidate();
    }

    public void redo() {
        Bitmap bitmap =bitmapList.redo();
        bmpCanvas.drawBitmap(bitmap,0,0,paint);
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bmp, 0, 0, paint);
    }


    public boolean onTouchEvent(MotionEvent event) {
        Point cur = new Point((int) event.getX(), (int) event.getY());

        PaintFragment P = new PaintFragment();

        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d("入れてる?","分からない");
            bitmapList.append(bmp.copy(Bitmap.Config.ARGB_8888, true));
        }

        if (oldpos.x < 0) {
            oldpos = cur;
        }

        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);

        if(P.iconflag == 1){
            paint.setColor(Color.WHITE);
            paint.setStrokeWidth(30);
            bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
        }
        else{
            if(paint.getColor() == Color.WHITE) paint.setColor(Color.BLACK);
        }

        bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
        oldpos = cur;
        if (event.getAction() == MotionEvent.ACTION_UP) {
            oldpos = new Point(-1, -1);
        }
        invalidate();
        return true;
    }
}
