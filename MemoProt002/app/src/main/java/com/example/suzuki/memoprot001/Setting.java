package com.example.suzuki.memoprot001;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

public class Setting extends ActionBarActivity {
    protected Button spinnerButtonBackGround;
    protected Button spinnerButtonFont;
    protected Button spinnerButtonLine;

    //
    // �X�s�i�[���{�^���p�̃`�F�b�N�ϐ��錾
    protected int groundchecking;
    protected int groundchecked;
    protected int fontchecking;
    protected int fontchecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //�X�s�i�[���{�^��[BackGround][Font]�ݒu
        spinnerButtonBackGround = (Button) findViewById(R.id.background);
        spinnerButtonBackGround.setText(R.string.background);
        //�G�����Ƃ��̓����OnclickListener�ŏ���
        spinnerButtonBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] itemBackGround = {getString(R.string.white), getString(R.string.black), getString(R.string.green)};
                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.setbackground)
                        .setSingleChoiceItems(itemBackGround, groundchecking, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                //�A�C�e����I�������炱���ɓ���
                                groundchecking = item;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //OK���������炱���ɓ���
                                groundchecked = groundchecking;
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });

        spinnerButtonFont = (Button) findViewById(R.id.font);
        spinnerButtonFont.setText(R.string.font);
        spinnerButtonFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] itemFont = {getString(R.string.gothic), getString(R.string.MSgothic), getString(R.string.mintyo)};
                new AlertDialog.Builder(Setting.this)
                        .setTitle(R.string.setfont)
                        .setSingleChoiceItems(itemFont, fontchecking, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                fontchecking = item;
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                fontchecked = fontchecking;
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });

        spinnerButtonLine = (Button) findViewById(R.id.Line);
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

        //�߂���������Ƃ��ɉ�ʂ�߂�
        Button back = (Button) findViewById(R.id.backbutton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}