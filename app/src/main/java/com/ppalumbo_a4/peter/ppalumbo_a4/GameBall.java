package com.ppalumbo_a4.peter.ppalumbo_a4;

import android.graphics.PointF;

/**
 * Created by peter on 3/16/16.
 */
public class GameBall {

    //width and height of the screen
    private int mWidth;
    private int mHeight;

    //Ball position
    private PointF mBallPos;

    //Ball Radius
    private int mBallRadius;

    //Ball Velocity
    private PointF mBallVel;

    //Ball Acceleration
    private PointF mBallAccel;

    //Device Acceleration
    private PointF mDeviceAccel;

    //System Time
    private long mTime;

    public GameBall(int width, int height){
        mWidth = width;
        mHeight = height;

        mBallRadius = 100;

        mBallPos = new PointF(100, 100);
        mBallVel = new PointF(0, 0);

        mBallAccel = new PointF(0, 0);

        mDeviceAccel = new PointF(0, 0);
    }

    public void runSimulation() {
        mBallAccel.x = -mDeviceAccel.x/10;
        mBallAccel.y = mDeviceAccel.y/10;
        //Time for equations and previous mTime
        long dt = System.currentTimeMillis() - mTime;
        mTime = System.currentTimeMillis();

        //Physics
        mBallVel.x =  mBallAccel.x*dt;
        mBallVel.y =  mBallAccel.y*dt;

        mBallPos.x += mBallVel.x;// + 0.5*mBallAccel.x*dt*dt;
        mBallPos.y += mBallVel.y;// + 0.5*mBallAccel.y*dt*dt;


        //If ball hits screen edge reverse it's velocity
        if (mBallPos.x > (mWidth-mBallRadius)){
            mBallPos.x=mWidth-mBallRadius;
            mBallVel.x *= -1.0;
        }
        if (mBallPos.y > (mHeight-mBallRadius)){
            mBallPos.y=mHeight-mBallRadius;
            mBallVel.y *= -1.0;
        }
        if (mBallPos.x < (0+mBallRadius)){
            mBallPos.x=0+mBallRadius;
            mBallVel.x *= -1.0;
        }
        if (mBallPos.y < (0+mBallRadius)){
            mBallPos.y=0+mBallRadius;
            mBallVel.y *= -1.0;
        }
    }

    //getters and setters

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public int getmWidth() {
        return mWidth;
    }

    public void setmWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getmHeight() {
        return mHeight;
    }

    public void setmHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public PointF getBallPos() {
        return mBallPos;
    }

    public void setBallPos(PointF mBallPos) {
        this.mBallPos = mBallPos;
    }

    public int getBallRadius() {
        return mBallRadius;
    }

    public void setBallRadius(int mBallRadius) {
        this.mBallRadius = mBallRadius;
    }

    public PointF getBallVel() {
        return mBallVel;
    }

    public void setBallVel(PointF mBallVel) {
        this.mBallVel = mBallVel;
    }

    public PointF getBallAccel() {
        return mBallAccel;
    }

    public void setBallAccel(PointF mBallAccel) {
        this.mBallAccel = mBallAccel;
    }

    public PointF getDeviceAccel() {
        return mDeviceAccel;
    }

    public void setDeviceAccel(PointF mDeviceAccel) {
        this.mDeviceAccel = mDeviceAccel;
    }
}
