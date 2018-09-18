package com.example.azia.diplom.homeWork;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBHomeWorkHelper;

import java.util.List;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.ViewHolder> {

    private final Context context;
    private final List<HomeWorkList> homeWorkLists;
    public DBHomeWorkHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    private HomeWorkActivity mainActivity;
    private Dialog dialog;

    public HomeWorkAdapter(Context context, List<HomeWorkList> homeWorkLists, HomeWorkActivity mainActivity) {
        this.context = context;
        this.homeWorkLists = homeWorkLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.homework, null);

        ViewHolder viewHolder = new ViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_home_work);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dbSQL = new DBHomeWorkHelper(context);
        sqLiteDatabase = dbSQL.getWritableDatabase();

        final HomeWorkList homeWorkList = homeWorkLists.get(position);
        holder.objectTV.setText(homeWorkList.getObject());
        holder.taskTV.setText(homeWorkList.getTask());
        holder.dateTV.setText(homeWorkList.getDate());
        holder.teacherTV.setText(homeWorkList.getTeacher());

    }

    @Override
    public int getItemCount() {
        return homeWorkLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView objectTV;
        TextView taskTV;
        TextView dateTV;
        TextView teacherTV;

        public ViewHolder(View itemView) {
            super(itemView);

            objectTV = itemView.findViewById(R.id.hw_object);
            taskTV = itemView.findViewById(R.id.hw_task);
            dateTV = itemView.findViewById(R.id.hw_date);
            teacherTV = itemView.findViewById(R.id.hw_teacher);

        }
    }
}
