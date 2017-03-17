package com.example.usmannoor.snakesandladderdemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.Random;

public class game_page extends AppCompatActivity {

    MySurfaceView mySurfaceView;
    boolean touchpressed = false;
    int current = 0;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }

    class MySurfaceView extends SurfaceView implements Runnable {
        Rect rect[][] = new Rect[10][10];

        Thread thread = null;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Random random;

        public MySurfaceView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            surfaceHolder = getHolder();
            random = new Random();
        }

        public void onResumeMySurfaceView() {
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseMySurfaceView() {
            boolean retry = true;
            running = false;
            while (retry) {
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (running) {
                if (surfaceHolder.getSurface().isValid()) {
                    Canvas canvas = surfaceHolder.lockCanvas();
                    //... actual drawing on canvas

                    canvas.drawColor(Color.BLACK);
                    draw_grid(canvas);
                    Bitmap a = BitmapFactory.decodeResource(getResources(), R.drawable.snake);
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.snake2);
                    Bitmap c = BitmapFactory.decodeResource(getResources(), R.drawable.snake3);
                    Bitmap d = BitmapFactory.decodeResource(getResources(), R.drawable.ladder1);
                    Bitmap e = BitmapFactory.decodeResource(getResources(), R.drawable.ladder3);
                    canvas.drawBitmap(a, 450, 200, paint);
                    canvas.drawBitmap(b, 130, 1000, paint);
                    canvas.drawBitmap(c, 810, 700, paint);
                    canvas.drawBitmap(d, 225, 660, paint);
                    canvas.drawBitmap(e, 625, 400, paint);

                    if (touchpressed) {


                        random_draw(canvas);
                        random_draw_computer(canvas);
                        touchpressed = false;

                    }


                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }

        }

        public void draw_grid(Canvas canvas) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            paint.setColor(Color.YELLOW);

            int left = canvas.getWidth() / 10;
            int top = canvas.getHeight() / 10 - 30;
            int right = canvas.getWidth() / 10;
            int bottom = top;


            int number = 100;
            for (int j = 0; j < 10; j++) {
                for (int i = 0; i < 10; i++) {
                    rect[j][i] = new Rect();
                    if (number > 0) {
                        rect[j][i].set(i * left, j * top, i * left + right, j * top + bottom);
                        canvas.drawRect(rect[j][i], paint);
                        paint.setTextSize(60);
                        canvas.drawText(Integer.toString(number), i * left, (j + 1) * top, paint);
                        number -= 1;
                    }
                }
            }


        }

        public int randon_number() {

            Random rand = new Random();
            int n = rand.nextInt(6) + 1;
            return n;
        }


        public void random_draw(Canvas canvas) {


            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            int num = randon_number();
            current += num;
            int temp = 100 - current;
            int x = temp % 10;
            int y = temp / 10;
            canvas.drawRect(rect[y][x], paint);

        }

        public void random_draw_computer(Canvas canvas) {


            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            int num = randon_number();
            current += num;
            int temp = 100 - current;
            int x = temp % 10;
            int y = temp / 10;
            canvas.drawRect(rect[y][x], paint);

        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    touchpressed = true;
                    break;
            }
            invalidate();
            return super.onTouchEvent(event);
        }
    }


}