package com.awesome.scott.stop;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import org.w3c.dom.Text;

import java.util.List;


public class StartMenu extends Activity {

    SharedPreferences loadAttempts;
    SharedPreferences.Editor editor;

    int x1SingleStopAttempts = 0;
    int x2SingleStopAttempts = 0;
    int doubleStopAttempts = 0;
    int doubleStopAttemptsx2 = 0;

    private TextView totalAttemptsView;
    private TextView titleView;

    public MediaPlayer backgroundMusicPlayer;
    public boolean backgroundMusicIsPlayingBool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        titleView = (TextView)findViewById(R.id.title);

        //set music player
        backgroundMusicPlayer = MediaPlayer.create(StartMenu.this,R.raw.holfix_basic_fun);

        //retrieve saved data
        loadAttempts = getSharedPreferences("NumberOfAttemptSaveFile", MODE_PRIVATE);
        editor = loadAttempts.edit();

        totalAttemptsView = (TextView) findViewById(R.id.totalAttemptsView);

        x1SingleStopAttempts = loadAttempts.getInt("1xSingleStopAttempts", 0);
        x2SingleStopAttempts = loadAttempts.getInt("2xSingleStopAttempts", 0);
        doubleStopAttempts = loadAttempts.getInt("doubleStopAttempts",0);
        doubleStopAttemptsx2 = loadAttempts.getInt("doubleStopAttemptsx2", 0);

        YoYo.with(Techniques.RotateInDownLeft)
                .duration(1000)
                .playOn(findViewById(R.id.title));

        //set display according to saved data
        int totalAttempts = x1SingleStopAttempts + x2SingleStopAttempts + doubleStopAttempts + doubleStopAttemptsx2;

        totalAttemptsView.setText("0");
        if (totalAttempts == 0){
            totalAttemptsView.setText("0");
        }else{
            totalAttemptsView.setText(String.valueOf(totalAttempts));
        }

//        startBackgroundMusic();

    }



//    @Override
//    protected void onPause() {
//        if (this.isFinishing()){ //basically BACK was pressed from this activity
//
//            if (backgroundMusicIsPlayingBool == true){
//                stopBackgroundMusic();
//            }
//        }
//        Context context = getApplicationContext();
//        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
//        if (!taskInfo.isEmpty()) {
//            ComponentName topActivity = taskInfo.get(0).topActivity;
//            if (!topActivity.getPackageName().equals(context.getPackageName())) {
//               if (backgroundMusicIsPlayingBool == true){
//                   stopBackgroundMusic();
//               }
////                Toast.makeText(xYourClassNamex.this, "YOU LEFT YOUR APP", Toast.LENGTH_SHORT).show();
//            }
//            else {
////                Toast.makeText(xYourClassNamex.this, "YOU SWITCHED ACTIVITIES WITHIN YOUR APP", Toast.LENGTH_SHORT).show();
//            }
//        }
//        super.onPause();
//    }



    public void startBackgroundMusic(){
        backgroundMusicPlayer.start();
        backgroundMusicIsPlayingBool = true;
    }

    public void stopBackgroundMusic(){
        backgroundMusicPlayer.stop();
        backgroundMusicIsPlayingBool = false;
    }

    public boolean isMusicPlaying(){
        return backgroundMusicIsPlayingBool;
    }

    public void moveToLevelSelector(View view) {
        Intent openSelector = new Intent(this,LevelSelector.class);
        startActivity(openSelector);
    }

    public void leaderboardButtonPressed(View view) {
        Intent openLeaderboard = new Intent(StartMenu.this, Leaderboard.class);
        startActivity(openLeaderboard);
    }
}
