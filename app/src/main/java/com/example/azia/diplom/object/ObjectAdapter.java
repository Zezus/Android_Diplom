package com.example.azia.diplom.object;

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
import com.example.azia.diplom.dataBase.DBGradeHelper;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cn.refactor.lib.colordialog.ColorDialog;


public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.ViewHolder> {

    private final Context context;
    private final List<ObjectList> objectLists;
    public DBObjectHelper dbSQL;
    public DBGradeHelper dbSQL2;
    public SQLiteDatabase sqLiteDatabase;
    public SQLiteDatabase sqLiteDatabase2;
    private ObjectActivity mainActivity;
    private Dialog dialog;


    public ObjectAdapter(Context context, List<ObjectList> objectLists, ObjectActivity mainActivity) {
        this.context = context;
        this.objectLists = objectLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.object, null);

        ViewHolder viewHolder = new ViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_object);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dbSQL = new DBObjectHelper(context);
        sqLiteDatabase = dbSQL.getWritableDatabase();
        dbSQL2 = new DBGradeHelper(context);
        sqLiteDatabase2 = dbSQL2.getWritableDatabase();

        final ObjectList objectList = objectLists.get(position);
        holder.objectTV.setText(objectList.getObject());
        holder.teacherTV.setText(objectList.getTeacher());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDialog dialog = new ColorDialog(context);
                dialog.setTitle("Удаление");
                dialog.setContentText("Вы уверены, что хотите удалить предмет?");
                //dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
                dialog.setPositiveListener("ДА", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dbSQL.deleteInfo(String.valueOf(objectList.getId()), sqLiteDatabase);
                        dbSQL2.deleteInfo(String.valueOf(objectList.getId()), sqLiteDatabase2);
                        dbSQL.close();
                        dbSQL2.close();
                        TastyToast.makeText(context, "Предмет удален  !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                        dialog.cancel();
                        Intent intent = new Intent(context, ObjectActivity.class);
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
        return objectLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView objectTV;
        TextView teacherTV;
        FloatingActionButton delete;


        public ViewHolder(View itemView) {
            super(itemView);

            objectTV = itemView.findViewById(R.id.ob_tv_object);
            teacherTV = itemView.findViewById(R.id.ob_tv_teacher);
            delete = itemView.findViewById(R.id.del_ob);
        }
    }
}
