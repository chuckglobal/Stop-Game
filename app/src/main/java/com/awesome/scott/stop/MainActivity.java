package com.awesome.scott.stop;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
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



import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    TextView counterDisplay;
    TextView speedDisplay;
    TextView attemptsDisplay;

    Button stopButton;
    Button startButton;

    int stopValue = 0;
    int counterValue = 0;
    int levelSpeed;
    int selectedLevel;
    int maxValue = 200;
    final int goalScore = 100;
    private final int sentinel = 0;
    int numberOfAttempts = 0;
    private int debug = 0;

    MediaPlayer player;
    MediaPlayer stopPlayer;

    Timer T;

    SharedPreferences progressionFile;
    SharedPreferences.Editor progressionEditor;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;



//    AdView mAdView;
//    AdRequest adRequest;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Intent intentExtras = getIntent();
        retrieveSelectedlevel(intentExtras);


//        mAdView = (AdView) findViewById(R.id.adView);
//        adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        progressionFile = getSharedPreferences("levelProgressionProgressFile", MODE_PRIVATE);
        progressionEditor = progressionFile.edit();
        sharedPref = getSharedPreferences("NumberOfAttemptSaveFile", MODE_PRIVATE);
        editor = sharedPref.edit();

        SharedPreferences loadAttempts = getSharedPreferences("NumberOfAttemptSaveFile",MODE_PRIVATE);

        if(selectedLevel == 1) {
            numberOfAttempts = loadAttempts.getInt("1xSingleStopAttempts", 0);
        }else{
            numberOfAttempts = loadAttempts.getInt("2xSingleStopAttempts",0);
        }


        attemptsDisplay = (TextView) findViewById(R.id.attemptDisplay);
        attemptsDisplay.setText(String.valueOf(numberOfAttempts));


        stopButton = (Button)findViewById(R.id.StopButton);
        startButton = (Button)findViewById(R.id.startButton);

        counterDisplay = (TextView) findViewById(R.id.counterView);
        speedDisplay = (TextView)findViewById(R.id.speedDisplay);

        if(selectedLevel == 1){
            speedDisplay.setText("x1");
        }else{
            speedDisplay.setText("x2");
        }


        T = new Timer();
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            if(player != null && player.isPlaying()){
                player.stop();

            }
//            Toast.makeText(xYourClassNamex.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                if(player != null && player.isPlaying()){
                    player.stop();

                }
//                Toast.makeText(xYourClassNamex.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
            }
            else {
//                Toast.makeText(xYourClassNamex.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }

    private void retrieveSelectedlevel(Intent intent){
        Bundle bundle = intent.getExtras();
        if(!bundle.isEmpty()){
            levelSpeed = bundle.getInt("speed");
            selectedLevel = bundle.getInt("levelSelected");

        }
    }


    public void stopButtonPressed(View view) {
        stopPlayer = MediaPlayer.create(MainActivity.this, R.raw.brake);
        stopPlayer.start();
        player.stop();
        T.cancel();
        calculateScore();



        T = null;
        T = new Timer();

        counterValue = 0;
        stopValue = 0;
        counterDisplay.setText(String.valueOf(sentinel));
        stopButton.setEnabled(false);
        startButton.setEnabled(true);

    }

    public void startPressed(View view) {
        player = MediaPlayer.create(MainActivity.this,R.raw.gun_shot);

        player.start();
        player.setOnCompletionListener(new
                                               MediaPlayer.OnCompletionListener() {
                                                   @Override
                                                   public void onCompletion(MediaPlayer arg0) {
                                                       player.reset();
                                                       player = MediaPlayer.create(MainActivity.this,R.raw.suspense_2);
                                                   }
                                               });

        player.start();
        numberOfAttempts++;
        attemptsDisplay.setText(String.valueOf(numberOfAttempts));

        if(selectedLevel == 1) {

            editor.putInt("1xSingleStopAttempts", numberOfAttempts);
            editor.apply();
        }else{
            editor.putInt("2xSingleStopAttempts", numberOfAttempts);
            editor.apply();
        }


        stopButton.setEnabled(true);

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (counterValue <= maxValue) {

                            counterValue++;
                            counterDisplay.setText(String.valueOf(counterValue));

                        } else {
                            noAttempt();
                        }
                    }
                });
            }
        }, 0, levelSpeed);
        startButton.setEnabled(false);


    }

    private void noAttempt(){
        T.cancel();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Loser");
        alertDialog.setMessage("You gave up didn't you?");
        alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intentLevel = new Intent(MainActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("speed", 25);
                bundle.putInt("levelSelected", 2);
                intentLevel.putExtras(bundle);
                startActivity(intentLevel);

            }
        });

        alertDialog.show();
        T = null;
        T = new Timer();

        counterValue = 0;
        stopValue = 0;
        counterDisplay.setText("0");
        stopButton.setEnabled(false);


    }

    private void calculateScore() {

        if(selectedLevel == 1) {
            if (counterValue == goalScore) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Winner");
                alertDialog.setMessage("But can you take on two?");
                alertDialog.setButton("Next Level", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int progression = progressionFile.getInt("progressionNumber",0);
                        progression++;
                        progressionEditor.putInt("progressionNumber", progression);
                        progressionEditor.apply();
                        Intent intentLevel = new Intent(MainActivity.this, MainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("speed", 25);
                        bundle.putInt("levelSelected", 2);
                        intentLevel.putExtras(bundle);
                        startActivity(intentLevel);
                        startButton.setEnabled(true);

                    }
                });

                alertDialog.show();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Loser");
                alertDialog.setMessage(counterValue + ": And you thought it would be easy ");
                alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startButton.setEnabled(true);
                    }
                });

                alertDialog.show();

            }
        }

        //if the x2 level was selected use seperate AlertDialog
        if(selectedLevel == 2){
            if (counterValue == goalScore) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Winner");
                alertDialog.setMessage("hmm... lets see how you handle the next one");
                alertDialog.setButton("Next Level", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        int progression = progressionFile.getInt("progressionNumber",1);
                        progression++;
                        editor.putInt("progressionNumber", progression);
                        editor.apply();

                        Intent openDouble = new Intent(MainActivity.this, DoubleCounterActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("doubleSpeedx1", 50);
                        bundle.putInt("doubleLevel", 1);
                        openDouble.putExtras(bundle);

                        startActivity(openDouble);

                    }
                });

                alertDialog.show();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Loser");
                alertDialog.setMessage(counterValue + ": And you thought it would be easy ");
                alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startButton.setEnabled(true);
                    }
                });

                alertDialog.show();

            }
        }
    }


    public void returnToHomeScreenButtonClicked(View view) {



        Intent openHomeScreen = new Intent(MainActivity.this,StartMenu.class);
        startActivity(openHomeScreen);
    }

    public void debugClicked(View view) {
        debug++;
        if (debug == 5){
            counterValue = goalScore;
            calculateScore();
        }
    }
}
