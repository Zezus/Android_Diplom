package com.example.azia.diplom;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.UUID;

/**
 * Created by СадвакасовР on 04.04.2018.
 */

public class Item implements Parcelable {

    public static final Creator<Item> CREATOR = new Creator<Item>() {

        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
    private UUID id;
    private String title;
    private int photo;


    public Item() {
    }

    public Item(Parcel in) {
        String[] data = new String[3];
        in.readStringArray(data);
        data[0] = id.toString();
        data[1] = title;
        data[2] = String.valueOf(photo);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[]{id.toString(), title, String.valueOf(photo)});
    }
}
