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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;


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
    private String filename;
    public int color = 0;
    private SimpleDateFormat sdf;

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
        save_btn = (Button) findViewById(R.id.save_btn);
        delete_btn = (Button) findViewById(R.id.delete_btn);

        save_btn.setOnClickListener(this);
        delete_btn.setOnClickListener(this);

        filename = getResources().getString(R.string.file_name);
        try {
            FileInputStream fis = openFileInput(filename);
            byte[] readBytes = new byte[fis.available()];
            fis.read(readBytes);
            editText.setText(new String(readBytes));
            fis.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void onClick(View v) {
        if (v == save_btn) {
            //�ۑ�
            try {
//                FileOutputStream fos = openFileOutput(filename, Context.MODE_PRIVATE);
//                fos.write(editText.getText().toString().getBytes());
//                fos.close();
//                OutputStream out;
//                    out = openFileOutput("memotest.txt" , MODE_PRIVATE | MODE_APPEND);
//                    PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
            onPause();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (v == delete_btn) {
            //�j��
            editText.setText("");
            deleteFile(filename);
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
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }
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

        //設定ボタンが押されたとき画面を呼ぶ
        if (id == R.id.action_setting) {
            Intent intent = new Intent(MainActivity.this, Setting.class);
            Bundle bundle = new Bundle();

            bundle.putInt("Color", color);
            intent.putExtras(bundle);
            startActivityForResult(intent, color);
            return true;
        }

        if (id == R.id.action_share) {
            Intent intent = new Intent(MainActivity.this, DrawNoteK.class);

            startActivity(intent);
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

    //画面が強制的に閉じられる時に保存する
    @Override
    protected void onPause() {
        super.onPause();

        String memoTest = "test";
        // タイトル、内容を取得
        String title = "memoTest";
        String content = editText.getText().toString();
        // ファイル名を生成  ファイル名 ： yyyyMMdd_HHmmssSSS.txt
        // （既に保存されているファイルは、そのままのファイル名とする）

        // 保存
        OutputStream out = null;
        PrintWriter writer = null;
        try {
            out = this.openFileOutput(memoTest, MODE_PRIVATE);
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

        public PlaceholderFragment() {
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
