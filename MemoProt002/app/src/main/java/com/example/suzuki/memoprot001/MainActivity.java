package com.example.suzuki.memoprot001;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Selection;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    public int color = 0;
    private CharSequence mTile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mTile = getTitle();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTile);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //メニューとメモ一覧の
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu, menu);

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
        Intent i;
        Bundle b;

        //設定ボタンが押されたとき設定画面を呼ぶ
        //変数colorの値も渡す
        if (id == R.id.action_setting) {
            i = new Intent(MainActivity.this, Setting.class);
            b = new Bundle();
            b.putInt("Color", color);
            i.putExtras(b);
            startActivityForResult(i, color);
            return true;
        }
        //共有ボタンでお絵かき画面を呼ぶ
        if (id == R.id.action_share) {
            i = new Intent(MainActivity.this, DrawNoteK.class);
            startActivity(i);
            return true;
        }
        //メニューとメモ一覧の
        EditText et = (EditText) findViewById(R.id.editText);
        switch (item.getItemId()) {
            case R.id.menu_save:
                saveMemo();
                break;
            case R.id.menu_open:
                i = new Intent(this, MemoList.class);
                b = new Bundle();
                b.putInt("Color", color);
                i.putExtras(b);
                startActivityForResult(i, color);
                break;
            case R.id.menu_new:
                et.setText("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //startActivityで呼んだ画面から自動で画面を受け取る関数onActivityResult
    //どの画面を呼んだ後もここで受け取るので全画面に対応できるように頑張って書くこと
    //onActivityResultはstartActivity()で呼び出した時の第二引数、第一引数、向こうのintentの順番で引数を受け取る
    //第二引数で数字を渡すのが普通っぽいのでここではint Numberで第二引数を受け取るように調整
    protected void onActivityResult(int Number, int requestCode, Intent intent) {
        super.onActivityResult(Number, requestCode, intent);
        Bundle bundle = intent.getExtras();

        if (bundle.getInt("color") >= 10) {
            //背景色設定
            PaintDrawable paintDrawable;
            switch (bundle.getInt("color")) {
                case 11:
                    paintDrawable = new PaintDrawable(android.graphics.Color.DKGRAY);
                    break;
                case 12://red
                    paintDrawable = new PaintDrawable(Color.rgb(255, 51, 51));
                    break;
                case 13://blue
                    paintDrawable = new PaintDrawable(Color.rgb(51,204,255));
                    break;
                case 14://green
                    paintDrawable = new PaintDrawable(Color.rgb(0,255,102));
                    break;
                case 15://pink
                    paintDrawable = new PaintDrawable(Color.rgb(255,153,204));
                    break;
                case 16://orange
                    paintDrawable = new PaintDrawable(Color.rgb(255,153,0));
                    break;
                case 17://purple
                    paintDrawable = new PaintDrawable(Color.rgb(255,102,204));
                    break;
                default:
                    paintDrawable = new PaintDrawable(android.graphics.Color.WHITE);
            }
            getWindow().setBackgroundDrawable(paintDrawable);
            color = bundle.getInt("color");
            color -= 10;
        }

        if (bundle.getString("text") != null) {
            EditText et = (EditText) findViewById(R.id.editText);
            et.setText(intent.getStringExtra("text"));
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

    public void onSectionAttached(int number) {
        switch (number){
            case 1:
                mTile = getString(R.string.action_add);
                EditText et = (EditText) findViewById(R.id.editText);
                et.setText("");
                break;
            case 2:
                mTile = getString(R.string.folder);
                Intent i;
                Bundle b;
                i = new Intent(this, MemoList.class);
                b = new Bundle();
                b.putInt("Color", color);
                i.putExtras(b);
                startActivityForResult(i, color);
                break;
            case 3:
                mTile = getString(R.string.view);
                break;
        }
    }

    @Override
    public void onStop() {
        EditText et = (EditText) findViewById(R.id.editText);
        SharedPreferences pref = getSharedPreferences("MemoPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("memo", et.getText().toString());
        editor.putInt("cursor", Selection.getSelectionStart(et.getText()));
        editor.commit();
        super.onStop();
    }

    //メモ保存(平原)
    void saveMemo() {
        EditText et = (EditText) this.findViewById(R.id.editText);
        String title;
        String memo = et.getText().toString();

        if (memo.trim().length() > 0) {
            if (memo.indexOf("\n") == -1) {
                title = memo.substring(0, Math.min(memo.length(), 20));
            } else {
                title = memo.substring(0, Math.min(memo.indexOf("\n"), 20));
            }
            String ts = DateFormat.getDateTimeInstance().format(new Date());
            MemoDBHelper memos = new MemoDBHelper(this);
            SQLiteDatabase db = memos.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("title", title + "\n" + ts);
            values.put("memo", memo);
            db.insertOrThrow("memoDB", null, values);
            memos.close();
        }
        Toast.makeText(this, getString(R.string.Save), Toast.LENGTH_SHORT).show();
    }

//端末側の戻るボタンが押されたときの処理
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    // ダイアログ表示など特定の処理を行いたい場合はここに記述
                    finish();
            }
        }
        //return trueで戻るボタンが無効になる
        return super.dispatchKeyEvent(event);
    }
}
