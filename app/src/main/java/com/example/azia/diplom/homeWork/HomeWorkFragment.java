package com.example.azia.diplom.homeWork;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBHomeWorkHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWorkFragment extends Fragment {

    DBHomeWorkHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private RecyclerView itemRecyclerView;
    private ArrayList<HomeWorkList> homeWorkLists;
    private HomeWorkAdapter homeWorkAdapter;

    public HomeWorkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_work, container, false);

        itemRecyclerView = view.findViewById(R.id.hw_objects_rv);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homeWorkLists = new ArrayList<>();
        dataBaseHelper = new DBHomeWorkHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = dataBaseHelper.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String object, task, date, teacher, image, date_sort, id;
                //String object, task, date, teacher, id;
                object = cursor.getString(0);
                task = cursor.getString(1);
                date = cursor.getString(2);
                teacher = cursor.getString(3);
                image = cursor.getString(4);
                date_sort = cursor.getString(5);
                id = cursor.getString(6);
                HomeWorkList homeWork1 = new HomeWorkList();
                homeWork1.setObject(object);
                homeWork1.setTask(task);
                homeWork1.setDate(date);
                homeWork1.setTeacher(teacher);
                homeWork1.setImage(image);
                homeWork1.setDate_sort(date_sort);
                homeWork1.setId(Integer.parseInt(id));
                homeWorkLists.add(homeWork1);
            } while (cursor.moveToNext());
        }
        cursor.close();

        /*Collections.sort(homeWorkLists, new Comparator<HomeWorkList>() {
            @Override
            public int compare(HomeWorkList lhs, HomeWorkList rhs) {
                return Integer.valueOf(lhs.getDate()).compareTo(Integer.valueOf(rhs.getDate()));
            }
        });*/

        Collections.sort(homeWorkLists, new Comparator<HomeWorkList>() {
            @Override
            public int compare(HomeWorkList lhs, HomeWorkList rhs) {
                return Integer.valueOf(lhs.getDate_sort()).compareTo(Integer.valueOf(rhs.getDate_sort()));
            }
        });
        itemRecyclerView.setAdapter(new HomeWorkAdapter(getContext(), homeWorkLists, (HomeWorkActivity) getActivity()));
        homeWorkAdapter = (new HomeWorkAdapter(getContext(), homeWorkLists, (HomeWorkActivity) getActivity()));
        homeWorkAdapter.notifyDataSetChanged();
        itemRecyclerView.setAdapter(homeWorkAdapter);


        return view;
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
