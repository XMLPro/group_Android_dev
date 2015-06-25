package com.example.admin_2.memozaki;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

/**
 * Created by Admin_2 on 2015/06/18.
 */
public class EditActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }
}
