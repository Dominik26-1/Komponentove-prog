package sk.tuke.gamestudio.core;


public class Player {
    private String name;
    private int score;

    public Player(String name) {
        this.setName(name);
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score = this.score + score;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName() + " score: "  + this.getScore();
    }

}
