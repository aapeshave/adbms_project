package SummarizationReviews;

import SummarizationReviews.pojo.ReviewEntity;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ajinkya on 4/22/17.
 */
public class SummarizationReviews {
    public static void main(String args[]) {
        Configuration configuration = new Configuration();
        try {
            Job job = Job.getInstance(configuration, "Min Max Average Job");
            job.setJarByClass(SummarizationReviews.class);

            job.setMapperClass(MapperReviewRatings.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(ReviewEntity.class);

            job.setCombinerClass(ReducerRatings.class);

            job.setReducerClass(ReducerRatings.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(ReviewEntity.class);

            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SummarizationReviews.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
