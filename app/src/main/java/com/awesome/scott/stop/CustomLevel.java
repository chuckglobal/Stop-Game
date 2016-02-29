package com.awesome.scott.stop;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class CustomLevel extends Activity {


    int numberOfAttempts;
    int maxValue = 200;
    int stopValue = 0;
    int counterValue = 0;
    int levelSpeed;
    final int goalScore = 100;
    final private int sentinel = 0;

    MediaPlayer player;
    MediaPlayer stopPlayer;

    TextView counterDisplay;
//    TextView attemptsDisplay;

    Button startButton;
    Button stopButton;

    Timer T;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_level);

        Intent intentExtras = getIntent();
        retrieveSelectedlevel(intentExtras);

        //temporary
        numberOfAttempts = 0;

        counterDisplay = (TextView)findViewById(R.id.counterView);
//        attemptsDisplay = (TextView)findViewById(R.id.attemptDisplay);
        stopButton = (Button)findViewById(R.id.StopButton);
        startButton = (Button)findViewById(R.id.startButton);

        T = new Timer();
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()){ //basically BACK was pressed from this activity
            player.stop();
//            Toast.makeText(xYourClassNamex.this, "YOU PRESSED BACK FROM YOUR 'HOME/MAIN' ACTIVITY", Toast.LENGTH_SHORT).show();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                player.stop();
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
            levelSpeed = bundle.getInt("level");

        }
    }



    public void startPressed(View view) {
        player = MediaPlayer.create(CustomLevel.this, R.raw.gun_shot);

        player.start();
        player.setOnCompletionListener(new
                                               MediaPlayer.OnCompletionListener() {
                                                   @Override
                                                   public void onCompletion(MediaPlayer arg0) {
                                                       player.reset();
                                                       player = MediaPlayer.create(CustomLevel.this,R.raw.suspense_2);
                                                   }
                                               });

        player.start();
        numberOfAttempts++;
//        attemptsDisplay.setText(String.valueOf(numberOfAttempts));

        stopButton.setEnabled(true);

        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (counterValue <= maxValue) {
                            counterDisplay.setText(String.valueOf(counterValue));
                            counterValue++;


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
        counterValue--;
        T.cancel();
        calculateScore();

        T = null;
        T = new Timer();

        counterValue = 0;
        stopValue = 0;
        counterDisplay.setText(String.valueOf(sentinel));
        stopButton.setEnabled(false);
    }

    public void stopButtonPressed(View view) {
        stopPlayer = MediaPlayer.create(CustomLevel.this, R.raw.brake);
        stopPlayer.start();

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

    private void calculateScore(){
        if(counterValue == goalScore) {

//            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//            alertDialog.setTitle("Winner");
//            alertDialog.setMessage(counterValue + ": Congrats, now try it a little faster");
//            alertDialog.setButton("Change speed", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                    finish();
//                }
//            });
//
//            alertDialog.show();

            new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Winner")
                    .setContentText(counterValue + ": Congrats, now try it a little faster")
                    .setConfirmText("Change Speed")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            finish();
                        }
                    })
                    .show();
        }else{
//            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//            alertDialog.setTitle("Loser");
//            alertDialog.setMessage(counterValue + ": Don't yell at me for making it too hard");
//            alertDialog.setButton("Retry", new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
//
//            alertDialog.show();

            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Loser")
                    .setContentText(counterValue + ": Don't yell at me for making it too hard")
                    .show();
            startButton.setEnabled(true);

        }

    }

    public void returnToHomeScreenButtonClicked(View view) {
        Intent openHomeScreen = new Intent(CustomLevel.this,StartMenu.class);
        startActivity(openHomeScreen);
    }
}

