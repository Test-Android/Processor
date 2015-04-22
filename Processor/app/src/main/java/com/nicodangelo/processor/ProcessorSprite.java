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

    public ProcessorSprite(GameView game, Bitmap b, int x, int y)
    {
        this.game = game;
        this.b = b;
        this.x = x;
        this.y = y;
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
        int width = b.getWidth();
        int height = b.getHeight();
        Rect temp = new Rect(this.x, this.y,width + this.x,height + this.y);
        if(temp.contains(x,y))
            return true;
        else
            return false;
    }

}
