package takayuki.techinstitute.jp.memoprot003.paint;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by Owner on 2015/12/15.
 */
public class BitmapList {
    public Bitmap[] bmp;
    public int setcursor = 0;
    public int getcursor = 0;
    public boolean flag = true;
    public BitmapList(int num){
        bmp = new Bitmap[num];
        setcursor = 0;
        getcursor = 0;
    }

    public Bitmap getBitmap(int index){
        return bmp[index];
    }

    public void addBitmap(Bitmap bitmap){
        if(getcursor < bmp.length){
            bmp[getcursor] = bitmap;
            setcursor++;
            getcursor++;
            Log.d("log:if", "addBitmap: ");
            return;
        }
        for(int i = 0; i<bmp.length-1; i++){
            bmp[i] = bmp[i+1];
            Log.d("log:for", "addBitmap: ");
        }
        bmp[getcursor - 2] = bitmap;
    }

    //undoの処理
    public Bitmap undo(){
        if(iscursorzero()){
            Log.d("log:iscursorzero", "undo: ");
            return bmp[getcursor];
        }
        if (flag == true && getcursor > 1 || getcursor == 4) {
            getcursor = getcursor - 2;
            flag = false;
            Log.d("log:if flag", "undo: ");
            return bmp[getcursor];
        }
        Log.d("log:getcursor", "undo: ");
        getcursor--;
        return bmp[getcursor];
    }

    //redoの処理
    public Bitmap redo(){
        if(iscursor()){
            Log.d("log:iscursor", "redo: ");
            return bmp[getcursor];
        }
        Log.d("log:redo", "redo: ");
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
        if ((setcursor - getcursor) < 2 || getcursor >= 3){
            return true;
        }
        return false;
    }

}