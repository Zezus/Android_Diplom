package com.example.azia.diplom.homeWork;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBHomeWorkHelper;
import com.example.azia.diplom.dataBase.DBObjectHelper;
import com.example.azia.diplom.object.ObjectList;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.refactor.lib.colordialog.PromptDialog;

public class AddHomeWorkActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public DBHomeWorkHelper dbSQL;
    public DBObjectHelper dbSQL2;
    public SQLiteDatabase sqLiteDatabase;
    public Spinner objectSpinner;
    public String object;
    public int teacherPos;
    public Button btn_send;
    public Button datePicker;
    private final int Pick_imageNo = 0;
    public EditText task;
    Cursor cursor;
    String[] objects;
    private ArrayList<ObjectList> objectLists;

    private final int Pick_image = 1;
    public String date = "";
    private ImageView imageView;
    private Bitmap selectedImage;
    private FloatingActionButton imageSelect;
    String temp;
    private FloatingActionButton deleteImage;
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
        imageSelect = findViewById(R.id.hwadd_fab_image);
        imageView = findViewById(R.id.imageView);
        deleteImage = findViewById(R.id.fab_hw_delimage);


        Toolbar toolbar = findViewById(R.id.toolbar_add_hw);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        imageSelect.setOnClickListener(new View.OnClickListener() {

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
        });


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
            String image_v = imageUri.toString();

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
                dbSQL = new DBHomeWorkHelper(getApplicationContext());
                sqLiteDatabase = dbSQL.getWritableDatabase();
                dbSQL.addInfo(object_v, task_v, date_v, teacher_v, image_v, sqLiteDatabase);
                //dbSQL.addInfo(object_v, task_v, date_v, teacher_v, sqLiteDatabase);
                Toast.makeText(getApplicationContext(), "Домашнее задание добавлено ", Toast.LENGTH_LONG).show();
                dbSQL.close();


                Intent intent = new Intent();
                intent.setClass(AddHomeWorkActivity.this, HomeWorkActivity.class);
                startActivity(intent);
            }

        });

    }

    //Обрабатываем результат выбора в галерее:
    @Override
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
                        if (Integer.valueOf(b.length) < 1215000) {
                            //imageView.setImageBitmap(selectedImage);
                        } else {
                            new PromptDialog(this)
                                    .setDialogType(PromptDialog.DIALOG_TYPE_INFO)
                                    .setAnimationEnable(true)
                                    .setTitleText("INFO")
                                    .setContentText("Вы выбрали слишком большой файл, пожалуйста выберите другое фото. Мы над этим работаем")
                                    .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                                        @Override
                                        public void onClick(PromptDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    }).show();
                        }


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
        if (requestCode == Pick_imageNo) {
            selectedImage = BitmapFactory.decodeStream(null);
        }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
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


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        date = String.format("%1$tA", c) + " - " + DateFormat.getDateInstance().format(c.getTime());
        String butText = String.format("%1$tA", c) + System.getProperty("line.separator") + DateFormat.getDateInstance().format(c.getTime());
        Button btn = findViewById(R.id.hwadd_date_bt);
        btn.setText(butText);
    }

}
