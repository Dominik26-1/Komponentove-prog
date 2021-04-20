package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void addComment(Comment comment) {
        try{
        entityManager.persist(comment);}
        catch (Exception e){
            System.err.println("Problem inserting comment");
            System.err.println("Your comment can not be loaded.");
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<Comment> getComments(String game) {
        try {
            return entityManager.createNamedQuery("Comment.getAllComments").setParameter("game",game).getResultList();
        }catch (Exception e){
            System.err.println("Problem selecting comment");
            System.err.println("Comments can not be loaded.");
            System.err.println(e.getMessage());
            return null;
        }

    }

    @Override
    public void reset() {
        try {
            entityManager.createNamedQuery("Comment.resetComments").executeUpdate();
        } catch (Exception e) {
            System.err.println("Problem resetting comment");
            System.err.println("Comments can not be reset.");
            System.err.println(e.getMessage());
        }
    }
}
