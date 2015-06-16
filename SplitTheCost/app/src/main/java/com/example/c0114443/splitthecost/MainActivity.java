package com.example.c0114443.splitthecost;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = (Button)findViewById(R.id.btnCalc);
        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //�����ɏ������L�q

                //�I�u�W�F�N�g���擾
                EditText etxtNum = (EditText)findViewById(R.id.eTxtNum);
                EditText etxtMoney = (EditText)findViewById(R.id.eTxtMoney);
                TextView txtResult = (TextView)findViewById(R.id.txtResult);

                //���͓��e���擾
                String strNum = etxtNum.getText().toString();
                String strMoney = etxtMoney.getText().toString();

                //���l�ɕϊ�
                int num = Integer.parseInt(strNum);
                int money = Integer.parseInt(strMoney);

                //���芨�v�Z
                int result = money / num;

                //���ʕ\���p�e�L�X�g�ɐݒ�
                txtResult.setText(Integer.toString(result));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
