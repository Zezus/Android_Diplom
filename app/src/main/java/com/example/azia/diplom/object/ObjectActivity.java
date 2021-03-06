package com.example.azia.diplom.object;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import com.example.azia.diplom.R;
import com.example.azia.diplom.WebSite.WikipediaActivity;
import com.example.azia.diplom.WebSite.WolframActivity;
import com.example.azia.diplom.grade.GradeActivity;
import com.example.azia.diplom.helpers.Item;
import com.example.azia.diplom.homeWork.HomeWorkActivity;
import com.example.azia.diplom.mainMenu.HomeFragment;
import com.example.azia.diplom.mainMenu.ListFragment;
import com.example.azia.diplom.notes.NoteActivity;
import com.example.azia.diplom.schedule.ScheduleActivity;
import com.example.azia.diplom.timer.TimerActivity;

public class ObjectActivity extends AppCompatActivity implements ListFragment.Callback, NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object);

        drawerLayout = findViewById(R.id.main_content_object);
        navigationView = findViewById(R.id.nav_view_object);
        toolbar = findViewById(R.id.toolbar_object);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        // добавляем фрагмент
        ObjectFragment myFragment = new ObjectFragment();
        fragmentTransaction.add(R.id.ob2_container, myFragment);
        fragmentTransaction.commit();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddObjectSheet addObjectSheet = new AddObjectSheet();
                addObjectSheet.show(getSupportFragmentManager(), "example");

                //Intent intent = new Intent();
                // intent.setClass(ObjectActivity.this, AddObjectActivity.class);
                // startActivity(intent);
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Class fragmentClass = null;
        Intent i = null;

        switch (item.getItemId()) {
            case R.id.item_schedule:
                i = new Intent(ObjectActivity.this, ScheduleActivity.class);
                startActivity(i);
                break;

            case R.id.item_timer:
                i = new Intent(ObjectActivity.this, TimerActivity.class);
                startActivity(i);
                break;

            case R.id.item_object:
                i = new Intent(ObjectActivity.this, ObjectActivity.class);
                startActivity(i);
                break;

            case R.id.item_homework:
                i = new Intent(ObjectActivity.this, HomeWorkActivity.class);
                startActivity(i);
                break;

            case R.id.item_grade:
                i = new Intent(ObjectActivity.this, GradeActivity.class);
                startActivity(i);
                break;

            case R.id.item_note:
                i = new Intent(ObjectActivity.this, NoteActivity.class);
                startActivity(i);
                break;

            case R.id.item_wikipedia:
                i = new Intent(ObjectActivity.this, WikipediaActivity.class);
                startActivity(i);
                break;

            case R.id.item_walfram:
                i = new Intent(ObjectActivity.this, WolframActivity.class);
                startActivity(i);
                break;
            default:
                fragmentClass = HomeFragment.class;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void changeFragmentClicked(View view, Item item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (item.getTitle() == "Расписание") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, ScheduleActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Таймер для выполнения \n\tдомашнего задания") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Предметы и \n\tпреподаватели") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, ObjectActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Домашнее задание") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Оценки") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, GradeActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Записи") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, NoteActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Wolfram Alpha") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, WolframActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Wikipedia") {
            Intent intent = new Intent();
            intent.setClass(ObjectActivity.this, WikipediaActivity.class);
            startActivity(intent);
        }
        transaction.commit();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ObjectActivity.this, ScheduleActivity.class);
        startActivity(intent);
        finish();
    }
}
