package com.awesome.scott.stop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class DoubleCounterActivity extends Activity {

    TextView counterDisplay1;
    TextView counterDisplay2;

    Button stopButton1;
    Button stopButton2;
    Button startButton;

    Random randomGenerator;

    Timer T1;
    Timer T2;

    int n;
    final int maxValue = 120;
    final int goalValue = 100;
    final int delayValue = 40;
    private int speedValue = 30;
    private int selectedDoubleLevel = 1;

    private boolean T1stop;
    private boolean T2stop;

    int counterValue1;
    int counterValue2;
    int cheatValue = 0;

    int numberOfAttemptsDouble = 0;
    TextView attemptsDisplay;
    TextView speedDisplay;

    MediaPlayer player;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    SharedPreferences progressionFile;
    SharedPreferences.Editor progressionEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double_counter);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1876787092384518~2010397989");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Intent intentExtras = getIntent();
        retrieveSelectedlevel(intentExtras);

        progressionFile = getSharedPreferences("levelProgressionProgressFile", MODE_PRIVATE);
        progressionEditor = progressionFile.edit();
        sharedPref = getSharedPreferences("NumberOfAttemptSaveFile", MODE_PRIVATE);
        editor = sharedPref.edit();

        SharedPreferences loadDoubleAttempts = getSharedPreferences("NumberOfAttemptSaveFile", MODE_PRIVATE);

        if (selectedDoubleLevel == 1) {
            numberOfAttemptsDouble = loadDoubleAttempts.getInt("doubleStopAttempts", 0);
        } else {
            numberOfAttemptsDouble = loadDoubleAttempts.getInt("doubleStopAttemptsx2", 0);
        }

        attemptsDisplay = (TextView) findViewById(R.id.attemptDisplay);
        speedDisplay = (TextView) findViewById(R.id.speedDisplay1);

        attemptsDisplay.setText(String.valueOf(numberOfAttemptsDouble));
        randomGenerator = new Random();

        counterDisplay1 = (TextView) findViewById(R.id.counterView1);
        counterDisplay2 = (TextView) findViewById(R.id.counterView2);

        stopButton1 = (Button) findViewById(R.id.stopButton1);
        stopButton2 = (Button) findViewById(R.id.stopButton2);
        startButton = (Button) findViewById(R.id.startButton);

        if (selectedDoubleLevel == 1) {
            speedDisplay.setText("x1");
        } else {
            speedDisplay.setText("x2");
        }


    }

    private void retrieveSelectedlevel(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (!bundle.isEmpty()) {
            selectedDoubleLevel = bundle.getInt("doubleLevel");
        }
        if (selectedDoubleLevel == 1) {
            speedValue = bundle.getInt("doubleSpeedx1");
        } else {
            speedValue = bundle.getInt("doublSpeedx2");
        }
    }


    public void startButtonPressed(View view) {
        player = MediaPlayer.create(DoubleCounterActivity.this, R.raw.gun_shot);
        player.start();
        player.setOnCompletionListener(new
                                               MediaPlayer.OnCompletionListener() {
                                                   @Override
                                                   public void onCompletion(MediaPlayer arg0) {
                                                       player.reset();
                                                       player = MediaPlayer.create(DoubleCounterActivity.this, R.raw.suspense_2);

                                                   }
                                               });
        player.start();

        numberOfAttemptsDouble++;
        attemptsDisplay.setText(String.valueOf(numberOfAttemptsDouble));

        if (selectedDoubleLevel == 1) {
            editor.putInt("doubleStopAttempts", numberOfAttemptsDouble);
            editor.apply();
        } else {
            editor.putInt("doubleStopAttemptsx2", numberOfAttemptsDouble);
            editor.apply();
        }


        final int n = randomGenerator.nextInt(2);

        T1 = new Timer();
        T2 = new Timer();
        T1stop = false;
        T2stop = false;


        switch (n) {


            //start T1
            case 0:

                T1.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (counterValue1 < maxValue) {

                                    counterValue1++;
                                    counterDisplay1.setText(String.valueOf(counterValue1));
                                    if (counterValue1 == delayValue) {
                                        stopButton2.setEnabled(true);
                                        stopButton1.setEnabled(true);
                                        T2.scheduleAtFixedRate(new TimerTask() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (counterValue2 < maxValue) {

                                                            counterValue2++;
                                                            counterDisplay2.setText(String.valueOf(counterValue2));

                                                        } else {
                                                            noAttempt();
                                                        }
                                                    }
                                                });
                                            }
                                        }, 0, speedValue);
                                    }

                                }

                            }
                        });
                    }
                }, 0, speedValue);
                break;

            //start T2
            case 1:

                T2.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (counterValue2 < maxValue) {

                                    counterValue2++;
                                    counterDisplay2.setText(String.valueOf(counterValue2));
                                    if (counterValue2 == delayValue) {
                                        stopButton1.setEnabled(true);
                                        stopButton2.setEnabled(true);
                                        T1.scheduleAtFixedRate(new TimerTask() {
                                            @Override
                                            public void run() {
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        if (counterValue1 < maxValue) {

                                                            counterValue1++;
                                                            counterDisplay1.setText(String.valueOf(counterValue1));

                                                        } else {
                                                            noAttempt();
                                                        }


                                                    }
                                                });
                                            }
                                        }, 0, speedValue);
                                    }

                                }


                            }
                        });
                    }
                }, 0, speedValue);
                break;
        }
        startButton.setEnabled(false);


    }

    public void T1StopPressed(View view) {
        MediaPlayer stopPlayer = MediaPlayer.create(DoubleCounterActivity.this, R.raw.brake);
        stopPlayer.start();

        T1.cancel();
        T1 = null;
        T1stop = true;
        if (T1stop == true && T2stop == true) {
            calculateScore();
            stopButton1.setEnabled(false);
            stopButton2.setEnabled(false);
            counterValue1 = 0;
            counterValue2 = 0;
            counterDisplay1.setText(String.valueOf(counterValue1));
            counterDisplay2.setText(String.valueOf(counterValue2));
            startButton.setEnabled(true);
        }
    }

    public void T2StopPressed(View view) {
        MediaPlayer stopPlayer = MediaPlayer.create(DoubleCounterActivity.this, R.raw.brake);
        stopPlayer.start();
        T2.cancel();
        T2 = null;
        T2stop = true;
        if (T1stop == true && T2stop == true) {
            calculateScore();
            stopButton1.setEnabled(false);
            stopButton2.setEnabled(false);
            counterValue1 = 0;
            counterValue2 = 0;
            counterDisplay1.setText(String.valueOf(counterValue1));
            counterDisplay2.setText(String.valueOf(counterValue2));
            startButton.setEnabled(true);
        }
    }

    private void noAttempt() {

        T1.cancel();
        T1 = null;
        T1stop = true;
        T2.cancel();
        T2 = null;
        T2stop = true;

//            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//            alertDialog.setTitle("Loser");
//            alertDialog.setMessage("You're not gonna win if you don't try");
//            alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//
//        alertDialog.show();

        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Loser")
                .setContentText("You'r not gonna win if you don't try")
                .setConfirmText("Let's do this")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        finish();
                    }
                })
                .show();

        stopButton1.setEnabled(false);
        stopButton2.setEnabled(false);
        counterValue1 = 0;
        counterValue2 = 0;
        counterDisplay1.setText(String.valueOf(counterValue1));
        counterDisplay2.setText(String.valueOf(counterValue2));
        startButton.setEnabled(true);

    }

    private void calculateScore() {
        if (T1stop == true && T2stop == true) {
            if (selectedDoubleLevel == 1) {
                if (counterValue1 == goalValue && counterValue2 == goalValue) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                    alertDialog.setTitle("Winner");
//                    alertDialog.setMessage("Those stopping skills");
//                    alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            int progression = progressionFile.getInt("progressionNumber",2);
//                            progression++;
//                            progressionEditor.putInt("progressionNumber", progression);
//                            progressionEditor.apply();
//                            selectedDoubleLevel = 2;
//                            speedValue = 25;
//                        }
//                    });
//                    alertDialog.show();

                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Winner")
                            .setContentText("Those stopping skills")
                            .setConfirmText("Next")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    speedDisplay.setText("x2");

                                    int progression = progressionFile.getInt("progressionNumber", 2);
                                    if(progression < 2){
                                        progression = 2;
                                    }
                                    progression++;
                                    progressionEditor.putInt("progressionNumber", progression);
                                    progressionEditor.commit();
                                    
                                    progressionEditor.putInt("progressionNumber", progression);
                                    progressionEditor.apply();
                                    selectedDoubleLevel = 2;
                                    speedValue = 25;
                                }
                            })
                            .show();
                } else {
//                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                    alertDialog.setTitle("Loser");
//                    alertDialog.setMessage(counterValue1 + " - " + counterValue2 + ": Too Slow");
//                    alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            startButton.setEnabled(true);
//                        }
//                    });
//
//                    alertDialog.show();

                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Loser")
                            .setContentText(counterValue1 + " - " + counterValue2 + ": Too Slow")
                            .setConfirmText("Retry")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    startButton.setEnabled(true);
                                }
                            })
                            .show();
                }
            }
            if (selectedDoubleLevel == 2) {
                if (counterValue1 == goalValue && counterValue2 == goalValue) {
//                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                    alertDialog.setTitle("Winner");
//                    alertDialog.setMessage("Impressive");
//                    alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            int progression = progressionFile.getInt("progressionNumber", 3);
//                            progression++;
//                            progressionEditor.putInt("progressionNumber", progression);
//                            progressionEditor.apply();
//                            startButton.setEnabled(true);
//                        }
//                    });
//
//                    alertDialog.show();

                    new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Winner")
                            .setContentText("Impressive")
                            .setConfirmText("Retry")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    int progression = progressionFile.getInt("progressionNumber", 3);
                                    progression++;
                                    progressionEditor.putInt("progressionNumber", progression);
                                    progressionEditor.commit();

                                    progressionEditor.putInt("progressionNumber", progression);
                                    progressionEditor.apply();
                                    startButton.setEnabled(true);
                                }
                            })
                            .show();
                } else {
//                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                    alertDialog.setTitle("Loser");
//                    alertDialog.setMessage(counterValue1 + " - " + counterValue2 + ": Too Slow");
//                    alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            startButton.setEnabled(true);
//                        }
//                    });
//
//                    alertDialog.show();

                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Loser")
                            .setContentText(counterValue1 + " - " + counterValue2 + ": Too Slow")
                            .setConfirmText("Retry")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    startButton.setEnabled(true);

                                }
                            })
                            .show();
                }


            }
        }
    }

    public void returnToHomeScreenButtonClicked(View view) {
        Intent openHomeScreen = new Intent(DoubleCounterActivity.this, StartMenu.class);
        startActivity(openHomeScreen);
    }

    public void cheatButtonClicked(View view) {

        cheatValue++;
        if (cheatValue == 3){
            cheatValue = 0;
            if(selectedDoubleLevel == 1){
                speedDisplay.setText("x2");

                int progression = progressionFile.getInt("progressionNumber", 2);
                if(progression < 2){
                    progression = 2;
                }
                progression++;
                editor.putInt("progressionNumber", progression);
                editor.commit();

                progressionEditor.putInt("progressionNumber", progression);
                progressionEditor.apply();
                selectedDoubleLevel = 2;
                speedValue = 25;
                Toast.makeText(DoubleCounterActivity.this, "Cheat Activated", Toast.LENGTH_SHORT).show();

            }
            if (selectedDoubleLevel == 2){
                int progression = progressionFile.getInt("progressionNumber", 3);
                progression++;
                editor.putInt("progressionNumber", progression);
                editor.commit();

                progressionEditor.putInt("progressionNumber", progression);
                progressionEditor.apply();
                startButton.setEnabled(true);
                Toast.makeText(DoubleCounterActivity.this, "Cheat Activated", Toast.LENGTH_SHORT).show();

            }
        }

    }
}
