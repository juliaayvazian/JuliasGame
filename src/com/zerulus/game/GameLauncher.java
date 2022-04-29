package com.zerulus.game;

public class GameLauncher {

    public GameLauncher(){
        new Window();
        GamePanel gamePanel = new GamePanel(1280, 720);
        gamePanel.run();
    }

    public static void main(String[] args) {
        new GameLauncher();
    }
}
