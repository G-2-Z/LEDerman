package tools.remote.lederman;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

/**
 * Created by Georg on 01.04.2017.
 */

public class ColorPickerFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private OnColorChangedListener mListener;
    private static int currentR;
    private static int currentG;
    private static int currentB;
    private static int bright;
    private static int sat;
    private static int currentColor;

    public ColorPickerFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ColorPickerFragment newInstance() {
        ColorPickerFragment fragment = new ColorPickerFragment();
        return fragment;
    }

    private static void calculateColor(){
        float tmp = ((float)ColorPickerFragment.bright)/255.0f;
        int r = ColorPickerFragment.currentR+ColorPickerFragment.sat;
        if (r > 255) r=255;
        r = (int)((float)r * tmp);
        int g = ColorPickerFragment.currentG+ColorPickerFragment.sat;
        if (g > 255) g=255;
        g = (int)((float)g * tmp);
        int b = ColorPickerFragment.currentB +ColorPickerFragment.sat;
        if (b > 255) b=255;
        b = (int)((float)b * tmp);
        ColorPickerFragment.currentColor = (r<<16)+(g<<8)+b;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.color_fragment_control_center, container, false);
        ColorPickerView ColorPickerView = (ColorPickerView) rootView.findViewById(R.id.color_picker);
        ColorPickerView.setOnColorChangedListener(mListener);
        ColorPickerView.setOnColorChangedListener(new OnColorChangedListener() {
            @Override
            public void colorChanged(int color) {


                ColorPickerFragment.currentR =  Color.red(color);
                ColorPickerFragment.currentG =  Color.green(color);
                ColorPickerFragment.currentB =  Color.blue(color);
                ColorPickerFragment.calculateColor();
                mListener.colorChanged(currentColor);
            }
        });
        SeekBar sat_seekbar = (SeekBar) rootView.findViewById(R.id.saturation_seekBar);
        SeekBar bright_seekbar = (SeekBar) rootView.findViewById(R.id.brightness_seekBar);
        sat_seekbar.setMax(255);
        bright_seekbar.setMax(255);
        sat_seekbar.setProgress(0);
        ColorPickerFragment.sat = 0;
        bright_seekbar.setProgress(255);
        ColorPickerFragment.bright = 255;
        sat_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ColorPickerFragment.sat =  progress;
                ColorPickerFragment.calculateColor();
                mListener.colorChanged(ColorPickerFragment.currentColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        bright_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ColorPickerFragment.bright =  progress;
                ColorPickerFragment.calculateColor();
                mListener.colorChanged(ColorPickerFragment.currentColor);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnColorChangedListener) {
            mListener = (OnColorChangedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnColorChangedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}