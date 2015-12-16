package takayuki.techinstitute.jp.memoprot003;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Switch;

import takayuki.techinstitute.jp.memoprot003.Memo.MemoEditFragment;
import takayuki.techinstitute.jp.memoprot003.Memo.MemoFragment;
import takayuki.techinstitute.jp.memoprot003.Memo.MemoItem;
import takayuki.techinstitute.jp.memoprot003.paint.PaintFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,MemoFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PaintFragment.newInstance()).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch(id){
            case R.id.paint:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, PaintFragment.newInstance()).commit();
                break;
            case R.id.memo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MemoEditFragment.newInstance()).commit();
                break;
            case R.id.memoList:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, MemoFragment.newInstance()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onListFragmentInteraction(MemoItem item){

    }

}
