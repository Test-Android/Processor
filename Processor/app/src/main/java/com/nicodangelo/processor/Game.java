package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Canvas;

public class Game implements Runnable
{
    private boolean running = false;
    private Thread mainThread;
    private GameView view;
    public final String NAME = "Processor";
    private static boolean paused;

    public Game(GameView view)
    {
        this.view = view;
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
