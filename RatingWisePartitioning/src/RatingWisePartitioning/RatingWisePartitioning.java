package RatingWisePartitioning;

import RatingWisePartitioning.pojo.Review;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by ajinkya on 4/24/17.
 */
public class RatingWisePartitioning {
    public static void main(String args[]) {
        Configuration configuration = new Configuration();

        try {
            Job ratingPartitioningJob = Job.getInstance(configuration, "Partitioning by Rating");
            ratingPartitioningJob.setJarByClass(RatingWisePartitioning.class);

            ratingPartitioningJob.setMapperClass(MapperByRating.class);
            ratingPartitioningJob.setMapOutputKeyClass(DoubleWritable.class);
            ratingPartitioningJob.setMapOutputValueClass(Text.class);

            ratingPartitioningJob.setReducerClass(ReducerByRating.class);
            ratingPartitioningJob.setOutputKeyClass(DoubleWritable.class);
            ratingPartitioningJob.setOutputValueClass(Text.class);
            ratingPartitioningJob.setNumReduceTasks(5);

            ratingPartitioningJob.setPartitionerClass(PartitionRating.class);
            FileInputFormat.addInputPath(ratingPartitioningJob, new Path(args[0]));
            FileOutputFormat.setOutputPath(ratingPartitioningJob, new Path(args[1]));
            System.exit(ratingPartitioningJob.waitForCompletion(true) ? 0 : 1);

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
