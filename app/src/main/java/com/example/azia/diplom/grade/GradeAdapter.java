package com.example.azia.diplom.grade;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBGradeHelper;
import com.sdsmdg.tastytoast.TastyToast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import cn.refactor.lib.colordialog.PromptDialog;

public class GradeAdapter extends RecyclerView.Adapter<GradeAdapter.ViewHolder> {

    private final Context context;
    private final List<GradeList> gradeLists;
    public DBGradeHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    private GradeActivity mainActivity;
    private Dialog dialog;
    private double avgrades;

    public GradeAdapter(Context context, List<GradeList> gradeLists, GradeActivity mainActivity) {
        this.context = context;
        this.gradeLists = gradeLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public GradeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.grade, null);

        GradeAdapter.ViewHolder viewHolder = new GradeAdapter.ViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_grade);

        return new GradeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradeAdapter.ViewHolder holder, int position) {

        dbSQL = new DBGradeHelper(context);
        sqLiteDatabase = dbSQL.getWritableDatabase();

        final GradeList gradeList = gradeLists.get(position);

        String[] grades = gradeList.getGrades().split(" ");
        int count = 0;
        for (int c = 0; c < grades.length; c++) {
            if (!grades[c].equals("")) {
                count++;
            }
        }
        String[] s = new String[count];
        int b = 0;
        for (int j = 0; j < grades.length; j++) {
            if (!grades[j].equals("")) {
                s[b] = grades[j];
                b++;
            }
        }
        avgrades = 0;

        for (int i = 0; i < s.length; i++) {
            avgrades += Integer.valueOf(s[i]);
        }
        if (s.length >= 1) {
            avgrades = avgrades / s.length;
            avgrades = new BigDecimal(avgrades).setScale(2, RoundingMode.UP).doubleValue();
        }
        holder.objectTV.setText(gradeList.getObject());
        holder.avgradeTV.setText(String.valueOf(avgrades));
        holder.gradeTV.setText(gradeList.getGrades());
        holder.updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.addgradeET.getText().length() != 0) {
                    String grade = holder.addgradeET.getText().toString();

                    dbSQL.updateInfo(String.valueOf(gradeList.getId()), gradeList.getObject(), String.valueOf(avgrades), gradeList.getGrades() + " " + grade, sqLiteDatabase);
                    dbSQL.close();
                    TastyToast.makeText(context, "Обновлено", TastyToast.LENGTH_LONG,
                            TastyToast.INFO);
                    Intent intent = new Intent(context, GradeActivity.class);
                    context.startActivity(intent);
                } else {
                    new PromptDialog(context)
                            .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                            .setAnimationEnable(true)
                            .setTitleText("")
                            .setContentText("Заполните все поля")
                            .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }
        });
        holder.deleteBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] grades = gradeList.getGrades().split(" ");
                String grades_raw = "";
                for (int i = 0; i < grades.length - 1; i++) {
                    grades_raw += grades[i] + " ";
                }
                dbSQL.updateInfo(String.valueOf(gradeList.getId()), gradeList.getObject(), String.valueOf(avgrades), grades_raw, sqLiteDatabase);
                dbSQL.close();
                TastyToast.makeText(context, "Удалено", TastyToast.LENGTH_LONG,
                        TastyToast.INFO);
                Intent intent = new Intent(context, GradeActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gradeLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView objectTV;
        TextView avgradeTV;
        TextView gradeTV;
        EditText addgradeET;
        Button updateBT;
        Button deleteBT;


        public ViewHolder(View itemView) {
            super(itemView);

            objectTV = itemView.findViewById(R.id.grade_tv_object);
            avgradeTV = itemView.findViewById(R.id.grade_tv_avgrade);
            gradeTV = itemView.findViewById(R.id.grade_tv_grade);
            addgradeET = itemView.findViewById(R.id.grade_et_addgrade);
            updateBT = itemView.findViewById(R.id.grade_btn_update);
            deleteBT = itemView.findViewById(R.id.grade_btn_del);

        }
    }
}
