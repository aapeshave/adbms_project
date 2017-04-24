package FindingTopRatedProducts;

import FindingTopRatedProducts.pojo.Metadata;
import com.google.gson.Gson;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.List;


/**
 * Created by ajinkya on 4/23/17.
 */


public class MapperCategories extends Mapper<Object, Text, Text, Text> {
    private Gson gson;

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        gson = new Gson();
        Metadata metadata = gson.fromJson(value.toString(), Metadata.class);
        List<List<String>> categories = metadata.getCategories();
        for (List<String> individualCategoryList : categories) {
            for (String category : individualCategoryList) {
                context.write(new Text(category.trim()), new Text(metadata.getAsin()));
            }
        }
    }
}
