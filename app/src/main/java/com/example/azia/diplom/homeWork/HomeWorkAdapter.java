package com.example.azia.diplom.homeWork;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.dataBase.DBHomeWorkHelper;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.List;

import cn.refactor.lib.colordialog.ColorDialog;

public class HomeWorkAdapter extends RecyclerView.Adapter<HomeWorkAdapter.ViewHolder> {

    private final Context context;
    private final List<HomeWorkList> homeWorkLists;
    public DBHomeWorkHelper dbSQL;
    public SQLiteDatabase sqLiteDatabase;
    private HomeWorkActivity mainActivity;
    private Dialog dialog;
    private Boolean flag = false;
    private Boolean isImageFitToScreen = true;
    private String fullScreenInd;


    public HomeWorkAdapter(Context context, List<HomeWorkList> homeWorkLists, HomeWorkActivity mainActivity) {
        this.context = context;
        this.homeWorkLists = homeWorkLists;
        this.mainActivity = mainActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.homework, null);

        ViewHolder viewHolder = new ViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_home_work);

        return new ViewHolder(view);
    }


    public static Bitmap decodeSampledBitmapFromResource(String path,
                                                         int reqWidth, int reqHeight) {

        // Читаем с inJustDecodeBounds=true для определения размеров
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Вычисляем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Читаем с использованием inSampleSize коэффициента
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Реальные размеры изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Вычисляем наибольший inSampleSize, который будет кратным двум
            // и оставит полученные размеры больше, чем требуемые
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        dbSQL = new DBHomeWorkHelper(context);
        sqLiteDatabase = dbSQL.getWritableDatabase();

        final HomeWorkList homeWorkList = homeWorkLists.get(position);
        holder.objectTV.setText(homeWorkList.getObject());
        holder.taskTV.setText(homeWorkList.getTask());
        holder.dateTV.setText(homeWorkList.getDate());
        holder.teacherTV.setText(homeWorkList.getTeacher());

        if (!homeWorkList.getImage().equals("")) {
            Picasso.with(context).load(homeWorkList.getImage()).resize(500, 0).into(holder.imageIV);
        } else {
            holder.imageIV.setImageBitmap(null);
        }
        holder.imageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    holder.imageIV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    holder.imageIV.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = true;
                    holder.imageIV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    holder.imageIV.setScaleType(ImageView.ScaleType.FIT_XY);
                }*/
                Intent intent = new Intent(context, HomeWorkImageActivity.class);
                intent.putExtra("image", homeWorkList.getImage());
                context.startActivity(intent);
            }
        });
        //holder.imageIV.setImageBitmap(getResizedBitmap(homeWorkList.getImage(), 200, 200));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ColorDialog dialog = new ColorDialog(context);
                dialog.setTitle("Удаление");
                dialog.setContentText("Вы уверены, что хотите удалить это задание?");
                //dialog.setContentImage(getResources().getDrawable(R.mipmap.sample_img));
                dialog.setPositiveListener("ДА", new ColorDialog.OnPositiveListener() {
                    @Override
                    public void onClick(ColorDialog dialog) {
                        dbSQL.deleteInfo(String.valueOf(homeWorkList.getId()), sqLiteDatabase);
                        TastyToast.makeText(context, "Задание удалено !", TastyToast.LENGTH_LONG,
                                TastyToast.INFO);
                        dbSQL.close();
                        dialog.cancel();
                        Intent intent = new Intent(context, HomeWorkActivity.class);
                        context.startActivity(intent);
                    }
                })
                        .setNegativeListener("НЕТ", new ColorDialog.OnNegativeListener() {
                            @Override
                            public void onClick(ColorDialog dialog) {
                                dialog.cancel();
                            }
                        }).show();
//                AlertDialog.Builder aldial = new AlertDialog.Builder(context);
//                aldial.setMessage("Удалить предмет?").setCancelable(false)
//                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dbSQL.deleteInfo(String.valueOf(objectList.getId()), sqLiteDatabase);
//                                Toast.makeText(context, "Предмет удален", Toast.LENGTH_LONG).show();
//                                dbSQL.close();
//                            }
//                        })
//                        .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialog.cancel();
//                            }
//                        });
//                AlertDialog alert = aldial.create();
//                alert.setTitle("Program");
//                alert.show();
            }
        });

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
    public int getItemCount() {
        return homeWorkLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView objectTV;
        TextView taskTV;
        TextView dateTV;
        TextView teacherTV;
        ImageView imageIV;
        FloatingActionButton delete;


        public ViewHolder(View itemView) {
            super(itemView);

            objectTV = itemView.findViewById(R.id.hw_tv_object);
            taskTV = itemView.findViewById(R.id.hw_tv_task);
            dateTV = itemView.findViewById(R.id.hw_tv_date);
            teacherTV = itemView.findViewById(R.id.hw_tv_teacher);
            imageIV = itemView.findViewById(R.id.hw_iv_image);
            delete = itemView.findViewById(R.id.hw_del);

        }
    }
}
