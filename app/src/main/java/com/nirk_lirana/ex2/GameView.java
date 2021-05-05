package com.nirk_lirana.ex2;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View {
    //size screen
    private int canvasWidth;
    private int canvasHight;
    private Brick brick;
    private BrickCollection pbc;
    private String textStartGame="Click to PLAY!";
    public Thread gameThread;

    //obj game
    private int score = 0;
    private int lives = 3;
    private Ball ball;
    private Paddle paddle;

    //pens
    private Paint livesPen;
    private Paint scorePen;
    private Paint scoreCirclePen;
    private Paint EmptyScoreCirclePen;
    private Paint startPen;

    //FINEL brickCollection
    public static final int ROWS = 5;
    public static final int COLS = 7;
    public static final int SPACE = 10;

    //FINEL brickCollection
    public static final int START = 0;///////////////////////////////////////////////////////////////////new///
    public static final int PLAY = 1;
    public static final int GAMEOVER = 2;

    //ver gameView
    private int stateGame=START;
    private long speed;
    volatile boolean threadIsStopped = true;
    private int action;

    public GameView(Context context, AttributeSet atr) {
        super(context,atr);

        livesPen = new Paint();
        livesPen.setTextSize(40);
        livesPen.setColor(Color.WHITE);
        livesPen.setTextAlign(Paint.Align.RIGHT);

        scorePen=new Paint();
        scorePen.setTextSize(40);
        scorePen.setColor(Color.WHITE);
        scorePen.setTextAlign(Paint.Align.LEFT);

        scoreCirclePen=new Paint();
        scoreCirclePen.setColor(Color.WHITE);
        scoreCirclePen.setStyle(Paint.Style.FILL);

        EmptyScoreCirclePen=new Paint();
        EmptyScoreCirclePen.setColor(Color.GREEN);
        EmptyScoreCirclePen.setStrokeWidth(4);
        EmptyScoreCirclePen.setStyle(Paint.Style.STROKE);

        startPen=new Paint();
        startPen.setTextSize(80);
        startPen.setStyle(Paint.Style.FILL);
        startPen.setColor(Color.WHITE);
        startPen.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.rgb(15, 30, 45));

        ball.onDraw(canvas);
        paddle.onDraw(canvas);
        for (int i = 0; i < pbc.brickList.size(); i++) {
            brick = pbc.brickList.get(i);
            brick.onDraw(canvas);
        }
        canvas.drawText("Score: "+score, 25,40, scorePen);
        canvas.drawText("Lives: ", canvasWidth-160,40, livesPen);
        if(lives == 3) {
            canvas.drawCircle(canvasWidth - 30, 30, 16, scoreCirclePen);
            canvas.drawCircle(canvasWidth - 30, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 80, 30, 16, scoreCirclePen);
            canvas.drawCircle(canvasWidth - 80, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 130, 30, 16, scoreCirclePen);
            canvas.drawCircle(canvasWidth - 130, 30, 20, EmptyScoreCirclePen);
        }

        if(lives == 2){
            canvas.drawCircle(canvasWidth - 30, 30, 16, scoreCirclePen);
            canvas.drawCircle(canvasWidth - 30, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 80, 30, 16, scoreCirclePen);
            canvas.drawCircle(canvasWidth - 80, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 130, 30, 20, EmptyScoreCirclePen);
        }

        if(lives == 1){
            canvas.drawCircle(canvasWidth - 30, 30, 16, scoreCirclePen);
            canvas.drawCircle(canvasWidth - 30, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 80, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 130, 30, 20, EmptyScoreCirclePen);
        }

        //----------------GET_READY----------------
        if ((stateGame == START && lives>0 && !pbc.brickList.isEmpty())|| ballout()) {
            canvas.drawText(""+textStartGame, canvasWidth/2, canvasHight/2, startPen);
        }
        //-----------------------------------------

        //-----------------PLAYING-----------------

        ball.isCollideWithRect(paddle);

        if(pbc.isCollideWithBall(ball)) {
            MainActivity.mediaPlayer.start();
            score+=5*lives;
        }

        if(lives == 0 && !pbc.brickList.isEmpty())
        {
            canvas.drawCircle(canvasWidth - 30, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 80, 30, 20, EmptyScoreCirclePen);
            canvas.drawCircle(canvasWidth - 130, 30, 20, EmptyScoreCirclePen);
            canvas.drawText("GAMR OVER - YOU LOSS!", canvasWidth/2, canvasHight /2, startPen);
            stateGame=GAMEOVER;
        }

        else if(pbc.brickList.isEmpty())
        {
            canvas.drawText("GAMR OVER - YOU WIN!", canvasWidth/2, canvasHight /2, startPen);
            stateGame=GAMEOVER;
        }

        //-----------------------------------------

        //----------------GAME_OVER----------------
        if(stateGame == GAMEOVER)
        {
            newGame();
        }
        //-----------------------------------------
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float dx = 0;
        action = event.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            if (stateGame == START) {
                //Log.d("mydebug", "touch");
                speed = ball.speed();
                stateGame = PLAY;
            } else if (event.getX() > canvasWidth / 2) {
                dx = 5;

            } else {
                dx = -5;

            }
            paddle.setPaddleDX((float) dx);

        }
        if(action == MotionEvent.ACTION_UP){
            paddle.setPaddleDX((float) dx);
        }

        return true;
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Log.d("debug", "onSizeChanged");
        canvasWidth = w;
        canvasHight = h;

        // Log.d("debug", ":");
        // Log.d("debug", "onSizeChanged");

        ball = new Ball((canvasWidth / 2), canvasHight - 80, 20);
        paddle = new Paddle((canvasWidth / 2) - (canvasWidth / 14), canvasHight - 50, canvasWidth / 7, 10);
        pbc = new BrickCollection(COLS, ROWS, canvasWidth, canvasHight, SPACE);
        pbc.createBrickCollection();

        gameThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("mylog", "Creating a new thread ");
                while (!Thread.currentThread().isInterrupted()) {
                    if (!threadIsStopped) {
                        moveBandP();
                        SystemClock.sleep(speed * 2);
                    }
                }
                Log.d("mylog", "Closing Thread");
            }
        });
        gameThread.start();
    }

    public void moveBandP(){
        if(stateGame == PLAY) {
            ball.fly(canvasWidth, canvasHight);
            paddle.moveRandL(canvasWidth);
        }
        postInvalidate();
    }


    public boolean ballout() {
        if((ball.getBallCY() + ball.getBallRadius() > canvasHight) && lives>0){

            //Log.d("debug", "ballout");
            lives--;

            ball.setBallCX(0);
            ball.setBallCY(0);
            ball.setBallDY(0);
            ball.setBallDX(0);
            ball.setBallCX(canvasWidth/2);

            //   Log.d("debug", "ph: "+paddle.getPaddleCY());
            paddle.setPaddleCX((canvasWidth/2)-(canvasWidth/14));
            ball.setBallCY(canvasHight-80);

            stateGame=START;
            return true;
        }
        return false;
    }

    public void newGame(){
        if(pbc.brickList.isEmpty() || lives == 0 ){
            ball.setBallCX(0);
            ball.setBallCY(0);
            ball.setBallDY(0);
            ball.setBallDX(0);
            ball.setBallCX(canvasWidth/2);
            paddle.setPaddleCX((canvasWidth/2)-(canvasWidth/14));
            ball.setBallCY(canvasHight-80);

            if(action == 0)
            {
                if (lives == 0) { pbc.brickList.clear();}
                lives = 3;
                score = 0;
                pbc.setCol(COLS);
                pbc.setRow(ROWS);
                pbc.setCanvasH(canvasHight);
                pbc.setCanvasW(canvasWidth);
                pbc.setSpace(SPACE);
                pbc = new BrickCollection(COLS, ROWS, canvasWidth, canvasHight, SPACE);
                pbc.createBrickCollection();
                pbc.setNewGame(true);
            }

            stateGame=START;
        }
    }
}
