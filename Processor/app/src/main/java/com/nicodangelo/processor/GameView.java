package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.nicodangelo.bits.Bit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jetts on 4/20/2015.
 */
public class GameView extends SurfaceView
{
    private SurfaceHolder holder;
    private Game game;
    public  ArrayList<ProcessorSprite> sprites;
    private ArrayList<ItemSprite> items;
    private Bit bit;
    private int selected = 0;
    private final int width;
    private final int height;
    private static boolean ableSelect = true;
    private long lastClick;
    public GameView(Context context, int width, int height, Bit bit)
    {
        super(context);

        //make sure to connect the bit to "bit"
        this.bit = bit;

        System.out.println("got to the context.");
        this.width = width;
        this.height = height;
        sprites = new ArrayList<ProcessorSprite>();
        items = new ArrayList<ItemSprite>();
        game = new Game(this, bit);
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

        Bitmap bmp  = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
        sprites.add(new ProcessorSprite(this,bmp,100,100));
        sprites.add(new ProcessorSprite(this,bmp,300,300));
        sprites.add(new ProcessorSprite(this,bmp,900,400));
        sprites.add(new ProcessorSprite(this,bmp,600,600));
        sprites.add(new ProcessorSprite(this,bmp,200,900));
        sprites.add(new ProcessorSprite(this,bmp,700,200));
        sprites.add(new ProcessorSprite(this,bmp,400,500));
        sprites.add(new ProcessorSprite(this,bmp,800,100));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.star1);
        items.add(new ItemSprite(this,bmp,width - 70, height - 200,8));
        lastClick = System.nanoTime();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);

        for(int k = 0; k < sprites.size(); k++)
            sprites.get(k).onDraw(canvas);
        for(int k = 0; k < items.size(); k++)
            items.get(k).onDraw(canvas);
    }
    public void addSprite()
    {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
        sprites.add(new ProcessorSprite(this,bmp,(int)(Math.random() * 700),(int)(Math.random() * 1000)));
    }
    public static void selectivity(boolean select)
    {
        ableSelect = select;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        getSelected((int)event.getX(), (int)event.getY());

        if(selected >= sprites.size())
            selected = 0;

        if(ableSelect && event.getAction() == MotionEvent.ACTION_MOVE)
            sprites.get(selected).changePos(event.getX(),event.getY(),width,height);
        else if(!ableSelect && event.getAction() == MotionEvent.ACTION_DOWN && System.nanoTime() - lastClick > 100000)
        {
            lastClick = System.nanoTime();

            System.out.println("Selected sprite == " + selected);
            System.out.println("X and Y inside seleceted == " + sprites.get(selected).clickedInside((int)event.getX(),(int)event.getY()));
            if(sprites.get(selected).clickedInside((int)event.getX(),(int)event.getY()))
            {
                switch(sprites.get(selected).getType())
                {
                    case 0: bit.addBits(1); break;
                    case 1: bit.addBits(8); break;
                    case 2: bit.addBits(16); break;
                    case 3: bit.addBits(32); break;
                    case 4: bit.addBits(64); break;

                }
            }
        }

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
        //return super.onTouchEvent(event);
        return true;
    }
    public void getSelected(int x, int y)
    {
        if(ableSelect)
        {
            for(int k = sprites.size() - 1; k >= 0; k--)
            {
                if(sprites.get(k).clickedInside(x,y))
                {
                    System.out.println("SPRITE " + k + " CLICKED");
                    int lastSelected = selected;
                    selected = k;
                    updateSelected(lastSelected, selected);
                    break;
                }
            }
        }
        else
        {
            for(int k = 0; k < items.size(); k++)
            {
                if(items.get(k).clickedInside(x,y))
                {
                    items.get(k).buy(bit);
                    break;
                }
            }
        }
    }
    public void updateSelected(int old, int cur)
    {
        Bitmap b;
        switch(sprites.get(old).getType())
        {
            case 0: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
                    sprites.get(old).updateBit(b); break;
            case 1: b = BitmapFactory.decodeResource(getResources(), R.drawable.renderme);
                sprites.get(old).updateBit(b); break;
            case 2: b = BitmapFactory.decodeResource(getResources(), R.drawable.star2);
                sprites.get(old).updateBit(b); break;
            case 3: b = BitmapFactory.decodeResource(getResources(), R.drawable.renderme2);
                sprites.get(old).updateBit(b); break;
            case 4: b = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                sprites.get(old).updateBit(b); break;
        }

//TODO     ADD SPRITE IMAGES FOR SELECTED ITEMS, I IMPLEMENTED THE ABILITY TO DO SO
//TODO     JUST SOMETHING SIMPLE LIKE A RED OUTLINE WOULD WORK
        switch(sprites.get(cur).getType())
        {
            case 0: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_sel_1);
                sprites.get(cur).updateBit(b); break;
            case 1: b = BitmapFactory.decodeResource(getResources(), R.drawable.selrenderme);
                sprites.get(cur).updateBit(b); break;
            case 2: b = BitmapFactory.decodeResource(getResources(), R.drawable.selstar2);
                sprites.get(cur).updateBit(b); break;
            case 3: b = BitmapFactory.decodeResource(getResources(), R.drawable.selrenderme2);
                sprites.get(cur).updateBit(b); break;
            case 4: b = BitmapFactory.decodeResource(getResources(), R.drawable.selic_launcher);
                sprites.get(cur).updateBit(b); break;
        }
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
            case 2: b = BitmapFactory.decodeResource(getResources(),R.drawable.star2);
                sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            case 3: b = BitmapFactory.decodeResource(getResources(), R.drawable.renderme2);
                sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            case 4: b = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            default: b = BitmapFactory.decodeResource(getResources(),R.drawable.proc_1);
                sprites.add(new ProcessorSprite(this,b,x,y,0)); break;
        }
        selected = sprites.size() - 1;
    }
}
