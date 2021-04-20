package sk.tuke.gamestudio.test;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingServiceTest {


    @Test
    public void testReset(){

        RatingService service = new RatingServiceJDBC();
        Date date = new Date();

        Rating firstRating = new Rating("pexeso","prvyHrac",1, date);
        service.setRating(firstRating);
        service.reset();
        int rating =  service.getRating("pexeso","prvyHrac");
        assertEquals(-1,rating);


    }
    @Test
    public void testGetRating(){

        RatingService service = new RatingServiceJDBC();
        Date date = new Date();
        Rating firstRating = new Rating("pexeso","prvyHrac",1, date);
        service.setRating(firstRating);
        int rating =  service.getRating("pexeso","prvyHrac");
        assertEquals(1,rating);



    }


    @Test
    public void testGetAvgRating(){
        RatingService service = new RatingServiceJDBC();
        Date date = new Date();
        service.reset();
        Rating firstRating = new Rating("pexeso","prvyHrac",1, date);
        Rating secondRating = new Rating("pexeso","druhyHrac",1, date);
        Rating thirdRating = new Rating("mines","prvyHrac",1, date);
        Rating fourthRating = new Rating("pexeso","tretiHrac",3, date);

        service.setRating(firstRating);
        service.setRating(secondRating);
        service.setRating(thirdRating);
        service.setRating(fourthRating);

        double avg = service.getAverageRating("pexeso");
        assertEquals(1.67, (avg),0.001);
    }

    @Test
    public void testEmptyAvg(){
        RatingService service = new RatingServiceJDBC();
        Date date = new Date();
        service.reset();
        double avg = service.getAverageRating("pexeso");
        assertEquals(0,avg,0.0001);
    }

    @Test
    public void testToStringRating(){
        Date actualDate = new Date();
        Rating firstRating = new Rating("pexeso","prvyHrac",3, actualDate);
        String printRating = firstRating.toString();
        assertEquals("Player prvyHrac rating: 3", printRating);
    }

    @Test
    public void testSetRating(){
        RatingService service = new RatingServiceJDBC();

        //service.reset();
        Date actualDate = new Date();
        Rating firstRating = new Rating("pexeso", "prvyHrac", 4, actualDate);


        service.setRating(firstRating);

        int ranking = service.getRating("pexeso","prvyHrac");
        assertEquals(4, ranking);

        Rating secondRating = new Rating("pexeso", "prvyHrac", 1, actualDate);
        service.setRating(secondRating);

        ranking = service.getRating("pexeso","prvyHrac");
        assertEquals(1, ranking);
    }

}

