package edu.calpoly.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.calpoly.abstractions.Article;
import edu.calpoly.abstractions.ParseArticleVisitor;
import edu.calpoly.models.newsapi.NewsApiError;
import edu.calpoly.models.newsapi.NewsApiResponse;
import edu.calpoly.models.newsapi.NewsApiSuccess;

import java.util.ArrayList;

public class NewsApiParseArticleVisitor implements ParseArticleVisitor {
    @Override
    public ArrayList<Article> visitJsonString(String content) throws JsonProcessingException {
        NewsApiResponse newsApiResponse;
        ObjectMapper mapper = new ObjectMapper();


        try {
            newsApiResponse = new NewsApiResponse(null, mapper.readValue(content, NewsApiError.class));
        } catch (JsonProcessingException e) {
            newsApiResponse = new NewsApiResponse(mapper.readValue(content, NewsApiSuccess.class), null);
        }

        if (newsApiResponse.isSuccess()) {
            return new ArrayList<>(newsApiResponse.getSuccessObject().articles());
        } else {
           return new ArrayList<>();
        }
    }
}
