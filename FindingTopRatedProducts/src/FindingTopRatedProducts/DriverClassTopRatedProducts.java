package FindingTopRatedProducts;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * Created by ajinkya on 4/23/17.
 */
public class DriverClassTopRatedProducts {
    public static void main(String args[]) throws InterruptedException, IOException, ClassNotFoundException {
        Configuration configuration = new Configuration();
        Job topRatedProductsJob = Job.getInstance(configuration, "Rating Partitioning Pattern");
        topRatedProductsJob.setJarByClass(DriverClassTopRatedProducts.class);

        topRatedProductsJob.setMapperClass(MapperCategories.class);
        topRatedProductsJob.setMapOutputKeyClass(Text.class);
        topRatedProductsJob.setMapOutputValueClass(Text.class);

        topRatedProductsJob.setReducerClass(ReducerCategories.class);
        topRatedProductsJob.setOutputKeyClass(Text.class);
        topRatedProductsJob.setOutputValueClass(Text.class);

        topRatedProductsJob.setPartitionerClass(PartitionerCategory.class);

        try {
            DistributedCache.addCacheFile(new Path(args[2]).toUri(), topRatedProductsJob.getConfiguration());
            //DistributedCache.addFileToClassPath(new Path(args[2]), configuration);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        topRatedProductsJob.setNumReduceTasks(11);
        try {
            FileInputFormat.addInputPath(topRatedProductsJob, new Path(args[0]));
            FileOutputFormat.setOutputPath(topRatedProductsJob, new Path(args[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.exit(topRatedProductsJob.waitForCompletion(true) ? 0 : 1);
    }
}
