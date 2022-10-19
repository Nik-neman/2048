package com.javarush.task.task35.task3513;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Model {
    private final static int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    private Stack<Tile[][]>  previousStates = new Stack<>();
    int score;
    Stack<Integer> previousScores = new Stack<>();
    boolean isSaveNeeded = true;
    int maxTile;

    public Model() {
        resetGameTiles();
    }

    private void addTile(){
        List<Tile> tiles = getEmptyTiles();
        if (!tiles.isEmpty()) {
            int numerTile = (int) (tiles.size() * Math.random());
            tiles.get(numerTile).value = Math.random() < 0.9 ? 2 : 4;
        }
    }

    private List<Tile> getEmptyTiles(){
        List<Tile> tiles = new ArrayList<>();
        for(int i = 0; i < gameTiles.length; i++){
            for (int j = 0; j < gameTiles[i].length; j++){
                if(gameTiles[i][j].isEmpty()){//
                    tiles.add(gameTiles[i][j]);
                }
            }
        }
        return tiles;
    }

     void resetGameTiles(){
        this.gameTiles = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < gameTiles.length; i++){
            for (int j = 0; j < gameTiles[i].length; j++){
                gameTiles[i][j] = new Tile(0);
            }
        }
        this.score = 0;
        this.maxTile = 0;
        addTile();
        addTile();
    }

    private boolean compressTiles(Tile[] tiles){
        boolean changed = false;
        for (int i =0; i < tiles.length-1; i++){
            for (int j = 0; j < tiles.length - 1; j++){
                if(tiles[j].value == 0 && tiles[j + 1].value != 0){
                    tiles[j].value = tiles[j + 1].value;
                    tiles[j + 1].value = 0;
                    changed = true;
                }
            }
        }
        return changed;
    }

    private boolean mergeTiles(Tile[] tiles){
        boolean changed = false;
        for(int i = 0; i < tiles.length-1; i ++) {
            if (tiles[i].value == tiles[i + 1].value && tiles[i].value != 0) {
                tiles[i].value += tiles[i].value;
                tiles[i + 1].value = 0;
                this.compressTiles(tiles);
                if (tiles[i].value > maxTile) maxTile = tiles[i].value;
                score += tiles[i].value;
                changed = true;
            }
        }
        return changed;
    }

    void left(){
        if(isSaveNeeded) {
            saveState(gameTiles);
        }

        for (Tile[] tiles: gameTiles){
            if (compressTiles(tiles) & mergeTiles(tiles)) {
                addTile();
            }
        }
        isSaveNeeded = true;
    }

    void right(){
        saveState(gameTiles);
        turnRight();
        turnRight();
        left();
        turnRight();
        turnRight();
    }

    void up(){
        saveState(gameTiles);
        turnRight();
        turnRight();
        turnRight();
        left();
        turnRight();
    }

    void down(){
        saveState(gameTiles);
        turnRight();
        left();
        turnRight();
        turnRight();
        turnRight();
    }

    void turnRight(){
        Tile[][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];
        for(int i = 0; i < FIELD_WIDTH; i ++){
            for (int j = 0; j < FIELD_WIDTH; j++){
                temp[i][FIELD_WIDTH - 1 - j] = gameTiles[j][i];
            }
        }
        gameTiles = temp;
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove(){
        boolean change = false;
        for(int i = 0; i < FIELD_WIDTH; i ++){
            for (int j = 0; j < FIELD_WIDTH; j++){
                if((j + 1) < FIELD_WIDTH){
                    if(gameTiles[i][j].value == gameTiles[i][j + 1].value
                            || gameTiles[i][j].value == 0){
                        change = true;
                    }
                }
                if((i + 1) < FIELD_WIDTH ) {
                    if (gameTiles[i][j].value == gameTiles[i + 1][j].value) {
                        change = true;
                    }
                }
            }
        }
        return change;
    }

    private void saveState(Tile[][] tiles){
        Tile[][] safed = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for(int i = 0; i < FIELD_WIDTH; i++){
            for (int j = 0; j < FIELD_WIDTH; j++){
               safed[i][j] = new Tile(tiles[i][j].value) ;
            }
        }
        previousStates.push(safed);
        previousScores.push(score);
        isSaveNeeded = false;
    }

    public void rollback(){
        if(!previousStates.isEmpty() && !previousScores.isEmpty()) {
            gameTiles =(Tile[][]) previousStates.pop();
            score = previousScores.pop();
        }
    }
}
