package com.example.azia.diplom;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 5000;

    private TextView mTextViewCountDown;
    private FloatingActionButton mButtonStartPause;
    private FloatingActionButton mButtonReset;

    private CountDownTimer mCountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_timer);
        getSupportActionBar().setTitle("        Timer");

        mTextViewCountDown = findViewById(R.id.textView_countdown);
        mButtonStartPause = findViewById(R.id.fab_start);
        mButtonReset = findViewById(R.id.fab_stop);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
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

    private void startTimer() {
        spinner.setVisibility(View.VISIBLE);
        mButtonStartPause.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_pause));
        mButtonReset.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stopnot));

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
                mButtonReset.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stop));

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
        mButtonReset.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_stop));
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
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mTextViewCountDown.setText(timeLeftFormatted);
    }

}
