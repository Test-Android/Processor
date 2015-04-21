package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jetts on 4/20/2015.
 */
public class GameView extends SurfaceView
{
    private SurfaceHolder holder;
    private Game game;
    public GameView(Context context)
    {
        super(context);
        game = new Game();
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder)
            {
                game.start();
            }
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3)
            {

            }
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder)
            {
                game.stop();
            }
        });
    }
    @Override
    protected void onDraw(Canvas canvas)
    {

    }
}
