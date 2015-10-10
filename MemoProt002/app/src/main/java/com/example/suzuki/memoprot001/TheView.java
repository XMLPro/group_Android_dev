package com.example.suzuki.memoprot001;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.InputStream;

public class TheView extends Activity {
//    GridView mGrid;

    private static final int REQUEST_GALLERY = 0;
    private ImageView imgView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.the_view);
        imgView = (ImageView) findViewById(R.id.imgview_id);
        // �M�������[�Ăяo��
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                in.close();
                // �I�������摜��\��
                imgView.setImageBitmap(img);
            } catch (Exception e) {

            }
        }
    }
        /*
        //���R�[�h�̎擾
        Cursor cursor = managedQuery(MediaStore.Images.Media.INTERNAL_CONTENT_URI, null, null, null,
                null);

        cursor.moveToFirst();

        // init for loop
        int fieldIndex;
        Long id;
        int cnt = 0, VolMax = 0;
        HashMap<Integer, Uri> uriMap = new HashMap<Integer, Uri>(); //URI��Map�ŊǗ�����

//        do {
        while(cursor.getCount() < 0) {
            //�J����ID�̎擾
            fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            id = cursor.getLong(fieldIndex);
            //ID����URI���擾
            Uri bmpUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, id);
            uriMap.put(cnt, bmpUri);
            cnt++;
        }
//        } while (cursor.moveToNext());

        VolMax = --cnt;
        cnt = 0;

//         Setting GridView
        mGrid = (GridView) findViewById(R.id.myGrid);
        mGrid.setAdapter(new myAdapter(getContentResolver(), uriMap, VolMax));
    }

    // GridView�p��CustomAdapter
    //
    public class myAdapter extends BaseAdapter {
        private ContentResolver cr;
        private HashMap<Integer, Uri> hm;
        private int MAX;
        private Bitmap tmpBmp;
        ImageView imageView;

        public myAdapter(ContentResolver _cr, HashMap<Integer, Uri> _hm, int max) {
            cr = _cr;
            hm = _hm;
            // MAX = max;
            MAX = 30;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                imageView = new ImageView(TheView.this);
                imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            } else {
                imageView = (ImageView) convertView;
            }

            try {
                tmpBmp = MediaStore.Images.Media.getBitmap(cr, hm.get(position));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(tmpBmp);

            return imageView;
        }

        public final int getCount() {
            return MAX;
        }

        public final Object getItem(int position) {
            return position;
        }

        public final long getItemId(int position) {
            return position;
        }
    }
    */

}