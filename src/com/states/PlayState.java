package com.states;

import com.graphics.Font;
import com.graphics.Sprite;
import com.util.KeyHandler;
import com.util.MouseHandler;
import com.util.Vector2f;

import java.awt.*;

public class PlayState extends GameState{

    private Font font;

    public PlayState(GameStateManager gsm){
        super(gsm);
        font = new Font("font/ZeldaFont.png", 16, 16);
    }


    public void update(){

    }
    public void input(MouseHandler mouseHandler, KeyHandler keyHandler){

    }
    public void render(Graphics2D g){
        Sprite.drawArray(g, font, "DIN MAMMA", new Vector2f(100, 100), 32, 32, 32, 0);

    }
}
