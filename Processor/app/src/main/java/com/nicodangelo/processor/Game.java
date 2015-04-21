package com.nicodangelo.processor;

public class Game implements Runnable
{
    private boolean running = false;
    private Thread mainThread;
    public final String NAME = "Processor";

    public Game()
    {

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
            render();
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

    }
}
