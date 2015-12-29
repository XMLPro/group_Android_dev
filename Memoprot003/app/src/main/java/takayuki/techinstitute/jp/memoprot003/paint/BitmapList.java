package takayuki.techinstitute.jp.memoprot003.paint;


import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Owner on 2015/12/15.
 */
public class BitmapList {
    private Bitmap[] bmpList;
    private int cursor;
    private final static int LIMIT =6;


    public BitmapList() {
    }

    public void append(Bitmap bitmap) {
        if (bmpList == null) {
            bmpList = new Bitmap[1];
            bmpList[0] = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        }
        if(cursor + 2 > LIMIT){
            Bitmap[] upd = new Bitmap[LIMIT];
            for (int i = 0; i < bmpList.length-1; i++) {
                upd[i] = bmpList[i+1].copy(Bitmap.Config.ARGB_8888, true);
            }
            upd[upd.length-1] = bitmap.copy(Bitmap.Config.ARGB_8888, true);
            bmpList = upd;
            cursor = bmpList.length - 1;
            return;
        }

        Bitmap[] upd = new Bitmap[cursor + 2];
        for (int i = 0; i <= cursor; i++) {
            upd[i] = bmpList[i].copy(Bitmap.Config.ARGB_8888, true);
        }
        bmpList = upd;
        cursor = bmpList.length - 1;
        bmpList[cursor] = bitmap.copy(Bitmap.Config.ARGB_8888, true);
    }

    public Bitmap undo() {
        if (cursor == 0) {
            return bmpList[0];
        }
        cursor--;
        return bmpList[cursor];
    }

    public Bitmap redo() {
        if (cursor == bmpList.length - 1) {
            return bmpList[bmpList.length - 1];
        }
        cursor++;
        return bmpList[cursor];
    }
}
