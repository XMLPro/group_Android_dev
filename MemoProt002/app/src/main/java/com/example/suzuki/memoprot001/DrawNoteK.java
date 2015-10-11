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
 * ?�?�?�C?�?�?�N?�?�?�X?�̒�`
 */
public class DrawNoteK extends ActionBarActivity {
    DrawNoteView view;
    public Intent i;

    //�V�摜�\��
    private static final int REQUEST_GALLERY = 0;
    //�摜�r���[�p�̐���
    public int G;

    //?�A?�N?�V?�?�?�?�?�o?�[?�?�ύX?�?�?�邽?�߂̐�?�?�
    int change = 0;
    int color = 0;

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

        Intent intent = getIntent();
        G = intent.getIntExtra("G", 0);
    }

    public void change(){
        //mainActivity����摜�v���r���[�ŌĂ΂ꂽ���̏���
        if (G == 2) {
            G = 0;
            toView();
        }
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

    /**
     * ?�?�?�j?�?�?�[?�?�?�N?�?�?�b?�N?�?�?�ꂽ?�?�?�̃C?�x?�?�?�g
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
            //���摜�폜��
            case R.id.imageDelete:
                i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_PICK);
                startActivityForResult(i, 1);
                break;
            case R.id.action_eraser:
                if (change == 0) {
                    change = 1;
                    //?�A?�N?�V?�?�?�?�?�o?�[?�?�?�ĕ\?�?�?�?�?�?�֐�invalidateOptionsMenu()
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

    //�摜�v���r���[�ֈڍs������֐�toView
    public void toView() {
        i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_PICK);
        startActivityForResult(i, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //�摜�v���r���[�I����
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                // WindowManager�̃C���X�^���X�擾
                WindowManager wm = getWindowManager();
                // Display�̍L���擾 Actionbar�̑傫���Ԃ�y����-240
                Display disp = wm.getDefaultDisplay();
                int width = disp.getWidth();
                int height = disp.getHeight() - 240;

                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                //�傫�����t�B�b�g������
                Bitmap img2 = Bitmap.createScaledBitmap(img, width, height, false);
                in.close();
                // ��ʂ������đI�������摜�œh��
                view.readImage(img2);
                Toast.makeText(this, getString(R.string.openM), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
        //�摜�폜�I����
        else if (requestCode == 1) {
            try {
                getContentResolver().delete(data.getData(), null, null);
                Toast.makeText(this, getString(R.string.deleteM), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
            }
        }
    }

    //�ۑ�
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
     * ?�`?�?�N?�?�?�X?�̒�`
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
         * ?�`?�?�C?�x?�?�?�g
         */
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(bmp, 0, 0, null);
            change();
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

            if (change == 0) {
                switch (color) {
                    case 0:
                        paint.setColor(Color.BLACK);
                        break;
                    case 1:
                        paint.setColor(Color.RED);
                        break;
                    case 2:
                        paint.setColor(Color.GREEN);
                        break;
                    case 3:
                        paint.setColor(Color.BLUE);
                        break;
                }
                paint.setStyle(Paint.Style.FILL);
                paint.setStrokeWidth(10);
            } else if (change == 1) {
                paint.setColor(Color.WHITE);
//                paint.setStyle(Paint.Style.FILL);
//                paint.setStrokeWidth(50);
                paint.setStyle(Paint.Style.FILL);
                bmpCanvas.drawCircle(oldpos.x, oldpos.y, 60, paint);
//                bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
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