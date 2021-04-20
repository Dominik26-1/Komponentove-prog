package sk.tuke.gamestudio.core;


import com.sun.istack.NotNull;

import java.util.*;

public class Field{
    private final Tile fieldTile[][];
    private final int rowCount;
    private final int columnCount;
    private List<Player> partipiants;
    private Player activePlayer;
    private GameState gameState;
    private Situation situation;
    private int unSuccessfulTurn;

    public Field(int rows, int columns) {
        rowCount = rows;
        columnCount = columns;
        fieldTile = new Tile[rowCount][columnCount];
        partipiants = new ArrayList<>();
        generate();
        gameState = GameState.PLAYING;
        situation = Situation.STARTING;


    }
    public Field(GameDifficulty difficulty){
        rowCount = difficulty.getRowCount();
        columnCount = difficulty.getColumnCount();
        fieldTile = new Tile[rowCount][columnCount];
        partipiants = new ArrayList<>();
        generate();
        gameState = GameState.PLAYING;
        situation = Situation.STARTING;

    }


    public void openTile(int row, int column) {
        //close openedTile if player ended his turn
        List<Tile> openedTile = getOpenedTile();
        if(openedTile.size()!=1){
            closeTile(openedTile);
        }
        try {
            if (fieldTile[row][column].getState() == TileState.CLOSED) {
                fieldTile[row][column].setState(TileState.OPEN);
            }
        }catch (Exception e){
            situation = Situation.INVALID;
            return;
        }
        //open Tile
        openedTile =  getOpenedTile();
        if(openedTile.size()==2){
            if(checkPair(openedTile.get(0),openedTile.get(1))){
                activePlayer.addScore((int) (50*(getRemainingTile()/(rowCount*columnCount)*10) -  (unSuccessfulTurn*15)));
                unSuccessfulTurn=0;
                situation = Situation.PAIRED;
            }
            else{
                unSuccessfulTurn++;
                situation = Situation.UNPAIRED;
                switchPlayer();
            }
        }
        else if(openedTile.size()==1){
            situation = Situation.ONECARD;
        }
        //check if game is finished
        isFinished();
    }

    private boolean checkPair(@NotNull Tile firstTile, @NotNull Tile secondTile) {
        if (firstTile.getValue() == secondTile.getValue()) {
            firstTile.setState(TileState.PAIRED);
            secondTile.setState(TileState.PAIRED);
            return true;
        }
        return false;
    }

    private void closeTile(List<Tile> openedTile) {
        if(openedTile.size()==0){
            return;
        }
        for (Tile t : openedTile){
            t.setState(TileState.CLOSED);
        }
    }

    private void isFinished() {
        for (Tile[] t : fieldTile) {
            for (Tile tile : t) {
                if (tile.getState() != TileState.PAIRED) {
                    gameState = GameState.PLAYING;
                    return;
                }
            }
        }
        gameState = GameState.FINISHED;
    }

    private void generate() {
        //check if generating is possible
       if(columnCount*rowCount%2!=0){
           return;
       }
        List<Integer> source = new ArrayList<>();
        for (int i = 0; i < rowCount*columnCount/2; i++) {
            source.add(Integer.valueOf(i+1));
            source.add(Integer.valueOf(i+1));

        }

        for (int positionRow = 0; positionRow < rowCount; positionRow++) {
            for (int positionColumn = 0; positionColumn < columnCount; positionColumn++) {
                int index = (int) (Math.random() * source.size() - 1);
                fieldTile[positionRow][positionColumn] = new Tile(source.get(index));
                source.remove(index);


            }
        }

    }

    private void switchPlayer() {

        if (partipiants.indexOf(activePlayer) >= partipiants.size() - 1) {
            activePlayer = partipiants.get(0);
            return;
        }
        activePlayer = partipiants.get(partipiants.indexOf(activePlayer) + 1);
        return;
    }

    public void addPlayer(Player partipiant) {
        if(activePlayer==null){
            activePlayer = partipiant;
        }
        partipiants.add(partipiant);
    }

    public Player getWinner() {
        Player winner;

        Collections.sort(partipiants, new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getScore()-o2.getScore();
            }

        });
        if(partipiants.get(0).getScore()==partipiants.get(1).getScore()){
            return null;
        }
        winner = partipiants.get(0);

        return winner;
    }
    
    public List<Tile> getOpenedTile() {
        List<Tile> openedTile = new ArrayList<>();
        for (Tile[] t : fieldTile) {
            for (Tile tile : t) {
                if (tile.getState() == TileState.OPEN) {
                    openedTile.add(tile);
                }
            }
        }
        return openedTile;
    }

    public double getRemainingTile(){
        List<Tile> pairedTile = new ArrayList<>();
        for (Tile[] t : fieldTile) {
            for (Tile tile : t) {
                if (tile.getState() == TileState.PAIRED) {
                    pairedTile.add(tile);
                }
            }
        }
        return (columnCount*rowCount) - pairedTile.size();
    }

    public List<Player> getPartipiants(){
        return partipiants;
    }

    public int getRowCount() { return rowCount;
    }

    public int getColumnCount() { return columnCount;
    }

    public Tile getTile(int row, int column) {
        return fieldTile[row][column];
    }

    public GameState getGameState() {
        return gameState;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
    public Situation getSituation() {
        return situation;
    }

}
