package com.example.azia.diplom.object;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.sdsmdg.tastytoast.TastyToast;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddObjectSheet extends BottomSheetDialogFragment {

    public EditText object;
    public EditText teacher;
    public DBObjectHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    private Button btn_send;
    private FloatingActionButton fab_send1;
    private FloatingActionButton fab_send2;
    private FloatingActionButton fab_keyboard;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_object_sheet, container, false);

        btn_send = v.findViewById(R.id.ob_btn_send_sheet);
        fab_send1 = v.findViewById(R.id.ob_fab_send_sheet);
        fab_send2 = v.findViewById(R.id.ob_fab_send_sheet2);
        fab_keyboard = v.findViewById(R.id.ob_fab_keyboard_sheet);
        teacher = v.findViewById(R.id.ob_et_teacher_sheet);
        object = v.findViewById(R.id.ob_et_object_sheet);

        btn_send.setOnClickListener(view -> {
            String object_v = object.getText().toString();
            String teacher_v = teacher.getText().toString();

            if (object_v.length() == 0 || teacher_v.length() == 0) {
                new PromptDialog(getContext())
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

            } else {

                dbSQL = new DBObjectHelper(getContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(object_v, teacher_v, sqLiteDatabase);
                Toast.makeText(getContext(), "Предмет и преподаватель добавлены", Toast.LENGTH_LONG).show();
                dbSQL.close();


                Intent intent = new Intent(getContext(), ObjectActivity.class);
                getActivity().startActivity(intent);
            }

        });

        fab_send1.setOnClickListener(view -> {
            String object_v = object.getText().toString();
            String teacher_v = teacher.getText().toString();

            if (object_v.length() == 0 || teacher_v.length() == 0) {
                new PromptDialog(getContext())
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

            } else {

                dbSQL = new DBObjectHelper(getContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(object_v, teacher_v, sqLiteDatabase);
                Toast.makeText(getContext(), "Предмет и преподаватель добавлены", Toast.LENGTH_LONG).show();
                dbSQL.close();


                Intent intent = new Intent(getContext(), ObjectActivity.class);
                getActivity().startActivity(intent);
            }

        });

        fab_send2.setOnClickListener(view -> {
            String object_v = object.getText().toString();
            String teacher_v = teacher.getText().toString();

            if (object_v.length() == 0 || teacher_v.length() == 0) {
                new PromptDialog(getContext())
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

            } else {

                dbSQL = new DBObjectHelper(getContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(object_v, teacher_v, sqLiteDatabase);
                TastyToast.makeText(getContext(), "Добавлено  !", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                dbSQL.close();


                Intent intent = new Intent(getContext(), ObjectActivity.class);
                getActivity().startActivity(intent);
            }

        });

        fab_keyboard.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        });

        return v;
    }
}
