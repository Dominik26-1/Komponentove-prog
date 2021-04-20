package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;

import static sk.tuke.gamestudio.service.CommentServiceDBJC.*;


public class RatingServiceJDBC implements RatingService {
    public final String JDBC_SELECT_RATING = "SELECT rating FROM rating WHERE game=? AND player=?";
    public final String JDBC_AVG_SELECT_RATING = "SELECT avg(rating) from rating where game=?";
    public final String JDBC_DELETE_RATING = "DELETE FROM rating";
    public final String JDBC_INSERT_RATING = "INSERT INTO rating(player,game, rating, ratedon) VALUES(?,?,?, ?) ON CONFLICT (player,game) DO UPDATE set rating = ?, ratedOn = ?;";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(JDBC_INSERT_RATING)) {
            statement.setString(1, rating.getPlayer());
            statement.setString(2, rating.getGame());
            statement.setInt(3, rating.getRating());
            statement.setTimestamp(4, new Timestamp(rating.getDate().getTime()));
            statement.setInt(5, rating.getRating());
            statement.setTimestamp(6, new Timestamp(rating.getDate().getTime()));

            statement.executeUpdate();
        } catch (SQLException throwables) {
            System.err.println("Problem setting rating");
            System.err.println("Rating can not be set.");
            System.err.println(throwables.getMessage());
        }
    }

    @Override
    public double getAverageRating(String game) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(JDBC_AVG_SELECT_RATING)) {
            statement.setString(1,game);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {

                    return Math.round(rs.getDouble(1) * 100.0) / 100.0;
                }
            }
        } catch (Exception throwables) {
            System.err.println("Problem selecting rating");
            System.err.println("Rating can not be loaded.");
            System.err.println(throwables.getMessage());
        }
        return -1;
    }


    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(JDBC_SELECT_RATING)) {
            statement.setString(1, game);
            statement.setString(2, player);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    return rs.getInt(1);
                }
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            System.err.println("Problem selecting rating");
            System.err.println("Rating can not be loaded.");
            System.err.println(throwables.getMessage());
        }
        return -1;
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(JDBC_DELETE_RATING);

        } catch (SQLException throwables) {
            System.err.println("Problem reseting rating");
            System.err.println("Rating can not be reset.");
            System.err.println(throwables.getMessage());
        }
    }
}
