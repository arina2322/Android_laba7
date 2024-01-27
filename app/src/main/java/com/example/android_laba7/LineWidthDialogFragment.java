package com.example.android_laba7;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class LineWidthDialogFragment extends DialogFragment {
    private ImageView widthImageView;
    private OnSeekBarChangeListener lineWidthChanged
            = new OnSeekBarChangeListener() {
        final Bitmap bitmap = Bitmap.createBitmap(
                400,
                100,
                Bitmap.Config.ARGB_8888
        );
        final Canvas canvas = new Canvas(bitmap);

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Paint paint = new Paint();
            paint.setColor(getDoodleFragment().getDoodleView().getDrawingColor());
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(progress);

            bitmap.eraseColor(
                    getResources().getColor(
                            android.R.color.transparent,
                            getContext().getTheme()
                    )
            );
            canvas.drawLine(30, 50, 370, 50, paint);
            widthImageView.setImageBitmap(bitmap);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View lineWidthDialogView = getActivity().getLayoutInflater().inflate(
                R.layout.fragment_line_width,
                null
        );
        builder.setView(lineWidthDialogView);

        builder.setTitle(R.string.title_line_width_dialog);

        widthImageView = lineWidthDialogView.findViewById(R.id.width_image_view);

        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        final SeekBar widthSeekBar = lineWidthDialogView.findViewById(R.id.width_seek_bar);
        widthSeekBar.setOnSeekBarChangeListener(lineWidthChanged);
        widthSeekBar.setProgress(doodleView.getLineWidth());

        builder.setPositiveButton(
                R.string.button_set_line_width,
                (dialogInterface, i) -> doodleView.setLineWidth(widthSeekBar.getProgress())
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
