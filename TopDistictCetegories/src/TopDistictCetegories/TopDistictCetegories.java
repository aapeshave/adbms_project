package TopDistictCetegories;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by ajinkya on 4/23/17.
 */
public class TopDistictCetegories {
    public static void main(String args[]) {

        try {
            Configuration configuration = new Configuration();
            Job distinctJob = Job.getInstance(configuration, "Distinct");
            distinctJob.setJarByClass(TopDistictCetegories.class);

            distinctJob.setMapperClass(MapperDistinctCategory.class);
            distinctJob.setMapOutputKeyClass(Text.class);
            distinctJob.setMapOutputValueClass(LongWritable.class);

            distinctJob.setReducerClass(ReducerDistinctCategories.class);
            distinctJob.setOutputKeyClass(Text.class);
            distinctJob.setOutputValueClass(LongWritable.class);

            FileInputFormat.addInputPath(distinctJob, new Path(args[0]));
            FileOutputFormat.setOutputPath(distinctJob, new Path(args[1]));
            boolean complete = distinctJob.waitForCompletion(true);

            Configuration configuration1 = new Configuration();
            if (complete) {
                Job topTenJob = Job.getInstance(configuration1,"top ten");
                topTenJob.setJarByClass(TopDistictCetegories.class);

                topTenJob.setNumReduceTasks(0);

                topTenJob.setMapperClass(TopTenMapper.class);
                topTenJob.setMapOutputKeyClass(NullWritable.class);
                topTenJob.setMapOutputValueClass(Text.class);

                FileInputFormat.addInputPath(topTenJob,new Path(args[1]));
                FileOutputFormat.setOutputPath(topTenJob, new Path(args[2]));
                System.exit(topTenJob.waitForCompletion(true)? 0 :1);
            }

        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
