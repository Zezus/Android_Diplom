package com.example.azia.diplom.object;

import android.os.Parcel;
import android.os.Parcelable;


public class ObjectList implements Parcelable {

    public static final Creator<ObjectList> CREATOR = new Creator<ObjectList>() {
        @Override
        public ObjectList createFromParcel(Parcel in) {
            return new ObjectList(in);
        }

        @Override
        public ObjectList[] newArray(int size) {
            return new ObjectList[size];
        }
    };
    private int id;
    private String object;
    private String teacher;

    public ObjectList() {
    }

    public ObjectList(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        data[0] = String.valueOf(id);
        data[1] = object;
        data[2] = teacher;
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

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(id), object, teacher});
    }
}
