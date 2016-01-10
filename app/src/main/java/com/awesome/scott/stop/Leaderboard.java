package com.awesome.scott.stop;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class Leaderboard extends StartMenu {

    SharedPreferences loadAttempts;
    SharedPreferences.Editor editor;

    SharedPreferences progressionFile;
    SharedPreferences.Editor progressionEditor;

    int x1SingleStopAttempts = 0;
    int x2SingleStopAttempts = 0;
    int doubleStopAttempts = 0;
    int doubleStopAttemptsx2 = 0;

    TextView x1Score;
    TextView x2Score;
    TextView doubleScore;
    TextView doubleScorex2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        x1Score = (TextView)findViewById(R.id.x1ScoreDisplay);
        x2Score = (TextView)findViewById(R.id.x2ScoreDisplay);
        doubleScore = (TextView)findViewById(R.id.doubleScoreDisplay);
        doubleScorex2 = (TextView)findViewById(R.id.doubleScoreDisplayx2);

        loadAttempts = getSharedPreferences("NumberOfAttemptSaveFile", MODE_PRIVATE);
        editor = loadAttempts.edit();

        progressionFile = getSharedPreferences("levelProgressionProgressFile", MODE_PRIVATE);
        progressionEditor = progressionFile.edit();

        x1SingleStopAttempts = loadAttempts.getInt("1xSingleStopAttempts", 0);
        x2SingleStopAttempts = loadAttempts.getInt("2xSingleStopAttempts", 0);
        doubleStopAttempts = loadAttempts.getInt("doubleStopAttempts",0);
        doubleStopAttemptsx2 = loadAttempts.getInt("doubleStopAttemptsx2", 0);

        x1Score.setText(String.valueOf(x1SingleStopAttempts));
        x2Score.setText(String.valueOf(x2SingleStopAttempts));
        doubleScore.setText(String.valueOf(doubleStopAttempts));
        doubleScorex2.setText(String.valueOf(doubleStopAttemptsx2));


    }



    public void resetButtonPressed(View view) {

//        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                .setTitleText("Are you sure?")
//                .setContentText("All attempts and progress will be erased!")
//                .setConfirmText("Yes, delete it!")
//                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                    @Override
//                    public void onClick(SweetAlertDialog sDialog) {
//                        editor.clear();
//                        editor.commit();
//
//                        progressionEditor.clear();
//                        progressionEditor.commit();
//
//                        x1SingleStopAttempts = loadAttempts.getInt("1xSingleStopAttempts", 0);
//                        x2SingleStopAttempts = loadAttempts.getInt("2xSingleStopAttempts", 0);
//                        doubleStopAttempts = loadAttempts.getInt("doubleStopAttempts", 0);
//                        doubleStopAttemptsx2 = loadAttempts.getInt("doubleStopAttemptsx2", 0);
//
//                        x1Score.setText(String.valueOf(x1SingleStopAttempts));
//                        x2Score.setText(String.valueOf(x2SingleStopAttempts));
//                        doubleScore.setText(String.valueOf(doubleStopAttempts));
//                        doubleScorex2.setText(String.valueOf(doubleStopAttemptsx2));
//                        sDialog.dismissWithAnimation();
//                    }
//                })
//                .show();





        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Reset Your Attempts");
        alertDialog.setMessage("This will reset all of your attempts to 0, are you sure you want to continue?");
        alertDialog.setButton("Oops, don't do that", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });
        alertDialog.setButton("Reset", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                editor.clear();
                editor.commit();

                progressionEditor.clear();
                progressionEditor.commit();

                x1SingleStopAttempts = loadAttempts.getInt("1xSingleStopAttempts", 0);
                x2SingleStopAttempts = loadAttempts.getInt("2xSingleStopAttempts", 0);
                doubleStopAttempts = loadAttempts.getInt("doubleStopAttempts", 0);
                doubleStopAttemptsx2 = loadAttempts.getInt("doubleStopAttemptsx2", 0);

                x1Score.setText(String.valueOf(x1SingleStopAttempts));
                x2Score.setText(String.valueOf(x2SingleStopAttempts));
                doubleScore.setText(String.valueOf(doubleStopAttempts));
                doubleScorex2.setText(String.valueOf(doubleStopAttemptsx2));

            }
        });

        alertDialog.show();

    }

//    @Override
//    protected void onPause() {
//        if (this.isFinishing()){ //basically BACK was pressed from this activity
//
//
////                startMenuClass.stopBackgroundMusic();
//
//        }
//        Context context = getApplicationContext();
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//        if (!taskInfo.isEmpty()) {
//            ComponentName topActivity = taskInfo.get(0).topActivity;
//            if (!topActivity.getPackageName().equals(context.getPackageName())) {
//
//                backgroundMusicPlayer.stop();
//
//            }
//            else {
//
//
//            }
//        }
//        super.onPause();
//    }
}
