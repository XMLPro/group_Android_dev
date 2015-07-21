package com.example.suzuki.memoprot001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Setting extends ActionBarActivity {
    // �X�s�i�[���{�^���p�̃`�F�b�N�ϐ��錾
    protected int colorset;
    protected int color;
    protected int fontset;
    protected int font;

    public PaintDrawable paintDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //�J���F�ݒ�

        Intent intent = getIntent();
        color = intent.getIntExtra("Color",0);
        switch(color) {
            case 1:
                paintDrawable = new PaintDrawable(Color.DKGRAY);
                break;
            case 2:
                paintDrawable = new PaintDrawable(Color.RED);
                break;
            case 3:
                paintDrawable = new PaintDrawable(Color.BLUE);
                break;
            case 4:
                paintDrawable = new PaintDrawable(Color.GREEN);
                break;
            default:
                paintDrawable = new PaintDrawable(Color.WHITE);
        }
        getWindow().setBackgroundDrawable(paintDrawable);

        Button spinnerButtonBackGround  = (Button) findViewById(R.id.background);
        spinnerButtonBackGround.setText(R.string.background);
        //�G�����Ƃ��̓����OnclickListener�ŏ���
        spinnerButtonBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] itemBackGround = {getString(R.string.white), getString(R.string.black),
                        getString(R.string.red), getString(R.string.blue), getString(R.string.green)};
                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.setbackground)
                        .setSingleChoiceItems(itemBackGround, colorset, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                //�A�C�e����I�������炱���ɓ���
                                colorset = item;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //OK���������炱���ɓ���
                                color = colorset;
                                //�F�ݒ�̔��f 1:�� 2:�� 3:�� 4:�� �f�t�H���g�͔�
                                switch(color) {
                                    case 1:
                                        paintDrawable = new PaintDrawable(Color.DKGRAY);
                                        break;
                                    case 2:
                                        paintDrawable = new PaintDrawable(Color.RED);
                                        break;
                                    case 3:
                                        paintDrawable = new PaintDrawable(Color.BLUE);
                                        break;
                                    case 4:
                                        paintDrawable = new PaintDrawable(Color.GREEN);
                                        break;
                                    default:
                                        paintDrawable = new PaintDrawable(Color.WHITE);
                                }
                                getWindow().setBackgroundDrawable(paintDrawable);
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
                        .setSingleChoiceItems(itemFont, fontset, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                fontset = item;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                font = fontset;
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

        //�߂���������Ƃ��ɔw�i�F��n���ĉ�ʂ�߂�
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
}