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
    protected int color;
    protected int fontSet;
    protected int font;

    public PaintDrawable paintDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Intent intent = getIntent();
        color = intent.getIntExtra("Color", 0);
        switch (color) {
            case 1:
                paintDrawable = new PaintDrawable(Color.DKGRAY);
                break;
<<<<<<< HEAD
            case 2://Ô
                paintDrawable = new PaintDrawable(Color.rgb(255, 51, 51));
=======
            case 2:
                paintDrawable = new PaintDrawable(Color.rgb(255,51,51));
>>>>>>> bfd3e61ab73c4b80a86dfb9ac88a539e5474cc76
                break;
            case 3:
                paintDrawable = new PaintDrawable(Color.rgb(51,204,255));
                break;
            case 4:
                paintDrawable = new PaintDrawable(Color.rgb(0,255,102));
                break;
            case 5:
                paintDrawable = new PaintDrawable(Color.rgb(255,153,204));
                break;
            case 6:
                paintDrawable = new PaintDrawable(Color.rgb(255,153,0));
                break;
            case 7:
                paintDrawable = new PaintDrawable(Color.rgb(255,102,204));
                break;
            default:
                paintDrawable = new PaintDrawable(Color.WHITE);
        }
        getWindow().setBackgroundDrawable(paintDrawable);

        Button spinnerButtonBackGround = (Button) findViewById(R.id.background);
        spinnerButtonBackGround.setText(R.string.background);
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
                                color = item;
                                switch (color) {
                                    case 1:
                                        paintDrawable = new PaintDrawable(Color.DKGRAY);
                                        break;
                                    case 2:
                                        paintDrawable = new PaintDrawable(Color.rgb(255,51,51));
                                        break;
                                    case 3:
                                        paintDrawable = new PaintDrawable(Color.rgb(51,204,255));
                                        break;
                                    case 4:
                                        paintDrawable = new PaintDrawable(Color.rgb(0,255,102));
                                        break;
                                    case 5:
                                        paintDrawable = new PaintDrawable(Color.rgb(255,153,204));
                                        break;
                                    case 6:
                                        paintDrawable = new PaintDrawable(Color.rgb(255,153,0));
                                        break;
                                    case 7:
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
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
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