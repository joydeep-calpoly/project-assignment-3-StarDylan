package edu.calpoly.abstractions;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.models.newsapi.NewsApiArticle;

import java.util.ArrayList;

public interface ParseArticleVisitor {

    /**
     * Parses Json into an object
     *
     * @param content Simple Format Article json
     * @return Successfully parsed Article object or null if invalid
     * @throws JsonProcessingException if Json is Invalid
     */
    ArrayList<? extends Article> visitJsonString(String content) throws JsonProcessingException;
}
