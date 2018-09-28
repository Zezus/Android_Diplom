package com.example.azia.diplom.notes;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBNotesHelper;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private final Context context;
    private final List<NoteList> noteLists;
    public DBNotesHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    private NoteActivity mainActivity;
    private Dialog dialog;


    public NoteAdapter(Context context, List<NoteList> noteLists, NoteActivity mainActivity) {
        this.context = context;
        this.noteLists = noteLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.note, null);

        ViewHolder viewHolder = new ViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_object);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dbSQL = new DBNotesHelper(context);
        sqLiteDatabase = dbSQL.getWritableDatabase();

        final NoteList noteList = noteLists.get(position);
        holder.noteTV.setText(noteList.getNote());
    }

    @Override
    public int getItemCount() {
        return noteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        MultiAutoCompleteTextView noteTV;
        FloatingActionButton delete;


        public ViewHolder(View itemView) {
            super(itemView);

            noteTV = itemView.findViewById(R.id.note_note_et);
        }
    }
}

