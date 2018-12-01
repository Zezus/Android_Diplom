package com.example.azia.diplom.object;

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
import com.example.azia.diplom.dataBase.DBObjectHelper;

import java.util.ArrayList;

public class ObjectFragment extends Fragment {

    DBObjectHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private RecyclerView itemRecyclerView;
    private ArrayList<ObjectList> objectLists;

    public ObjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_object, container, false);

        itemRecyclerView = view.findViewById(R.id.ob2_objects_rv);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        objectLists = new ArrayList<>();
        dataBaseHelper = new DBObjectHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = dataBaseHelper.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String object, teacher, id;
                object = cursor.getString(0);
                teacher = cursor.getString(1);
                id = cursor.getString(2);
                ObjectList object1 = new ObjectList();
                object1.setObject(object);
                object1.setTeacher(teacher);
                object1.setId(Integer.parseInt(id));
                objectLists.add(object1);
            } while (cursor.moveToNext());
        }
        cursor.close();

        itemRecyclerView.setAdapter(new ObjectAdapter(getContext(), objectLists, (ObjectActivity) getActivity()));

        return view;

    }
}
