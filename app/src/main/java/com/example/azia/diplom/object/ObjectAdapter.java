package com.example.azia.diplom.object;

import android.app.Dialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBObjectHelper;

import java.util.List;


public class ObjectAdapter extends RecyclerView.Adapter<ObjectAdapter.ViewHolder> {

    private final Context context;
    private final List<ObjectList> objectLists;
    public DBObjectHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
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

        final ObjectList objectList = objectLists.get(position);
        holder.objectTV.setText(objectList.getObject());
        holder.teacherTV.setText(objectList.getTeacher());
    }

    @Override
    public int getItemCount() {
        return objectLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView objectTV;
        TextView teacherTV;

        public ViewHolder(View itemView) {
            super(itemView);

            objectTV = itemView.findViewById(R.id.ob_tv_object);
            teacherTV = itemView.findViewById(R.id.ob_tv_teacher);
        }
    }
}
