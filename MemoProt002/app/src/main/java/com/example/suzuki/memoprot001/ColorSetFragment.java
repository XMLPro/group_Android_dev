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

        Settings p = (Settings)getActivity().getApplication();
        int Thick = p.getThick();
        int Red = Color.red(p.getC());
        int Green = Color.green(p.getC());
        int Blue = Color.blue(p.getC());
        ((SeekBar) content.findViewById(R.id.seek)).setProgress(Thick);
        ((SeekBar) content.findViewById(R.id.seek_red)).setProgress(Red);
        ((SeekBar) content.findViewById(R.id.seek_green)).setProgress(Green);
        ((SeekBar) content.findViewById(R.id.seek_blue)).setProgress(Blue);

        builder.setMessage("設定")
                .setPositiveButton(getString(R.string.Noff), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int red = ((SeekBar) content.findViewById(R.id.seek_red)).getProgress();
                        int green = ((SeekBar) content.findViewById(R.id.seek_green)).getProgress();
                        int blue = ((SeekBar) content.findViewById(R.id.seek_blue)).getProgress();
                        int thick =  ((SeekBar) content.findViewById(R.id.seek)).getProgress();

                        Settings paintC = (Settings)getActivity().getApplication();
                        paintC.setC(Color.rgb(red, green, blue));

                        Settings T = (Settings) getActivity().getApplication();
                        T.setThick(thick);

                        int fill = 0;
                        Settings F = (Settings) getActivity().getApplication();
                        F.setFill(fill);

                        dismiss();
                    }
                })
                .setNeutralButton(getString(R.string.Non), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int red = ((SeekBar) content.findViewById(R.id.seek_red)).getProgress();
                        int green = ((SeekBar) content.findViewById(R.id.seek_green)).getProgress();
                        int blue = ((SeekBar) content.findViewById(R.id.seek_blue)).getProgress();
                        int thick =  ((SeekBar) content.findViewById(R.id.seek)).getProgress();
                        Settings paintC = (Settings) getActivity().getApplication();
                        paintC.setC(Color.rgb(red, green, blue));

                        Settings T = (Settings) getActivity().getApplication();
                        T.setThick(thick);

                        int fill = 1;
                        Settings F = (Settings)getActivity().getApplication();
                        F.setFill(fill);

                        dismiss();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        int red = ((SeekBar) content.findViewById(R.id.seek_red)).getProgress();
                        int green = ((SeekBar) content.findViewById(R.id.seek_green)).getProgress();
                        int blue = ((SeekBar) content.findViewById(R.id.seek_blue)).getProgress();
                        int thick =  ((SeekBar) content.findViewById(R.id.seek)).getProgress();
                        Settings paintC = (Settings) getActivity().getApplication();

                        Settings T = (Settings) getActivity().getApplication();
                        T.setThick(thick);

                        paintC.setC(Color.rgb(red, green, blue));
                        dismiss();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
