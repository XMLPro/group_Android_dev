package com.example.admin_2.memozaki;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends ActionBarActivity {
    String mFileName;
    boolean mNotSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, 1, 0, "new");
        menu.add(0, 2, 0, "delete");
        MenuItem item = menu.findItem(1);
        MenuItem del = menu.findItem(2);
        item.setChecked(true);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi) {
        switch (mi.getItemId()) {
            case 1:
                Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            case 2:
                // [�폜] �I������
                // �t�@�C���폜
                if (!mFileName.isEmpty()) {
                    this.deleteFile(mFileName);
                }
                // �ۑ������ɁA��ʂ����
                mNotSave = true;
                this.finish();
                break;
            default:
                break;
        }
        return true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        // �����Ńt�@�C���ۑ��������s��
        // [�폜] �ŉ�ʂ����Ƃ��́A�ۑ����Ȃ�
        if (mNotSave) {
            return;
        }

        // �^�C�g���A���e���擾
        EditText eTxtTitle = (EditText) findViewById(R.id.eTxtTitle);
        EditText eTxtContent = (EditText) findViewById(R.id.eTxtContent);
        String title = eTxtTitle.getText().toString();
        String content = eTxtContent.getText().toString();

        // �^�C�g���A���e���󔒂̏ꍇ�A�ۑ����Ȃ�
        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, R.string.msg_destruction, Toast.LENGTH_SHORT).show();
            return;
        }

        // �t�@�C�����𐶐�  �t�@�C���� �F yyyyMMdd_HHmmssSSS.txt
        // �i���ɕۑ�����Ă���t�@�C���́A���̂܂܂̃t�@�C�����Ƃ���j
        if (mFileName.isEmpty()) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.JAPAN);
            mFileName = sdf.format(date) + ".txt";
        }

        // �ۑ�
        OutputStream out = null;
        PrintWriter writer = null;
        try {
            out = this.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            // �^�C�g����������
            writer.println(title);
            // ���e��������
            writer.print(content);
            writer.close();
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, "File save error!", Toast.LENGTH_LONG).show();
        }
    }

    //���j���[�n�@���߂ĕ��u
//    public boolean OnMenuItemSelected(int featureId,MenuItem item) {
//        super.onMenuItemSelected(featureId, item);
//
//        switch(item.getItemId()) {
//            case R.id.action_add:
//              case 10:
//              Intent���g������ʑJ�ڏ���
//                Intent intent = new Intent(MainActivity.this, EditActivity.class);
//                startActivity(intent);
//                Toast.makeText(this, "Congratulations!", Toast.LENGTH_SHORT).show();
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
}