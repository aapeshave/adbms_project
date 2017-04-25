package pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ajinkya on 4/25/17.
 */
public class SentimentalMainClass {
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("entities")
    @Expose
    private List<Object> entities = null;
    @SerializedName("hotWords")
    @Expose
    private List<HotWord> hotWords = null;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Object> getEntities() {
        return entities;
    }

    public void setEntities(List<Object> entities) {
        this.entities = entities;
    }

    public List<HotWord> getHotWords() {
        return hotWords;
    }

    public void setHotWords(List<HotWord> hotWords) {
        this.hotWords = hotWords;
    }
}
