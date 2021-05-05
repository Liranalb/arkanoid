package com.nirk_lirana.ex2;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import java.util.Random;

public class Brick {

    final float brickX, brickY, brickW, brickH;
    private int brickColor;

    public Brick(float brickX, float brickY, float brickW, float brickH) {
        this.brickX = brickX;
        this.brickY = brickY;
        this.brickW = brickW;
        this.brickH = brickH;
        this.brickColor = new Random().nextInt();
        if(this.brickColor == Color.rgb(0, 26, 51))
        {
            this.brickColor = Color.rgb(250,0,0);
        }

        //preventing a block with the same color of the canvas
        if (this.brickColor == Color.rgb(15, 30, 45)) {
            this.brickColor = new Random().nextInt();
        }
    }

    public float getBrickX() {
        return brickX;
    }

    public float getBrickY() {
        return brickY;
    }

    public float getBrickW() {
        return brickW;
    }

    public float getBrickH() {
        return brickH;
    }


    protected void onDraw(Canvas canvas) {
        Paint pBrick=new Paint();
        pBrick.setColor(brickColor);
        canvas.drawRect(brickX, brickY,brickX+brickW,brickY+brickH,  pBrick);
    }
}
