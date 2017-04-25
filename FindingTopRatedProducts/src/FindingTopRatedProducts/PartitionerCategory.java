package FindingTopRatedProducts;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajinkya on 4/23/17.
 */
public class PartitionerCategory extends Partitioner<Text, Text> implements Configurable {
    private Configuration configuration;
    private List<String> categoryList;


    @Override
    public Configuration getConf() {
        return configuration;
    }

    @Override
    public void setConf(Configuration configuration) {
        this.configuration = configuration;
        try {
            getLocalCacheFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getPartition(Text key, Text value, int i) {
        int partition = 10;
        if (categoryList.contains(key.toString())) {
            partition = categoryList.indexOf(key.toString());
        }
        return partition;
    }

    private void getLocalCacheFiles() throws IOException {
        categoryList = new ArrayList<>();
        categoryList.add("Wallet Cases");
        categoryList.add("Batteries");
        categoryList.add("Headsets");
        categoryList.add("Screen Protectors");
        categoryList.add("Chargers");
        categoryList.add("Accessory Kits");
        categoryList.add("Accessories");
        categoryList.add("Basic Cases");
        categoryList.add("Cases");
        categoryList.add("Cell Phones & Accessories");
    }
}
