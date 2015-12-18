package takayuki.techinstitute.jp.memoprot003.Memo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import takayuki.techinstitute.jp.memoprot003.MainActivity;
import takayuki.techinstitute.jp.memoprot003.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class MemoFragment extends Fragment {

    private int mColumnCount = 1;
    private MemoDBHelper memos;
    private MemoViewAdapter adapter;
    private OnListFragmentInteractionListener mListener;
    ArrayList<MemoItem> memoItems;
    MainActivity m = new MainActivity();


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MemoFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MemoFragment newInstance() {
        MemoFragment fragment = new MemoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        memos = new MemoDBHelper(getContext());
        SQLiteDatabase db = memos.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from memoDB", null);
        cursor.moveToFirst();
        memoItems = new ArrayList<>();
        for (int i = 0; i < cursor.getCount(); i++) {
            memoItems.add(new MemoItem(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("memo"))
            ));
            cursor.moveToNext();
        }
        adapter = new MemoViewAdapter(memoItems, null);
        return view;
    }

    public void onStart(){
        super.onStart();

        Button button = (Button)getActivity().findViewById(R.id.notification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m.sendNotification();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    public void setup() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_memo);
        toolbar.setTitle("メモ帳");
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) (getView().findViewById(R.id.list));
        recyclerView.setAdapter(adapter);

        Context context = getView().getContext();
        if(context != null) {
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        setup();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(MemoItem item);
    }
}
