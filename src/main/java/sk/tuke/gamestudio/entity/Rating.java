package sk.tuke.gamestudio.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Native;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.getAverageRating",
        query = "SELECT AVG(r.rating) from Rating r where r.game=:game")
@NamedQuery(name = "Rating.resetRating",
        query = "DELETE FROM Rating")
@NamedQuery(name = "Rating.getRating",
        query = "SELECT r.rating FROM Rating r where r.game=:game AND r.player=:player")

public class Rating implements Serializable {



    @Id
    private String game;
    @Id
    private String player;
    private int rating;
    private Date date;



    public Rating(){

    }

    public Rating(String game, String player, int rating, Date date) {
        this.game = game;
        this.player = player;
        this.rating = rating;
        this.date = date;
    }

    public String getGame() {
        return game;
    }

    

    public String getPlayer() {
        return player;
    }


    public int getRating() {
        return rating;
    }


    public Date getDate() {
        return date;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Player " + player + " rating: " + rating;
    }
}
