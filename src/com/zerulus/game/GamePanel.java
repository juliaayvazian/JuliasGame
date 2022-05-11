package com.zerulus.game;

import com.states.GameStateManager;
import com.util.KeyHandler;
import com.util.MouseHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel implements Runnable{

    public static int width;
    public static int height;
    
    private Thread thread;
    private boolean running = false;

    private BufferedImage image;
    private Graphics2D graphics2D;

    private MouseHandler mouseHandler;
    private KeyHandler keyHandler;

    private GameStateManager gsm;

    public GamePanel(int width, int height ){
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
    }

    public void addNotify(){
        super.addNotify();

        if(thread == null) {
            thread = new Thread(this, "GameThread");
            thread.start();
        }
    }

    public void init(){
        running = true;
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        graphics2D = (Graphics2D) image.getGraphics();

        mouseHandler = new MouseHandler(this);
        keyHandler = new KeyHandler(this);

        gsm = new GameStateManager();
    }

    public void run(){
        init();

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ; // Time Before Update
        final int MUBR = 5; // Most updates before render

        double lastUpdateTime = System.nanoTime();
        double lastRendertime;
        
        final double TARGET_FPS = 60;
        final double TTBR = 1000000000/ TARGET_FPS; //Total time before render

        int frameCount = 0;
        int lastSecondTime = (int) (lastUpdateTime / 1000000000); //(fix) moved the bracket to the end of the fraction
        int oldFrameCount = 0;

        while(running){
            double now = System.nanoTime();
            int updateCount = 0;
            while(((now-lastUpdateTime) > TBU) && (updateCount < MUBR)){
                update();
                input(mouseHandler, keyHandler);
                lastUpdateTime += TBU;
                updateCount++;
            }

            if(now-lastUpdateTime > TBU){
                lastUpdateTime = now - TBU;
            }

            input(mouseHandler, keyHandler);
            render();
            draw();
            lastRendertime = now;
            frameCount++;

            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if(thisSecond >  lastSecondTime){
                if(frameCount != oldFrameCount){
                    System.out.println("NEW SECOND " + thisSecond + " " + frameCount); //(fix) added spaces
                    oldFrameCount=frameCount;
                }

                frameCount = 0;
                lastSecondTime = thisSecond;
            }

            while(now - lastRendertime < TTBR && now - lastUpdateTime < TBU){
                Thread.yield();
                try{
                    Thread.sleep(1);
                } catch (Exception e){
                    System.out.println("ERROR: Yielding thread");
                }
                now = System.nanoTime();
            }
        }
    }

    public void update(){
        gsm.update();
    }

    public void input(MouseHandler mouseHandler, KeyHandler keyHandler){
        gsm.input(mouseHandler, keyHandler);

    }

    public void render(){
        if(graphics2D != null){
            graphics2D.setColor(new Color(235, 211, 243));
            graphics2D.fillRect(0, 0, width, height);
            gsm.render(graphics2D);
        }
    }

    public void draw(){
        Graphics g2 = (Graphics) this.getGraphics(); // (fix) changed Graphics() to getGraphics()
        g2.drawImage(image, 0, 0, width, height, null);
        g2.dispose();
    }

}
