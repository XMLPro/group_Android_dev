package com.example.suzuki.memoprot001;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suzuki on 2015/10/04.
 */
public class MemoListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private MemoDBHelper memos;
    private TextView itemID;
    private TextView text;
    private TextView date;
    private List<MemoListItem> itemList;


    public MemoListAdapter(Context context) {
        //ListView内でボタンを扱うために必要
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        setListItems();
    }

    private void setListItems() {
        itemList = new ArrayList<MemoListItem>();
        memos = new MemoDBHelper(this.context);
        SQLiteDatabase db = memos.getReadableDatabase();
        //memoDBテーブルから全てのタイトルのデータを持ったカーソルを取得
        Cursor cursor = db.rawQuery("SELECT id, title, date FROM memoDB", null);
        cursor.moveToFirst();
        Log.d("getCount: ", "cursor: " + cursor.getCount());
        for (int i = 0; i < cursor.getCount(); i++) {
            //取得したレコードの0番目のカラムを取得 -> 0番目のカラムはメモのタイトル
            Log.d("getString: ", "cursor: " + cursor.getString(0));
            MemoListItem item = new MemoListItem();
            item.setId(cursor.getString(cursor.getColumnIndex("id")));
            item.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            item.setDate(cursor.getString(cursor.getColumnIndex("date")));
            itemList.add(item);
            //次のレコードにカーソルを移す
            cursor.moveToNext();
        }
        db.close();
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.listitem, parent, false);
            text = (TextView) rowView.findViewById(R.id.list_item);
            itemID = (TextView) rowView.findViewById(R.id.list_item_id);
            date = (TextView) rowView.findViewById(R.id.date);

            text.setText(itemList.get(position).getTitle());
            itemID.setText("" + itemList.get(position).getId());
            date.setText(itemList.get(position).getDate());

            rowView.setTag(text);
            rowView.setTag(itemID);
            rowView.setTag(date);
        } else {
            text = (TextView) rowView.getTag();
            itemID = (TextView) rowView.getTag();
            date = (TextView) rowView.getTag();
        }

        Button btn = (Button) rowView.findViewById(R.id.button);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("btn.list_item()", "ポジション: " + position);
                MemoDBHelper memos = new MemoDBHelper(context);
                memos.deleteRecode(String.valueOf(itemList.get(position).getId()));
                setListItems();
                notifyDataSetChanged();
            }
        });

        return rowView;
    }

}
