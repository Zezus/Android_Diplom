package com.example.azia.diplom;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


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

                String object, room, time_start, time_end, day, id;
                object = cursor.getString(0);
                room = cursor.getString(1);
                time_start = cursor.getString(2);
                time_end = cursor.getString(3);
                day = cursor.getString(4);
                id = cursor.getString(5);
                if (day.equals("Суббота")) {
                    ScheduleList schedule1 = new ScheduleList();
                    schedule1.setObject(object);
                    schedule1.setRoom(room);
                    schedule1.setTime_start(time_start);
                    schedule1.setTime_end(time_end);
                    schedule1.setDay(day);
                    schedule1.setId(Integer.parseInt(id));
                    scheduleLists.add(schedule1);
                }
            } while (cursor.moveToNext());
        }

        mList.setAdapter(new ScheduleListAdapter(getContext(), scheduleLists, (ScheduleActivity) getActivity()));
        scheduleListAdapter = (new ScheduleListAdapter(getContext(), scheduleLists, (ScheduleActivity) getActivity()));
        scheduleListAdapter.notifyDataSetChanged();
        mList.setAdapter(scheduleListAdapter);
        return view;
    }

}
