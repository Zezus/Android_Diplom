package com.example.azia.diplom.notes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBNotesHelper;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cn.refactor.lib.colordialog.ColorDialog;

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
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDialog dialog = new ColorDialog(context);
                dialog.setTitle("Удаление");
                dialog.setContentText("Вы уверены, что хотите удалить запичь?");
                //dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
                dialog.setPositiveListener("ДА", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dbSQL.deleteInfo(String.valueOf(noteList.getId()), sqLiteDatabase);
                        TastyToast.makeText(context, "Запись удалена !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                        dbSQL.close();
                        dialog.cancel();
                        Intent intent = new Intent(context, NoteActivity.class);
                        context.startActivity(intent);
                    }
                })
                        .setNegativeListener("НЕТ", new ColorDialog.OnNegativeListener() {
                            @Override
                            public void onClick(ColorDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
//                AlertDialog.Builder aldial = new AlertDialog.Builder(context);
//                aldial.setMessage("Удалить предмет?").setCancelable(false)
//                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dbSQL.deleteInfo(String.valueOf(objectList.getId()), sqLiteDatabase);
//                                Toast.makeText(context, "Предмет удален", Toast.LENGTH_LONG).show();
//                                dbSQL.close();
//                            }
//                        })
//                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = aldial.create();
//                alert.setTitle("Program");
//                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView noteTV;
        FloatingActionButton delete;


        public ViewHolder(View itemView) {
            super(itemView);

            noteTV = itemView.findViewById(R.id.note_note_et);
            delete = itemView.findViewById(R.id.del_note);

        }
    }
}

