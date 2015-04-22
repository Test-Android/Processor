package com.nicodangelo.processor;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Main extends Activity
{
    boolean pause = false;
    public static TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        FrameLayout layout = new FrameLayout(this);
        final GameView game = new GameView(this,width,height);
        LinearLayout gameButtons = new LinearLayout(this);
        Button b = new Button(this);
        b.setText("Pause");
        b.setWidth(300);
        b.setBackgroundColor(Color.BLACK);
        b.setTextColor(Color.WHITE);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pause) {
                    pause = false;
                    Game.paused(false);
                } else {
                    pause = true;
                    Game.paused(true);
                }
            }
        });
        Button add = new Button(this);
        add.setText("add");
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               game.addSprite();
            }
        });
        gameButtons.addView(b);
        gameButtons.addView(add);
        layout.addView(game);
        layout.addView(gameButtons);
        setContentView(layout);

    }
    @Override
    protected void onPause()
    {
        Game.paused(true);
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        Game.paused(false);
        super.onResume();
    }
}
