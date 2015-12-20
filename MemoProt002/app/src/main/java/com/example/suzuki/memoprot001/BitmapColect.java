package com.example.suzuki.memoprot001;

import android.graphics.Bitmap;

/**
 * Created by Admin_2 on 2015/12/15.
 */
public class BitmapColect {
    Bitmap[] bmp;
    int cusor;

    public BitmapColect(int bmsize) {
        bmp = new Bitmap[bmsize];
        cusor = 0;
    }
    public void addBitmap(Bitmap bp){
        if(cusor < bmp.length){
            bmp[cusor] = bp;
            cusor++;
            return;
        }
        for(int i = 0; i<bmp.length-1; i++){
            bmp[i] = bmp[i+1];
        }
        bmp[cusor-1] = bp;
    }

    public Bitmap getBitmap(int index){
        return bmp[index];
    }

    public Bitmap undo(){
        if(cusor == 0){
            return bmp[0];
        }
        return bmp[cusor-1];
    }

    public Bitmap redo(){
        if(cusor == bmp.length-1){
            return bmp[cusor];
        }
        return bmp[cusor+1];
    }

}
