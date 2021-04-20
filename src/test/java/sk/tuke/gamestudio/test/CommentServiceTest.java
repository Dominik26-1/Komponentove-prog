package sk.tuke.gamestudio.test;


import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.CommentServiceDBJC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentServiceTest {
    @org.junit.jupiter.api.Test
    public void testAddComment(){
        CommentService service = new CommentServiceDBJC();

        service.reset();
        Date actualDate = new Date();
        Comment firstComment = new Comment("pexeso", "prvyHrac", "bolo to ok", actualDate);


        service.addComment(firstComment);
        List<Comment> commentList = service.getComments("pexeso");
        assertEquals(1, commentList.size());
        assertEquals("pexeso", commentList.get(0).getGame());
        assertEquals("prvyHrac", commentList.get(0).getPlayer());
        assertEquals("bolo to ok", commentList.get(0).getComment());
        assertEquals(actualDate , commentList.get(0).getDate());

        service.reset();
        Comment emptyComment = new Comment("pexeso", "prvyHrac", "", actualDate);
        service.addComment(emptyComment);
        commentList = service.getComments("pexeso");
        assertEquals(0, commentList.size());

        service.reset();
        Comment nullComment = new Comment("pexeso", "prvyHrac", null, actualDate);


        service.addComment(nullComment);
        commentList = service.getComments("pexeso");
        assertEquals(0, commentList.size());


    }

    @Test
    public void testGetComments(){
    CommentService service = new CommentServiceDBJC();
    Date date = new Date();

    Comment firstComment = new Comment("pexeso", "prvyHrac", "vynikajuca hra", date);
    Comment secondComment = new Comment("pexeso", "druhyHrac", "dobra hra", date);
    Comment thirdComment = new Comment("mines", "druhyHrac", "dalo sa", date);

    Comment fourtScore = new Comment("pexeso", "tretiHrac", "najlepsia hra na svete", date);
    Comment fifthScore = new Comment("pexeso", "stvrtyHrac", "nie velmi dobra hra", date);
        service.reset();
        service.addComment(firstComment);
        service.addComment(secondComment);
        service.addComment(thirdComment);
        service.addComment(fourtScore);
        service.addComment(fifthScore);

    List<Comment> commentList = service.getComments("pexeso");

    assertEquals(4,commentList.size());
    assertEquals("druhyHrac",commentList.get(0).getPlayer());
    assertEquals("prvyHrac",commentList.get(1).getPlayer());
    assertEquals("stvrtyHrac",commentList.get(2).getPlayer());
    assertEquals("tretiHrac",commentList.get(3).getPlayer());


    List<Comment> commentsMines = service.getComments("mines");
    assertEquals(1, commentsMines.size());
    assertEquals("druhyHrac", commentsMines.get(0).getPlayer());

}

    @Test
    public void emptyTestGetScores(){
        CommentService service = new CommentServiceDBJC();
        Date date = new Date();

        Comment firstComment = new Comment("pexeso","prvyHrac","bolo to ok", date);
        service.reset();
        List<Comment> emptyList =  service.getComments("pexeso");
        assertEquals(0,emptyList.size());
    }

    @Test
    public void testReset(){

        CommentService service = new CommentServiceDBJC();
        Date date = new Date();
        service.reset();

        List<Comment> listComments =  service.getComments("pexeso");
        assertEquals(0,listComments.size());


        Comment firstComment = new Comment("pexeso","prvyHrac","ma to este rezervy", date);
        service.addComment(firstComment);
        service.reset();
        listComments =  service.getComments("pexeso");
        assertEquals(0,listComments.size());
    }
    @Test
    public void testToStringComment(){
        Date actualDate = new Date();
        Comment firstComment = new Comment("pexeso","prvyHrac","bolo to ok", actualDate);
        String printComment = firstComment.toString();
        assertEquals("Player prvyHrac : \"bolo to ok\" at "+ actualDate,printComment);
    }
}
