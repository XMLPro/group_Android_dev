package com.example.c0114443.memomo;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class MainActivity extends Activity implements OnClickListener {

    EditText editText1;
    Button button1, button2;
    String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = (EditText) findViewById(R.id.editText1);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        fileName = getResources().getString(R.string.file_name);
        try {
            FileInputStream fis = openFileInput(fileName);
            byte[] readBytes = new byte[fis.available()];
            fis.read(readBytes);
            editText1.setText(new String(readBytes));
            fis.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v == button1) {
            //•Û‘¶
            try {
                FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
                fos.write(editText1.getText().toString().getBytes());
                fos.close();
            } catch (Exception e) {
                ;
            }

        } else if (v == button2) {
            //”jŠü
            editText1.setText("");
            deleteFile(fileName);
        }
        ;
    }
}
