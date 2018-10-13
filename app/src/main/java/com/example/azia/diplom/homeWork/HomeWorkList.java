package com.example.azia.diplom.homeWork;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class HomeWorkList implements Parcelable {

    public static final Creator<HomeWorkList> CREATOR = new Creator<HomeWorkList>() {
        @Override
        public HomeWorkList createFromParcel(Parcel in) {
            return new HomeWorkList(in);
        }

        @Override
        public HomeWorkList[] newArray(int size) {
            return new HomeWorkList[size];
        }
    };

    private int id;
    private String object;
    private String task;
    private String date;
    private String teacher;
    private Bitmap image;

    public HomeWorkList() {
    }


    public HomeWorkList(Parcel in) {
        String[] data = new String[6];
        in.readStringArray(data);
        data[0] = String.valueOf(id);
        data[1] = String.valueOf(object);
        data[2] = String.valueOf(task);
        data[3] = String.valueOf(date);
        data[4] = String.valueOf(teacher);
        data[5] = String.valueOf(image);
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

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{String.valueOf(id), object, task, date, teacher});
    }
}
