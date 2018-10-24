package com.example.azia.diplom.notes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBNotesHelper;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddNoteActivity extends AppCompatActivity {

    public DBNotesHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    public EditText noteET;
    public Button btn_send;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteET = findViewById(R.id.noteadd_note);
        btn_send = findViewById(R.id.noteadd_button);

        btn_send.setOnClickListener(view -> {
            String note_v = noteET.getText().toString();

            if (note_v.length() == 0) {
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

                dbSQL = new DBNotesHelper(getApplicationContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(note_v, sqLiteDatabase);
                Toast.makeText(getApplicationContext(), "Запись добавлена", Toast.LENGTH_LONG).show();
                dbSQL.close();


                Intent intent = new Intent();
                intent.setClass(AddNoteActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
    }
}
