package com.example.azia.diplom;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements ListFragment.Callback {

    private RelativeLayout itemLayout;
    // private ActionBar actionBar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // actionBar = getSupportActionBar();
        // actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // добавляем фрагмент
        ListFragment myFragment = new ListFragment();
        fragmentTransaction.add(R.id.container, myFragment);
        fragmentTransaction.commit();

    }


    @Override
    public void changeFragmentClicked(View view, Item item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (item.getTitle() == "iPhone X") {
            /*ItemFragment itemFragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putParcelable("item", item);
            itemFragment.setArguments(args);
            String tag = ItemFragment.class.getSimpleName();
            transaction.replace(R.id.container, itemFragment, tag);
            transaction.addToBackStack(tag)*/
            //  if (actionBar != null) {
            //       actionBar.setDisplayHomeAsUpEnabled(true);
            //  }
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TimerActivity.class);
            startActivity(intent);

        } else {
            //  ScheduleFragment scheduleFragment= new ScheduleFragment();
            // transaction.replace(R.id.container, scheduleFragment);
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
            //Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_LONG).show();
        }
        transaction.commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
