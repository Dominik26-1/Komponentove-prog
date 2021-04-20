package sk.tuke.gamestudio.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.util.Date;




@Entity
@NamedQuery(name = "Score.getTopScores",
        query = "SELECT s FROM Score s WHERE s.game=:game ORDER BY s.points DESC")
@NamedQuery(name = "Score.resetScores",
        query = "DELETE FROM Score")
public class Score {

    private String game;
    private String player;
    private int points;
    private Date date;

    @Id
    @GeneratedValue
    private int ident;


    public Score(){

    }

    public Score(String game, String player, int points, Date date) {
        this.game = game;
        this.player = player;
        this.points = points;
        this.date = date;
    }

    public String getGame() {
        return game;
    }


    public String getPlayer() {
        return player;
    }


    public int getPoints() {
        return points;
    }


    public Date getDate() {
        return date;
    }

    public int getIdent() {
        return ident;
    }

    public void setIdent(int ident) {
        this.ident = ident;
    }

    @Override
    public String toString() {
        return "Player "+ player + " score: " + points;
    }
}
