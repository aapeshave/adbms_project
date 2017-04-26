import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ajinkya on 4/26/17.
 */
public class MahoutEngine {
    private static final String REVIEWER_ID = "A292527VPX98P8";

    private static File sentiments_out = new File("/Users/ajinkya/Documents/adbms_project/RecommnedationModel/output/recos.txt");

    public static void main(String args[]) {
        RecommendationEngine engine = new RecommendationEngine();
        engine.startRecommendationModel();
        try {
            String[] recommendedThings = engine.recommendThings(REVIEWER_ID);
            for (String thing : recommendedThings) {
                System.out.println(thing);
            }


            FileWriter fw;
            try {
                if (!sentiments_out.exists()) {
                    sentiments_out.createNewFile();
                }
                fw = new FileWriter(sentiments_out.getAbsoluteFile(), true);
                for (String item : recommendedThings) {
                    fw.append(item);
                    fw.append("\n");
                }
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
}
