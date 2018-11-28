package com.example.azia.diplom.timer;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.azia.diplom.R;
import com.example.azia.diplom.WebSite.WikipediaActivity;
import com.example.azia.diplom.WebSite.WolframActivity;
import com.example.azia.diplom.grade.GradeActivity;
import com.example.azia.diplom.helpers.Item;
import com.example.azia.diplom.homeWork.HomeWorkActivity;
import com.example.azia.diplom.mainMenu.HomeFragment;
import com.example.azia.diplom.mainMenu.ListFragment;
import com.example.azia.diplom.notes.NoteActivity;
import com.example.azia.diplom.object.ObjectActivity;
import com.example.azia.diplom.schedule.ScheduleActivity;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity implements ListFragment.Callback, NavigationView.OnNavigationItemSelectedListener {

    private static final long START_TIME_IN_MILLIS = 0;

    private TextView mTextViewCountDown;
    public DrawerLayout drawerLayout;
    private FloatingActionButton mButtonStartPause;
    private FloatingActionButton mButtonReset;

    private CountDownTimer mCountDownTimer;
    public NavigationView navigationView;
    public Toolbar toolbar;
    private SeekBar seekBar;

    private boolean mTimerRunning;
    private long mTimeLeftInMillis = 0;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        //getSupportActionBar().setIcon(R.drawable.ic_timer_white);

        seekBar = findViewById(R.id.timer_seekBar);
        mTextViewCountDown = findViewById(R.id.textView_countdown);
        mButtonStartPause = findViewById(R.id.fab_start);
        mButtonReset = findViewById(R.id.fab_stop);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        drawerLayout = findViewById(R.id.main_content_timer);
        navigationView = findViewById(R.id.nav_view_timer);
        toolbar = findViewById(R.id.toolbar_timer);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 10) {
                    mTextViewCountDown.setText("0" + String.valueOf(progress) + ":00");
                } else {
                    mTextViewCountDown.setText(String.valueOf(progress) + ":00");
                }
                mTimeLeftInMillis = progress * 60000;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    if (mTimeLeftInMillis == 0) {
                        TastyToast.makeText(getApplicationContext(), "Минимальное время - 1 минута  !", TastyToast.LENGTH_LONG,
                                TastyToast.CONFUSING);
                    } else {
                        startTimer();
                    }
                }
            }
        });

        mButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        updateCountDownText();


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Class fragmentClass = null;
        Intent i = null;

        switch (item.getItemId()) {
            case R.id.item_schedule:
                i = new Intent(TimerActivity.this, ScheduleActivity.class);
                startActivity(i);
                break;

            case R.id.item_timer:
                i = new Intent(TimerActivity.this, TimerActivity.class);
                startActivity(i);
                break;

            case R.id.item_object:
                i = new Intent(TimerActivity.this, ObjectActivity.class);
                startActivity(i);
                break;

            case R.id.item_homework:
                i = new Intent(TimerActivity.this, HomeWorkActivity.class);
                startActivity(i);
                break;

            case R.id.item_grade:
                i = new Intent(TimerActivity.this, GradeActivity.class);
                startActivity(i);
                break;

            case R.id.item_note:
                i = new Intent(TimerActivity.this, NoteActivity.class);
                startActivity(i);
                break;

            case R.id.item_wikipedia:
                i = new Intent(TimerActivity.this, WikipediaActivity.class);
                startActivity(i);
                break;

            case R.id.item_walfram:
                i = new Intent(TimerActivity.this, WolframActivity.class);
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
            intent.setClass(TimerActivity.this, ScheduleActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Таймер для выполнения \n\tдомашнего задания") {
            Intent intent = new Intent();
            intent.setClass(TimerActivity.this, TimerActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Предметы и \n\tпреподаватели") {
            Intent intent = new Intent();
            intent.setClass(TimerActivity.this, ObjectActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Домашнее задание") {
            Intent intent = new Intent();
            intent.setClass(TimerActivity.this, HomeWorkActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Оценки") {
            Intent intent = new Intent();
            intent.setClass(TimerActivity.this, GradeActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Записи") {
            Intent intent = new Intent();
            intent.setClass(TimerActivity.this, NoteActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Wolfram Alpha") {
            Intent intent = new Intent();
            intent.setClass(TimerActivity.this, WolframActivity.class);
            startActivity(intent);
        } else if (item.getTitle() == "Wikipedia") {
            Intent intent = new Intent();
            intent.setClass(TimerActivity.this, WikipediaActivity.class);
            startActivity(intent);
        }
        transaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }


    private void startTimer() {
        spinner.setVisibility(View.VISIBLE);
        mButtonStartPause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause));
        mButtonReset.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stop_black));
        seekBar.setEnabled(false);

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTextViewCountDown.setText("00:00");
                mTimerRunning = false;
                // mButtonStartPause.setText("Start");
                mButtonStartPause.setBackgroundResource(R.drawable.ic_play);
                mButtonStartPause.setEnabled(false);
                mButtonReset.setEnabled(true);
                spinner.setVisibility(View.GONE);
                mButtonReset.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stop_white));
                notification();
                seekBar.setEnabled(true);
                seekBar.post(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(0);
                        seekBar.refreshDrawableState();
                    }
                });
            }
        }.start();


        mTimerRunning = true;
        // mButtonStartPause.setText("pause");
        mButtonStartPause.setBackgroundResource(R.drawable.ic_pause);
        mButtonReset.setEnabled(false);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        //    mButtonStartPause.setText("Start");
        mButtonStartPause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play));
        mButtonReset.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stop_white));
        spinner.setVisibility(View.GONE);
        mButtonReset.setEnabled(true);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        mButtonReset.setEnabled(false);
        mButtonStartPause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_play));
        mButtonStartPause.setEnabled(true);
        spinner.setVisibility(View.GONE);
        seekBar.setEnabled(true);
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
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

    public void notification() {
        int notId = 1;
        String title = "Diary";
        String text = "Таймер - 00:00";

        Intent mainIntent = new Intent(getApplicationContext(), TimerActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext());

        notificationBuilder.setContentTitle(title)
                .setContentText(text)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.bottom_bar)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_MAX);

        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) getApplication().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notId, notification);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(TimerActivity.this, ScheduleActivity.class);
        startActivity(intent);
        finish();
    }

}
