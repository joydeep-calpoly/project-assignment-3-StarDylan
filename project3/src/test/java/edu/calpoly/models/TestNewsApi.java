package edu.calpoly.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.abstractions.Article;
import edu.calpoly.models.newsapi.NewsApiArticle;
import edu.calpoly.models.newsapi.NewsApiError;
import edu.calpoly.models.newsapi.NewsApiResponse;
import edu.calpoly.models.newsapi.NewsApiSuccess;
import edu.calpoly.parsers.NewsApiParseArticleVisitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestNewsApi {

    @Test
    @DisplayName("Simple Successful News API should work")
    void testGood() throws JsonProcessingException {
        String json = """
                {
                    "status": "ok",
                    "articles": [],
                    "totalResults": 0
                }
                """;

        List<? extends Article> articles = Article.acceptParser(new NewsApiParseArticleVisitor(), json);

        assertEquals(0, articles.size());
    }

    @Test
    @DisplayName("Parse all good articles")
    void parseGoodArticles() throws JsonProcessingException {
        String json = """
                {
                    "status": "ok",
                    "articles": [
                        {
                            "title": "test",
                            "description": "test2",
                            "url": "https://google.com",
                            "publishedAt": "2024-10-09T01:00:00Z"
                        }
                    ],
                    "totalResults": 1
                }
                """;

        List<? extends Article> articles = Article.acceptParser(new NewsApiParseArticleVisitor(), json);

        assertEquals(1, articles.size());
        assertEquals(new NewsApiArticle("test", "test2", "https://google.com", "2024-10-09T01:00:00Z"), articles.getFirst());

    }

    @Test
    @DisplayName("Simple Error News API should also work")
    void testError() throws JsonProcessingException {
        String json = """
                {
                    "status": "error",
                    "code": "weirdError",
                    "message": "Please repeat request"
                }
                """;

        List<? extends Article> articles = Article.acceptParser(new NewsApiParseArticleVisitor(), json);
        assertEquals(0, articles.size());
    }
}