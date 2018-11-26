package com.example.azia.diplom.schedule;


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
import com.example.azia.diplom.dataBase.DBScheduleHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * A simple {@link Fragment} subclass.
 */
public class Saturday6Fragment extends Fragment {


    RecyclerView mList;
    DBScheduleHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private ScheduleListAdapter scheduleListAdapter;
    private ArrayList<ScheduleList> scheduleLists;

    public Saturday6Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saturday6, container, false);


        mList = view.findViewById(R.id.l_rv);
        mList.setLayoutManager(new LinearLayoutManager(getContext()));
        scheduleLists = new ArrayList<>();

        dataBaseHelper = new DBScheduleHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = dataBaseHelper.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String object, room, time_start, time_end, time_sort, day, teacher, id;
                object = cursor.getString(0);
                room = cursor.getString(1);
                time_start = cursor.getString(2);
                time_end = cursor.getString(3);
                time_sort = cursor.getString(4);
                day = cursor.getString(5);
                teacher = cursor.getString(6);
                id = cursor.getString(7);
                if (day.equals("Суббота")) {
                    ScheduleList schedule1 = new ScheduleList();
                    schedule1.setObject(object);
                    schedule1.setRoom(room);
                    schedule1.setTime_start(time_start);
                    schedule1.setTime_end(time_end);
                    schedule1.setTime_sort(time_sort);
                    schedule1.setDay(day);
                    schedule1.setTeacher(teacher);
                    schedule1.setId(Integer.parseInt(id));
                    scheduleLists.add(schedule1);
                }
            } while (cursor.moveToNext());
        }

        Collections.sort(scheduleLists, new Comparator<ScheduleList>() {
            @Override
            public int compare(ScheduleList lhs, ScheduleList rhs) {
                return Integer.valueOf(lhs.getTime_sort()).compareTo(Integer.valueOf(rhs.getTime_sort()));
            }
        });

        mList.setAdapter(new ScheduleListAdapter(getContext(), scheduleLists, (ScheduleActivity) getActivity()));
        scheduleListAdapter = (new ScheduleListAdapter(getContext(), scheduleLists, (ScheduleActivity) getActivity()));
        scheduleListAdapter.notifyDataSetChanged();
        mList.setAdapter(scheduleListAdapter);
        return view;
    }

}
