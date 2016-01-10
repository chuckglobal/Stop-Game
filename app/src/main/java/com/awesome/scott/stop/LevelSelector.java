package com.awesome.scott.stop;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class LevelSelector extends Activity {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    int progressionNumber;

    private Button singleStopx1;
    private Button singleStopx2;
    private Button doubleStopx1;
    private Button doubleStopx2;


//
//    ViewGroup rootContainer;
//    Scene enterScene;
//    Scene defaultScene;
//    Scene exitScene;
//    Transition transitionMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selector);



//        rootContainer = (ViewGroup)findViewById(R.id.rootContainer);
//
//        enterScene = Scene.getSceneForLayout(rootContainer, R.layout.acitivity_level_selector_enter_animation, this);
//        defaultScene = Scene.getSceneForLayout(rootContainer, R.layout.activity_level_selector, this);
//        exitScene = Scene.getSceneForLayout(rootContainer,R.layout.activity_level_selector_exit_animation,this);
//
//        transitionMgr = TransitionInflater.from(this).inflateTransition(R.transition.transitions);
//
//        enterScene.enter();
//        TransitionManager.go(defaultScene,transitionMgr);

        singleStopx1 = (Button)findViewById(R.id.singleStopx1);
        singleStopx2 = (Button)findViewById(R.id.singleStopx2);
        doubleStopx1 = (Button)findViewById(R.id.doubleStopx1);
        doubleStopx2 = (Button)findViewById(R.id.doubleStopx2);

        sharedPref = getSharedPreferences("levelProgressionProgressFile", MODE_PRIVATE);
        editor = sharedPref.edit();

        progressionNumber = sharedPref.getInt("progressionNumber",0);
//        determineProgression();



    }




//    @Override
//    protected void onPause() {
//
//
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
//                    backgroundMusicPlayer.stop();
//
//            }
//            else {
//
//                backgroundMusicPlayer.stop();
//
//            }
//        }
//        super.onPause();
//    }

    private void determineProgression(){
        if (progressionNumber == 0){
            singleStopx1.setEnabled(true);
        }
        if (progressionNumber >= 1){
            singleStopx1.setEnabled(true);
            singleStopx2.setEnabled(true);
        }
        if (progressionNumber >= 2){
            singleStopx1.setEnabled(true);
            singleStopx2.setEnabled(true);
            doubleStopx1.setEnabled(true);
        }
        if (progressionNumber >= 3){
            singleStopx1.setEnabled(true);
            singleStopx2.setEnabled(true);
            doubleStopx1.setEnabled(true);
            doubleStopx2.setEnabled(true);
        }
    }


    private void entranceAnimation(){

    }

    private void levelSelectedAnimation(){

    }


    public void level100Pressed(View view) {
        Intent intentLevel = new Intent(LevelSelector.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("speed", 40);
        bundle.putInt("levelSelected",1);
        intentLevel.putExtras(bundle);
        startActivity(intentLevel);


    }

    public void level200Pressed(View view) {
        Intent intentLevel = new Intent(LevelSelector.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("speed", 25);
        bundle.putInt("levelSelected", 2);
        intentLevel.putExtras(bundle);
        startActivity(intentLevel);
    }

    public void doubleStopButtonPressed(View view) {
        Intent openDouble = new Intent(this, DoubleCounterActivity.class);

        Bundle bundle = new Bundle();
        bundle.putInt("doubleSpeedx1", 50);
        bundle.putInt("doubleLevel", 1);
        openDouble.putExtras(bundle);

        startActivity(openDouble);

    }

    public void doubleStopx2ButtonPressed(View view) {
        Intent openDoubleStop = new Intent(this, DoubleCounterActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("doublSpeedx2", 25);
        bundle.putInt("doubleLevel", 2);
        openDoubleStop.putExtras(bundle);

        startActivity(openDoubleStop);

    }

    public void customButtonClicked(View view) {
        Intent openCustom = new Intent(LevelSelector.this,CustomStopLevel.class);
        startActivity(openCustom);
    }




}
