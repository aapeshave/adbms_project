import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.*;
import pojo.Review;
import pojo.SentimentalMainClass;

import java.io.*;

/**
 * Created by ajinkya on 4/25/17.
 */
public class SentimentalReviewAnalysis {
    private static File outFile = new File("/Users/ajinkya/Documents/adbms_project/ReviewSentimentalAnalysis/output/sentiment_out.json");

    public static void main(String args[]) {
        try {
            parseFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseFile() {

        String jsonFile = "/Users/ajinkya/Documents/adbms_project/ReviewSentimentalAnalysis/input/part-m-00009";
        BufferedReader bufferedReader = null;
        String line = "";
        Gson gson = new Gson();

        try {
            bufferedReader = new BufferedReader(new FileReader(jsonFile));
            while ((line = bufferedReader.readLine()) != null) {
                Review jsonReview = gson.fromJson(line.toString(), Review.class);
                getSentimentalResullts(jsonReview.getReviewText());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void getSentimentalResullts(String text) {
        NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
                NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27,
                "449976ca-4b96-42ab-bf76-ddad10996981",
                "jxkBX8NPa8kI");

        EntitiesOptions entitiesOptions = new EntitiesOptions.Builder()
                .emotion(true)
                .sentiment(true)
                .limit(2)
                .build();

        KeywordsOptions keywordsOptions = new KeywordsOptions.Builder()
                .emotion(true)
                .sentiment(true)
                .limit(2)
                .build();

        Features features = new Features.Builder()
                .entities(entitiesOptions)
                .keywords(keywordsOptions)
                .build();

        AnalyzeOptions parameters = new AnalyzeOptions.Builder()
                .text(text)
                .features(features)
                .build();

        AnalysisResults response = service
                .analyze(parameters)
                .execute();

        createJsonFileOfSentiments(response.toString());
    }

    private static void createJsonFileOfSentiments(String inputToWrite) {
        Gson gson = new Gson();
        FileWriter fw;
        try {
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            fw = new FileWriter(outFile.getAbsoluteFile(), true);
            SentimentalMainClass toWrite = gson.fromJson(inputToWrite, SentimentalMainClass.class);
            fw.append(gson.toJson(toWrite));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
