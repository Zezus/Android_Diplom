package com.example.azia.diplom;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AZIA on 12.07.2018.
 */

public class ScheduleList implements Parcelable {

    public static final Creator<ScheduleList> CREATOR = new Creator<ScheduleList>() {
        @Override
        public ScheduleList createFromParcel(Parcel in) {
            return new ScheduleList(in);
        }

        @Override
        public ScheduleList[] newArray(int size) {
            return new ScheduleList[size];
        }
    };
    private int id;
    private String object;
    private String room;
    private String time_start;
    private String time_end;
    private String day;

    public ScheduleList() {
    }

    public ScheduleList(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);
        data[0] = String.valueOf(id);
        data[1] = object;
        data[2] = room;
        data[3] = time_start;
        data[4] = time_end;
        data[5] = day;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(id), object, room, time_start, time_end, day});
    }
}