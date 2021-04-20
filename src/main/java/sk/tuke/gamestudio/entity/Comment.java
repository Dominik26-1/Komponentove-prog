package sk.tuke.gamestudio.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.util.Date;


@Entity
@NamedQuery(name = "Comment.getAllComments",
        query = "SELECT c FROM Comment c WHERE c.game=:game ORDER BY c.date DESC")
@NamedQuery(name = "Comment.resetComments",
        query = "DELETE FROM Comment")
public class Comment {



    @Id
    @GeneratedValue
    private int ident;

    private  String game;
    private  String player;
    private  String comment;
    private  Date date;

    public Comment(){

    }

    public Comment(String game, String player, String comment, Date date) {
        this.game = game;
        this.player = player;
        this.comment = comment;
        this.date = date;
    }

    public String getGame() {
        return game;
    }


    public String getPlayer() {
        return player;
    }


    public String getComment() {
        return comment;
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
        return "Player "+ player + " : \"" + comment + "\" at " + date;
    }
}
