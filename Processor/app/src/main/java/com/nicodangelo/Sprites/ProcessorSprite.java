package com.nicodangelo.Sprites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.nicodangelo.processor.GameView;

public class ProcessorSprite
{
    private GameView game;
    private Bitmap b;
    private int x = 0;
    private int y = 0;
    private int type = 0;

    public ProcessorSprite(GameView game, Bitmap b)
    {
        this.game = game;
        this.b = b;
    }
    public ProcessorSprite(GameView game, Bitmap b, int x, int y)
    {
        this.game = game;
        this.b = b;
        this.x = x;
        this.y = y;
    }
    public ProcessorSprite(GameView game, Bitmap b, int x, int y, int type)
    {
        this.game = game;
        this.b = b;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(b,x,y,null);
    }

    public void setType(int type)
    {
        this.type = type;
    }
    public int getType()
    {
        return type;
    }

    private void tick()
    {

    }
    public boolean clickedInside(int x, int y)
    {
        int width = b.getWidth();
        int height = b.getHeight();
        Rect temp = new Rect(this.x, this.y,width + this.x,height + this.y);
        if(temp.contains(x,y))
            return true;
        else
            return false;
    }

    public void changePos(float x, float y, int screenWidth, int screenHeight)
    {
        if(x + (b.getWidth() / 2) >= screenWidth)
            this.x = screenWidth - b.getWidth();
        else if(x - (b.getWidth() / 2) < 0)
            this.x = 0;
        else
            this.x = (int)x - b.getWidth() / 2;

        if(y + (b.getHeight() / 2) >= screenHeight)
            this.y = screenHeight - b.getHeight();
        else if(y - (b.getHeight() / 2) < 0)
            this.y = 0;
        else
            this.y = (int)y - b.getHeight() / 2;
    }
    public static Boolean collision(Rect selected, Rect temp)
    {
        if(selected.contains(temp.right,temp.top))
            return true;
        else if(selected.contains(temp.right,temp.bottom))
            return true;
        else if(selected.contains(temp.left,temp.top))
            return true;
        else if(selected.contains(temp.left,temp.bottom))
            return true;
        else if(temp.contains(selected.left,selected.top))
            return true;
        else if(temp.contains(selected.left,selected.bottom))
            return true;
        else if(temp.contains(selected.right,selected.top))
            return true;
        else if(temp.contains(selected.right,selected.bottom))
            return true;
        else
            return false;
    }
    public void updateBit(Bitmap b)
    {
        this.b = b;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }
    public int totalX()
    {
        return this.x + b.getWidth();
    }
    public int totalY()
    {
        return this.y + b.getHeight();
    }
    public Rect getRect()
    {
        return new Rect(x,y,b.getWidth(),b.getHeight());
    }
}
