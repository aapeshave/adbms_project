package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ajinkya on 4/25/17.
 */
public class HotWord {
    @SerializedName("setimet")
    @Expose
    private Setimet setimet;

    @SerializedName("relevance")
    @Expose
    private Double relevance;

    @SerializedName("textInfo")
    @Expose
    private String textInfo;

    @SerializedName("jsonEmotion")
    @Expose
    private JsonEmotion jsonEmotion;


    public Double getRelevance() {
        return relevance;
    }

    public void setRelevance(Double relevance) {
        this.relevance = relevance;
    }

    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo;
    }

    public JsonEmotion getJsonEmotion() {
        return jsonEmotion;
    }

    public void setJsonEmotion(JsonEmotion jsonEmotion) {
        this.jsonEmotion = jsonEmotion;
    }

    public Setimet getSetimet() {
        return setimet;
    }

    public void setSetimet(Setimet setimet) {
        this.setimet = setimet;
    }
}
