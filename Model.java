package com.javarush.task.task35.task3513;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Model {
    private final static int FIELD_WIDTH = 4;
    private Tile[][] gameTiles;
    int score;
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
        for (Tile[] tiles: gameTiles){

            if (compressTiles(tiles) & mergeTiles(tiles)) {
                addTile();
            }

        }
    }
}
