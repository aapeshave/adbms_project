package BinningProject;

import BinningProject.pojo.Metadata;
import com.google.gson.Gson;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * Created by ajinkya on 4/17/17.
 */
public class TestMapper extends Mapper<Object, Text, Text, NullWritable> {
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        Gson gson = new Gson();
        Metadata metadata = gson.fromJson(value.toString(), Metadata.class);

        context.write(new Text(metadata.getAsin()), NullWritable.get());
    }
}
