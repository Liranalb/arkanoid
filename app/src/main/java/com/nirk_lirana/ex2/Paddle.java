package com.nirk_lirana.ex2;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle {
    private float paddleCX, paddleCY, paddleW, paddleH, paddleDX;
    private int paddleColor;

    public Paddle(float paddleCX, float paddleCY, float paddleW, float paddleH) {
        this.paddleCY = paddleCY;
        this.paddleCX = paddleCX;
        this.paddleW = paddleW;
        this.paddleH = paddleH;
        this.paddleColor = Color.WHITE;
        this.paddleDX=0;
    }

    public float getPaddleCY() {
        return paddleCY;
    }

    public float getPaddleCX() {
        return paddleCX;
    }

    public void setPaddleCX(float paddleCX) {
        this.paddleCX = paddleCX;
    }

    public float getPaddleW() {
        return paddleW;
    }

    public float getPaddleH() {
        return paddleH;
    }

    public void setPaddleDX(float dx) {
        this.paddleDX = dx;
    }


    protected void onDraw(Canvas canvas) {
        Paint pPaddle = new Paint();
        pPaddle.setColor(paddleColor);
        canvas.drawRect(paddleCX, paddleCY, paddleCX + paddleW, paddleCY + paddleH, pPaddle);
    }

    public void moveRandL(int screenWidth) {

        if ((paddleCX + paddleW + paddleDX)<screenWidth && (paddleCX + paddleDX ) > 0) {
            paddleCX = paddleCX + paddleDX;
        }
    }





}



