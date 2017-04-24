package ReviewSummarization;


import ReviewSummarization.pojo.Review;
import ReviewSummarization.pojo.ReviewEntity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ajinkya on 4/22/17.
 */
public class MapperReviewRatings extends Mapper<Object, Text, Text, ReviewEntity> {
    private Gson gson;

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        gson = new Gson();

        try {
            Review fromRow = gson.fromJson(value.toString(), Review.class);
            Text asin = new Text(fromRow.getAsin());
            Float rating = Float.parseFloat(fromRow.getOverall());
            ReviewEntity valueToEmit = new ReviewEntity(1, rating, rating, rating);
            context.write(asin, valueToEmit);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
}
