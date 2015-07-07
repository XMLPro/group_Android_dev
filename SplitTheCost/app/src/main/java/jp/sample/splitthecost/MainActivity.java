package jp.sample.splitthecost;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //オブジェクト取得
                EditText etxtNum    = (EditText) findViewById(R.id.eTxtNum);
                EditText etxtMoney  = (EditText) findViewById(R.id.eTxtMoney);
                TextView txtResult  = (TextView) findViewById(R.id.txtResult);

                //入力内容を取得
                String strNum   = etxtNum.getText().toString();
                String strMoney = etxtMoney.getText().toString();

                //数値に変換
                int num      = Integer.parseInt(strNum);
                int money    = Integer.parseInt(strMoney);

                //割り勘計算
                int result = money / num;

                //結果表示用テキストに設定
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
}
