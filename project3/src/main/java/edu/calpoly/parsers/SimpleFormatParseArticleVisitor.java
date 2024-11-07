package edu.calpoly.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.calpoly.abstractions.Article;
import edu.calpoly.abstractions.ParseArticleVisitor;
import edu.calpoly.models.SimpleFormatArticle;
import edu.calpoly.models.newsapi.NewsApiArticle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SimpleFormatParseArticleVisitor implements ParseArticleVisitor {
    @Override
    public ArrayList<Article> visitJsonString(String content) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        return new ArrayList<>(Collections.singleton(mapper.readValue(content, SimpleFormatArticle.class)));
    }
}
