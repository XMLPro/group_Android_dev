package com.example.suzuki.memoprot001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class Setting extends ActionBarActivity {
    // スピナー風ボタン用のチェック変数宣言
    protected int color;
    protected int fontSet;
    protected int font;

    public PaintDrawable paintDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //開幕色設定
        Intent intent = getIntent();
        color = intent.getIntExtra("Color", 0);
        switch (color) {
            case 1:
                paintDrawable = new PaintDrawable(Color.DKGRAY);
                break;
            case 2://赤
                paintDrawable = new PaintDrawable(Color.rgb(255,51,51));
                break;
            case 3://青
                paintDrawable = new PaintDrawable(Color.rgb(51,204,255));
                break;
            case 4://緑
                paintDrawable = new PaintDrawable(Color.rgb(0,255,102));
                break;
            case 5://ピンク
                paintDrawable = new PaintDrawable(Color.rgb(255,102,204));
                break;
            case 6://橙
                paintDrawable = new PaintDrawable(Color.rgb(255,153,0));
                break;
            case 7://紫
                paintDrawable = new PaintDrawable(Color.rgb(255,102,204));
                break;
            default:
                paintDrawable = new PaintDrawable(Color.WHITE);
        }
        getWindow().setBackgroundDrawable(paintDrawable);

        Button spinnerButtonBackGround = (Button) findViewById(R.id.background);
        spinnerButtonBackGround.setText(R.string.background);
        //触ったときの動作をOnclickListenerで書く
        spinnerButtonBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] itemBackGround = {getString(R.string.white), getString(R.string.black),
                        getString(R.string.red), getString(R.string.blue), getString(R.string.green),
                        getString(R.string.pink), getString(R.string.orange), getString(R.string.purple)};
                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.setbackground)
                        .setSingleChoiceItems(itemBackGround, color, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                //アイテムを選択したらここに入る
                                color = item;
                                switch (color) {
                                    case 1:
                                        paintDrawable = new PaintDrawable(Color.DKGRAY);
                                        break;
                                    case 2://赤
                                        paintDrawable = new PaintDrawable(Color.rgb(255,51,51));
                                        break;
                                    case 3://青
                                        paintDrawable = new PaintDrawable(Color.rgb(51,204,255));
                                        break;
                                    case 4://緑
                                        paintDrawable = new PaintDrawable(Color.rgb(0,255,102));
                                        break;
                                    case 5://ピンク
                                        paintDrawable = new PaintDrawable(Color.rgb(255,102,204));
                                        break;
                                    case 6://橙
                                        paintDrawable = new PaintDrawable(Color.rgb(255,153,0));
                                        break;
                                    case 7://紫
                                        paintDrawable = new PaintDrawable(Color.rgb(255,102,204));
                                        break;
                                    default:
                                        paintDrawable = new PaintDrawable(Color.WHITE);
                                }
                                color += 10;
                                getWindow().setBackgroundDrawable(paintDrawable);
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //OKを押したときの処理
                                //何もなし
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });

        Button spinnerButtonFont = (Button) findViewById(R.id.font);
        spinnerButtonFont.setText(R.string.font);
        spinnerButtonFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] itemFont = {getString(R.string.gothic), getString(R.string.MSgothic), getString(R.string.mintyo)};
                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.setfont)
                        .setSingleChoiceItems(itemFont, fontSet, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                fontSet = item;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                font = fontSet;
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });

        Button spinnerButtonLine = (Button) findViewById(R.id.Line);
        spinnerButtonLine.setText(R.string.line);
        spinnerButtonLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {getString(R.string.on), getString(R.string.off)};
                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.line)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                            }
                        })
                        .show();
            }
        });

        //戻るを押したときに背景色を渡して画面を戻す
        Button back = (Button) findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("color", color);
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
        //端末側の戻るボタンが押されたときの処理
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ダイアログ表示など特定の処理を行いたい場合はここに記述
                    // 親クラスのdispatchKeyEvent()を呼び出さずにtrueを返すと戻るボタンが無効になる
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("color", color);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
            }
        }
        return true;
    }
}