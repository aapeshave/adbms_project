package SampleRandomSampling;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ajinkya on 4/24/17.
 */
public class RandomReviewEmitterMapper extends Mapper<LongWritable, Text, NullWritable, Text> {
    private int count = 0;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        super.setup(context);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        count++;
        if (count < 2000) {
            if (count % 2 == 0) {
                context.write(NullWritable.get(), value);
            }
        }
    }
}
