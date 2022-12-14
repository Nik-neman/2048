package com.javarush.task.task35.task3513;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controller extends KeyAdapter {
    private Model model;
    private View view;
    private static final int WINNING_TILE = 2048;

    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
    }

    Tile[][] getGameTiles(){
        return model.getGameTiles();
    }

    int getScore(){
        return model.score;
    }

    void resetGame(){
        model.score = 0;
        view.isGameWon = false;
        view.isGameLost = false;
        model.resetGameTiles();
    }

    public void keyPressed(KeyEvent keyEvent){
        if (keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE){
            resetGame();
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_Z){
            model.rollback();
        }
        if(!model.canMove()){
            view.isGameLost = true;
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_R){
            model.randomMove();
        }
        if (keyEvent.getKeyCode() == KeyEvent.VK_A){
            model.autoMove();
        }
        if(view.isGameLost == false && view.isGameWon == false){
            if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                model.left();
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                model.right();
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                model.up();
            }
            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                model.down();
            }
            if(model.maxTile == WINNING_TILE){
                view.isGameWon = true;
            }

        }
        view.repaint();
    }

    public View getView() {
        return view;
    }
}
