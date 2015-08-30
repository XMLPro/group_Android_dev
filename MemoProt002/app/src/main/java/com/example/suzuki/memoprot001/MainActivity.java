package com.example.suzuki.memoprot001;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private EditText editText;
    private Button save_btn, delete_btn;
    private String fileName = "1";
    public int color = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        editText = (EditText) findViewById(R.id.editText);
        //ファイル読み込み関数SetTextを呼び出し
        SetText();

        save_btn = (Button) findViewById(R.id.save_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);

        save_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == save_btn) {
            try {
                onPause();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == delete_btn) {
            editText.setText("");
        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                fileName = "1";
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                fileName = "2";
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                fileName = "3";
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                fileName = "4";
                break;
        }
        SetText();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    //アクションバー
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //設定ボタンが押されたとき設定画面を呼ぶ
        //変数colorの値も渡す
        if (id == R.id.action_setting) {
            Intent intent = new Intent(MainActivity.this, Setting.class);
            Bundle bundle = new Bundle();
            bundle.putInt("Color", color);
            intent.putExtras(bundle);
            startActivityForResult(intent, color);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //設定ボタンから画面を受け取る
    protected void onActivityResult(int requestCode, int Color, Intent intent) {
        super.onActivityResult(requestCode, Color, intent);
        Bundle bundle = intent.getExtras();
        //背景色設定
        PaintDrawable paintDrawable;
        switch (bundle.getInt("color")) {
            case 1:
                paintDrawable = new PaintDrawable(android.graphics.Color.DKGRAY);
                break;
            case 2:
                paintDrawable = new PaintDrawable(android.graphics.Color.RED);
                break;
            case 3:
                paintDrawable = new PaintDrawable(android.graphics.Color.BLUE);
                break;
            case 4:
                paintDrawable = new PaintDrawable(android.graphics.Color.GREEN);
                break;
            default:
                paintDrawable = new PaintDrawable(android.graphics.Color.WHITE);
        }
        getWindow().setBackgroundDrawable(paintDrawable);
        color = bundle.getInt("color");
    }

    //画面が閉じられるか遷移する時に保存する
    @Override
    protected void onPause() {
        super.onPause();

        // タイトル、内容を取得
        String content = editText.getText().toString();
        // 保存
        OutputStream out = null;
        PrintWriter writer = null;
        try {
            out = this.openFileOutput(fileName, MODE_PRIVATE);
            writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            // 内容書き込み
            writer.print(content);
            writer.close();
            Toast.makeText(this, getString(R.string.Save), Toast.LENGTH_SHORT).show();
            out.close();
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.SaveError), Toast.LENGTH_LONG).show();
        }
    }

    //ファイルを読み込みテキストにセットするクラスSetText
    protected void SetText() {
        try {
            FileInputStream fileInputStream;
            fileInputStream = openFileInput(fileName);
            byte[] readBytes = new byte[fileInputStream.available()];
            fileInputStream.read(readBytes);
            String readString = new String(readBytes);
            editText.setText(readString);
        } catch (FileNotFoundException e) {
            Toast.makeText(this, getString(R.string.FileError), Toast.LENGTH_SHORT).show();
            editText.setText("");
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.Error), Toast.LENGTH_SHORT).show();
            editText.setText("");
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
