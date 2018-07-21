package com.example.azia.diplom;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddScheduleActivity extends AppCompatActivity {

    public EditText room;
    public EditText object;
    public Spinner spinner;
    public EditText time_start;
    public EditText time_end;
    public Button btn_send;
    public EditText day;
    public DBScheduleHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    String[] cities = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        spinner = findViewById(R.id.days_spinner);
        room = findViewById(R.id.room);
        object = findViewById(R.id.object);
        time_start = findViewById(R.id.time_start);
        time_end = findViewById(R.id.time_end);
        btn_send = findViewById(R.id.btn_send);
        day = findViewById(R.id.days);

        //spinner.setPrompt("Выберите день недели");
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
        // Определяем разметку для использования при выборе элемента
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        //spinner.setAdapter(adapter);

        //AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        //  @Override
        //public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
        //  day = (String) parent.getItemAtPosition(position);
        //Toast.makeText(getApplicationContext(), day, Toast.LENGTH_LONG).show();
        //}

        //@Override
        //public void onNothingSelected(AdapterView<?> parent) {

        //}
        //};
        //spinner.setOnItemSelectedListener(itemSelectedListener);


        btn_send.setOnClickListener(view -> {
            String object_v = object.getText().toString();
            String room_v = room.getText().toString();
            String timeStart_v = time_start.getText().toString();
            String timeEnd_v = time_end.getText().toString();
            String dayy = day.getText().toString();

            dbSQL = new DBScheduleHelper(getApplicationContext());
            sqLiteDatabase = dbSQL.getWritableDatabase();
            dbSQL.addInfo(object_v, room_v, timeStart_v, timeEnd_v, dayy, sqLiteDatabase);
            Toast.makeText(getApplicationContext(), "DATABASE INSERTED" + dayy, Toast.LENGTH_LONG).show();
            dbSQL.close();
        });

    }


}
