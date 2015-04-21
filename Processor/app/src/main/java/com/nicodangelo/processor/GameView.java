package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jetts on 4/20/2015.
 */
public class GameView extends SurfaceView
{
    private SurfaceHolder holder;
    private Game game;
    private ArrayList<ProcessorSprite> sprites;
    public GameView(Context context)
    {
        super(context);
        System.out.println("got to the context.");
        sprites = new ArrayList<ProcessorSprite>();
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
        sprites.add(new ProcessorSprite(this,bmp,90,90));
        sprites.add(new ProcessorSprite(this,bmp,300,300));

    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        for(int k = 0; k < sprites.size(); k++)
            sprites.get(k).onDraw(canvas);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        for(int k = 0; k < sprites.size(); k++)
        {
            if(sprites.get(k).clickedInside((int)event.getX(),(int)event.getY()))
            {
                System.out.println("SPRITE " + k + " CLICKED");
                break;
            }
        }
        return super.onTouchEvent(event);
    }
}
