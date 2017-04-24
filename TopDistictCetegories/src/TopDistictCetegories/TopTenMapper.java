package TopDistictCetegories;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.TreeMap;

/**
 * Created by ajinkya on 4/24/17.
 */
public class TopTenMapper extends Mapper<Object, Text, NullWritable, Text> {
    private TreeMap<Integer, Text> categories = new TreeMap<>();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        try {
            String[] valueArray = value.toString().split("\t");
            String category = valueArray[0];
            String count = valueArray[1];
            categories.put(Integer.parseInt(count), new Text(category));
            if (categories.size() > 10) {
                categories.remove(categories.firstKey());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        for (Text text : categories.values()) {
            context.write(NullWritable.get(), text);
        }
    }
}
