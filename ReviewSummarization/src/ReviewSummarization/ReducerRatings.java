package ReviewSummarization;

import ReviewSummarization.pojo.ReviewEntity;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * Created by ajinkya on 4/22/17.
 */
public class ReducerRatings extends Reducer<Text, ReviewEntity, Text, ReviewEntity> {
    ReviewEntity result = new ReviewEntity();
    @Override
    protected void reduce(Text key, Iterable<ReviewEntity> values, Context context) throws IOException, InterruptedException {
        float sum=0;
        float count=0;
        float minimum=5;
        float maximum=0;
        for (ReviewEntity value : values) {
            if (minimum >= value.getMinimumRating()) {
                minimum = value.getMinimumRating();
            }
            if (maximum <= value.getMaximumRating()) {
                maximum = value.getMaximumRating();
            }
            sum += value.getReviewCount() * value.getAverageRating();
            count+= value.getReviewCount();
        }

        result.setAverageRating(sum/count);
        result.setMaximumRating(maximum);
        result.setMinimumRating(minimum);
        result.setReviewCount(((int) count));
        context.write(key, result);
    }
}
