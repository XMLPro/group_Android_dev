package com.example.suzuki.memoprot001;

import android.app.Application;

/**
 * Created by suzuki on 2015/10/11.
 */
public class Settings extends Application {
    private boolean updateFlag = false;
    private String updateID = "";

    public void setUpdateFlag(boolean flag) {
        this.updateFlag = flag;
    }

    public boolean getUpdeteFlag() {
        return this.updateFlag;
    }

    public void setUpdateID(String id) {
        this.updateID = id;
    }

    public String getUpdateID() {
        return this.updateID;
    }
}
