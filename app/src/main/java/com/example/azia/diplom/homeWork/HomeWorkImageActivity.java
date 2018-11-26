package com.example.azia.diplom.homeWork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.azia.diplom.R;
import com.squareup.picasso.Picasso;

public class HomeWorkImageActivity extends AppCompatActivity {

    public ImageView imageIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_image);

        imageIV = findViewById(R.id.iv_image_hw);

        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("image")).resize(500, 0).into(imageIV);


    }
}
