package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceDBJC implements CommentService {
    public static final String JDBC_URL = "jdbc:postgresql://localhost/pexeso";
    public static final String JDBC_USER = "postgres";
    public static final String JDBC_PASSWORD = "postgres";
    public final String JDBC_SELECT_COMMENT = "SELECT game,player,comment, commentedOn FROM comment WHERE game=? ORDER BY player";
    public final String JDBC_DELETE_COMMENT = "DELETE FROM comment";
    public final String JDBC_INSERT_COMMENT = "INSERT INTO comment(game,player,comment,commentedOn) VALUES (?,?,?,?)";

    @Override
    public void addComment(Comment comment) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(JDBC_INSERT_COMMENT)) {
            if(comment.getComment()!=null && comment.getComment().isBlank()){
                return;
            }
            statement.setString(1, comment.getGame());
            statement.setString(2, comment.getPlayer());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, new Timestamp(comment.getDate().getTime()));
                statement.executeUpdate();

        } catch (Exception throwables) {
            System.err.println("Problem inserting comment");
            System.err.println("Your comment can not be loaded.");
            System.err.println(throwables.getMessage());
        }
    }

    @Override
    public List<Comment> getComments(String game){
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(JDBC_SELECT_COMMENT)) {
            statement.setString(1, game);

            try (ResultSet rs = statement.executeQuery()) {
                List<Comment> listComments = new ArrayList<>();
                while (rs.next()){
                    listComments.add(new Comment(rs.getString(1), rs.getString(2), rs.getString(3),rs.getTimestamp(4)));
                }
                return listComments;
            }
        } catch (SQLException throwables) {
            System.err.println("Problem selecting comment");
            System.err.println("Comments can not be loaded.");
            System.err.println(throwables.getMessage());
        }
        return null;
    }

    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                              Statement statement = connection.createStatement()) {
        statement.executeUpdate(JDBC_DELETE_COMMENT);

    } catch (SQLException throwables) {
            System.err.println("Problem reseting comment");
            System.err.println("Comments can not be reset.");
            System.err.println(throwables.getMessage());
    }
    }
}
