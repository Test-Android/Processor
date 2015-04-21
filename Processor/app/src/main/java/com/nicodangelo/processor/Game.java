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
        while(running)
        {

        }
        stop();
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
