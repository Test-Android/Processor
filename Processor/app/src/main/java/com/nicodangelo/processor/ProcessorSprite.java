package com.nicodangelo.processor;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ProcessorSprite
{
    private GameView game;
    private Bitmap b;

    public ProcessorSprite(GameView game, Bitmap b)
    {
        this.game = game;
        this.b = b;
    }
    public void onDraw(Canvas canvas)
    {
        canvas.drawBitmap(b,100,100,null);
    }

    private void tick()
    {

    }

}
