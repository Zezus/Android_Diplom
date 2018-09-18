package com.example.azia.diplom.homeWork;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBHomeWorkHelper;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeWorkFragment extends Fragment {

    DBHomeWorkHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private RecyclerView itemRecyclerView;
    private ArrayList<HomeWorkList> homeWorkLists;

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

                String object, task, date, teacher, id;
                object = cursor.getString(0);
                task = cursor.getString(1);
                date = cursor.getString(2);
                teacher = cursor.getString(3);
                id = cursor.getString(4);
                HomeWorkList homeWork1 = new HomeWorkList();
                homeWork1.setObject(object);
                homeWork1.setTask(task);
                homeWork1.setDate(date);
                homeWork1.setTeacher(teacher);
                homeWork1.setId(Integer.parseInt(id));
                homeWorkLists.add(homeWork1);
            } while (cursor.moveToNext());
        }

        itemRecyclerView.setAdapter(new HomeWorkAdapter(getContext(), homeWorkLists, (HomeWorkActivity) getActivity()));

        return view;
    }

}
