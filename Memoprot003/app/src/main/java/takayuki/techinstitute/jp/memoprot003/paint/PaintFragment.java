package takayuki.techinstitute.jp.memoprot003.paint;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AppKeyPair;

import java.io.File;
import java.io.InputStream;

import takayuki.techinstitute.jp.memoprot003.DUpLoad.UploadPicture;
import takayuki.techinstitute.jp.memoprot003.R;

public class PaintFragment extends Fragment implements Toolbar.OnMenuItemClickListener, ColorFragment.OnColorSetLisner {
    private DrawNoteView noteView;
    public static int iconflag = 0;
    private static final int REQUEST_GALLERY = 100;
    private static final int UPLOAD_GALLERY = 200;
    private static final String APP_KEY = "vov7o5v46gvyx92";
    private static final String APP_SECRET = "6goe1eupm6dqvqz";
    private DropboxAPI<AndroidAuthSession> mApi;
    private boolean logged_in = false;

    public PaintFragment() {
        // Required empty public constructor
    }

    public static PaintFragment newInstance() {
        PaintFragment fragment = new PaintFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeyPair);
        mApi = new DropboxAPI<>(session);
        View view = inflater.inflate(R.layout.fragment_paint,container,false);
        //setup();
        return view;//inflater.inflate(R.layout.fragment_paint, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iconflag == 0){
                    fab.setImageResource(R.drawable.pencil);
                    iconflag = 1;
                    return;
                }
                iconflag = 0;
                fab.setImageResource(R.drawable.eraser);
            }
        });
        setup();
    }

    @Override
    public void onResume() {
        super.onResume();
        AndroidAuthSession session = mApi.getSession();
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();
                //storeAuth(session);
                // Store it locally in our app for later use
                String accessToken = session.getOAuth2AccessToken();
                logged_in = true;

            } catch (IllegalStateException e) {
            }
        }
    }

    private void logOut() {
        mApi.getSession().unlink();
        logged_in = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        logOut();
        super.onDetach();
    }

    public void setup() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_paint);
        toolbar.setTitle("お絵かき");
        toolbar.inflateMenu(R.menu.paint_menu);
        toolbar.setOnMenuItemClickListener(this);
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

//    public boolean onMenuItemClick(MenuItem item) {
    @Override
    public void oncolorset(int color) {
        noteView = (DrawNoteView)(getView().findViewById(R.id.draw));
        noteView.setColor(color);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.save_image:
                noteView = (DrawNoteView)(getView().findViewById(R.id.draw));
                noteView.saveToFile();
                Toast.makeText(getContext(), "保存しました", Toast.LENGTH_SHORT).show();
                break;
            case R.id.read_image:
                i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_PICK);
                startActivityForResult(i, REQUEST_GALLERY);
                break;
            case R.id.share:
                if (!logged_in) {
                    mApi.getSession().startOAuth2Authentication(getActivity());
                }
                i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_PICK);
                startActivityForResult(i, UPLOAD_GALLERY);
                break;
            case R.id.action_delete:
                noteView = (DrawNoteView)(getActivity().findViewById(R.id.draw));
                noteView.clearDrawList();
                break;
            case R.id.color:
                ColorFragment fragment =ColorFragment.newInstant(this);
                fragment.show(getFragmentManager(),"show");
                break;
            case R.id.undo:
                noteView = (DrawNoteView)(getActivity().findViewById(R.id.draw));
                Log.d("undo","実行してる？");
                noteView.undo();
                break;
            case R.id.redo:
                noteView = (DrawNoteView)(getActivity().findViewById(R.id.draw));
                noteView.redo();
                break;
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY) {
            try {
                noteView = (DrawNoteView)(getView().findViewById(R.id.draw));
                ContentResolver cr = getContext().getContentResolver();
                InputStream in = cr.openInputStream(data.getData());
                Bitmap img = Bitmap.createScaledBitmap(BitmapFactory.decodeStream(in), noteView.getWidth(), noteView.getHeight(), false);
                in.close();
                noteView.readImage(img);
                Toast.makeText(getContext(), "画像を開きました", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == UPLOAD_GALLERY && resultCode == Activity.RESULT_OK) {
            ContentResolver cr = getContext().getContentResolver();
            String[] columns = {
                    MediaStore.Images.Media.DATA
            };
            Cursor c = cr.query(data.getData(), columns, null, null, null);
            int column_index = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            c.moveToFirst();
            String path = c.getString(column_index);
            UploadPicture upload = new UploadPicture(getContext(), mApi, "/MyPhoto", new File(path));
            upload.execute();
        }
    }
}
