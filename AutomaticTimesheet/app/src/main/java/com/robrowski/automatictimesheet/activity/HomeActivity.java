package com.robrowski.automatictimesheet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.robrowski.automatictimesheet.R;
import com.robrowski.automatictimesheet.activity.fragment.FabCreateMenuFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


// TODO FragmentActivity? Do I even need a fragment at all? no dynamic work here...
public class HomeActivity extends ActionBarActivity {
    private static final String TAG = "HomeActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FabCreateMenuFragment.addToActivity(this, R.id.home_layout);


        initTodayCard();
    }


    private void initTodayCard(){
        TextView todaysDateText = (TextView) findViewById(R.id.text_todays_date);
        String dateFormat = "EEEE, MMM dd, yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.US);
        todaysDateText.setText(sdf.format(new Date()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void loadToday(View v){
        Intent intent = new Intent(this, DayViewActivity.class);
        // TODO put date inside so the right tab is open
        this.startActivity(intent);
    }


}
