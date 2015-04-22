package com.nicodangelo.processor;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.nicodangelo.processor.R.mipmap.ic_launcher;


public class Main extends Activity
{
    boolean pause = false;
    boolean selected = true;
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
        ImageView iv = new ImageView(this);
        iv.setImageBitmap(BitmapFactory.decodeResource(getResources(), ic_launcher));
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(selected)
                {
                    selected = false;
                    game.selectivity(selected);
                }
                else
                {
                    selected = true;
                    game.selectivity(selected);

                }
            }
        });
        gameButtons.addView(b);
        gameButtons.addView(add);
        gameButtons.addView(iv);
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
