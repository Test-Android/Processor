package com.nicodangelo.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.nicodangelo.processor.GameView;

import java.util.List;

public class TempSprite
{
    private float x;
    private float y;
    private Bitmap b;
    private int life = 9;
    private Bitmap smoke[];
    private List<TempSprite> temps;

    public TempSprite(List<TempSprite> temps, GameView game, float x, float y,Bitmap b)
    {
        this.x = x;
        this.y = y;
        this.b = b;
        this.temps = temps;
    }
    public void onDraw(Canvas canvas)
    {
        update();
        canvas.drawBitmap(b,x,y,null);
    }

    private void update()
    {
        if(--life < 1)
        {
            temps.remove(this);
        }
    }


}
