package BinningProject;

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

public class BinningProject {

    public static void main(String args[]) {
        Configuration configuration = new Configuration();
        try {
            Job job = Job.getInstance(configuration, "Project Test Data");
            job.setJarByClass(BinningProject.class);

            job.setMapperClass(TestMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(NullWritable.class);

            job.setReducerClass(TestReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(NullWritable.class);

            FileInputFormat.addInputPath(job, new Path(args[0]));
            FileOutputFormat.setOutputPath(job, new Path(args[1]));
            System.exit(job.waitForCompletion(true) ? 0 : 1);

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(BinningProject.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
