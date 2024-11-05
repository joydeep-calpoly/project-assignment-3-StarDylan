package edu.calpoly;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.abstractions.Article;
import edu.calpoly.models.SimpleFormatArticle;
import edu.calpoly.models.newsapi.NewsApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArticleValidatorTest {
    @Test
    @DisplayName("Test Article is Invalid when Missing Property")
    void testMissingProperty() throws JsonProcessingException {
        String data = """
                {
                  "description": "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                  "publishedAt": "2021-04-16 09:53:23.709229",
                  "url": "https://canvas.calpoly.edu/courses/55411/assignments/274503"
                }""";


        Article article = SimpleFormatArticle.fromJson(data);
        assertNotNull(article);

        List<Article> articles = new ArrayList<>(Collections.singleton(article));

        List<Article> filteredArticles = ArticleValidator.validateAndFilter(articles, null);

        assertEquals(0, filteredArticles.size());
    }

    @Test
    @DisplayName("Parse and validate only good articles")
    void parseGoodAndBadArticles() throws JsonProcessingException {
        String json = """
                {
                    "status": "ok",
                    "articles": [
                        {
                            "title": "test",
                            "description": "test2",
                            "url": "https://google.com",
                            "publishedAt": "2024-10-09T01:00:00Z"
                        },
                        {
                            "title": "test",
                            "description": "test2",
                            "publishedAt": "2024-10-09T01:00:00Z"
                        }
                    ],
                    "totalResults": 2
                }
                """;

        NewsApiResponse response = NewsApiResponse.fromJson(json);

        assertTrue(response.isSuccess());
        List<Article> articles = new ArrayList<>(response.getSuccessObject().articles());

        List<Article> filteredArticles = ArticleValidator.validateAndFilter(articles, null);

        assertEquals(1, filteredArticles.size());
    }

}