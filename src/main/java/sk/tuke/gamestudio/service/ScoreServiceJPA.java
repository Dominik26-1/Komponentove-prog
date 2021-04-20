package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Score;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class ScoreServiceJPA implements ScoreService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addScore(Score score) {
        try{
            entityManager.persist(score);
        }catch (Exception throwables){
            System.err.println("Problem inserting score");
            System.err.println("Your score can not be loaded.");
            System.err.println(throwables.getMessage());
        }
    }

    @Override
    public List<Score> getTopScores(String game) {
        try {
            return entityManager.createNamedQuery("Score.getTopScores")
                    .setParameter("game", game).setMaxResults(3).getResultList();
        }
        catch (Exception exception){
            System.err.println("Problem selecting score");
            System.err.println("Score can not be loaded.");
            System.err.println(exception.getMessage());
        }
        return null;
    }

    @Override
    public void reset() {
        try{
        entityManager.createNamedQuery("Score.resetScores").executeUpdate();
        // alebo:
        // entityManager.createNativeQuery("delete from score").executeUpdate();
    }catch (Exception exception){
            System.err.println("Problem reseting score");
            System.err.println("Score can not be reset.");
            System.err.println(exception.getMessage());
        }

    }
}

