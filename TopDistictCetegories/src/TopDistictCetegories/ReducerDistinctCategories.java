package TopDistictCetegories;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * Created by ajinkya on 4/24/17.
 */

/**
 * Count the number of distinct values for category
 */
public class ReducerDistinctCategories extends Reducer<Text, LongWritable, Text, LongWritable> {

    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long count = 0;
        for (LongWritable value : values) {
            count = count + value.get();
        }
        context.write(key, new LongWritable(count));
    }
}
