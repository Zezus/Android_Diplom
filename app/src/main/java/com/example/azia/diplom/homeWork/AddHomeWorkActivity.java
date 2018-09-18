package com.example.azia.diplom.homeWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBHomeWorkHelper;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.example.azia.diplom.object.ObjectList;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddHomeWorkActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public DBHomeWorkHelper dbSQL;
    public DBObjectHelper dbSQL2;
    public SQLiteDatabase sqLiteDatabase;
    public Spinner objectSpinner;
    public String object;
    public int teacherPos;
    public Button btn_send;
    public Button datePicker;
    public String date;
    public EditText task;
    Cursor cursor;
    String[] objects;
    private ArrayList<ObjectList> objectLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);

        datePicker = findViewById(R.id.hwadd_date_bt);
        objectSpinner = findViewById(R.id.hwadd_object_sp);
        btn_send = findViewById(R.id.hwadd_btn_send);
        task = findViewById(R.id.hwadd_task);


        datePicker.setOnClickListener(view -> {
            DialogFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "date picker");
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, objects);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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


        btn_send.setOnClickListener(view -> {
            String object_v = object;
            String task_v = task.getText().toString();
            String date_v = date.toString();
            String teacher_v = objectLists.get(teacherPos).getTeacher();

            dbSQL = new DBHomeWorkHelper(getApplicationContext());
            sqLiteDatabase = dbSQL.getWritableDatabase();
            dbSQL.addInfo(object_v, task_v, date_v, teacher_v, sqLiteDatabase);
            Toast.makeText(getApplicationContext(), "Домашнее задание добавлено ", Toast.LENGTH_LONG).show();
            dbSQL.close();


            Intent intent = new Intent();
            intent.setClass(AddHomeWorkActivity.this, HomeWorkActivity.class);
            startActivity(intent);

        });

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        date = DateFormat.getDateInstance().format(c.getTime());

        TextView test = findViewById(R.id.hwadd_test);
        test.setText(date);
    }
}
