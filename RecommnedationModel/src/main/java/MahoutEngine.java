import org.apache.mahout.cf.taste.common.TasteException;

/**
 * Created by ajinkya on 4/26/17.
 */
public class MahoutEngine {
    private static final String REVIEWER_ID = "A3H7H6UTJ7WPPU";

    public static void main(String args[]) {
        RecommendationEngine engine = new RecommendationEngine();
        engine.startRecommendationModel();
        try {
            String[] recommendedThings = engine.recommendThings(REVIEWER_ID);
            for (String thing : recommendedThings) {
                System.out.println(thing);
            }

        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
}
