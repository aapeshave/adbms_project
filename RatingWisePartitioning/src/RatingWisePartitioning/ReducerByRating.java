package RatingWisePartitioning;

import RatingWisePartitioning.pojo.Review;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ajinkya on 4/24/17.
 */
public class ReducerByRating extends Reducer<DoubleWritable, Text, Text, NullWritable> {
    @Override
    protected void reduce(DoubleWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value, NullWritable.get());
        }
    }
}
