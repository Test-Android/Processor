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

    public ProcessorSprite(GameView game, Bitmap b)
    {
        this.game = game;
        this.b = b;
    }
    public void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(b,0,0,null);
    }

    private void tick()
    {

    }
    public boolean clickedInside(int x, int y)
    {
        Rect temp = new Rect(this.x, this.y,400,600);
        if(temp.contains(x,y))
            return true;
        else
            return false;
    }

}
