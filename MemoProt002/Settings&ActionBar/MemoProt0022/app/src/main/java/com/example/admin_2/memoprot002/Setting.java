package com.example.admin_2.memoprot002;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Setting extends ActionBarActivity {
    protected Button spinnerButtonBackGround;
    protected Button spinnerButtonFont;

    protected int groundchecking;
    protected int groundchecked;
    protected int fontchecking;
    protected int fontchecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //スピナー風ボタン[BackGround][Font]設置
        spinnerButtonBackGround = (Button) findViewById(R.id.background);
        spinnerButtonBackGround.setText("BackGround");
        spinnerButtonBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] itemBackGround = {"White", "Black", "Green"};
                new AlertDialog.Builder(Setting.this)
                        .setTitle("Set BackGround Skin")
                        .setSingleChoiceItems(itemBackGround, groundchecking, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                //アイテムを選択したらここに入る
                                groundchecking = item;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //OKを押したらここに入る
                                groundchecked = groundchecking;
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        //ダイアログに表示する項目

        spinnerButtonFont = (Button) findViewById(R.id.font);
        spinnerButtonFont.setText("Font");
        spinnerButtonFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] itemFont = {"Gothic", "MS Gothic", "Mintyoutai"};
                new AlertDialog.Builder(Setting.this)
                        .setTitle("Set Font")
                        .setSingleChoiceItems(itemFont, fontchecking, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                //アイテムを選択したらここに入る
                                fontchecking = item;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //OKを押したらここに入る
                                fontchecked = fontchecking;
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        //戻るを押したときに画面を戻す
        Button back = (Button) findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}