package com.example.c0114443.memoapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {

    SimpleAdapter mAdapter = null;

    List<Map<String, String>> mList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = new ArrayList<Map<String, String>>();

        mAdapter = new SimpleAdapter(
                this,
                mList,
                android.R.layout.simple_list_item_2,
                new String[] {"title","content"},
                new int[] {android.R.id.text1, android.R.id.text2}
                );

        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(mAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(
                    AdapterView<?> parent, View view, int pos, long id) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("NAME", mList.get(pos).get("filename"));
                intent.putExtra("TITLE", mList.get(pos).get("title"));
                intent.putExtra("CONTENT", mList.get(pos).get("content"));
            }
        });

        registerForContextMenu(list);
    }

    @Override
    protected void onResume(){
        super.onResume();

        mList.clear();

        String savePath = this.getFilesDir().getPath().toString();
        File[] files = new File(savePath).listFiles();

        Arrays.sort(files, Collections.reverseOrder());

        for(int i=0; i<files.length; i++){
            String fileName = files[i].getName();
            if(files[i].isFile() && fileName.endsWith(".txt")){
                String title = null;
                String content = null;

                try{
                    InputStream in = this.openFileInput(fileName);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));

                    title = reader.readLine();

                    char[] buf = new char[(int)files[i].length()];
                    int num = reader.read(buf);
                    content = new String(buf, 0, num);

                    reader.close();
                    in.close();
                } catch(Exception e){
                    Toast.makeText(this,"File read error!", Toast.LENGTH_LONG).show();
                }

                Map<String,String>map = new HashMap<String, String>();
                map.put("filename",fileName);
                map.put("title",title);
                map.put("content",content);
                mList.add(map);
            }
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo info){
        super.onCreateContextMenu(menu, view, info);
        getMenuInflater().inflate(R.menu.main_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.context_del:

                if(this.deleteFile(mList.get(info.position).get("filename"))){
                    Toast.makeText(this, R.string.msg_del, Toast.LENGTH_SHORT).show();
                }

                mList.remove(info.position);

                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }

        return false;
    }

}
