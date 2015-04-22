package com.nicodangelo.processor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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
        if(x + b.getWidth() >= screenWidth)
            this.x = screenWidth - b.getWidth();
        else
            this.x = (int)x;

        if(y + b.getHeight() >= screenHeight)
            this.y = screenHeight - b.getHeight();
        else
            this.y = (int)y;
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
}
