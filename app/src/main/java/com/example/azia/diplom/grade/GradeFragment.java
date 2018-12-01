package com.example.azia.diplom.grade;

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
import com.example.azia.diplom.dataBase.DBGradeHelper;

import java.util.ArrayList;

public class GradeFragment extends Fragment {

    DBGradeHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private RecyclerView gradeRecyclerView;
    private ArrayList<GradeList> gradeLists;

    public GradeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grade, container, false);

        gradeRecyclerView = view.findViewById(R.id.grade_rv);
        gradeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        gradeLists = new ArrayList<>();
        dataBaseHelper = new DBGradeHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = dataBaseHelper.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String object, avgrade, grade, id;
                object = cursor.getString(0);
                avgrade = cursor.getString(1);
                grade = cursor.getString(2);
                id = cursor.getString(3);
                GradeList grade1 = new GradeList();
                grade1.setObject(object);
                grade1.setAvgrades(avgrade);
                grade1.setGrades(grade);
                grade1.setId(Integer.parseInt(id));
                gradeLists.add(grade1);
            } while (cursor.moveToNext());
        }

        gradeRecyclerView.setAdapter(new GradeAdapter(getContext(), gradeLists, (GradeActivity) getActivity()));

        cursor.close();
        return view;
    }
}
