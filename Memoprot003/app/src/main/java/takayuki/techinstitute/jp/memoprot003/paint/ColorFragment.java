package takayuki.techinstitute.jp.memoprot003.paint;

/**
 * Created by Owner on 2015/12/16.
 */

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import takayuki.techinstitute.jp.memoprot003.R;

public class ColorFragment extends DialogFragment {
    private View v;
    int red;
    int green;
    int blue;
    OnColorSetLisner setLisner;

    interface OnColorSetLisner {
        void oncolorset(int color);
    }

    public static ColorFragment newInstant(Fragment fragment) {
        ColorFragment dialog = new ColorFragment();
        dialog.setTargetFragment(fragment, 0);
        return dialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.dialog_setting, null);
        builder.setView(content);
        final SeekBar seek_red = (SeekBar) content.findViewById(R.id.seek_red);
        SeekBar seek_green = (SeekBar) content.findViewById(R.id.seek_green);
        final SeekBar seek_blue = (SeekBar) content.findViewById(R.id.seek_blue);
        red = seek_red.getProgress();
        green = seek_green.getProgress();
        blue = seek_blue.getProgress();
        v = content.findViewById(R.id.color);
        seek_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = seekBar.getProgress();
                v.setBackgroundColor(Color.rgb(red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green = seekBar.getProgress();
                v.setBackgroundColor(Color.rgb(red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seek_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue = seekBar.getProgress();
                v.setBackgroundColor(Color.rgb(red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        v.setBackgroundColor(Color.rgb(red, green, blue));
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                setLisner = (OnColorSetLisner) getTargetFragment();
                setLisner.oncolorset(Color.rgb(red, green, blue));
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
