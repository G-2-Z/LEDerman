package tools.remote.lederman;

import android.content.Context;
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
    public static ColorPickerFragment newInstance() {
        ColorPickerFragment fragment = new ColorPickerFragment();
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