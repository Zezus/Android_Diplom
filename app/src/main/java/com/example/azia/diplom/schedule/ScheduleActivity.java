package com.example.azia.diplom.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.azia.diplom.R;
import com.example.azia.diplom.WebSite.WikipediaActivity;
import com.example.azia.diplom.WebSite.WolframActivity;
import com.example.azia.diplom.helpers.Item;
import com.example.azia.diplom.homeWork.HomeWorkActivity;
import com.example.azia.diplom.mainMenu.HomeFragment;
import com.example.azia.diplom.mainMenu.ListFragment;
import com.example.azia.diplom.notes.NoteActivity;
import com.example.azia.diplom.object.ObjectActivity;
import com.example.azia.diplom.timer.TimerActivity;

public class ScheduleActivity extends AppCompatActivity implements ListFragment.Callback, NavigationView.OnNavigationItemSelectedListener {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public DrawerLayout drawerLayout;
    public NavigationView navigationView;
    public TabLayout tabLayout;
    Bundle b;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        drawerLayout = findViewById(R.id.main_content);
        tabLayout = findViewById(R.id.tabs);
        navigationView = findViewById(R.id.nav_view_schedule);
        Toolbar toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();

        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddScheduleSheet addScheduleSheet = new AddScheduleSheet();
                addScheduleSheet.show(getSupportFragmentManager(), "example");

                /*Intent intent = new Intent();
                intent.setClass(ScheduleActivity.this, AddScheduleActivity.class);
                startActivity(intent);*/
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Class fragmentClass = null;
        Intent i = null;

        switch (item.getItemId()) {
            case R.id.item_schedule:
                i = new Intent(ScheduleActivity.this, ScheduleActivity.class);
                startActivity(i);
                break;

            case R.id.item_timer:
                i = new Intent(ScheduleActivity.this, TimerActivity.class);
                startActivity(i);
                break;

            case R.id.item_object:
                i = new Intent(ScheduleActivity.this, ObjectActivity.class);
                startActivity(i);
                break;

            case R.id.item_homework:
                i = new Intent(ScheduleActivity.this, HomeWorkActivity.class);
                startActivity(i);
                break;

            case R.id.item_note:
                i = new Intent(ScheduleActivity.this, NoteActivity.class);
                startActivity(i);
                break;

            case R.id.item_walfram:
                i = new Intent(ScheduleActivity.this, WolframActivity.class);
                startActivity(i);
                break;

            case R.id.item_wikipedia:
                i = new Intent(ScheduleActivity.this, WikipediaActivity.class);
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
            intent.setClass(ScheduleActivity.this, ScheduleActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Таймер для выполнения \n\tдомашнего задания") {
            Intent intent = new Intent();
            intent.setClass(ScheduleActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Предметы и \n\tпреподаватели") {
            Intent intent = new Intent();
            intent.setClass(ScheduleActivity.this, ObjectActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Домашнее задание") {
            Intent intent = new Intent();
            intent.setClass(ScheduleActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Записи") {
            Intent intent = new Intent();
            intent.setClass(ScheduleActivity.this, NoteActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Walfram Alpha") {
            Intent intent = new Intent();
            intent.setClass(ScheduleActivity.this, WolframActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Wikipedia") {
            Intent intent = new Intent();
            intent.setClass(ScheduleActivity.this, WikipediaActivity.class);
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
        finish();
        System.exit(0);
    }


    /**
     * A placeholder fragment containing a simple view.
     */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position) {
                case 0:
                    Monday1Fragment day1 = new Monday1Fragment();
                    b = new Bundle();
                    b.putString("tabID", "1");
                    return day1;
                case 1:
                    Tuesday2Fragment day2 = new Tuesday2Fragment();
                    b = new Bundle();
                    b.putString("tabID", "2");
                    return day2;
                case 2:
                    Wednesday3Fragment day3 = new Wednesday3Fragment();
                    b = new Bundle();
                    b.putString("tabID", "3");
                    return day3;
                case 3:
                    Thursday4Fragment day4 = new Thursday4Fragment();
                    b = new Bundle();
                    b.putString("tabID", "4");
                    return day4;
                case 4:
                    Friday5Fragment day5 = new Friday5Fragment();
                    b = new Bundle();
                    b.putString("tabID", "5");
                    return day5;
                case 5:
                    Saturday6Fragment day6 = new Saturday6Fragment();
                    b = new Bundle();
                    b.putString("tabID", "6");
                    return day6;

                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }
    }
}
