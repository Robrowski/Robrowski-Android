package com.robrowski.automatictimesheet.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.robrowski.automatictimesheet.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditEventActivity extends ActionBarActivity {
    private static final String TAG = EditEventActivity.class.toString();

    private Calendar myCalendar = Calendar.getInstance(); // TODO  Maybe keep this is the "event" object?
    private TextView mEditDate, mEditTime;

    private DatePickerDialog.OnDateSetListener mOnDateSetListener;
    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        mEditDate = (TextView) findViewById(R.id.edit_date);
        mEditTime = (TextView) findViewById(R.id.edit_time);
        updateDateLabel(); // Assumes event data is already loaded
        updateTimeLabel();

         mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }
        };

        mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay );
                myCalendar.set(Calendar.MINUTE, minute);
                updateTimeLabel();
            }
        };


    }

    /** Called when the date field is clicked on */
    public void editDate(View v){
        new DatePickerDialog(this,
                mOnDateSetListener,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /** Called when the time field is clicked on */
    public void editTime(View v){
        Log.i(TAG, "Edit time clicked");
        new TimePickerDialog(this,
                mOnTimeSetListener,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                false).show(); // TODO 24 hr mode as a preference
    }
    
    
    
    private void updateDateLabel(){
        String dateFormat = "MMM dd, yyyy"; // TODO better date format
        // TODO cute stuff to say "Yesterday" or "Two Days ago" or "Today"
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        mEditDate.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateTimeLabel(){
        String timeFormat = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat, Locale.US);
        mEditTime.setText(sdf.format(myCalendar.getTime()));
    }
    






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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
