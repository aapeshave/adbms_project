package FindingTopRatedProducts;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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


        Path[] files = DistributedCache.getLocalCacheFiles(getConf());
        //Path[] files = DistributedCache.getFileClassPaths(getConf());
        categoryList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (Path file : files) {
                try {
                    File myFile = new File(file.toUri());
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(myFile.toString()));
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        categoryList.add(line.trim());
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
