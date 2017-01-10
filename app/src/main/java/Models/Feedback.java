package Models;

/**
 * Created by jennifer on 2017-01-10.
 */

public class Feedback {
    private String usernamne;
    private String feedback;
    private int rating;

    public Feedback(String usernamne, String feedback, int rating) {

        this.usernamne = usernamne;
        this.feedback = feedback;
        this.rating = rating;
    }

    public String getUsernamne() {
        return usernamne;
    }

    public void setUsernamne(String usernamne) {
        this.usernamne = usernamne;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}



