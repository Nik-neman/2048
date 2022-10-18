package com.javarush.task.task35.task3513;

public class Main {
    public static void main(String[] args) {
        Tile[] tiles = {new Tile(4), new Tile(4), new Tile(2), new Tile(0)};
        Main main = new Main();

        System.out.println(main.compressTiles(tiles));
        for (Tile tile: tiles){
            System.out.print(tile.value);
        }
        System.out.println();
        System.out.println(main.mergeTiles(tiles));
        for (Tile tile: tiles){
            System.out.print(tile.value);
        }
    }

    int maxTile = 0;
    int score = 0;
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
}
