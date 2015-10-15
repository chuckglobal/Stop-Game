package com.awesome.scott.stop;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;


public class CustomStopLevel extends Activity {

    SeekBar seekBar;
    int level;
    TextView speedDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_stop_level);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        speedDisplay = (TextView)findViewById(R.id.speedDisplay);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {



            @Override

            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {

                speedDisplay.setText(String.valueOf(progresValue));
            }

            @Override

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override

            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_custom_stop_level, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setLevel(int i){
        level = i;
    }


    public void setLevelButtonClicked(View view) {
        level = seekBar.getProgress();
        if(level == 0){
            level = 1;
        }
        if(level == 100){
            level = 99;
        }
        level = level-100;
        level = Math.abs(level);
        Intent openLevel = new Intent(CustomStopLevel.this,CustomLevel.class);
        Bundle bundle = new Bundle();
        bundle.putInt("level", level);

        openLevel.putExtras(bundle);
        startActivity(openLevel);
    }




}
