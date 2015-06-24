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
                // [削除] 選択処理
                // ファイル削除
                if (!mFileName.isEmpty()) {
                    this.deleteFile(mFileName);
                }
                // 保存せずに、画面を閉じる
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
        // ここでファイル保存処理を行う
        // [削除] で画面を閉じるときは、保存しない
        if (mNotSave) {
            return;
        }

        // タイトル、内容を取得
        EditText eTxtTitle = (EditText) findViewById(R.id.eTxtTitle);
        EditText eTxtContent = (EditText) findViewById(R.id.eTxtContent);
        String title = eTxtTitle.getText().toString();
        String content = eTxtContent.getText().toString();

        // タイトル、内容が空白の場合、保存しない
        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, R.string.msg_destruction, Toast.LENGTH_SHORT).show();
            return;
        }

        // ファイル名を生成  ファイル名 ： yyyyMMdd_HHmmssSSS.txt
        // （既に保存されているファイルは、そのままのファイル名とする）
        if (mFileName.isEmpty()) {
            Date date = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS", Locale.JAPAN);
            mFileName = sdf.format(date) + ".txt";
        }

        // 保存
        OutputStream out = null;
        PrintWriter writer = null;
        try {
            out = this.openFileOutput(mFileName, Context.MODE_PRIVATE);
            writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            // タイトル書き込み
            writer.println(title);
            // 内容書き込み
            writer.print(content);
            writer.close();
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, "File save error!", Toast.LENGTH_LONG).show();
        }
    }

    //メニュー系　諦めて放置
//    public boolean OnMenuItemSelected(int featureId,MenuItem item) {
//        super.onMenuItemSelected(featureId, item);
//
//        switch(item.getItemId()) {
//            case R.id.action_add:
//              case 10:
//              Intentを使った画面遷移処理
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