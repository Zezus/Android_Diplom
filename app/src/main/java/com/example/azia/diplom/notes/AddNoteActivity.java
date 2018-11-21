package com.example.azia.diplom.notes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBNotesHelper;
import com.example.azia.diplom.homeWork.DatePickerFragment;
import com.example.azia.diplom.schedule.TimePickerFragment;
import com.sdsmdg.tastytoast.TastyToast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddNoteActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {


    public EditText noteET;
    public EditText titleET;
    public EditText time_view;
    public Button btn_send;
    public Button datePicker;
    public FloatingActionButton time_add;
    public FloatingActionButton keyboard;
    public Switch reminder;

    public String time_sort;
    public String date = "";
    public String hour;
    public String minute;
    public Boolean flag1 = false;
    public Date time;
    public int year;
    public int month;
    public int day;

    public DBNotesHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        titleET = findViewById(R.id.noteadd_title);
        noteET = findViewById(R.id.noteadd_note);
        btn_send = findViewById(R.id.noteadd_button);
        time_add = findViewById(R.id.fab_note_timeadd);
        time_view = findViewById(R.id.et_note_timeview);
        datePicker = findViewById(R.id.noteadd_date_bt);
        keyboard = findViewById(R.id.note_fab_keyboard);
        reminder = findViewById(R.id.note_switch);

        Toolbar toolbar = findViewById(R.id.toolbar_add_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datePicker.setVisibility(Button.INVISIBLE);
        time_add.setVisibility(Button.INVISIBLE);
        time_view.setVisibility(Button.INVISIBLE);
        date = null;
        time = null;


        time_add.setOnClickListener(view -> {
            flag1 = false;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), " time picker");
            flag1 = true;
        });

        datePicker.setOnClickListener(view -> {
            DialogFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "date picker");
        });

        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    datePicker.setVisibility(Button.VISIBLE);
                    time_add.setVisibility(Button.VISIBLE);
                    time_view.setVisibility(Button.VISIBLE);
                } else {
                    datePicker.setVisibility(Button.INVISIBLE);
                    time_add.setVisibility(Button.INVISIBLE);
                    time_view.setVisibility(Button.INVISIBLE);
                }
            }
        });

        keyboard.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });

        btn_send.setOnClickListener(view -> {
            String note_v = noteET.getText().toString();
            String title_v = titleET.getText().toString();

            if (note_v.length() == 0 || title_v.length() == 0) {
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
                dbSQL.addInfo(note_v, title_v, sqLiteDatabase);
                TastyToast.makeText(getApplicationContext(), "Добавлено  !", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                dbSQL.close();

                if (reminder.isChecked()) {

                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hour));
                    cal.set(Calendar.MINUTE, Integer.valueOf(minute));
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, day);
                    time = cal.getTime();

                    ContentResolver cr = getApplicationContext().getContentResolver();
                    ContentValues calEvent = new ContentValues();
                    calEvent.put(CalendarContract.Events.CALENDAR_ID, 1); // XXX pick)
                    calEvent.put(CalendarContract.Events.TITLE, "Diary !");
                    calEvent.put(CalendarContract.Events.DESCRIPTION, title_v + ". " + note_v);
                    calEvent.put(CalendarContract.Events.DTSTART, time.getTime());
                    calEvent.put(CalendarContract.Events.DTEND, time.getTime() + (60 * 60 * 1000));
                    calEvent.put(CalendarContract.Events.HAS_ALARM, 1);
                    calEvent.put(CalendarContract.Events.EVENT_TIMEZONE, CalendarContract.Calendars.CALENDAR_TIME_ZONE);

//save an event
                    @SuppressLint("MissingPermission") final Uri uri = cr.insert(CalendarContract.Events.CONTENT_URI, calEvent);

                    int dbId = Integer.parseInt(uri.getLastPathSegment());

//Now create a reminder and attach to the reminder
                    ContentValues reminders = new ContentValues();
                    reminders.put(CalendarContract.Reminders.EVENT_ID, dbId);
                    reminders.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
                    reminders.put(CalendarContract.Reminders.MINUTES, 0);

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    final Uri reminder = cr.insert(CalendarContract.Reminders.CONTENT_URI, reminders);

                    int added = Integer.parseInt(reminder.getLastPathSegment());

//this means reminder is added
                    if (added > 0) {
                        Intent vieww = new Intent(Intent.ACTION_VIEW);
                        vieww.setData(uri); // enter the uri of the event not the reminder

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                            vieww.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP
                                    | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                        } else {
                            vieww.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                    Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                    Intent.FLAG_ACTIVITY_NO_HISTORY |
                                    Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                        }

                    }
//                notId = (int)((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
//                Intent intent2 = new Intent();
//                intent2.setClass(AddNoteActivity.this, AlarmReceiver.class);
//                intent2.putExtra("notId", notId);
//                intent2.putExtra("title", title_v);
//                intent2.putExtra("text", note_v);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(AddNoteActivity.this, 0, intent2, PendingIntent.FLAG_CANCEL_CURRENT);
//                AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
//                int hourr = Integer.valueOf(hour);
//                int minutee = Integer.valueOf(minute);
//                Calendar startTime = Calendar.getInstance();
//                startTime.set(Calendar.HOUR_OF_DAY, hourr);
//                startTime.set(Calendar.MINUTE, minutee);
//                startTime.set(Calendar.SECOND, 0);
//                startTime.set(Calendar.YEAR, yearDate);
//                startTime.set(Calendar.MONTH, monthDate);
//                startTime.set(Calendar.DAY_OF_MONTH, dayDate);
//                long alarmStartTime = startTime.getTimeInMillis();
//
//
//                alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime, pendingIntent);
                }
                Intent intent = new Intent();
                intent.setClass(AddNoteActivity.this, NoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hour = "";
        minute = "";
        if (i < 10) {
            hour = "0" + String.valueOf(i);
        } else hour = String.valueOf(i);
        if (i1 < 10) {
            minute = "0" + String.valueOf(i1);
        } else minute = String.valueOf(i1);

        if (flag1 == true) {
            time_view.setText(hour + ":" + minute);
            time_sort = hour + minute;
        }

        flag1 = false;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        this.year = year;
        this.month = month;
        this.day = day;
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        date = String.format("%1$tA", c) + " - " + DateFormat.getDateInstance().format(c.getTime());
        String butText = String.format("%1$tA", c) + System.getProperty("line.separator") + DateFormat.getDateInstance().format(c.getTime());
        Button btn = findViewById(R.id.noteadd_date_bt);
        btn.setText(butText);
    }
}
