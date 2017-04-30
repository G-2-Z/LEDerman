package tools.remote.lederman;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;



public class ArrowKeyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public ArrowKeyFragment() {
        // Required empty public constructor
    }

    public static ArrowKeyFragment newInstance() {
        ArrowKeyFragment fragment = new ArrowKeyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_arrow_key, container, false);
        final Button up_button = (Button) rootView.findViewById(R.id.button_up);
        final Button down_button = (Button) rootView.findViewById(R.id.button_down);
        final Button left_button = (Button) rootView.findViewById(R.id.button_left);
        final Button right_button = (Button) rootView.findViewById(R.id.button_right);
        up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPM2ConnectionManager.getInstance().sendKey(72);
            }
        });
        down_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPM2ConnectionManager.getInstance().sendKey(80);
            }
        });
        left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPM2ConnectionManager.getInstance().sendKey(75);
            }
        });
        right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TPM2ConnectionManager.getInstance().sendKey(77);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }
}