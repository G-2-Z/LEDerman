package tools.remote.lederman;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    public ColorPickerFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ColorPickerFragment newInstance(OnColorChangedListener listener) {
        ColorPickerFragment fragment = new ColorPickerFragment();
        fragment.mListener = listener;
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.color_fragment_control_center, container, false);
        ColorPickerView ColorPickerView = (ColorPickerView) rootView.findViewById(R.id.color_picker);
        ColorPickerView.setOnColorChangedListener(mListener);
        return rootView;
    }
}