package ReviewSummarization;

import ReviewSummarization.pojo.ReviewEntity;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


/**
 * Created by ajinkya on 4/22/17.
 */
public class ReducerRatings extends Reducer<Text, ReviewEntity, Text, NullWritable> {
    private ReviewEntity result = new ReviewEntity();

    @Override
    protected void reduce(Text key, Iterable<ReviewEntity> values, Context context) throws IOException, InterruptedException {
        float sum = 0;
        float count = 0;
        float minimum = 5;
        float maximum = 0;
        for (ReviewEntity value : values) {
            if (minimum >= value.getMinimumRating()) {
                minimum = value.getMinimumRating();
            }
            if (maximum <= value.getMaximumRating()) {
                maximum = value.getMaximumRating();
            }
            sum += value.getReviewCount() * value.getAverageRating();
            count += value.getReviewCount();
        }

        extractResult(sum, count, minimum, maximum);

        StringBuilder builder = new StringBuilder();
        builder.append(key).append(",");
        builder.append(Float.toString(sum / count)).append(",");
        builder.append(Float.toString(maximum)).append(",");
        builder.append(Float.toString(minimum));
        context.write(new Text(builder.toString()), NullWritable.get());
    }

    private void extractResult(float sum, float count, float minimum, float maximum) {
        result.setAverageRating(sum / count);
        result.setMaximumRating(maximum);
        result.setMinimumRating(minimum);
        result.setReviewCount(((int) count));
    }
}
