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
 * ���C���N���X�̒�`
 */
public class DrawNoteK extends ActionBarActivity {
    DrawNoteView view;

    //�A�N�V�����o�[��ύX���邽�߂̐���
    int change = 0;
    int color = 0;

    /**
     * �A�v���̏���
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        //���G�`���^�C�g���\��
        setTitle(getString(R.string.draw));

        // �`��N���X��ݒ�
        view = new DrawNoteView(getApplication());
        setContentView(view);
    }

    /**
     * ���j���[�̐����C�x���g
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.draw, menu);

        //�����S���{�^���������ꂽ���Ƀ{�^���̃}�[�N�����M�ɕς���
        //�ϐ�change��0�Ȃ�����S���A1�Ȃ牔�M�Ƀ}�[�N��ύX
        if (change == 1) {
            MenuItem one = menu.findItem(R.id.action_eraser);
            one.setIcon(android.R.drawable.ic_menu_edit);
        }
        super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * ���j���[���N���b�N���ꂽ���̃C�x���g
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
            case R.id.action_eraser:
                if (change == 0) {
                    change = 1;
                    //�A�N�V�����o�[���ĕ\������֐�invalidateOptionsMenu()
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
        // �摜���t�@�C���ɏ�������
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

    //�[�����̖߂�{�^���������ꂽ�Ƃ��̏���
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // �_�C�A���O�\���ȂǓ���̏������s�������ꍇ�͂����ɋL�q
                    // �e�N���X��dispatchKeyEvent()���Ăяo������true��Ԃ��Ɩ߂�{�^��������ɂȂ�
                    finish();
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * �`��N���X�̒�`
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
         * ��ʃT�C�Y���ύX���ꂽ��
         */
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bmpCanvas = new Canvas(bmp);
            bmpCanvas.drawColor(Color.WHITE);
        }

        /**
         * �`��C�x���g
         */
        protected void onDraw(Canvas canvas) {
            canvas.drawBitmap(bmp, 0, 0, null);
        }

        /**
         * �^�b�`�C�x���g
         */
        public boolean onTouchEvent(MotionEvent event) {
            // �`��ʒu�̊m�F
            Point cur = new Point((int) event.getX(), (int) event.getY());
            if (oldpos.x < 0) {
                oldpos = cur;
            }

            Paint paint = new Paint();
            if (change == 0) {
                // �`�摮����ݒ�
                if(color == 0) {
                    paint.setColor(Color.BLACK);
                }else if(color == 1){
                    paint.setColor(Color.RED);
                }else if(color == 2){
                    paint.setColor(Color.GREEN);
                }else if(color == 3){
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
            // ���`��
            bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
            oldpos = cur;
            // �w�������グ������W�����Z�b�g
            if (event.getAction() == MotionEvent.ACTION_UP) {
                oldpos = new Point(-1, -1);
            }
            invalidate();
            return true;
        }

    }
}