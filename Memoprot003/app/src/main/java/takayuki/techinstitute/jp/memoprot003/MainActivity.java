package takayuki.techinstitute.jp.memoprot003;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import takayuki.techinstitute.jp.memoprot003.Memo.MemoEditFragment;
import takayuki.techinstitute.jp.memoprot003.Memo.MemoFragment;
import takayuki.techinstitute.jp.memoprot003.Memo.MemoItem;
import takayuki.techinstitute.jp.memoprot003.paint.PaintFragment;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MemoFragment.OnListFragmentInteractionListener {

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

    public void sendNotification(){
        Context context = this;
        String message = "a";

        Intent notificationIntent = new Intent(this, MemoFragment.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, 0, notificationIntent, 0);

        Notification.Builder builder = new Notification.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("a");
        builder.setContentTitle("test");
        builder.setContentText(message);
        builder.setContentInfo("Memo");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(android.R.drawable.ic_menu_info_details);
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notification);
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
        switch (id) {
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
    public void onListFragmentInteraction(MemoItem item) {

    }

}
