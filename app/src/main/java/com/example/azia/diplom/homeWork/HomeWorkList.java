package com.example.azia.diplom.homeWork;

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
    private String image;
    private Boolean flag;
    private String date_sort;

    public HomeWorkList() {
    }


    public HomeWorkList(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        data[0] = String.valueOf(id);
        data[1] = String.valueOf(object);
        data[2] = String.valueOf(task);
        data[3] = String.valueOf(date);
        data[4] = String.valueOf(teacher);
        data[5] = String.valueOf(image);
        data[6] = String.valueOf(date_sort);
    }

    public String getDate_sort() {
        return date_sort;
    }

    public void setDate_sort(String date_sort) {
        this.date_sort = date_sort;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
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
