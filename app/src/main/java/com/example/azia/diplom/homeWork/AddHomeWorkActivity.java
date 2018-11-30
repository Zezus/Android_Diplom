package com.example.azia.diplom.homeWork;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBHomeWorkHelper;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.example.azia.diplom.object.ObjectList;
import com.example.azia.diplom.schedule.TimePickerFragment;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddHomeWorkActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    public DBHomeWorkHelper dbSQL;
    public DBObjectHelper dbSQL2;
    public SQLiteDatabase sqLiteDatabase;
    public Spinner objectSpinner;
    public String object;
    public int teacherPos;
    public Button btn_send;
    public Button datePicker;
    public Button dateReminder;
    public FloatingActionButton timeReminder;
    public FloatingActionButton keyboard;
    public Switch reminderSwitch;
    private final int Pick_imageNo = 0;
    public EditText task;
    public EditText time_view;
    Cursor cursor;
    String[] objects;
    private ArrayList<ObjectList> objectLists;

    private final int Pick_image = 1;
    public String date = "";
    public String date_sort = "";
    public String dateReminderText = "";
    public String hour;
    public String minute;
    public int year;
    public int month;
    public int day;
    public Boolean flag1 = false;
    public Boolean flagDate = false;
    public Boolean flagDateReminder = false;
    public Date time;
    // private FloatingActionButton imageSelect;
    String temp;
    private Boolean flagEnd = false;
    // private ImageView imageView;
    private Bitmap selectedImage;
    //private FloatingActionButton deleteImage;
    private Uri imageUri;

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public String BitMapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);
        } else temp = "-";

        return temp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);


        datePicker = findViewById(R.id.hwadd_date_bt);
        objectSpinner = findViewById(R.id.hwadd_object_sp);
        btn_send = findViewById(R.id.hwadd_btn_send);
        task = findViewById(R.id.hwadd_task);
        reminderSwitch = findViewById(R.id.hwadd_switch);
        dateReminder = findViewById(R.id.hwadd_dateRem_bt);
        time_view = findViewById(R.id.et_hw_timeview);
        timeReminder = findViewById(R.id.fab_hw_timeadd);
        keyboard = findViewById(R.id.hwadd_fab_keyboard);

        //imageSelect = findViewById(R.id.hwadd_fab_image);
        //imageView = findViewById(R.id.imageView);
        //deleteImage = findViewById(R.id.fab_hw_delimage);


        Toolbar toolbar = findViewById(R.id.toolbar_add_hw);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dateReminder.setVisibility(Button.INVISIBLE);
        timeReminder.setVisibility(Button.INVISIBLE);
        time_view.setVisibility(Button.INVISIBLE);
        dateReminderText = null;
        time = null;

        timeReminder.setOnClickListener(view -> {
            flag1 = false;
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), " time picker");
            flag1 = true;
        });

        dateReminder.setOnClickListener(view -> {
            flagDateReminder = true;
            DialogFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), "date picker");
        });

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    dateReminder.setVisibility(Button.VISIBLE);
                    timeReminder.setVisibility(Button.VISIBLE);
                    time_view.setVisibility(Button.VISIBLE);
                } else {
                    dateReminder.setVisibility(Button.INVISIBLE);
                    timeReminder.setVisibility(Button.INVISIBLE);
                    time_view.setVisibility(Button.INVISIBLE);
                    dateReminder.setText("Выберите дату");
                    time_view.setText(null);

                }
            }
        });

        keyboard.setOnClickListener(view -> {
            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });


        /*imageSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });

        deleteImage.setOnClickListener(view -> {
            imageView.setImageBitmap(null);
            selectedImage = BitmapFactory.decodeStream(null);
            temp = "-";
            deleteImage.setVisibility(Button.INVISIBLE);
            imageUri = null;
        });*/


        datePicker.setOnClickListener(view -> {
            flagDate = true;
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


        btn_send.setOnClickListener(view -> {
            flagEnd = false;
            String object_v = object;
            String task_v = task.getText().toString();
            String date_v = date.toString();
            String teacher_v = objectLists.get(teacherPos).getTeacher();
            String image_v;
            if (imageUri == null) {
                image_v = "";
            } else image_v = imageUri.toString();
            String date_sort_v = date_sort.toString();

            if (object_v.length() == 0 || task_v.length() == 0 || date_v.length() == 0 || teacher_v.length() == 0) {
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

                if (reminderSwitch.isChecked()) {

                    int text_timeView = time_view.length();
                    String text_dateReminder = dateReminder.getText().toString();

                    if (text_timeView != 0 && text_dateReminder != "Выберите дату") {
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
                        calEvent.put(CalendarContract.Events.TITLE, "Расписание! Домашнее задание");
                        calEvent.put(CalendarContract.Events.DESCRIPTION, task_v);
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

                        dbSQL = new DBHomeWorkHelper(getApplicationContext());
                        sqLiteDatabase = dbSQL.getWritableDatabase();
                        dbSQL.addInfo(object_v, task_v, date_v, teacher_v, image_v, date_sort_v, sqLiteDatabase);
                        //dbSQL.addInfo(object_v, task_v, date_v, teacher_v, sqLiteDatabase);
                        TastyToast.makeText(getApplicationContext(), "Домашнее задание добавлено ", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        dbSQL.close();


                        Intent intent = new Intent();
                        intent.setClass(AddHomeWorkActivity.this, HomeWorkActivity.class);
                        startActivity(intent);
                    } else {
                        new PromptDialog(this)
                                .setDialogType(PromptDialog.DIALOG_TYPE_WARNING)
                                .setAnimationEnable(true)
                                .setTitleText("")
                                .setContentText("Заполните дату и время для напоминания")
                                .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                                    @Override
                                    public void onClick(PromptDialog dialog) {
                                        dialog.dismiss();
                                    }
                                }).show();
                    }

                } else {
                    dbSQL = new DBHomeWorkHelper(getApplicationContext());
                    sqLiteDatabase = dbSQL.getWritableDatabase();
                    dbSQL.addInfo(object_v, task_v, date_v, teacher_v, image_v, date_sort_v, sqLiteDatabase);
                    //dbSQL.addInfo(object_v, task_v, date_v, teacher_v, sqLiteDatabase);
                    TastyToast.makeText(getApplicationContext(), "Домашнее задание добавлено ", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    dbSQL.close();


                    Intent intent = new Intent();
                    intent.setClass(AddHomeWorkActivity.this, HomeWorkActivity.class);
                    startActivity(intent);
                }
            }

        });

    }

    //Обрабатываем результат выбора в галерее:
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        if (requestCode == Pick_image) {
            if (resultCode == RESULT_OK) {
                try {
                    deleteImage.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    //Получаем URI изображения, преобразуем его в Bitmap
                    //объект и отображаем в элементе ImageView нашего интерфейса:
                    imageUri = imageReturnedIntent.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    selectedImage = BitmapFactory.decodeStream(imageStream);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();

                    Picasso.with(getApplicationContext()).load(imageUri).into(imageView);
                    // imageView.setImageBitmap(getResizedBitmap(selectedImage,200, 200));
                    //    imageView.setImageBitmap(decodeSampledBitmapFromResource(getResources(), selectedImage.getGenerationId(), 150,150));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == Pick_imageNo) {
            selectedImage = BitmapFactory.decodeStream(null);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {  // это не нужно
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }
*/
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
        }

        flag1 = false;
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        if (flagDate) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            if (day < 10) {
                String dayZ = "0" + day;
                date_sort = year + "" + month + "" + dayZ;
            } else date_sort = year + "" + month + "" + day;

            date = String.format("%1$tA", c) + " - " + DateFormat.getDateInstance().format(c.getTime());
            String butText = String.format("%1$tA", c) + System.getProperty("line.separator") + DateFormat.getDateInstance().format(c.getTime());
            Button btn = findViewById(R.id.hwadd_date_bt);
            btn.setText(butText);
            flagDate = false;
        }
        if (flagDateReminder) {
            Calendar c = Calendar.getInstance();
            this.year = year;
            this.month = month;
            this.day = day;
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            dateReminderText = String.format("%1$tA", c) + " - " + DateFormat.getDateInstance().format(c.getTime());
            String butText = String.format("%1$tA", c) + System.getProperty("line.separator") + DateFormat.getDateInstance().format(c.getTime());
            Button btn = findViewById(R.id.hwadd_dateRem_bt);
            btn.setText(butText);
            flagDateReminder = false;
        }

    }

}
