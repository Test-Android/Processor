package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
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
    private ArrayList<ItemSprite> itemSprites;
    private int selected = 0;
    public GameView(Context context)
    {
        super(context);
        System.out.println("got to the context.");
        sprites = new ArrayList<ProcessorSprite>();
        itemSprites = new ArrayList<ItemSprite>();
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
        Bitmap bmp  = BitmapFactory.decodeResource(getResources(), R.drawable.star1);
        itemSprites.add(new ItemSprite(this,bmp,100,100,10));
        itemSprites.add(new ItemSprite(this,bmp,300,300,10));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        for(int k = 0; k < sprites.size(); k++)
            sprites.get(k).onDraw(canvas);
        for(int k = 0; k < itemSprites.size(); k++)
            itemSprites.get(k).onDraw(canvas);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        for(int k = sprites.size() - 1; k >= 0; k--)
        {
            if(sprites.get(k).clickedInside((int)event.getX(),(int)event.getY()))
            {
                System.out.println("SPRITE " + k + " CLICKED");
                selected = k;
                break;
            }
        }
        if(selected < itemSprites.size() -1)
        {
            for(int k = 0; k < itemSprites.size(); k++)
            {
                Rect temp = new Rect(itemSprites.get(k).getX(),itemSprites.get(k).getY(), itemSprites.get(k).getX() + 50,itemSprites.get(k).getY() + 50);
                if(temp.contains((int)event.getX(),(int)event.getY()))
                {
                    connect(selected,k);
                    break;
                }
            }
        }
        return super.onTouchEvent(event);
    }
    public void connect(int x1, int x2)
    {
        itemSprites.remove(x2);
        itemSprites.remove(x1);
        Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.renderme);
        sprites.add(new ProcessorSprite(this,b,0,0));
    }
}
