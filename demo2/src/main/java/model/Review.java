
package model;
public class Review {
    private int reviewId;
    private int dishId;
    private String comment;
    private int rating;

    // Default constructor
    public Review() {
    }

    // Parameterized constructor
    public Review(int dishId, String comment, int rating) {
        this.dishId = dishId;
        this.comment = comment;
        this.rating = rating;
    }

    // Getters and setters
    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating >= 1 && rating <= 5) {
            this.rating = rating;
        } else {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }
    }
}
