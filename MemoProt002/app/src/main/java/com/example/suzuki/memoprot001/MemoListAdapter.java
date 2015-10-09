package com.example.suzuki.memoprot001;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by suzuki on 2015/10/04.
 */
public class MemoListAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private TextView text;

    //    private List
    public MemoListAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
        super(context, resource, textViewResourceId, objects);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            rowView = inflater.inflate(R.layout.listitem, parent, false);
            text = (TextView) rowView.findViewById(R.id.list_item);
            text.setText(getItem(position).toString());
            rowView.setTag(text);
        } else {
            text = (TextView) rowView.getTag();
        }

        Button btn = (Button) rowView.findViewById(R.id.button);
        btn.setTag(position);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("btn.list_item()", "ポジション: " + position);
            }
        });

        return rowView;
    }
}
