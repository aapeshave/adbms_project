package pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajinkya on 4/22/17.
 */
public class Review {
    private String reviewerId;
    private String asin;
    private String reviewerName;
    private String reviewText;
    private String overall;                         //This is the rating stars given by user.
    private String summary;
    private String unixReviewTime;
    private String reviewTime;
    private List<String> helpful;

    public Review() {
        helpful = new ArrayList<String>();
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getOverall() {
        return overall;
    }

    public void setOverall(String overall) {
        this.overall = overall;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getUnixReviewTime() {
        return unixReviewTime;
    }

    public void setUnixReviewTime(String unixReviewTime) {
        this.unixReviewTime = unixReviewTime;
    }

    public String getReviewTime() {
        return reviewTime;
    }

    public void setReviewTime(String reviewTime) {
        this.reviewTime = reviewTime;
    }

    public List<String> getHelpful() {
        return helpful;
    }

    public void setHelpful(List<String> helpful) {
        this.helpful = helpful;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;

        Review review = (Review) o;

        if (!reviewerId.equals(review.reviewerId)) return false;
        if (!asin.equals(review.asin)) return false;
        if (reviewerName != null ? !reviewerName.equals(review.reviewerName) : review.reviewerName != null)
            return false;
        if (reviewText != null ? !reviewText.equals(review.reviewText) : review.reviewText != null) return false;
        if (overall != null ? !overall.equals(review.overall) : review.overall != null) return false;
        if (summary != null ? !summary.equals(review.summary) : review.summary != null) return false;
        if (unixReviewTime != null ? !unixReviewTime.equals(review.unixReviewTime) : review.unixReviewTime != null)
            return false;
        if (reviewTime != null ? !reviewTime.equals(review.reviewTime) : review.reviewTime != null) return false;
        return helpful != null ? helpful.equals(review.helpful) : review.helpful == null;
    }

    @Override
    public int hashCode() {
        int result = reviewerId.hashCode();
        result = 31 * result + asin.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewerId='" + reviewerId + '\'' +
                ", asin='" + asin + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", reviewText='" + reviewText + '\'' +
                ", overall='" + overall + '\'' +
                ", summary='" + summary + '\'' +
                ", unixReviewTime='" + unixReviewTime + '\'' +
                ", reviewTime='" + reviewTime + '\'' +
                '}';
    }
}
