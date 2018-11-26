package com.example.azia.diplom.schedule;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.example.azia.diplom.dataBase.DBScheduleHelper;
import com.example.azia.diplom.object.ObjectList;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddScheduleActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public EditText room;
    public Spinner objectSpinner;
    public Spinner spinner;
    public EditText time_viewstart;
    public EditText time_viewend;
    public Button btn_send;
    public Button time_add;
    public Button time_add2;
    public FloatingActionButton time_addstart;
    public FloatingActionButton time_addend;
    public TextView time_view;
    public String day;
    public String object;
    public DBScheduleHelper dbSQL;
    public DBObjectHelper dbSQL2;
    public SQLiteDatabase sqLiteDatabase;
    public SQLiteDatabase sqLiteDatabase2;
    public int teacherPos;
    public String time_sort;
    public Boolean flag1 = false;
    public Boolean flag2 = false;
    Cursor cursor;
    String[] cities = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
    String[] objects;
    private ArrayList<ObjectList> objectLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        Toolbar toolbar = findViewById(R.id.toolbar_add_schedule);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.days_spinner);
        room = findViewById(R.id.room);
        objectSpinner = findViewById(R.id.object);
        time_viewstart = findViewById(R.id.time_start);
        time_viewend = findViewById(R.id.time_end);
        btn_send = findViewById(R.id.btn_send);
        time_addstart = findViewById(R.id.fab_timeadd_start);
        time_addend = findViewById(R.id.fab_timeadd_end);

        time_addstart.setOnClickListener(view -> {
            flag1 = false;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), " time picker");
            flag1 = true;
        });

        time_addend.setOnClickListener(view -> {
            flag2 = false;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), " time picker");
            flag2 = true;
        });


        objectLists = new ArrayList<>();
        dbSQL2 = new DBObjectHelper(getApplicationContext());
        sqLiteDatabase = dbSQL2.getReadableDatabase();
        cursor = dbSQL2.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()) {
            do {

                String object, teacher, id;
                object = cursor.getString(0);
                teacher = cursor.getString(1);
                id = cursor.getString(2);
                ObjectList object1 = new ObjectList();
                object1.setObject(object);
                object1.setTeacher(teacher);
                object1.setId(Integer.parseInt(id));
                objectLists.add(object1);
            } while (cursor.moveToNext());
        }
        objects = new String[objectLists.size()];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = objectLists.get(i).getObject();
        }

        objectSpinner.setPrompt("Выберите предмет");
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_layout, objects);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(R.layout.spinner_text_layout);
        // Применяем адаптер к элементу spinner
        objectSpinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                object = (String) parent.getItemAtPosition(position);
                teacherPos = position;
                //Toast.makeText(getApplicationContext(), day, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        objectSpinner.setOnItemSelectedListener(itemSelectedListener);

        spinner.setPrompt("Выберите день недели");
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_text_layout, cities);
        // Определяем разметку для использования при выборе элемента
        adapter2.setDropDownViewResource(R.layout.spinner_text_layout);
        // Применяем адаптер к элементу spinner
        spinner.setAdapter(adapter2);

        AdapterView.OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                day = (String) parent.getItemAtPosition(position);
                //Toast.makeText(getApplicationContext(), day, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinner.setOnItemSelectedListener(itemSelectedListener2);


        btn_send.setOnClickListener(view -> {
            String object_v = object;
            String room_v = room.getText().toString();
            String timeStart_v = time_viewstart.getText().toString();
            String timeEnd_v = time_viewend.getText().toString();
            String time_sort_v = time_sort;
            String dayy = day;
            String teacher_v = objectLists.get(teacherPos).getTeacher();

            if (object_v.length() == 0 || room_v.length() == 0 || timeStart_v.length() == 0 || timeEnd_v.length() == 0 ||
                    time_sort_v.length() == 0 || dayy.length() == 0 || teacher_v.length() == 0) {
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

                dbSQL = new DBScheduleHelper(getApplicationContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(object_v, room_v, timeStart_v, timeEnd_v, time_sort_v, dayy, teacher_v, sqLiteDatabase);
                TastyToast.makeText(getApplicationContext(), "Предмет добавлен  !", TastyToast.LENGTH_LONG,
                        TastyToast.SUCCESS);
                dbSQL.close();


                Intent intent = new Intent();
                intent.setClass(AddScheduleActivity.this, ScheduleActivity.class);
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


    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String hour = "";
        String minute = "";
        if (i < 10) {
            hour = "0" + String.valueOf(i);
        } else hour = String.valueOf(i);
        if (i1 < 10) {
            minute = "0" + String.valueOf(i1);
        } else minute = String.valueOf(i1);

        if (flag1 == true) {
            time_viewstart.setText(hour + ":" + minute);
            time_sort = hour + minute;
        }
        if (flag2 == true) {
            time_viewend.setText(hour + ":" + minute);
        }

        flag1 = false;
        flag2 = false;
    }


}


