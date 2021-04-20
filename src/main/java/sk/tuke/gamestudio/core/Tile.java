package sk.tuke.gamestudio.core;


public class Tile {
    private TileState state;
    private final int value;
    public Tile(int value) {
        setState(TileState.CLOSED);
        this.value = value;
    }


    public TileState getState() {
        return state;
    }
    void setState(TileState state) {
        this.state = state;
    }
    public int getValue(){
        return this.value;
    }
}
