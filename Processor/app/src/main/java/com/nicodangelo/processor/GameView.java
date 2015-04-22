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
import android.view.View;

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
    private ItemSprite itemSprite;
    private int selected = 0;
    private final int width;
    private final int height;
    private static boolean ableSelect = true;
    public GameView(Context context, int width, int height)
    {
        super(context);
        System.out.println("got to the context.");
        this.width = width;
        this.height = height;
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

        Bitmap bmp  = BitmapFactory.decodeResource(getResources(), R.drawable.star1);
        sprites.add(new ProcessorSprite(this,bmp,100,100));
        sprites.add(new ProcessorSprite(this,bmp,300,300));
        sprites.add(new ProcessorSprite(this,bmp,900,400));
        sprites.add(new ProcessorSprite(this,bmp,600,600));
        sprites.add(new ProcessorSprite(this,bmp,200,900));
        sprites.add(new ProcessorSprite(this,bmp,700,200));
        sprites.add(new ProcessorSprite(this,bmp,400,500));
        sprites.add(new ProcessorSprite(this,bmp,800,100));
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        itemSprite = new ItemSprite(this,bmp,0,0,0);

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        for(int k = 0; k < sprites.size(); k++)
            sprites.get(k).onDraw(canvas);
        itemSprite.onDraw(canvas);
    }
    public void addSprite()
    {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.star1);
        sprites.add(new ProcessorSprite(this,bmp,(int)(Math.random() * 700),(int)(Math.random() * 1000)));
    }
    public static void selectivity(boolean select)
    {
        ableSelect = select;
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
        if(selected >= sprites.size())
            selected = 0;
        if(sprites.size() != 1)
        {
            Rect selectedRect = new Rect(sprites.get(selected).getX(),sprites.get(selected).getY(),sprites.get(selected).totalX(),sprites.get(selected).totalY());
            for(int k = 0; k < sprites.size();k++)
            {
                Rect tempRect = new Rect(sprites.get(k).getX(),sprites.get(k).getY(),sprites.get(k).totalX(),sprites.get(k).totalY());
                if(ProcessorSprite.collision(selectedRect, tempRect) && selected != k && sprites.get(selected).getType() == sprites.get(k).getType())
                {
                    connect(selected,k);
                    break;
                }
            }
        }
        if(selected < sprites.size() && ableSelect)
            sprites.get(selected).changePos(event.getX(),event.getY(),width,height);

        return super.onTouchEvent(event);
    }
    public void connect(int x1, int x2)
    {
        int x = sprites.get(selected).getX();
        int y = sprites.get(selected).getY();
        int type = sprites.get(selected).getType() + 1;
        if(x1 < x2)
        {
            sprites.remove(x2);
            sprites.remove(x1);
        }
        else
        {
            sprites.remove(x1);
            sprites.remove(x2);
        }
        Bitmap b;
        switch(type)
        {
            case 1: b = BitmapFactory.decodeResource(getResources(),R.drawable.renderme);
                    sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            case 2: b = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                    sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
        }

    }
}
