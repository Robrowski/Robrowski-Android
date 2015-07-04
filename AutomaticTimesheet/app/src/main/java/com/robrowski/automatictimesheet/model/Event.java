package com.robrowski.automatictimesheet.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Robrowski on 6/19/2015.
 */
@Data
@NoArgsConstructor
public class Event implements Parcelable{

    private long id = -1; // Implement this? maybe!?
    private Calendar calendar = Calendar.getInstance();
    private Boolean transition = false;
    // TODO better default values
    private String category = "Home", location = "Wellesley"; // location referenced by name


    /** // TODO Create an Event object from a db cursor */
    public Event(Cursor c){

    }



    private Event(Parcel in) {
        id = in.readLong();
        calendar.setTime( (Date) in.readSerializable());
        transition = new Boolean(in.readString());
        category = in.readString();
        location = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeSerializable( calendar.getTime());
        out.writeString(transition.toString());
        out.writeString(category);
        out.writeString(location);
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
