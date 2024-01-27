package com.example.android_laba7;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


public class ColorDialogFragment extends DialogFragment {
    private SeekBar alphaSeekBar;
    private SeekBar redSeekBar;
    private SeekBar greenSeekBar;
    private SeekBar blueSeekBar;
    private View colorView;
    private int color;
    private final OnSeekBarChangeListener colorChangedListener
            = new OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                color = Color.argb(
                        alphaSeekBar.getProgress(),
                        redSeekBar.getProgress(),
                        greenSeekBar.getProgress(),
                        blueSeekBar.getProgress()
                );
            }
            colorView.setBackgroundColor(color);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @Override
    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_color,
                null
        );
        builder.setView(colorDialogView);

        builder.setTitle(R.string.title_color_dialog);

        alphaSeekBar = colorDialogView.findViewById(R.id.color_seekbar_alpha);
        redSeekBar = colorDialogView.findViewById(R.id.color_seekbar_red);
        greenSeekBar = colorDialogView.findViewById(R.id.color_seekbar_green);
        blueSeekBar = colorDialogView.findViewById(R.id.color_seekbar_blue);
        colorView = colorDialogView.findViewById(R.id.color_view);

        alphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        redSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        greenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        blueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        color = doodleView.getDrawingColor();
        alphaSeekBar.setProgress(Color.alpha(color));
        redSeekBar.setProgress(Color.red(color));
        greenSeekBar.setProgress(Color.blue(color));
        blueSeekBar.setProgress(Color.green(color));

        builder.setPositiveButton(
                R.string.button_set_color,
                (dialogInterface, i) -> doodleView.setDrawingColor(color)
        );

        return builder.create();
    }

    private MainActivityFragment getDoodleFragment() {
        return (MainActivityFragment) getFragmentManager()
                .findFragmentById(R.id.doodle_fragment);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        MainActivityFragment fragment = getDoodleFragment();
        if (fragment != null) {
            fragment.setDialogOnScreen(true);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null) {
            fragment.setDialogOnScreen(false);
        }
    }
}
