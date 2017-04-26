import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.MemoryIDMigrator;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ajinkya on 4/26/17.
 */
public class RecommendationEngine {
    private static UserBasedRecommender recommender = null;
    private static String DATA_FILE_NAME = "/Users/ajinkya/Desktop/mahout_reviews_aj.json";
    MemoryIDMigrator thing2long = new MemoryIDMigrator();
    private DataModel dataModel;

    void startRecommendationModel() {
        try {

            Map<Long, List<Preference>> preferencesOfUsers = new HashMap<Long, List<Preference>>();
            BufferedReader dataFileReader = new BufferedReader(new FileReader(DATA_FILE_NAME));
            String currentLine;
            Gson gson = new Gson();

            while ((currentLine = dataFileReader.readLine()) != null) {
                Review jsonReview = gson.fromJson(currentLine, Review.class);
                String person = jsonReview.getReviewerID();
                String likeName = jsonReview.getAsin();

                long userLong = thing2long.toLongID(person);
                thing2long.storeMapping(userLong, person);

                long itemLong = thing2long.toLongID(likeName);
                thing2long.storeMapping(itemLong, likeName);

                List<Preference> reviewPreferencesList;
                if ((reviewPreferencesList = preferencesOfUsers.get(userLong)) == null) {
                    reviewPreferencesList = new ArrayList<Preference>();
                    preferencesOfUsers.put(userLong, reviewPreferencesList);
                }

                float prefValue = Float.parseFloat(jsonReview.getOverall());
                reviewPreferencesList.add(new GenericPreference(userLong, itemLong, prefValue));
            }


            FastByIDMap<PreferenceArray> userPereferencesMap = new FastByIDMap<PreferenceArray>();

            for (Map.Entry<Long, List<Preference>> entry : preferencesOfUsers.entrySet()) {
                userPereferencesMap.put(entry.getKey(), new GenericUserPreferenceArray(entry.getValue()));
            }

            dataModel = new GenericDataModel(userPereferencesMap);
            UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
            UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.9, similarity, dataModel);
            recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }

    String[] recommendThings(String reviewerID) throws TasteException {
        List<String> recommendations = new ArrayList<String>();

        List<RecommendedItem> items = recommender.recommend(thing2long.toLongID(reviewerID), 10);
        for (RecommendedItem item : items) {
            recommendations.add(thing2long.toStringID(item.getItemID()));
        }
        return recommendations.toArray(new String[recommendations.size()]);
    }
}
