package com.awesome.scott.stop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;


public class StartMenu extends Activity {

    SharedPreferences loadAttempts;
    SharedPreferences.Editor editor;

    int x1SingleStopAttempts = 0;
    int x2SingleStopAttempts = 0;
    int doubleStopAttempts = 0;
    int doubleStopAttemptsx2 = 0;

    private TextView totalAttemptsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        loadAttempts = getSharedPreferences("NumberOfAttemptSaveFile", MODE_PRIVATE);
        editor = loadAttempts.edit();

        totalAttemptsView = (TextView) findViewById(R.id.totalAttemptsView);

        x1SingleStopAttempts = loadAttempts.getInt("1xSingleStopAttempts", 0);
        x2SingleStopAttempts = loadAttempts.getInt("2xSingleStopAttempts", 0);
        doubleStopAttempts = loadAttempts.getInt("doubleStopAttempts",0);
        doubleStopAttemptsx2 = loadAttempts.getInt("doubleStopAttemptsx2", 0);

        int totalAttempts = x1SingleStopAttempts + x2SingleStopAttempts + doubleStopAttempts + doubleStopAttemptsx2;

        totalAttemptsView.setText("0");
        if (totalAttempts == 0){
            totalAttemptsView.setText("0");
        }else{
            totalAttemptsView.setText(String.valueOf(totalAttempts));
        }


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
