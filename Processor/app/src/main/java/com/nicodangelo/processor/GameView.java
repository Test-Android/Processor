//@author Jett Kasper
/*
Manages all game touch events, manages game thread with initialization, keeps track of "bits", updates all game visuals and some helper methods
 */

package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.nicodangelo.Sprites.ItemSprite;
import com.nicodangelo.Sprites.ProcessorSprite;
import com.nicodangelo.Sprites.TempSprite;
import com.nicodangelo.bits.Bit;

import java.util.ArrayList;

public class GameView extends SurfaceView
{
    //all global variables
    private SurfaceHolder holder;
    private Game game;
    public  ArrayList<ProcessorSprite>  sprites = new ArrayList<ProcessorSprite>();
    private ArrayList<ItemSprite>   items = new ArrayList<ItemSprite>();
    private ArrayList<TempSprite> temps = new ArrayList<TempSprite>();
    private Bit bit;
    private int selected = 0;
    private final int width;
    private final int height;
    private static boolean ableSelect = true;
    private long lastClick;

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //MAIN CONSTRUCTOR
    public GameView(Context context, int width, int height, Bit bit)
    {
        super(context);

        //make sure to connect the bit to "bit"
        this.bit = bit;
        this.width = width;
        this.height = height;
        game = new Game(this, bit);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder)
            {
                System.out.println("STARTED");
                game.start();
                decodeResources();
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3)
            {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder)
            {
                game.stop();
                System.out.println("STOPPING THE GAME");
            }
        });
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //THE VISUAL UPDATE METHOD
    @Override
    protected void onDraw(Canvas canvas)
    {
        canvas.drawColor(Color.WHITE);
        for(int k = 0; k < items.size(); k++)
            items.get(k).onDraw(canvas);
        for(int k = 0; k < sprites.size(); k++)
            sprites.get(k).onDraw(canvas);
        for(int k = 0; k < temps.size(); k++)
            temps.get(k).onDraw(canvas);

    }

    //TODO: take this out after some time (for us:))
    //Initializes the sprites
    public void decodeResources()
     {
        Bitmap bmp  = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
        sprites.add(new ProcessorSprite(this,bmp,100,100));
        sprites.add(new ProcessorSprite(this,bmp,300,300));
        sprites.add(new ProcessorSprite(this,bmp,900,400));
        sprites.add(new ProcessorSprite(this,bmp,600,600));
        sprites.add(new ProcessorSprite(this,bmp,200,900));
        sprites.add(new ProcessorSprite(this,bmp,700,200));
        sprites.add(new ProcessorSprite(this,bmp,400,500));
        sprites.add(new ProcessorSprite(this, bmp, 800, 100));
        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.add_button);
        items.add(new ItemSprite(this,bmp,width - 250, 200,8));
        lastClick = System.nanoTime();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //creates a randomly placed sprite on the screen
    public void addSprite(int type)
    {
        Bitmap bmp;
        switch(type)
        {
            case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
                    sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY())); break;
            case 1: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.renderme);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY())); break;
            case 2: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.star2);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY())); break;
            case 3: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.renderme2);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY())); break;
            case 4: bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY())); break;
        }

    }

    //Creates a sprite at given x, y value
    public void addSprite(int type, int x, int y)
    {
        Bitmap b;
        switch(type)
        {
            case 1: b = BitmapFactory.decodeResource(getResources(),R.drawable.proc_2);
                sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            case 2: b = BitmapFactory.decodeResource(getResources(),R.drawable.star2);
                sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            case 3: b = BitmapFactory.decodeResource(getResources(), R.drawable.renderme2);
                sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            case 4: b = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                sprites.add(new ProcessorSprite(this,b,x,y,type)); break;
            default: b = BitmapFactory.decodeResource(getResources(),R.drawable.proc_1);
                sprites.add(new ProcessorSprite(this,b,x,y,0)); break;
        }
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //TODO: Attempting to use the temp sprites
    //TODO: GET SEPERATE BITMAPS DEPENDING ON THE LIFE OF
    //TODO: THE TEMPSPRITE OBJECT(initalize them in the tempsprite
    //TODO: class maybe?)
    public void createTemp(int x, int y)
    {
        temps.add(new TempSprite(temps,this,(float)x,(float)y,BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //WHEN EVER THE SCREEN IS TOUCHED!
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction() == MotionEvent.ACTION_DOWN && ableSelect)
            getSelected((int) event.getX(), (int) event.getY());
        else if(event.getAction() == MotionEvent.ACTION_DOWN)
            buyProc((int)event.getX(),(int)event.getY());

        if(selected >= sprites.size())
            selected = 0;

        if(ableSelect && event.getAction() == MotionEvent.ACTION_MOVE)
            sprites.get(selected).changePos(event.getX(),event.getY(),width,height);
        else if(!ableSelect && event.getAction() == MotionEvent.ACTION_DOWN && System.nanoTime() - lastClick > 100000)
        {
            lastClick = System.nanoTime();

            System.out.println("Selected sprite == " + selected);
            System.out.println("X and Y inside seleceted == " + sprites.get(selected).clickedInside((int)event.getX(),(int)event.getY()));
            if(sprites.get(selected).clickedInside((int)event.getX(),(int)event.getY()))
            {
                switch(sprites.get(selected).getType())
                {
                    case 0: bit.addBits(1); break;
                    case 1: bit.addBits(8); break;
                    case 2: bit.addBits(16); break;
                    case 3: bit.addBits(32); break;
                    case 4: bit.addBits(64); break;

                }
            }
        }

        if(sprites.size() != 1)
        {
            Rect selectedRect = new Rect(sprites.get(selected).getX(),sprites.get(selected).getY(),sprites.get(selected).totalX(),sprites.get(selected).totalY());
            for(int k = 0; k < sprites.size();k++)
            {
                Rect tempRect = new Rect(sprites.get(k).getX(),sprites.get(k).getY(),sprites.get(k).totalX(),sprites.get(k).totalY());
                if(ProcessorSprite.collision(selectedRect, tempRect) && selected != k && sprites.get(selected).getType() == sprites.get(k).getType())
                {
                    connect(selected,k);
                    break;
                }
            }
        }
        return true;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //runs through all sprites on the screen and will select the one you click
    public void getSelected(int x, int y)
    {
        if(ableSelect)
        {
            for(int k = sprites.size() - 1; k >= 0; k--)
            {
                if(sprites.get(k).clickedInside(x,y))
                {
                    System.out.println("SPRITE " + k + " CLICKED");
                    int lastSelected = selected;
                    selected = k;
                    updateSelected(lastSelected, selected);
                    break;
                }
            }
        }
    }

    //"purchases" a processor and takes away from the total bits
    public void buyProc(int x, int y)
    {
        int count = 0;
        boolean bought = false;
        while(count < items.size() && !bought)
        {
            if(items.get(count).clickedInside(x,y))
            {
                if(items.get(count).buy(bit))
                {
                    addSprite(count);
                    bought = true;
                }
            }
            count++;
        }
    }

    //Unhighlight the previous Sprite and rehightlight the selected.
    public void updateSelected(int old, int cur)
    {
        Bitmap b;
        switch(sprites.get(old).getType())
        {
            case 0: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
                    sprites.get(old).updateBit(b); break;
            case 1: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_2);
                sprites.get(old).updateBit(b); break;
            case 2: b = BitmapFactory.decodeResource(getResources(), R.drawable.star2);
                sprites.get(old).updateBit(b); break;
            case 3: b = BitmapFactory.decodeResource(getResources(), R.drawable.renderme2);
                sprites.get(old).updateBit(b); break;
            case 4: b = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                sprites.get(old).updateBit(b); break;
        }

//TODO     ADD SPRITE IMAGES FOR SELECTED ITEMS, I IMPLEMENTED THE ABILITY TO DO SO
//TODO     JUST SOMETHING SIMPLE LIKE A RED OUTLINE WOULD WORK
        switch(sprites.get(cur).getType())
        {
            case 0: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_sel_1);
                sprites.get(cur).updateBit(b); break;
            case 1: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_sel_2);
                sprites.get(cur).updateBit(b); break;
            case 2: b = BitmapFactory.decodeResource(getResources(), R.drawable.selstar2);
                sprites.get(cur).updateBit(b); break;
            case 3: b = BitmapFactory.decodeResource(getResources(), R.drawable.selrenderme2);
                sprites.get(cur).updateBit(b); break;
            case 4: b = BitmapFactory.decodeResource(getResources(), R.drawable.selic_launcher);
                sprites.get(cur).updateBit(b); break;
        }
    }

    //If two Items are within  a given thresh hold of one another
    //They will deletes the two previous Items and creates a new one
    //One tier obove the two old ones.
    public void connect(int x1, int x2)
    {
        int x = sprites.get(selected).getX();
        int y = sprites.get(selected).getY();
        int type = sprites.get(selected).getType() + 1;
        if(x1 < x2)
        {
            sprites.remove(x2);
            sprites.remove(x1);
        }
        else
        {
            sprites.remove(x1);
            sprites.remove(x2);
        }
        addSprite(type,x,y);
        createTemp(x,y);
        selected = sprites.size() - 1;
    }


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /*
    All one liners!!!!
     */

    //returns random x value with in the screen width
    public int ranX()
    {
        return (int)(Math.random() * width);
    }

    //returns random y value with in the screen height
    public int ranY()
    {
        return (int)(Math.random() * height);
    }

    //determines wither or not motion is allowed by the user
    public static void selectivity(boolean select)
    {
        ableSelect = select;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
