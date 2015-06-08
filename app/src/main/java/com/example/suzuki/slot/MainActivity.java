package com.example.suzuki.slot;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends Activity {
    private TextView[] slotNum = new TextView[3];
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
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == event.ACTION_DOWN) {
            startSlot();
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void startSlot(){
        String[] slotValue = getResources().getStringArray(R.array.slot_num);
        slotNum[0] = (TextView) findViewById(R.id.slot0);
        slotNum[1] = (TextView) findViewById(R.id.slot1);
        slotNum[2] = (TextView) findViewById(R.id.slot2);

        Random r = new Random();
        for(int i=0;i<slotNum.length;i++){
            int rNum=r.nextInt(9);
            System.out.println("r.next: "+rNum);
            slotNum[i].setText(""+rNum);
//            String a = (String)slotNum[0].getText();//.setText("5");
  //          System.out.println(a);
        }
    }
}
