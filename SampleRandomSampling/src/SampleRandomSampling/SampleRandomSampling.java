package SampleRandomSampling;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ajinkya on 4/24/17.
 */
public class SampleRandomSampling {
    public static void main(String args[]) {
        Configuration configuration = new Configuration();
        try {
            Job job = Job.getInstance(configuration, "Sample random sampling");
            job.setJarByClass(SampleRandomSampling.class);

            job.setMapperClass(RandomReviewEmitterMapper.class);

            job.setNumReduceTasks(0);

            job.setOutputKeyClass(NullWritable.class);
            job.setOutputValueClass(Text.class);

            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SampleRandomSampling.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
