package com.example.c0114443.navi_simple;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item){
        switch(item.getItemId()){
            case R.id.action_menu1:
            //menu1�I�����̏������L�q
                break;
            case R.id.action_menu2:
            //menu2�I�����̏������L�q
                break;
            case R.id.action_menu3:
            //menu3�I�����̏������L�q
                break;
            case R.id.action_menu4:
                //menu4�I�����̏������L�q
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
