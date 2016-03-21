package com.ppalumbo_a4.peter.ppalumbo_a4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;

/**
 * Created by peter on 3/16/16.
 */
public class OptionsActivity extends Activity {


    private SeekBar mDifficultyBar;
    private int difficulty = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        if(savedInstanceState !=null){
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                difficulty = extras.getInt(WelcomeActivity.DIFFICULTY_TAG, difficulty);
            }
        }
        mDifficultyBar = (SeekBar)findViewById(R.id.difficulty_seekbar);
        mDifficultyBar.setProgress(difficulty);

        mDifficultyBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                difficulty = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Intent difficulty_output = new Intent();
        difficulty_output.putExtra(WelcomeActivity.DIFFICULTY_TAG, difficulty);
        setResult(RESULT_OK, difficulty_output);
        //finish();
    }
}
