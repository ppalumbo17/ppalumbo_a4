package com.ppalumbo_a4.peter.ppalumbo_a4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

/**
 * Created by peter on 3/16/16.
 */
public class GameView extends View {

    //The game ball position and radius
    private PointF mControlledBallPos;
    private int mControlledBallR;

    //The user ball position and radius
    private PointF mUserBallPos;
    private int mUserBallR;

    //Paint Objects
    private Paint mPaintControlledBall = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPaintUserBall = new Paint(Paint.ANTI_ALIAS_FLAG);


    //Constructor
    public GameView(Context context){
        super(context);

        //set to something realistic to start
        mControlledBallPos = new PointF(400, 40);
        mControlledBallR = 100;

        //make sure they start at the same position so you don't instantly lose
        //ensure user ball is 1/5 size of game ball
        mUserBallPos = new PointF(400, 40);
        mUserBallR = 20;
    }

    // Android calls this to redraw the view, after invalidate()
    @Override
    protected void onDraw(Canvas canvas)    {
        super.onDraw(canvas);
        //Log.d(TAG, "onDraw(); X = " + mX + " Y = " + mY);
        mPaintControlledBall.setColor(0xff0000ff);
        canvas.drawCircle(mControlledBallPos.x, mControlledBallPos.y, mControlledBallR, mPaintControlledBall);
        mPaintUserBall.setColor(0xffff0000);
        canvas.drawCircle(mUserBallPos.x, mUserBallPos.y, mUserBallR, mPaintUserBall);
    }

    //Setters

    public void setControlledBallPos(PointF GameBallPos) {
        this.mControlledBallPos = GameBallPos;
    }

    public void setControlledBallR(int GameBallR) {
        this.mControlledBallR = GameBallR;
    }

    public void setUserBallPos(PointF UserBallPos) {
        this.mUserBallPos = UserBallPos;
    }

    public void setUserBallR(int UserBallR) {
        this.mUserBallR = UserBallR;
    }
}
