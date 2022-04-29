package com.states;

import com.util.KeyHandler;
import com.util.MouseHandler;

import java.awt.*;

public abstract class GameState {

    private GameStateManager gsm;

    public GameState(GameStateManager gsm){
        this.gsm = gsm;

    }

    public abstract void update();
    public abstract void input(MouseHandler mouseHandler, KeyHandler keyHandler);
    public abstract void render(Graphics2D graphics2D);
}
