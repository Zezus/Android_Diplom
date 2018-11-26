package com.example.azia.diplom.mainMenu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.azia.diplom.R;
import com.example.azia.diplom.helpers.Item;
import com.example.azia.diplom.homeWork.HomeWorkActivity;
import com.example.azia.diplom.notes.NoteActivity;
import com.example.azia.diplom.object.ObjectActivity;
import com.example.azia.diplom.schedule.ScheduleActivity;
import com.example.azia.diplom.timer.TimerActivity;

import me.anwarshahriar.calligrapher.Calligrapher;

public class MainActivity extends AppCompatActivity implements ListFragment.Callback, NavigationView.OnNavigationItemSelectedListener {

    private RelativeLayout itemLayout;
    // private ActionBar actionBar;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // actionBar = getSupportActionBar();
        // actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);

        Toolbar toolbar = findViewById(R.id.am_toolbar);
        setSupportActionBar(toolbar);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, "rus.ttf", true);

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // добавляем фрагмент
        ListFragment myFragment = new ListFragment();
        fragmentTransaction.add(R.id.container, myFragment);
        fragmentTransaction.commit();*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fl_main, new HomeFragment());
        ft.commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Class fragmentClass = null;
        Intent i = null;

        switch (item.getItemId()) {
            case R.id.item_schedule:
                i = new Intent(MainActivity.this, ScheduleActivity.class);
                startActivity(i);
                break;

            case R.id.item_timer:
                i = new Intent(MainActivity.this, TimerActivity.class);
                startActivity(i);
                break;

            case R.id.item_object:
                i = new Intent(MainActivity.this, ObjectActivity.class);
                startActivity(i);
                break;

            case R.id.item_homework:
                i = new Intent(MainActivity.this, HomeWorkActivity.class);
                startActivity(i);
                break;

            case R.id.item_note:
                i = new Intent(MainActivity.this, NoteActivity.class);
                startActivity(i);
                break;

            default:
                fragmentClass = HomeFragment.class;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void changeFragmentClicked(View view, Item item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (item.getTitle() == "Расписание") {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ScheduleActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Таймер для выполнения \n\tдомашнего задания") {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Предметы и \n\tпреподаватели") {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ObjectActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Домашнее задание") {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Записи") {
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, NoteActivity.class);
            startActivity(intent);
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
