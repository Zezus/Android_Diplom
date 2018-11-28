package com.example.azia.diplom.object;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBGradeHelper;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.sdsmdg.tastytoast.TastyToast;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddObjectActivity extends AppCompatActivity {

    public EditText object;
    public EditText teacher;
    public Button btn_send;
    public DBObjectHelper dbSQL;
    public DBGradeHelper dbSQL2;
    public SQLiteDatabase sqLiteDatabase;
    public SQLiteDatabase sqLiteDatabase2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);

        object = findViewById(R.id.ob_et_object);
        teacher = findViewById(R.id.ob_et_teacher);
        btn_send = findViewById(R.id.ob_btn_send);

        Toolbar toolbar = findViewById(R.id.toolbar_add_object);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                dbSQL2 = new DBGradeHelper(getApplicationContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                sqLiteDatabase2 = dbSQL2.getWritableDatabase();
                dbSQL.addInfo(object_v, teacher_v, sqLiteDatabase);
                dbSQL2.addInfo(object_v, "0", "0", sqLiteDatabase2);
                TastyToast.makeText(getApplicationContext(), "Предмет и преподаватель добавлены", TastyToast.LENGTH_LONG,
                        TastyToast.SUCCESS);
                dbSQL.close();
                dbSQL2.close();


                Intent intent = new Intent();
                intent.setClass(AddObjectActivity.this, ObjectActivity.class);
                startActivity(intent);
            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
