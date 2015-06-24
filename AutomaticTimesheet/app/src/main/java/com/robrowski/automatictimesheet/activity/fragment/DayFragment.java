package com.robrowski.automatictimesheet.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.robrowski.automatictimesheet.R;

import java.util.Date;

/**

 * A fragment representing a list of Items.
 */
public class DayFragment extends Fragment {
    private static final String TAG = "DayFragment", KEY_DATE = "KEY_DATE";
    private Date mDate;

    public static DayFragment newInstance(Date d) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATE, d);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DayFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = (Date) getArguments().getSerializable(KEY_DATE);

        // TODO Pull events from DB to populate list with


        // TODO make inferences about events and label "time at work" etc
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day_view, container, false);

        LinearLayout mLayout = (LinearLayout) rootView.findViewById(R.id.day_linear_layout);

        for (int i = 0; i < 24; i++){
            TextView tv = new TextView(getActivity());
            // TODO make a prettier line
            tv.setText(String.format("%2d AM    ------------------------------------------------", i));
            tv.setPadding(10, 30, 10, 30);
            mLayout.addView(tv);
        }
        return rootView;
    }





}
