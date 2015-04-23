package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.TextView;

import com.nicodangelo.bits.Bit;

public class Game implements Runnable
{
    private boolean running = false;
    private Thread mainThread;
    private GameView view;
    private Bit bit;
    public final String NAME = "Processor";
    private static boolean paused;

    public Game(GameView view, Bit bit)
    {
        this.view = view;
        this.bit = bit;
    }

    public void run()
    {
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int ticks = 0;
        while(running)
        {

            long now = System.nanoTime();
            delta+=(now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1)
            {
                tick();
                ticks++;
                delta--;
            }
            if(!paused)
            {
                render();

            }
            frames++;

            if(System.currentTimeMillis() - timer > 1000)
            {
                timer+=1000;
                System.out.println("Ticks: " + ticks + "\nFrames: " + frames);
                frames = 0;
                ticks = 0;
            }
        }
    }

    public synchronized void start()
    {
        running = true;
        mainThread = new Thread(this, "game");
        mainThread.start();
    }

    public synchronized void stop()
    {
        running = false;
        try
        {
            mainThread.join();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
            System.out.println("HOLY CRAP IT BROKE");
        }
    }

    public void tick()
    {

    }
    public void render()
    {
        Canvas c = null;
        try
        {
            c = view.getHolder().lockCanvas();
            synchronized (view.getHolder())
            {
                view.onDraw(c);
                //c.drawColor(Color.WHITE);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setTextSize(30);
                c.drawText(Long.toString(bit.getBits()), 450, 40, paint);
            }
        }
        finally
        {
            if(c != null)
                view.getHolder().unlockCanvasAndPost(c);
        }
    }
    public static void paused(Boolean p)
    {
        paused = p;
    }
}
