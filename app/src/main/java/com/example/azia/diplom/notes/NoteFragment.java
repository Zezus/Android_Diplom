package com.example.azia.diplom.notes;

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
import com.example.azia.diplom.dataBase.DBNotesHelper;

import java.util.ArrayList;

public class NoteFragment extends Fragment {

    DBNotesHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    private RecyclerView noteRecyclerView;
    private ArrayList<NoteList> noteLists;

    public NoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note, container, false);

        noteRecyclerView = view.findViewById(R.id.notef_rv);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        noteLists = new ArrayList<>();
        dataBaseHelper = new DBNotesHelper(getContext());
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        cursor = dataBaseHelper.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String note, id;
                note = cursor.getString(0);
                id = cursor.getString(1);
                NoteList note1 = new NoteList();
                note1.setNote(note);
                note1.setId(Integer.parseInt(id));
                noteLists.add(note1);
            } while (cursor.moveToNext());
        }

        noteRecyclerView.setAdapter(new NoteAdapter(getContext(), noteLists, (NoteActivity) getActivity()));

        return view;
    }

}