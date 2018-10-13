package com.example.azia.diplom.object;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBObjectHelper;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddObjectActivity extends AppCompatActivity {

    public EditText object;
    public EditText teacher;
    public Button btn_send;
    public DBObjectHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);

        object = findViewById(R.id.ob_et_object);
        teacher = findViewById(R.id.ob_et_teacher);
        btn_send = findViewById(R.id.ob_btn_send);

        btn_send.setOnClickListener(view -> {
            String object_v = object.getText().toString();
            String teacher_v = teacher.getText().toString();

            if (object_v.length() == 0 || teacher_v.length() == 0) {
                new PromptDialog(this)
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

                dbSQL = new DBObjectHelper(getApplicationContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(object_v, teacher_v, sqLiteDatabase);
                Toast.makeText(getApplicationContext(), "Предмет и преподаватель добавлены", Toast.LENGTH_LONG).show();
                dbSQL.close();


                Intent intent = new Intent();
                intent.setClass(AddObjectActivity.this, ObjectActivity.class);
                startActivity(intent);
            }

        });
    }
}
