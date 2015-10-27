package com.example.suzuki.memoprot001;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

/**
 * Created by Admin on 2015/10/26.
 */
public class ColorSetFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View content = inflater.inflate(R.layout.dialog_setting, null);

        builder.setView(content);

        builder.setMessage("設定")
                .setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        int red = ((SeekBar)content.findViewById(R.id.seek_red)).getProgress();
                        int green =((SeekBar)content.findViewById(R.id.seek_green)).getProgress();
                        int blue = ((SeekBar)content.findViewById(R.id.seek_blue)).getProgress();
                        Settings paintC = (Settings)getActivity().getApplication();
                        paintC.setC(Color.rgb(red,green,blue));
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
