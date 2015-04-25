package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.widget.TextView;

import com.nicodangelo.bits.Bit;

public class ItemSprite
{
    private GameView game;
    private Bitmap b;
    private int x = 0;
    private int y = 0;
    private int price = 0;
    private int speed = 5;

    public ItemSprite(GameView game, Bitmap b, int x, int y, int price)
    {
        this.game = game;
        this.b = b;
        this.x = x;
        this.y = y;
        this.price = price;
    }

    public void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(b,x,y,null);
    }
    public Boolean buy(Bit b)
    {
        if(b.getBits() >= (long)price)
        {
            b.subtractBits((long)price);
            return true;
        }
        else
            return false;
    }
    private void tick()
    {

    }
    public boolean clickedInside(int x, int y)
    {
        Rect temp = new Rect(this.x, this.y,b.getWidth() + this.x,b.getHeight() + this.y);
        if(temp.contains(x,y))
            return true;
        else
            return false;
    }
    public void changePos(float x, float y)
    {
        if(x + b.getWidth() >= 960)
            this.x = 960 - b.getWidth();
        else
            this.x = (int)x;

        if(y + b.getHeight() >= 1080)
            this.y = 1080 - b.getHeight();
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