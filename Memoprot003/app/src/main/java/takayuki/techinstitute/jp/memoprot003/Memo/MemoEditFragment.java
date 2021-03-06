package takayuki.techinstitute.jp.memoprot003.Memo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import takayuki.techinstitute.jp.memoprot003.R;


public class MemoEditFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private boolean memoOverride = false;

    public MemoEditFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MemoEditFragment newInstance(String memo, boolean memoOverride, String memoID) {
        MemoEditFragment fragment = new MemoEditFragment();

        Bundle bundle = new Bundle();
        if(memo !=null){
            bundle.putString("memo",memo);
        }
        bundle.putBoolean("memoOverride",memoOverride);
        bundle.putString("id",memoID);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        setup();
        return inflater.inflate(R.layout.fragment_memo_edit, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String memo = getArguments().getString("memo",null);
        if(memo != null){
            ((EditText)getActivity().findViewById(R.id.editText)).setText(memo);
        }
        setup();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setup() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_edit);
        toolbar.setTitle("メモ帳");
        toolbar.inflateMenu(R.menu.edit_menu);
        toolbar.setOnMenuItemClickListener(this);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId() == R.id.save_data){
            saveMemo();
        }
        return true;
    }

    private void saveMemo(){
        memoOverride = getArguments().getBoolean("memoOverride",false);
        MemoDBHelper memos = new MemoDBHelper(getContext());
        SQLiteDatabase db = memos.getWritableDatabase();
        ContentValues values = new ContentValues();
        if(memoOverride){
            EditText ut = (EditText) getActivity().findViewById(R.id.editText);
            String upContent = ut.getText().toString();
            values.put("memo",upContent);
            db.update("memoDB", values, "id = ?", new String[]{getArguments().getString("id")});
            memos.close();
            Toast.makeText(getActivity(),"上書きしました",Toast.LENGTH_SHORT).show();
            return;
        }
        EditText et = (EditText) getActivity().findViewById(R.id.editText);
        String content = et.getText().toString();
        Date mDate = new Date();
        SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String title = fileNameDate.format(mDate);
        values.put("title",title);
        values.put("memo",content);
        long id = db.insert("memoDB",null,values);
        memos.close();
        memoOverride = true;
        Toast.makeText(getActivity(),"保存しました",Toast.LENGTH_SHORT).show();
    }

}
