package com.example.azia.diplom.schedule;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBScheduleHelper;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cn.refactor.lib.colordialog.ColorDialog;

/**
 * Created by AZIA on 20.07.2018.
 */

public class ScheduleListAdapter extends RecyclerView.Adapter<ScheduleListAdapter.ViewHolder> {

    private final Context context;
    private final List<ScheduleList> scheduleLists;
    public DBScheduleHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    boolean flag = false;
    Intent intent = new Intent();
    private ScheduleActivity mainActivity;
    private Dialog dialog;


    public ScheduleListAdapter(Context context, List<ScheduleList> scheduleLists, ScheduleActivity mainActivity) {
        this.context = context;
        this.scheduleLists = scheduleLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.schedule, null);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_monday1);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        dbSQL = new DBScheduleHelper(context);
        sqLiteDatabase = dbSQL.getWritableDatabase();

        final ScheduleList scheduleList = scheduleLists.get(position);
        holder.object.setText(scheduleList.getObject());
        holder.room.setText(scheduleList.getRoom());
        holder.time_start.setText(scheduleList.getTime_start());
        holder.time_end.setText(scheduleList.getTime_end());
        holder.days.setText(scheduleList.getDay());
        holder.teacher.setText(scheduleList.getTeacher());
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
                        dbSQL.deleteInfo(String.valueOf(scheduleList.getId()), sqLiteDatabase);
                        TastyToast.makeText(context, "Предмет удален  !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                        dbSQL.close();
                        dialog.cancel();
                        Intent intent = new Intent(context, ScheduleActivity.class);
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
//                aldial.setMessage("Удалить урок?").setCancelable(false)
//                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dbSQL.deleteInfo(String.valueOf(scheduleList.getId()), sqLiteDatabase);
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
        return scheduleLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView object;
        TextView room;
        TextView time_start;
        TextView time_end;
        TextView days;
        TextView teacher;
        RelativeLayout relativeLayout;
        FloatingActionButton delete;


        public ViewHolder(View itemView) {
            super(itemView);

            object = itemView.findViewById(R.id.sc_object);
            room = itemView.findViewById(R.id.sc_room);
            time_start = itemView.findViewById(R.id.sc_time_start);
            time_end = itemView.findViewById(R.id.sc_time_end);
            days = itemView.findViewById(R.id.sc_days);
            room = itemView.findViewById(R.id.sc_room);
            teacher = itemView.findViewById(R.id.sc_teacher);
            relativeLayout = itemView.findViewById(R.id.sc_rl);
            delete = itemView.findViewById(R.id.del_sc);
        }
    }
}
