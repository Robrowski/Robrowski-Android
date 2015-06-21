package com.robrowski.automatictimesheet.activity.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.robrowski.automatictimesheet.AppConstants;
import com.robrowski.automatictimesheet.R;
import com.robrowski.automatictimesheet.activity.EditEventActivity;
import com.robrowski.automatictimesheet.model.Event;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment that holds the floating action menu
 */
public class FabCreateMenuFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "FabCreateMenuFragment";

    private ActionButton mFabCreateMenu;
    private List<ActionButton> mCreateOptionsButtons = new ArrayList<ActionButton>();

    private final int mAnimationDelayPerItem = 75;
    private boolean mMenuOpening = false;
    private Handler mUiHandler = new Handler();


    /** Method to add an instance of this fragment to an activity programatically */
    public static void addToActivity(Activity a, int view_to_add_to) {
        Log.d(TAG, "Adding a new instance to " + a.getClass().getSimpleName());

        FragmentManager fragmentManager = a.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FabCreateMenuFragment fragment = new FabCreateMenuFragment();

        fragmentTransaction.add(view_to_add_to, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_fab_create_menu, container, false);

        // Get the Floating ActionButtons
        mFabCreateMenu = (ActionButton) mView.findViewById(R.id.fab_create_menu);
        ActionButton fab_new_category = (ActionButton) mView.findViewById(R.id.fab_new_category);
        ActionButton fab_new_event = (ActionButton) mView.findViewById(R.id.fab_new_event);
        ActionButton fab_new_location = (ActionButton) mView.findViewById(R.id.fab_new_location);

        // Save references
        mCreateOptionsButtons.add(fab_new_location);
        mCreateOptionsButtons.add(fab_new_category);
        if (!getActivity().getClass().getSimpleName().equals("EditEventActivity")){ // TODO smarter
            mCreateOptionsButtons.add(fab_new_event); // Only included if NOT in a new event
        }

        // Set up click listeners
        mFabCreateMenu.setOnClickListener(this);
        fab_new_event.setOnClickListener(optionClick);
        fab_new_location.setOnClickListener(optionClick);
        fab_new_category.setOnClickListener(optionClick);

        return mView;
    }


    /** Toggles the menu buttons up or down. Blocks during the animation */
    @Override
    public void onClick(View view) {
        toggleMenu();
    }

    public void toggleMenu(){
        int delay = 0;
        Log.d(TAG, "Toggling create menu");
        mFabCreateMenu.setClickable(false); // Lock it for safety
        mMenuOpening = !mMenuOpening;

        if (mMenuOpening){ // Opening menu
            // TODO animate change in the + icon to indicate cancel
            mFabCreateMenu.setImageResource(R.drawable.ic_clear_white_24dp);
            Log.d(TAG, "animating menu...");

            for(int i = 0; i < mCreateOptionsButtons.size(); i++){
                final ActionButton option = mCreateOptionsButtons.get(i);
                mUiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option.show();
                    }
                }, delay);
                delay += mAnimationDelayPerItem;
            }
        } else { // Closing menu
            Log.d(TAG, "closing menu...");
            mFabCreateMenu.setImageResource(R.drawable.ic_add_white_24dp);

            for(int i = mCreateOptionsButtons.size()-1; i >= 0; i--){
                final ActionButton option = mCreateOptionsButtons.get(i);
                mUiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        option.hide();
                    }
                }, delay);
                delay += mAnimationDelayPerItem;
            }
        }
        mFabCreateMenu.setClickable(true);
    }

    public View.OnClickListener optionClick = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            toggleMenu();
            Context c = getActivity();
            switch (view.getId()){
                case R.id.fab_new_category:
                    Log.d(TAG, "new category fab pressed" );
                    // TODO launch new category dialog
                    // TODO trigger update of spinners if in EditEventActivity
                    break;
                case R.id.fab_new_event:
                    Log.d(TAG, "new event FAB pressed" );
                    Intent intent = new Intent(c, EditEventActivity.class);
                    intent.putExtra(AppConstants.INTENT_EVENT_PARCELABLE, new Event());
                    c.startActivity(intent);
                    break;
                case R.id.fab_new_location:
                    Log.d(TAG,"New location FAB pressed");
                    // TODO launch new location dialong/activity.... TBD

                    break;
            }
        }
    };
}
