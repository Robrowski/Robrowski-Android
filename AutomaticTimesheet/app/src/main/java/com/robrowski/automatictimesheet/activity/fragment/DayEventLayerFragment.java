package com.robrowski.automatictimesheet.activity.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robrowski.automatictimesheet.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayEventLayerFragment extends Fragment {


    public DayEventLayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day_event_layer, container, false);
    }


}
