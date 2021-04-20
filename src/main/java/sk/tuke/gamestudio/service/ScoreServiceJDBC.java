package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Score;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static sk.tuke.gamestudio.service.CommentServiceDBJC.*;

public class ScoreServiceJDBC implements ScoreService {
    public final String JDBC_SELECT_SCORE = "SELECT game,player,points, playedOn FROM score WHERE game=? ORDER BY points DESC LIMIT 3";
    public final String JDBC_DELETE_SCORE = "DELETE FROM score";
    public final String JDBC_INSERT_SCORE = "INSERT INTO score(game,player,points,playedOn) VALUES (?,?,?,?)";


    @Override
    public void addScore(Score score){
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(JDBC_INSERT_SCORE)) {
            statement.setString(1, score.getGame());
            statement.setString(2, score.getPlayer());
            statement.setInt(3, score.getPoints());
            statement.setTimestamp(4, new Timestamp(score.getDate().getTime()));
            statement.executeUpdate();
        } catch (Exception throwables) {
            System.err.println("Problem inserting score");
            System.err.println("Your score can not be loaded.");
            System.err.println(throwables.getMessage());
        }
    }

    @Override
    public List<Score> getTopScores(String game){
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(JDBC_SELECT_SCORE)) {
            statement.setString(1, game);

            try (ResultSet rs = statement.executeQuery()) {
                List<Score> topScore = new ArrayList<>();
                while (rs.next()){
                    topScore.add(new Score(rs.getString(1),rs.getString(2),rs.getInt(3),rs.getTimestamp(4)));
                }
                return topScore;
            }
        } catch (Exception throwables) {
            System.err.println("Problem selecting score");
            System.err.println("Score can not be loaded.");
            System.err.println(throwables.getMessage());
        }
        return null;

    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            Statement statement = connection.createStatement()) {
            statement.executeUpdate(JDBC_DELETE_SCORE);

        } catch (Exception throwables) {
            System.err.println("Problem reseting score");
            System.err.println("Score can not be reset.");
            System.err.println(throwables.getMessage());
        }
    }
}
