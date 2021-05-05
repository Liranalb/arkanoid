package com.nirk_lirana.ex2;


import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.Random;

public class Ball {
    private float ballCX, ballCY, ballRadius, ballDX, ballDY;
    private int ballColor;
    private boolean ballIsMove;

    //Contractor
    public Ball(float ballcx, float ballcy, float ballradius) {
        this.ballCX = ballcx;
        this.ballCY = ballcy;
        this.ballRadius = ballradius;
        this.ballColor = Color.WHITE;
    }

    public float getBallCX() {
        return ballCX;
    }

    public void setBallCX(float ballcx) {
        this.ballCX = ballcx;
    }

    public float getBallCY() {
        return ballCY;
    }

    public void setBallCY(float ballcy) {
        this.ballCY = ballcy;
    }

    public float getBallRadius() {
        return ballRadius;
    }

    public float getBallDX() {
        return ballDX;
    }

    public void setBallDX(float ballDX) {
        this.ballDX = ballDX;
    }

    public float getBallDY() {
        return ballDY;
    }

    public void setBallDY(float ballDY) {
        this.ballDY = ballDY;
    }


    protected void onDraw(Canvas canvas) {
        Paint pBall=new Paint();
        pBall.setColor(ballColor);
        pBall.setStyle(Paint.Style.FILL);

        canvas.drawCircle(ballCX,ballCY, ballRadius,pBall);

        //for block rect
        Paint rect = new Paint();
        rect.setColor(Color.rgb(0, 37, 51));
        rect.setStyle(Paint.Style.STROKE);
        canvas.drawRect(ballCX-ballRadius,ballCY-ballRadius, ballCX+ballRadius, ballCY+ballRadius, rect);
    }

    public void fly (int canvasW, int canvasH) {
        if(ballIsMove)
        {
            ballCX = ballCX + ballDX;
            if(ballCX- ballRadius <0 || ballCX+ ballRadius >canvasW)
                ballDX = -ballDX;

            ballCY = ballCY + ballDY;
            if(ballCY- ballRadius <0)
                ballDY = -ballDY;
        }
    }

    public boolean isCollideWithRect(Paddle rect)
    {
        RectF ball = new RectF(ballCX-ballRadius, ballCY-ballRadius, ballCX+ballRadius, ballCY+ballRadius);
        RectF paddle = new RectF(rect.getPaddleCX(), rect.getPaddleCY(), rect.getPaddleCX()+rect.getPaddleW(),rect.getPaddleCY()+rect.getPaddleH());

        if(ball.intersect(paddle))
        {
            ballDY = (-ballDY);
            return true;
        }
        return false;
    }

    public long speed(){
        Random rnd = new Random();
        ballIsMove=true;

        int distx;
        do { distx = (-5) + rnd.nextInt(10);} while (distx == 0);

        int disty = 2 + rnd.nextInt(5);
        disty*=(-1);

        ballDX=distx;
        ballDY=disty;

        long speed  = (long)Math.sqrt((getBallDX()*getBallDX())+(getBallDY()*getBallDY()));
        return speed;
    }
}
