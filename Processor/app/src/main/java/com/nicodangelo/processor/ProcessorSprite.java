package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

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

    public void changePos(float x, float y)
    {
        DisplayMetrics metrics = new DisplayMetrics();
        .getWindowManager().getDefaultDisplay().getMetrics(metrics);

        metrics.heightPixels;
        metrics.widthPixels;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();  // deprecated
        int height = display.getHeight();  // deprecated
        if(x + b.getWidth() >= width)
            this.x = width - b.getWidth();
        else
            this.x = (int)x;

        if(y + b.getHeight() >= height)
            this.y = height - b.getHeight();
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
