package com.example.azia.diplom.schedule;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.example.azia.diplom.dataBase.DBScheduleHelper;
import com.example.azia.diplom.object.ObjectList;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Calendar;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddScheduleSheet extends BottomSheetDialogFragment implements TimePickerDialog.OnTimeSetListener {

    public EditText room;
    public Spinner objectSpinner;
    public Spinner spinner;
    public EditText time_viewstart;
    public EditText time_viewend;
    public Button btn_send;
    public FloatingActionButton time_addstart;
    public FloatingActionButton time_addend;
    public FloatingActionButton fab_keyboard;
    public String day;
    public String object;
    public DBScheduleHelper dbSQL;
    public DBObjectHelper dbSQL2;
    public SQLiteDatabase sqLiteDatabase;
    public int teacherPos;
    public String time_sort;
    public Boolean flag1 = false;
    public Boolean flag2 = false;
    public TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

        Calendar c = Calendar.getInstance();

        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            String hour = "";
            String minute = "";
            if (i < 10) {
                hour = "0" + String.valueOf(i); //i
            } else hour = String.valueOf(i); //i
            if (i1 < 10) {
                minute = "0" + String.valueOf(i1); //i1
            } else minute = String.valueOf(i1); //i1

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
    };
    Cursor cursor;
    String[] cities = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
    String[] objects;
    private ArrayList<ObjectList> objectLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_schedule_sheet, container, false);

        spinner = v.findViewById(R.id.days_spinner);
        room = v.findViewById(R.id.room);
        objectSpinner = v.findViewById(R.id.object);
        time_viewstart = v.findViewById(R.id.time_start);
        time_viewend = v.findViewById(R.id.time_end);
        btn_send = v.findViewById(R.id.btn_send);
        time_addstart = v.findViewById(R.id.fab_timeadd_start);
        time_addend = v.findViewById(R.id.fab_timeadd_end);
        fab_keyboard = v.findViewById(R.id.schedule_fab_keyboard_sheet);

        /*time_addstart.setOnClickListener(view -> {
            flag1 = false;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getFragmentManager(), " time picker");
            flag1 = true;
        });*/

        /*time_addend.setOnClickListener(view -> {
            flag2 = false;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getFragmentManager(), " time picker");
            flag2 = true;
        });*/

        fab_keyboard.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        });

        time_addstart.setOnClickListener(view -> {
            flag1 = false;
            createDialog().show();
            flag1 = true;
        });

        time_addend.setOnClickListener(view -> {
            flag2 = false;
            createDialog().show();
            flag2 = true;
        });

        objectLists = new ArrayList<>();
        dbSQL2 = new DBObjectHelper(getContext());
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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_text_layout, objects);
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
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), R.layout.spinner_text_layout, cities);
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

                dbSQL = new DBScheduleHelper(getContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(object_v, room_v, timeStart_v, timeEnd_v, time_sort_v, dayy, teacher_v, sqLiteDatabase);
                TastyToast.makeText(getContext(), "Предмет добавлен  !", TastyToast.LENGTH_LONG,
                        TastyToast.SUCCESS);
                dbSQL.close();

                Intent intent = new Intent(getContext(), ScheduleActivity.class);
                getActivity().startActivity(intent);
            }

        });

        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            getActivity().finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public TimePickerDialog createDialog() {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), timePickerListener, hour, minute, true);
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
