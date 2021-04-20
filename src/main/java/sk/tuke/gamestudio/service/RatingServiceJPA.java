package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class RatingServiceJPA implements RatingService{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void setRating(Rating rating) {
        try{
        entityManager.createNativeQuery("INSERT INTO rating(player,game, date,rating) VALUES(?1,?2,?3, ?4) " +
                "ON CONFLICT (player,game) DO UPDATE set rating = ?4, date = ?3").setParameter(1,rating.getPlayer())
        .setParameter(2,rating.getGame()).setParameter(3,rating.getDate())
        .setParameter(4,rating.getRating()).executeUpdate();}
        catch (Exception c){

            System.err.println("Problem setting rating");
            System.err.println("Rating can not be set.");
            System.err.println(c.getMessage());
        }
    }

    @Override
    public double getAverageRating(String game) {
        try{
        return (Double) entityManager.createNamedQuery("Rating.getAverageRating")
                .setParameter("game",game).getSingleResult();}
        catch (Exception e){
            System.err.println("Problem selecting rating");
            System.err.println("Rating can not be loaded.");
            System.err.println(e.getMessage());
        }
        return -1;

    }

    @Override
    public int getRating(String game, String player) {
        try {
            return (Integer) entityManager.createNamedQuery("Rating.getRating")
                    .setParameter("game", game)
                    .setParameter("player", player)
                    .getSingleResult();
        }
        catch (Exception e){
            System.err.println("Problem selecting rating");
            System.err.println("Rating can not be loaded.");
            System.err.println(e.getMessage());
        }
        return -1;
    }

    @Override
    public void reset() {
        try{
        entityManager.createNamedQuery("Rating.resetRating").executeUpdate();}
        catch (Exception e){
            System.err.println("Problem resetting rating");
            System.err.println("Rating can not be reset.");
            System.err.println(e.getMessage());
        }
    }
}
