package com.robrowski.automatictimesheet.activity.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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


    private RelativeLayout mDayInferencesLayout, mDayEventsLayout;

    private static final int TIME_LABEL_TOP_PADDING = 80,
            TIME_LABEL_BOT_PADDING = TIME_LABEL_TOP_PADDING,
            TEXT_VIEW_HEIGHT = 57, // Measured on Galazy S5
            HOURS_IN_DAY = 24,
            MINUTES_IN_DAY = 60,
            DAY_HEIGHT_PIXELS = (TIME_LABEL_BOT_PADDING + TIME_LABEL_TOP_PADDING + TEXT_VIEW_HEIGHT)
                    * HOURS_IN_DAY;

    private static final float PIXELS_PER_MINUTE = DAY_HEIGHT_PIXELS / ( HOURS_IN_DAY * MINUTES_IN_DAY);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_day_view, container, false);

        // Get layouts for filling with content later
        mDayEventsLayout = (RelativeLayout) rootView.findViewById(R.id.day_events_layout);
        mDayInferencesLayout = (RelativeLayout) rootView.findViewById(R.id.day_inferences_layout);


        // Set up time labels
        LinearLayout mLayout = (LinearLayout) rootView.findViewById(R.id.day_times_layout);
        for (int i = 0; i < HOURS_IN_DAY; i++){
            TextView tv = new TextView(getActivity());
            // TODO make a prettier line
            tv.setText(String.format("%2d AM -----------------------------------------------------------", i));
            tv.setPadding(10, TIME_LABEL_TOP_PADDING, 10, TIME_LABEL_BOT_PADDING);
            mLayout.addView(tv);
        }



        // TODO set scrolled position to something interesting for a given day (ex, 2 hr before first event)



        // TODO populate events layout with events from DB
        // TODO populate interface with cards representing inferences



        return rootView;
    }





}
