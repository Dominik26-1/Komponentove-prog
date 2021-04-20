package sk.tuke.gamestudio.test;


import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class ScoreServiceTest {
    @Test
    public void testAddScore(){
        ScoreService service = new ScoreServiceJDBC();

        service.reset();
        Date actualDate = new Date();
        Score firstScore = new Score("pexeso","prvyHrac",100, actualDate);


        service.addScore(firstScore);
        List<Score> scoreList = service.getTopScores("pexeso");
        assertEquals(1,scoreList.size());
        assertEquals("pexeso",scoreList.get(0).getGame());
        assertEquals("prvyHrac",scoreList.get(0).getPlayer());
        assertEquals(100,scoreList.get(0).getPoints());
        assertEquals(actualDate ,scoreList.get(0).getDate());



    }

    @Test
    public void testGetTopScores(){

        ScoreService service = new ScoreServiceJDBC();
        Date date = new Date();

        Score firstScore = new Score("pexeso","prvyHrac",100, date);
        Score secondScore = new Score("pexeso","druhyHrac",200, date);
        Score thirdScore = new Score("mines","druhyHrac",200, date);

        Score fourtScore = new Score("pexeso","tretiHrac",300, date);
        Score fifthScore = new Score("pexeso","stvrtyHrac",250, date);
        service.reset();
        service.addScore(firstScore);
        service.addScore(secondScore);
        service.addScore(thirdScore);
        service.addScore(fourtScore);
        service.addScore(fifthScore);

        List<Score> scoreList = service.getTopScores("pexeso");

        assertEquals(3,scoreList.size());
        assertEquals("tretiHrac",scoreList.get(0).getPlayer());
        assertEquals("stvrtyHrac",scoreList.get(1).getPlayer());
        assertEquals("druhyHrac",scoreList.get(2).getPlayer());


        List<Score> scoreMines = service.getTopScores("mines");
        assertEquals(1,scoreMines.size());
        assertEquals("druhyHrac",scoreMines.get(0).getPlayer());

    }

    @Test
    public void emptyTestGetScores(){
        ScoreService service = new ScoreServiceJDBC();
        Date date = new Date();

        Score firstScore = new Score("pexeso","prvyHrac",100, date);
        service.reset();
        List<Score> emptyList =  service.getTopScores("pexeso");
        assertEquals(0,emptyList.size());
    }

    @Test
    public void testReset(){

        ScoreService service = new ScoreServiceJDBC();
        Date date = new Date();
        service.reset();

        List<Score> listScore =  service.getTopScores("pexeso");
        assertEquals(0,listScore.size());


        Score firstScore = new Score("pexeso","prvyHrac",100, date);
        service.addScore(firstScore);
        service.reset();
        listScore =  service.getTopScores("pexeso");
        assertEquals(0,listScore.size());
    }
    @Test
    public void testToStringScore(){
        Date actualDate = new Date();
        Score firstScore = new Score("pexeso","prvyHrac",100, actualDate);
        String printScore = firstScore.toString();
        assertEquals("Player prvyHrac score: 100",printScore);
    }
}
