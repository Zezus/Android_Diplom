package com.example.azia.diplom.notes;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteList implements Parcelable {

    public static final Creator<NoteList> CREATOR = new Creator<NoteList>() {
        @Override
        public NoteList createFromParcel(Parcel in) {
            return new NoteList(in);
        }

        @Override
        public NoteList[] newArray(int size) {
            return new NoteList[size];
        }
    };

    private int id;
    private String note;

    public NoteList() {
    }

    public NoteList(Parcel in) {
        String[] data = new String[2];
        in.readStringArray(data);
        data[0] = String.valueOf(id);
        data[1] = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String object) {
        this.note = object;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(id), note});
    }
}
