package com.robrowski.automatictimesheet.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.robrowski.automatictimesheet.AppConstants;
import com.robrowski.automatictimesheet.R;
import com.robrowski.automatictimesheet.activity.dialog.DeleteDialog;
import com.robrowski.automatictimesheet.activity.fragment.FabCreateMenuFragment;
import com.robrowski.automatictimesheet.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditEventActivity
        extends ActionBarActivity
        implements AdapterView.OnItemSelectedListener, DeleteDialog.DeleteDialogListener {
    private static final String TAG = "EditEventActivity";

    private TextView mEditDate, mEditTime;

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;
    private CompoundButton mTransitionSwitch;
    private Spinner mLocCategorySpinner, mLocSpinner;

    /** This is the event initially displayed here. It should not be passed anywhere except to
     *  be saved to the db. It will hold all edits.
     */
    private Event mEvent = new Event();// TODO - dont init here
    private Calendar mCalendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEvent = (Event) getIntent().getParcelableExtra(AppConstants.INTENT_EVENT_PARCELABLE);
        mCalendar = mEvent.getCalendar();

        setContentView(R.layout.activity_edit_event);
        FabCreateMenuFragment.addToActivity(this, R.id.edit_layout);

        // Acquire references to fields
        mEditDate = (TextView) findViewById(R.id.edit_date);
        mEditTime = (TextView) findViewById(R.id.edit_time);
        mTransitionSwitch = (CompoundButton) findViewById(R.id.transition_switch);
        mLocCategorySpinner = (Spinner) findViewById(R.id.loc_category_spinner);
        mLocSpinner = (Spinner) findViewById(R.id.location_spinner);
        updateDateLabel(); // Assumes event data is already loaded
        updateTimeLabel();
        updateTransitionSwitch();


        // Set up listeners for opening dialogs
        mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };

        mTransitionSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mEvent.setTransition( isChecked);
                updateTransitionSwitch();
            }
        });


        // Set up the spinners
        String[] categories = {"Home", "Work", "Shopping", "Other"};
        String[] locations  = {"Wellesley", "N Reading", "Westborough", "Moms", "Dads", "Kellys"};
        setSpinnerAdapter(mLocCategorySpinner, categories);
        setSpinnerAdapter(mLocSpinner, locations);
    }

    // TODO populate spinner from DB of locations (geo fences)
    // TODO customize spinner? make it pretty?
    private void setSpinnerAdapter(Spinner s, Object[] objects){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item , objects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setOnItemSelectedListener(this);
        s.setAdapter(adapter);
    }




    // TODO something about not messing with the future? meh
    /** Called when the date field is clicked on */
    public void editDate(View v){
        new DatePickerDialog(this,
                mOnDateSetListener,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /** Called when the time field is clicked on */
    public void editTime(View v){
        Log.d(TAG, "Edit time clicked");
        new TimePickerDialog(this,
                mOnTimeSetListener,
                mCalendar.get(Calendar.HOUR_OF_DAY),
                mCalendar.get(Calendar.MINUTE),
                false).show(); // TODO 24 hr mode as a preference
    }



    private void updateDateLabel(){
        // TODO make the cute format optional via preferences
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        Calendar twoDaysAgo = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();

        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        twoDaysAgo.add(Calendar.DAY_OF_YEAR, -2);
        tomorrow.add(Calendar.DAY_OF_YEAR, 1);

        if (datesEqual(mCalendar, today)) { // Today
            mEditDate.setText("Today");
        } else if (datesEqual(mCalendar, yesterday)){ // Yesterday
            mEditDate.setText("Yesterday");
        } else if (datesEqual(mCalendar, twoDaysAgo)) { // Two days ago
            mEditDate.setText("Two days ago");
        } else if (datesEqual(mCalendar, tomorrow)){
            mEditDate.setText("Tomorrow");
        } else {
            String dateFormat = "EEEE, MMM dd, yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
            mEditDate.setText(sdf.format(mCalendar.getTime()));
        }
    }

    // Compare two dates (year and date of year)
    private boolean datesEqual(Calendar a, Calendar b){
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR);
        }


    private void updateTimeLabel(){
        String timeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        mEditTime.setText(sdf.format(mCalendar.getTime()));
    }

    private void updateTransitionSwitch() {
        mTransitionSwitch.setChecked(mEvent.getTransition());
        if (mEvent.getTransition()){
            mTransitionSwitch.setText("Arrival");
        } else {
            mTransitionSwitch.setText("Departure");
        }

    }



    /* Stuff for the spinners */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selected = (String) parent.getSelectedItem();
        if (view.getId() == R.id.loc_category_spinner) { // Category
            mEvent.setCategory(selected);
            Log.i(TAG, "Category: " + mEvent.getCategory());
        } else { // location spinner
            mEvent.setLocation( selected);
            Log.i(TAG, "Location: " + selected);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /* Action bar + menu */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch( item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_save:
                // TODO Save event to db
                navigateUp();
                return true;
            case R.id.action_delete:
                DeleteDialog.showDeleteDialog(this, this);
                return true;
            case android.R.id.home: // Being explicit. Also, just discard all changes
                navigateUp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void deletionConfirmed() {
        // TODO delete event from db
        navigateUp();
    }

    /** Helper for up navigation **/
    private void navigateUp(){
        // TODO proper up nav to PREVIOUS activity
        NavUtils.navigateUpFromSameTask(this);
    }
}
