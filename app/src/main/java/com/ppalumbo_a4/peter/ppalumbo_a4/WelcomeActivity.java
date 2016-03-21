package com.ppalumbo_a4.peter.ppalumbo_a4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by peter on 3/16/16.
 */
public class WelcomeActivity extends Activity {

    //TAGS
    public static final String TAG = "WelcomeActivity.java";
    public static final String SCORE_TAG = "highScore";
    public static final String DIFFICULTY_TAG = "difficulty";

    public static int SET_SCORE_REQUEST =0;
    public static int SET_DIFFICULTY_REQUEST =1;

    private Button mStartButton;
    private Button mOptionsButton;

    private int highScore=0;
    private int difficulty=2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //Start the game
        mStartButton = (Button)findViewById(R.id.start_game_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(WelcomeActivity.this, GameActivity.class);
                game.putExtra(SCORE_TAG, highScore);
                game.putExtra(DIFFICULTY_TAG, difficulty);
                startActivityForResult(game, SET_SCORE_REQUEST);

            }
        });

        mOptionsButton = (Button)findViewById(R.id.options_game_button);
        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent options = new Intent(WelcomeActivity.this, OptionsActivity.class);
                options.putExtra(DIFFICULTY_TAG, difficulty);
                startActivityForResult(options, SET_DIFFICULTY_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode == RESULT_OK){
            if(requestCode == SET_SCORE_REQUEST){
                //set score text view
            }
            if(requestCode == SET_DIFFICULTY_REQUEST){
                difficulty = data.getIntExtra(DIFFICULTY_TAG, difficulty);
            }
        }
    }
}
