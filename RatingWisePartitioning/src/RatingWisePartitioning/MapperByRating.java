package RatingWisePartitioning;

import RatingWisePartitioning.pojo.Review;
import com.google.gson.Gson;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ajinkya on 4/24/17.
 */
public class MapperByRating extends Mapper<Object,Text,DoubleWritable,Text> {
    private Gson gson;
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        gson = new Gson();
        Review emitReview = gson.fromJson(value.toString(), Review.class);
        Double rating = Double.parseDouble(emitReview.getOverall());
        context.write(new DoubleWritable(rating), value);
    }
}
