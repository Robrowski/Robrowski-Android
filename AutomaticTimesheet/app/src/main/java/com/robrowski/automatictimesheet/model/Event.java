package com.robrowski.automatictimesheet.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Robrowski on 6/19/2015.
 */
public class Event implements Parcelable{

    public long id = -1; // Implement this? maybe!?
    public Calendar mCalendar = Calendar.getInstance();
    public Boolean mTransition = false;
    // TODO better default values
    public String mCategory = "Home", mLocation = "Wellesley"; // location referenced by name


    /** Create an Event object from a db cursor */
    public Event(Cursor c){

    }

    /** Default Constructor */
    public Event(){

    }

    private Event(Parcel in) {
        id = in.readLong();
        mCalendar.setTime( (Date) in.readSerializable());
        mTransition = new Boolean(in.readString());
        mCategory = in.readString();
        mLocation = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeSerializable( mCalendar.getTime());
        out.writeString( mTransition.toString());
        out.writeString(mCategory);
        out.writeString(mLocation);
    }

    @Override
    public int describeContents() {
        return 0; // TODO research what 'describeContents' should do
    }

    public static final Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
