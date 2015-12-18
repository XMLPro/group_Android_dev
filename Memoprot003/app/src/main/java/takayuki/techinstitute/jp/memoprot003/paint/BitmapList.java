package takayuki.techinstitute.jp.memoprot003.paint;

import android.graphics.Bitmap;

/**
 * Created by Owner on 2015/12/15.
 */
public class BitmapList {
    public Bitmap[] bmp;
    public int setcusor;
    public int getcusor;
    public BitmapList(int num){
        bmp = new Bitmap[num];
        setcusor = 0;
        getcusor = 0;
    }

    public Bitmap getBitmap(int index){
        return bmp[index];
    }

    public void addBitmap(Bitmap bitmap){
        if(setcusor < bmp.length){
            bmp[setcusor] = bitmap;
            setcusor++;
            getcusor++;
            return;
        }
        for(int i = 0; i<bmp.length-1; i++){
            bmp[i] = bmp[i+1];
        }
        bmp[setcusor-1] = bitmap;
    }

    public Bitmap undo(){
        if(iscusorzero()){
            return bmp[getcusor];
        }
        getcusor--;
        return bmp[getcusor];
    }


    public boolean iscusorzero(){
        if (getcusor == 0){
            return true;
        }
        return false;
    }

}
