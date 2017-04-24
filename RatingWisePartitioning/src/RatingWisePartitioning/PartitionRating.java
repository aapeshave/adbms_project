package RatingWisePartitioning;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * Created by ajinkya on 4/24/17.
 */
public class PartitionRating extends Partitioner<DoubleWritable, Text> implements Configurable {
    private Configuration configuration;

    @Override
    public Configuration getConf() {
        return configuration;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public int getPartition(DoubleWritable doubleWritable, Text text, int i) {
        return (int) doubleWritable.get() - 1;
    }
}
