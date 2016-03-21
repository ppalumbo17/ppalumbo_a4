package com.ppalumbo_a4.peter.ppalumbo_a4;

import android.app.Activity;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.os.Handler;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity{
    private static final String TAG = "GameActivity";
    //The Game View
    GameView mGameView;

    //The Game Ball
    ControlledBall mControlledBall;

    //The User Ball
    GameBall mGameBall;

    Timer mTimer;

    TimerTask mTimerTask;

    Handler mHandler = new Handler();

    //Sensor manager handles different phone sensors
    SensorManager mSensorManager;

    //Current time for score
    long current_time;

    //This keeps score of the game
    long game_score = 0;

    //What if I make a context
    Context context;

    //Bool to check if game is over
    boolean isGameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        FrameLayout mainview = (FrameLayout)findViewById(R.id.main_game_view);

        mGameView = new GameView(this);
        mainview.addView(mGameView);

        //set game over to false and game score to 0
        isGameOver = false;
        game_score =0;
        context = this;

        //initialize the current time after the game starts
        current_time = System.currentTimeMillis();

        // Get screen dimensions (in pixels).
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;


        //Create Game and User Balls
        mControlledBall = new ControlledBall(screenWidth, screenHeight);
        mGameBall = new GameBall(screenWidth, screenHeight);

        int gameBallRadius = screenWidth/7;
        int userBallRadius = screenWidth/20;

        mControlledBall.setmBallRadius(gameBallRadius);
        PointF controlledBallPos = new PointF(screenWidth/2, screenHeight/2);
        mControlledBall.setmBallPos(controlledBallPos);

        mGameBall.setBallRadius(userBallRadius);
        PointF userBallPos = new PointF(screenWidth/2, screenHeight/2);
        mGameBall.setBallPos(userBallPos);


        // Get sensor manager and register the listener to it.
        mSensorManager = ((SensorManager)getSystemService(Context.SENSOR_SERVICE));
        Sensor accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(accelEventListener, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
//        Sensor gyroscope =mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        mSensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
        Sensor pressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorManager.registerListener(pressEventListener, pressure, SensorManager.SENSOR_DELAY_FASTEST);
    }

    // Define the acclerometer listener to tell where bal to move
    private SensorEventListener accelEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            PointF accel = new PointF(event.values[0], event.values[1]);
            //mControlledBall.setmBallAccel(accel);
            if(!isGameOver) {
                mGameBall.setDeviceAccel(accel);
            }
            //Log.d(TAG, "onSensorChanged() " + event.toString());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // ignore
        }
    };

    //Define the pressure event listener to tell if user has pressed on the screen
    private SensorEventListener pressEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            PointF press = new PointF(0,0);
            mGameBall.setDeviceAccel(press);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void onResume()  {
        Log.d(TAG, "onResume() called");

        // Create the timer and its task.
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mControlledBall.runSimulation();
                mGameBall.runSimulation();

                // Get ball position and radius from simulator, and pass to the view.
                mGameView.setControlledBallPos(mControlledBall.getmBallPos());
                mGameView.setControlledBallR(mControlledBall.getmBallRadius());

                mGameView.setUserBallPos(mGameBall.getBallPos());
                mGameView.setUserBallR(mGameBall.getBallRadius());

                //check if game ball has gone outside of automated ball
                checkGameOver(mControlledBall.getmBallPos(), mControlledBall.getmBallRadius(), mGameBall.getBallPos(), mGameBall.getBallRadius());
                // The handler will tell the background thread to redraw the view.
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGameView.invalidate();
                    }
                });


            }
        };

        if(isGameOver){
            Toast toast = Toast.makeText(context, "Game Over", Toast.LENGTH_LONG);
        }


        // Assign the task to the timer.
        mTimer.schedule(mTimerTask,
                10,     // delay (ms) before task is to be executed
                10);    // time (ms) between successive task executions

        super.onResume();
    }

    // Unregister sensor listener so it doesn't keep sending data.
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
        mSensorManager.unregisterListener(accelEventListener);
    }

    public void checkGameOver(PointF controlled, int controlledRadius, PointF user, int userRadius){
        double distance = Math.sqrt(Math.pow((controlled.x-user.x), 2.0)+Math.pow((controlled.y-user.y),2));
        double radius = controlledRadius + userRadius;
        //check if center of balls distance is greater than the balls combined radius if so end game
        if(distance >= radius){
            mControlledBall.setmBallAccel(new PointF(0, 0));
            mControlledBall.setmBallVel(new PointF(0, 0));
            mGameBall.setBallAccel(new PointF(0, 0));
            mGameBall.setBallVel(new PointF(0, 0));
            mGameBall.setDeviceAccel(new PointF(0, 0));
            isGameOver = true;

//            DialogFragment game_over = new GameOverDialog();
//            game_over.show(getFragmentManager(), "gameOver");
            //Toast over = Toast.makeText(context, "Game Over", Toast.LENGTH_LONG);
            //over.show();
        }
        else{
            //adjusts scoring;
            game_score = current_time-game_score;
            current_time = System.currentTimeMillis();
            String score = Long.toString(game_score);
            //Toast toast = Toast.makeText(context, score, Toast.LENGTH_SHORT);
            //toast.show();
        }
    }


}
