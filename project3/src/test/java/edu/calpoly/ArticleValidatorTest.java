package edu.calpoly;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.abstractions.Article;
import edu.calpoly.models.SimpleFormatArticle;
import edu.calpoly.models.newsapi.NewsApiResponse;
import edu.calpoly.parsers.NewsApiParseArticleVisitor;
import edu.calpoly.parsers.SimpleFormatParseArticleVisitor;
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


        List<? extends Article> articles = Article.acceptParser(new SimpleFormatParseArticleVisitor(), data);
        assertNotNull(articles);
        assertEquals(1, articles.size());

        List<Article> filteredArticles = ArticleValidator.validateAndFilter(new ArrayList<>(articles), null);

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

        List<? extends Article> articles = Article.acceptParser(new NewsApiParseArticleVisitor(), json);

        List<Article> filteredArticles = ArticleValidator.validateAndFilter(new ArrayList<>(articles), null);

        assertEquals(1, filteredArticles.size());
    }

}