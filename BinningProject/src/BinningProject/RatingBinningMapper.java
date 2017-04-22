package BinningProject;

import BinningProject.pojo.Metadata;
import com.google.gson.Gson;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;

/**
 * Created by ajinkya on 4/21/17.
 */
public class RatingBinningMapper extends Mapper<Object, Text, Text, NullWritable> {
    private Gson gson;
    private MultipleOutputs<Text, NullWritable> multipleOutputs;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        multipleOutputs = new MultipleOutputs<>(context);
    }

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        gson = new Gson();
        Metadata metadata = gson.fromJson(value.toString(), Metadata.class);
        Double price = 0.00;
        if (metadata.getPrice() != null) {
            price = Double.parseDouble(metadata.getPrice());
        }
        if (price > Double.parseDouble("0")) {
            if (price <= Double.parseDouble("2.00")) {
                multipleOutputs.write("bins", value, NullWritable.get(), "less than $2");
            } else if (price <= Double.parseDouble("5.00")) {
                multipleOutputs.write("bins", value, NullWritable.get(), "less than $5");
            } else if (price <= Double.parseDouble("10.00")) {
                multipleOutputs.write("bins", value, NullWritable.get(), "less than $10");
            } else if (price <= Double.parseDouble("15.00")) {
                multipleOutputs.write("bins", value, NullWritable.get(), "less than $15");
            } else {
                multipleOutputs.write("bins", value, NullWritable.get(), "greater than $15");
            }
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        multipleOutputs.close();
    }
}
