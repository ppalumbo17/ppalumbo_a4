package com.ppalumbo_a4.peter.ppalumbo_a4;

import android.app.Activity;

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
import android.widget.FrameLayout;
import android.os.Handler;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity {
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FrameLayout mainview = (FrameLayout)findViewById(R.id.main_game_view);

        mGameView = new GameView(this);
        mainview.addView(mGameView);

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
        mSensorManager.registerListener(sensorEventListener, accelerometer, SensorManager.SENSOR_DELAY_UI);
//        Sensor gyroscope =mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
//        mSensorManager.registerListener(sensorEventListener, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);

    }

    // Define the sensor event listener.
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            PointF accel = new PointF(event.values[0], event.values[1]);
            //mControlledBall.setmBallAccel(accel);
            mGameBall.setDeviceAccel(accel);
            //Log.d(TAG, "onSensorChanged() " + event.toString());
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // ignore
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
                //EDITED
//                mSimView.setBallPos(mSimulator_second.getBallPos());
//                mSimView.setR(mSimulator_second.getBallRadius());

                // The handler will tell the background thread to redraw the view.
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mGameView.invalidate();
                    }
                });
            }
        };

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
        mSensorManager.unregisterListener(sensorEventListener);
    }
}
