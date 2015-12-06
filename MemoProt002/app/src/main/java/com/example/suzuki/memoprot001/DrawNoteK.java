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
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DrawNoteK extends ActionBarActivity {
    DrawNoteView view;
    public Intent i;
    private static final int REQUEST_GALLERY = 0;

    public int G;
    public ColorSetFragment csFragment;

    int change = 0;
    int color = 0;
    int thick = 10;
    private static final String APP_KEY = "3j3o6hefxvfku5c";
    private static final String APP_SECRET = "ympn6o0newj1si5";
    private DropboxAPI<AndroidAuthSession> mApi;
    private boolean logged_in = false;
    private File shareFile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        mApi = new DropboxAPI<>(session);

        setTitle(getString(R.string.draw));

        // ?�`?�?�N?�?�?�X?�?�ݒ�
        view = new DrawNoteView(getApplication());
        setContentView(view);

        Intent intent = getIntent();
        G = intent.getIntExtra("G", 0);

        //undo,redoの処理
        final DrawNoteView viewdo = (DrawNoteView)findViewById(R.id.DrawNoteView);
        findViewById(R.id.btnUndo).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                viewdo.undo();
            }
        });

        findViewById(R.id.btnRedo).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                viewdo.redo();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        AndroidAuthSession session = mApi.getSession();
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();
                //storeAuth(session);
                // Store it locally in our app for later use
                String accessToken = session.getOAuth2AccessToken();
                logged_in = true;

            } catch (IllegalStateException e) {
            }
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void change() {
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

        //?�?�?8�?�?�S?�?�?�{?�^?�?�?�?�?�?�?�?�?�ꂽ?�?�?�Ƀ{?�^?�?�?�̃}?�[?�N?�?�?�?�?�M?�ɕς�?�?�
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
            case R.id.colorSet:
                csFragment = new ColorSetFragment();
                csFragment.show(getFragmentManager(), "test ");
                color = R.id.colorSet;
                break;
            case R.id.dropLogin:
                if (logged_in) {
                    logOut();
                    item.setTitle("ログイン");
                } else {
                    mApi.getSession().startOAuth2Authentication(DrawNoteK.this);
                    item.setTitle("ログアウト");
                }
                break;
            case R.id.share:
                if (view.saveToFile() != null) {
                    saveToFile(view.saveToFile());
                    UploadPicture upload = new UploadPicture(DrawNoteK.this, mApi, "/MyPhoto", shareFile);
                    upload.execute();
                }
                break;
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
            case R.id.yellow:
                color = 4;
                break;
            case R.id.cyan:
                color = 5;
                break;
            case R.id.sc:
                color = 6;
                break;
            case R.id.gray:
                color = 7;
                break;
            case R.id.brown:
                color = 8;
                break;
            case R.id.magenta:
                color = 9;
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
                // Display�̍L���擾 Actionbar�̑傫���Ԃ�y����-240
                Display disp = getWindowManager().getDefaultDisplay();
                Point p = new Point();
                disp.getSize(p);
                int width = p.x;
                int height = p.y;

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
        setFile(new File(AttachName));
        // save index
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = getContentResolver();
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", AttachName);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    public void setFile(File f) {
        this.shareFile = f;
    }

    private void logOut() {
        mApi.getSession().unlink();
        logged_in = false;
    }

    /**
     * ?�`?�?�N?�?�?�X?�̒�`
     */
    class DrawNoteView extends android.view.View {

        Bitmap bmp = null;
        Canvas bmpCanvas;
        Point oldpos = new Point(-50, -50);
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
            canvas.restore();
            canvas.drawBitmap(bmp, 0, 0, null);
            change();
        }

        /**
         * ?�^?�b?�`?�C?�x?�?�?�g
         */
        public boolean onTouchEvent(MotionEvent event) {
            // ?�`?�?�ʒu?�̊m?�F
            Point cur = new Point((int) event.getX(), (int) event.getY());

//            Settings Change = (Settings) getApplication();
//            change = Change.getChange();

            Settings Thick = (Settings) getApplication();
            thick = Thick.getThick();

            paint.setStrokeWidth(thick);
            if(thick == 0){paint.setStrokeWidth(10);}

            if (oldpos.x < 0) {
                oldpos = cur;
            }

            if (change == 0) {
                switch (color) {
                    case R.id.colorSet:
                        Settings paintC = (Settings) getApplication();
                        paint.setColor(paintC.getC());
                        break;
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
                    case 4:
                        paint.setColor(Color.YELLOW);
                        break;
                    case 5:
                        paint.setColor(Color.CYAN);
                        break;
                    case 6:
                        paint.setColor(Color.rgb(252, 226, 196));
                        break;
                    case 7:
                        paint.setColor(Color.GRAY);
                        break;
                    case 8:
                        paint.setColor(Color.rgb(128, 0, 0));
                        break;
                    case 9:
                        paint.setColor(Color.MAGENTA);
                        break;
                }
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                // ?�?�?�`?�?�
                paint.setAntiAlias(true);
                bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
            } else if (change == 1) {
                paint.setColor(Color.WHITE);
                paint.setStyle(Paint.Style.FILL);

                bmpCanvas.drawCircle(oldpos.x, oldpos.y, 60, paint);
                bmpCanvas.drawLine(oldpos.x, oldpos.y, cur.x, cur.y, paint);
            }
//            else if (change == 2) {
//                paint.setColor(Color.BLACK);
//                paint.setStyle(Paint.Style.FILL_AND_STROKE);
//                bmpCanvas.drawCircle(oldpos.x, oldpos.y, 25, paint);
//            }

            oldpos = cur;
            // ?�w?�?�?�?�?�?�?�グ?�?�?�?�?�?�W?�?�?�?�?�Z?�b?�g
            if (event.getAction() == MotionEvent.ACTION_UP) {
                oldpos = new Point(-1, -1);
            }
            bmpCanvas.save();
            invalidate();
            return true;
        }

//
//        private final HistoryStack<ArrayList<PointF>> history = new HistoryStack<ArrayList<PointF>>();
//        private ArrayList<PointF> currentStroke;
//
//        public DrawingView(Context context, AttributeSet attrs) {
//            super(context, attrs);
//            // TODO Auto-generated constructor stub
//        }
//
//        /**
//         * アンドゥ
//         */
//        public void undo(){
//            history.undo();
//            invalidate();
//        }
//
//        /**
//         * リドゥ
//         */
//        public void redo(){
//            history.redo();
//            invalidate();
//        }
//
//        @Override
//        public boolean onTouchEvent(MotionEvent event) {
//            if( event.getAction() == MotionEvent.ACTION_DOWN){
//                // 新しい描画
//                currentStroke = new ArrayList<PointF>();
//                return true;
//            }
//            else if(event.getAction() == MotionEvent.ACTION_MOVE){
//                currentStroke.add(new PointF(event.getX(),event.getY()));
//                invalidate();
//                return true;
//            }
//            else if(event.getAction()==MotionEvent.ACTION_UP){
//                history.add(currentStroke);
//                currentStroke = null;
//                invalidate();
//
//                return true;
//            }
//
//            return super.onTouchEvent(event);
//        }
//
//        /**
//         * PointFの配列を元に一連の線を描画する
//         * @param canvas
//         * @param paint
//         * @param stroke
//         */
//        private void drawStroke(Canvas canvas,Paint paint,ArrayList<PointF> stroke){
//            PointF startPoint = null;
//            for(PointF pf:stroke){
//                if( startPoint != null){
//                    canvas.drawLine(startPoint.x, startPoint.y, pf.x, pf.y, paint);
//                }
//
//                startPoint = pf;
//            }
//        }
//
//        private final Paint paint = new Paint();
//
//        {
//            paint.setColor(Color.CYAN);
//            paint.setStrokeWidth(1.f);
//        }
//
//
//        @Override
//        protected void onDraw(Canvas canvas) {
//
//            // 履歴に入っている線を描画する
//            for(final ArrayList<PointF> stroke:history.iterateUndo()){
//                drawStroke(canvas,paint,stroke);
//            }
//
//            // 現在描画中の線を描画する
//            if( currentStroke != null){
//                drawStroke(canvas,paint,currentStroke );
//            }
//        }

    }
}