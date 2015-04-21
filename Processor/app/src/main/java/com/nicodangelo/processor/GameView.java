package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jetts on 4/20/2015.
 */
public class GameView extends SurfaceView
{
    private SurfaceHolder holder;
    private Game game;
    private ProcessorSprite sprite;
    private ItemSprite oneChip;
    public GameView(Context context)
    {
        super(context);
        System.out.println("got to the context.");
        game = new Game(this);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder)
            {
                System.out.println("SHOULD HAVE BEEN STARTED");
                game.start();
            }
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3)
            {

            }
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder)
            {
                game.stop();
                System.out.println("STOPPING THE GAME");
            }
        });
        Bitmap bmp  = BitmapFactory.decodeResource(getResources(), R.drawable.renderme);
        sprite = new ProcessorSprite(this,bmp);
        oneChip = new ItemSprite(this,bmp,400,400,20);

    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        sprite.onDraw(canvas);
        oneChip.onDraw(canvas);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(sprite.clickedInside((int)event.getX(),(int)event.getY()))
            System.out.println("YOU CLICKED INSIDE THE THING");
        else if(oneChip.clickedInside((int)event.getX(),(int)event.getY()))
            System.out.println("YOU CLICKED AN ITEM SPRITE");
        return super.onTouchEvent(event);
    }
}
