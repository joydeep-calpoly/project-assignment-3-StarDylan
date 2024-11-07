package edu.calpoly.abstractions;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface Article {
    String title();
    String description();
    String url();
    String publishedAt();

    /**
     * Whether the article is valid (has all required fields) or not
     * @return article validity
     */
    default boolean isValid() {
        return title() != null && description() != null && url() != null && publishedAt() != null;
    }

    static List<Article> acceptParser(ParseArticleVisitor visitor, String json) throws JsonProcessingException {
        return visitor.visitJsonString(json);
    }

}
