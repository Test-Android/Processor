//@author Jett Kaspar
/*
Manages all game touch events, manages game thread with initialization, keeps track of "bits", updates all game visuals and some helper methods
 */

package com.nicodangelo.processor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
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
    protected  ArrayList<ProcessorSprite>  sprites;
    private ArrayList<ItemSprite>   items;
    private ArrayList<TempSprite> temps;
    private Bitmap smoke[];
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


        sprites = new ArrayList<ProcessorSprite>();
        items = new ArrayList<ItemSprite>();
        temps = new ArrayList<TempSprite>();
        smoke = new Bitmap[9];


        game = new Game(this, bit);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback()
        {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder)
            {
                System.out.println("STARTED");
                game.start();
                createSmoke();
                createButtons();
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
        canvas.drawColor(Color.rgb(59,147,229));
        for(int k = 0; k < sprites.size(); k++)
            sprites.get(k).onDraw(canvas);
        for(int k = 0; k < temps.size(); k++)
            temps.get(k).onDraw(canvas);
        for(int k = 0; k < items.size(); k++)
            items.get(k).onDraw(canvas);

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
        lastClick = System.nanoTime();
    }
    //This creates the buttons to buy processors
    public void createButtons()
    {
        Bitmap bmp;
        bmp = createTextBit("Buy: 16b Processor");
        items.add(new ItemSprite(this,bmp,width - 330, 200,16));
        bmp = createTextBit("Buy: 64b Processor");
        items.add(new ItemSprite(this,bmp,width - 330, 350,64));
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //creates a randomly placed sprite on the screen
    public void addSprite(int type)
    {
        Bitmap bmp;
        switch(type)
        {
            case 0: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
                    sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY(),type)); break;
            case 1: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.proc_2);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY(),type)); break;
            case 2: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.proc_3);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY(),type)); break;
            case 3: bmp = BitmapFactory.decodeResource(getResources(), R.drawable.renderme2);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY(),type)); break;
            case 4: bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                sprites.add(new ProcessorSprite(this,bmp,ranX(),ranY(),type)); break;
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
            case 2: b = BitmapFactory.decodeResource(getResources(),R.drawable.proc_3);
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
    //TODO: Bitmap yourBitmap = Bitmap.createBitmap(sourceBitmap, x to start from, y to start from, width, height) lets try this
    public void createTemp(int x, int y)
    {
        temps.add(new TempSprite(temps,this,(float)x,(float)y,this.smoke));
    }
    public void createSmoke()
    {
        this.smoke[0] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_1);
        this.smoke[1] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_2);
        this.smoke[2] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_3);
        this.smoke[3] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_4);
        this.smoke[4] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_5);
        this.smoke[5] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_6);
        this.smoke[6] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_7);
        this.smoke[7] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_8);
        this.smoke[8] = BitmapFactory.decodeResource(getResources(),R.drawable.smoke_9);


    }
    //This creates and returns a bitmap containing the text you sent to it
    public Bitmap createTextBit(String s)
    {
        Bitmap bmp = Bitmap.createBitmap(250,120, Bitmap.Config.ARGB_8888);
        String a[] = s.split(" ");
        String n = " " + a[0] + " " + a[1];
        Paint p = new Paint();
        Canvas c = new Canvas(bmp);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeWidth(6);
        c.drawRect(0,0,250,110,p);

        p.setTextSize(50);
        p.setStyle(Paint.Style.FILL);
        c.drawText(n,10,50,p);
        c.drawText(a[2],10,100,p);
        return bmp;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //WHEN EVER THE SCREEN IS TOUCHED!
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        
        int curX = (int)event.getX();
        int curY = (int)event.getY();
        

        if(event.getAction() == MotionEvent.ACTION_MOVE)
        {

            int chosen = getSelected(curX,curY);
            if(chosen != -1)
            {
                sprites.get(chosen).changePos((int)event.getX(),event.getY(),width,height);
                Rect rectOne = sprites.get(chosen).getRect();
                for(int b = 0; b < sprites.size();b++)
                {
                    Rect rectTwo = sprites.get(b).getRect();
                    if(sprites.get(chosen).getType() == sprites.get(b).getType())
                    {
                        if(ProcessorSprite.collision(rectOne,rectTwo))
                        {
                            connect(chosen, b);
                            return true;
                        }
                    }
                }
            }
            
            
        }
        else if(MotionEvent.ACTION_DOWN == event.getAction())
        {
            int chosen = getSelected(curX,curY);
            if(chosen != -1)
            {
                switch(sprites.get(chosen).getType())
                {
                    case 0: bit.addBits(1); break;
                    case 1: bit.addBits(8); break;
                    case 2: bit.addBits(16); break;
                    case 3: bit.addBits(32); break;
                    case 4: bit.addBits(64); break;
                }
            }
        }
/*        else if(MotionEvent.ACTION_UP == event.getAction())
        {
            //now here is were we need to check for pieces connecting
            //idk if we have a method for that but we need one...
            //also i'm doing this in teh github site so if there are errors thats why
          /*for(int a = 0; a < sprites.size();a++)
            {
                Rect rectOne = sprites.get(a).getRect();

                for(int b = 0; b < sprites.size();b++)
                {
                    Rect rectTwo = sprites.get(b).getRect();
                    if(sprites.get(a).getType() == sprites.get(b).getType())
                    {
                        if(ProcessorSprite.collision(rectOne,rectTwo))
                        {
                            connect(a, b);
                            return true;
                        }
                    }
                } */
        return true;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //runs through all sprites on the screen and will select the one you click
    public int getSelected(int x, int y)
    {
        for(int k = sprites.size() - 1; k >= 0; k--)
        {
            if(sprites.get(k).clickedInside(x,y))
            {
                System.out.println("SPRITE " + k + " CLICKED");
//                updateSelected(lastSelected, selected);
                return k;
            }            
        }
        return -1;
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
/*    public void updateSelected(int old, int cur)
    {
        Bitmap b;
        switch(sprites.get(old).getType())
        {
            case 0: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_1);
                    sprites.get(old).updateBit(b); break;
            case 1: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_2);
                sprites.get(old).updateBit(b); break;
            case 2: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_3);
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
            case 2: b = BitmapFactory.decodeResource(getResources(), R.drawable.proc_sel_3);
                sprites.get(cur).updateBit(b); break;
            case 3: b = BitmapFactory.decodeResource(getResources(), R.drawable.selrenderme2);
                sprites.get(cur).updateBit(b); break;
            case 4: b = BitmapFactory.decodeResource(getResources(), R.drawable.selic_launcher);
                sprites.get(cur).updateBit(b); break;
        }
    } */

    //OLD: If two Items are within  a given thresh hold of one another
    //They will deletes the two previous Items and creates a new one
    //One tier obove the two old ones.
    //NEW: need to run through the screen or sprites or something to check
    // if they're connecting and then connect them;
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
