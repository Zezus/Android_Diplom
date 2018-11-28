package com.example.azia.diplom.grade;

import android.os.Parcel;
import android.os.Parcelable;

public class GradeList implements Parcelable {

    public static final Creator<GradeList> CREATOR = new Creator<GradeList>() {
        @Override
        public GradeList createFromParcel(Parcel in) {
            return new GradeList(in);
        }

        @Override
        public GradeList[] newArray(int size) {
            return new GradeList[size];
        }
    };

    private int id;
    private String object;
    private String avgrades;
    private String grades;

    public GradeList() {
    }

    public GradeList(Parcel in) {
        String[] data = new String[4];
        in.readStringArray(data);
        data[0] = String.valueOf(id);
        data[1] = object;
        data[2] = avgrades;
        data[3] = grades;
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

    public String getAvgrades() {
        return avgrades;
    }

    public void setAvgrades(String avgrades) {
        this.avgrades = avgrades;
    }

    public String getGrades() {
        return grades;
    }

    public void setGrades(String grades) {
        this.grades = grades;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(id), object, avgrades, grades});
    }
}
