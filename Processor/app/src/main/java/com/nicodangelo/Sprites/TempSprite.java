package com.nicodangelo.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.nicodangelo.processor.GameView;

import java.util.List;

public class TempSprite
{
    private float x;
    private float y;
    private Bitmap b[];
    private int life = 9;
    private Bitmap smoke[];
    private List<TempSprite> temps;

    public TempSprite(List<TempSprite> temps, GameView game, float x, float y,Bitmap b[])
    {
        this.x = x;
        this.y = y;
        this.b = b;
        this.temps = temps;
    }
    public void onDraw(Canvas canvas)
    {
        update();
        switch(life)
        {
            case 0: canvas.drawBitmap(b[0],x,y,null); break;
            case 1: canvas.drawBitmap(b[1],x,y,null); break;
            case 2: canvas.drawBitmap(b[2],x,y,null); break;
            case 3: canvas.drawBitmap(b[3],x,y,null); break;
            case 4: canvas.drawBitmap(b[4],x,y,null); break;
            case 5: canvas.drawBitmap(b[5],x,y,null); break;
            case 6: canvas.drawBitmap(b[6],x,y,null); break;
            case 7: canvas.drawBitmap(b[7],x,y,null); break;
            case 8: canvas.drawBitmap(b[8],x,y,null); break;
            case 9: canvas.drawBitmap(b[9],x,y,null); break;
        }
    }

    private void update()
    {
        if(--life < 1)
        {
            temps.remove(this);
        }
    }


}
