package com.nirk_lirana.ex2;


import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrickCollection {
    List<Brick> brickList = new ArrayList<Brick>();
    RectF brick;

    private int col;
    private int row;
    private int brickW;
    private int brickH;

    private int canvasW;
    private int canvasH;

    private int space;
    private int spaceX;
    private int spaceY;

    private boolean newGame;

    public BrickCollection(int col, int row, int canvasW, int canvasH, int space) {

      Random rand = new Random();

      int rawNum = 2 + rand.nextInt(6 - 2 + 1);
      int colNum = 3 + rand.nextInt(7 - 3 + 1);

        this.col = colNum;
        this.row = rawNum;

        this.brickW = (canvasW-space*(colNum+1))/(colNum) + 1;
        this.brickH = canvasH/20;

        this.canvasW = canvasW;
        this.canvasH = canvasH;

        this.space = space;
        this.spaceX = space;
        this.spaceY = 120;

        this.newGame = false;
    }


    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCanvasW(int canvasW) {
        this.canvasW = canvasW;
    }

    public void setCanvasH(int canvasH) {
        this.canvasH = canvasH;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    public void createBrickCollection()
    {
        if(newGame)
        {
            spaceX = space;
            spaceY = 130;
        }

        for (int i = 1; i <= row; i++)
        {
            for (int j = 1; j <= col; j++)
            {
                brickList.add(new Brick(spaceX, spaceY, brickW, brickH));
                spaceX+=brickW+space;
                //isCollide[i][j]=false;
            }
            spaceX = space;
            spaceY+=brickH+space;
        }
    }

    public boolean isCollideWithBall(Ball rect) {
        RectF ball = new RectF(rect.getBallCX()-rect.getBallRadius(), rect.getBallCY()-rect.getBallRadius(), rect.getBallCX()+rect.getBallRadius(), rect.getBallCY()+rect.getBallRadius());
        for (int i = 0; i < brickList.size(); i++) {
            brick = new RectF(brickList.get(i).getBrickX(), brickList.get(i).getBrickY(), brickList.get(i).getBrickX()+brickList.get(i).getBrickW(), brickList.get(i).getBrickY()+brickList.get(i).getBrickH());
            if(brick.intersect(ball)) {
                brickList.remove(i);
                rect.setBallDY(-rect.getBallDY());
                return true;
            }
        }
        return false;
    }
}

