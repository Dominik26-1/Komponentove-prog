package sk.tuke.gamestudio.consoleUI;


import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.core.*;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConsoleUI {
    private Field field;
    private final Pattern inputPatter = Pattern.compile("([A-G])([1-9][0-9]*)");
    private final Scanner scanner = new Scanner(System.in);
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    public static final String GREEN_BACKGROUND = "\033[42m";
    public static final String TRANSPARENT_BACKGROUND = "\033[0m";

    public ConsoleUI(Field field) {
        this.field = field;

    }

    public ConsoleUI() {
        fieldInput();
    }

    public void play() {
        System.out.println();
        System.out.println("Welcome in my game Pexeso.");
        System.out.println();
        playerInput();
        show();
        //program cycle
        while (field.getGameState() == GameState.PLAYING) {
            processInput();
            runningPlayerInfo();
            show();
        }
        scorePrint();
        playerFeedback();
        FeedbackPrint();

    }
    private void fieldInput() {
        System.out.println();
        System.out.println("Enter the difficulty for the game (1 - Easy, 2 - Medium, 3 - Difficult) : ");
        String count = scanner.nextLine();

        switch (count) {
            case "1":
                this.field = new Field(GameDifficulty.EASY);
                break;
            case "2":
                this.field = new Field(GameDifficulty.MEDIUM);
                break;
            case "3":
                this.field = new Field(GameDifficulty.DIFFICULT);
                break;
            default:
                System.out.println("You entered invalid option. Choose one from 1,2 or 3.");
                fieldInput();
                break;
        }


    }

    private void playerInput() {
        System.out.println("Enter number of player: ");
        String count = scanner.nextLine().toUpperCase();
        try{
            Integer.parseInt(count);
        }catch (Exception e){
            System.out.println("Wrong format of number of player. It has to be the positive number.");
            playerInput();
            return;
        }
        int nb = Integer.parseInt(count);
        if (nb < 2 ) {
            System.out.println("This game have to be played by more than 1 player");
            playerInput();
            return;
        } else {
            for (int i = 0; i < nb; i++) {
                System.out.println("Enter name of player nb " + (i + 1) + " : ");
                String name = scanner.nextLine();
                Player player = new Player(name);
                field.addPlayer(player);

            }
        }
    }

    private void processInput() {
        System.out.println("Enter command (x - Exit game, B2 - open Tile at B2) : ");
        String line = scanner.nextLine().toUpperCase();
        if ("X".equals(line)) {
            scorePrint();
            playerFeedback();
            FeedbackPrint();
            System.exit(0);
        }
        Matcher matcher = inputPatter.matcher(line);
        if (matcher.matches()) {
            int row = line.charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(2)) - 1;
            field.openTile(row, column);
        } else {
            System.err.println("Wrong input! Correct input in format 'B2'");
            processInput();
        }

    }

    public void runningPlayerInfo() {
        System.out.println();
        for (Player pl : field.getPartipiants()) {
            System.out.println(pl);
        }
        System.out.println();
    }

    public void show() {
        System.out.print("  ");
        for (int i = 0; i < field.getColumnCount(); i++) {
            System.out.print(i + 1 + "  ");
        }
        System.out.println();
        for (int i = 0; i < field.getRowCount(); i++) {
            System.out.print((char) (i + 'A') + " ");
            for (int j = 0; j < field.getColumnCount(); j++) {
                if (field.getTile(i, j).getState() == TileState.OPEN) {
                    System.out.print(TRANSPARENT_BACKGROUND);
                    System.out.printf("%02d ", field.getTile(i, j).getValue());
                } else if (field.getTile(i, j).getState() == TileState.PAIRED) {
                    System.out.print(GREEN_BACKGROUND);
                    System.out.printf("%02d", field.getTile(i, j).getValue());
                    System.out.print(TRANSPARENT_BACKGROUND + " ");

                } else {
                    System.out.print(TRANSPARENT_BACKGROUND);
                    //System.out.printf("%02d ", field.getTile(i, j).getValue());
                    System.out.printf("-- ");
                }
            }
            System.out.println(TRANSPARENT_BACKGROUND);
        }
        System.out.println();
        switch (field.getSituation()) {
            case PAIRED:
                System.out.println("You successfully find the pair.");
                System.out.println("You have " + field.getActivePlayer().getScore() + " points.");
                System.out.println("Continue in your turn.");
                System.out.println();
                break;
            case UNPAIRED:
                System.out.println("You are not able to find the pair.");
                System.out.println();
                break;
            case INVALID:
                System.out.println("You are out of the field. Try to enter input again!");
                System.out.println();
            default:
                System.out.println();
                break;
        }
        System.out.println("-------------------------------------------------------------");
        System.out.println("Player " + field.getActivePlayer().getName() + " has turn.");
    }

    private void scorePrint() {
        System.out.println("Game is finished.");
        if(field.getWinner()!=null) {
            System.out.println("Game winner is " + field.getWinner().getName() + " with " + field.getWinner().getScore());
        }
        for (Player pl : field.getPartipiants()) {
            System.out.println(pl);
            scoreService.addScore(new Score("pexeso", pl.getName(), pl.getScore(), new Date()));
        }

        List<Score> hallOfFame = scoreService.getTopScores("pexeso");
        System.out.println("************************************************************");
        System.out.println("RECORDS - HALL of FAME");
        for (Score sc : hallOfFame) {
            System.out.println(sc);
        }
        System.out.println("************************************************************");

    }

    private void playerFeedback() {
        for (Player pl: field.getPartipiants()){
            commentInput(pl);
            ratingInput(pl);
        }
    }

    private void ratingInput(Player player) {
        System.out.println(player.getName());
        System.out.println("Do you want rate this game (1-5, 1 - terrible, 5 - super, N - No): ");
        String questionRating = scanner.nextLine().toUpperCase();
        try{
            Integer.parseInt(questionRating);

        }catch(Exception e){
            if(!questionRating.equals("N")) {
                System.out.println("Your rating is in invalid format.");
            }
            System.out.println();
            return;
        }
        Integer ratingValue = Integer.parseInt(questionRating);
        if(ratingValue>0 && ratingValue<6){
            Rating rating = new Rating("pexeso",player.getName(),ratingValue,new Date());
            System.out.println(rating);
            ratingService.setRating(rating);
            System.out.println();
            System.out.println("Thank you for your rating, " + player.getName());
        }
        else{
            System.out.println("Your ranting is out of range of rating options");
            System.out.println();
            return;
        }


    }

    private void commentInput(Player player) {
        System.out.println(player.getName());
        System.out.println("Do you want comment this game (Y - Yes, N - No): ");
        String questionComment = scanner.nextLine().toUpperCase();

        switch (questionComment) {
            case "Y":
                System.out.println("Your comment: ");
                String commentString = scanner.nextLine();
                Comment comment = new Comment("pexeso",player.getName(),commentString,new Date());
                commentService.addComment(comment);
                System.out.println();
                System.out.println("Thank you for your comment, " + player.getName() + ".");
                break;
            default:
                System.out.println();
                break;
        }
    }

    private void FeedbackPrint() {
        List<Comment> commentList =  commentService.getComments("pexeso");
        for(Comment c : commentList){
            System.out.println(c);
        }
        System.out.println();
        System.out.println("Average rating for this game is " + ratingService.getAverageRating("pexeso"));
    }

}

