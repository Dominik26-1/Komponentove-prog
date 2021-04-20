package sk.tuke.gamestudio.core;


public enum GameDifficulty {
    DIFFICULT(10, 10),
    MEDIUM(7, 8),
    EASY(5, 4);

    private final int columnCount;
    private final int rowCount;

    private GameDifficulty(int rowCount, int columnCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
    }
    public int getColumnCount() {
        return columnCount;
    }

    public int getRowCount() {
        return rowCount;
    }
}
