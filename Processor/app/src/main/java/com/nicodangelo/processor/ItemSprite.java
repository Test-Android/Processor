package com.nicodangelo.processor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Jetts on 4/21/2015.
 */
public class ItemSprite
{
    private GameView game;
    private Bitmap b;
    private int x = 0;
    private int y = 0;
    private int price = 0;
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
}