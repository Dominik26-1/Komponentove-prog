package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

public interface RatingService {
    void setRating(Rating rating);
    double getAverageRating(String game);
    int getRating(String game, String player);
    void reset();
}
