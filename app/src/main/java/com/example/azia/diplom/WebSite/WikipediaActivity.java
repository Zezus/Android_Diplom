package com.example.azia.diplom.WebSite;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.grade.GradeActivity;
import com.example.azia.diplom.helpers.Item;
import com.example.azia.diplom.homeWork.HomeWorkActivity;
import com.example.azia.diplom.mainMenu.HomeFragment;
import com.example.azia.diplom.mainMenu.ListFragment;
import com.example.azia.diplom.notes.NoteActivity;
import com.example.azia.diplom.object.ObjectActivity;
import com.example.azia.diplom.schedule.ScheduleActivity;
import com.example.azia.diplom.timer.TimerActivity;

public class WikipediaActivity extends AppCompatActivity implements ListFragment.Callback, NavigationView.OnNavigationItemSelectedListener {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    private WebView webWikipedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikipedia);

        webWikipedia = findViewById(R.id.webWikipedia);
        drawerLayout = findViewById(R.id.main_content_wikipedia);
        navigationView = findViewById(R.id.nav_view_wikipedia);
        toolbar = findViewById(R.id.toolbar_wikipedia);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        webWikipedia.getSettings().setJavaScriptEnabled(true);
        webWikipedia.loadUrl("https://www.wikipedia.org/");
        webWikipedia.setWebViewClient(new MyWebViewClient());

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Class fragmentClass = null;
        Intent i = null;

        switch (item.getItemId()) {
            case R.id.item_schedule:
                i = new Intent(WikipediaActivity.this, ScheduleActivity.class);
                startActivity(i);
                break;

            case R.id.item_timer:
                i = new Intent(WikipediaActivity.this, TimerActivity.class);
                startActivity(i);
                break;

            case R.id.item_object:
                i = new Intent(WikipediaActivity.this, ObjectActivity.class);
                startActivity(i);
                break;

            case R.id.item_homework:
                i = new Intent(WikipediaActivity.this, HomeWorkActivity.class);
                startActivity(i);
                break;

            case R.id.item_grade:
                i = new Intent(WikipediaActivity.this, GradeActivity.class);
                startActivity(i);
                break;

            case R.id.item_note:
                i = new Intent(WikipediaActivity.this, NoteActivity.class);
                startActivity(i);
                break;

            case R.id.item_walfram:
                i = new Intent(WikipediaActivity.this, WolframActivity.class);
                startActivity(i);
                break;

            case R.id.item_wikipedia:
                i = new Intent(WikipediaActivity.this, WikipediaActivity.class);
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
            intent.setClass(WikipediaActivity.this, ScheduleActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Таймер для выполнения \n\tдомашнего задания") {
            Intent intent = new Intent();
            intent.setClass(WikipediaActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Предметы и \n\tпреподаватели") {
            Intent intent = new Intent();
            intent.setClass(WikipediaActivity.this, ObjectActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Домашнее задание") {
            Intent intent = new Intent();
            intent.setClass(WikipediaActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Оценки") {
            Intent intent = new Intent();
            intent.setClass(WikipediaActivity.this, GradeActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Записи") {
            Intent intent = new Intent();
            intent.setClass(WikipediaActivity.this, NoteActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Walfram Alpha") {
            Intent intent = new Intent();
            intent.setClass(WikipediaActivity.this, WolframActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Wikipedia") {
            Intent intent = new Intent();
            intent.setClass(WikipediaActivity.this, WikipediaActivity.class);
            startActivity(intent);
        }
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (webWikipedia.canGoBack()) {
            webWikipedia.goBack();
        } else {
            Intent intent = new Intent(WikipediaActivity.this, ScheduleActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
