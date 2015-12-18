package takayuki.techinstitute.jp.memoprot003.paint;

import android.graphics.Bitmap;

/**
 * Created by Owner on 2015/12/15.
 */
public class BitmapList {
    public Bitmap[] bmp;
    public int setcursor = 0;
    public int getcursor = 0;
    public BitmapList(int num){
        bmp = new Bitmap[num];
        setcursor = 0;
        getcursor = 0;
    }

    public Bitmap getBitmap(int index){
        return bmp[index];
    }

    public void addBitmap(Bitmap bitmap){
        if(setcursor < bmp.length){
            bmp[setcursor] = bitmap;
            setcursor++;
            getcursor++;
            return;
        }
        for(int i = 0; i<bmp.length-1; i++){
            bmp[i] = bmp[i+1];
        }
        bmp[setcursor-1] = bitmap;
    }

    //undoの処理
    public Bitmap undo(){

        if(iscursorzero()){
            return bmp[getcursor];
        }
        getcursor--;
        return bmp[getcursor];
    }

    //redoの処理
    public Bitmap redo(){

        if(iscursor()){
            return bmp[getcursor];
        }
        getcursor++;
        return bmp[getcursor];
    }

    //undoを4回行ったときの処理
    public boolean iscursorzero(){
        if (getcursor == 0){
            return true;
        }
        return false;
    }

    //redoを4回行ったときの処理
    public boolean iscursor(){
        if ((setcursor - getcursor) < 2){
            return true;
        }
        return false;
    }

}
