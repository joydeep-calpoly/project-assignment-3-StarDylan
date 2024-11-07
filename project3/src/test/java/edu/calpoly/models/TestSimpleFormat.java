package edu.calpoly.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.abstractions.Article;
import edu.calpoly.parsers.NewsApiParseArticleVisitor;
import edu.calpoly.parsers.SimpleFormatParseArticleVisitor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestSimpleFormat {
    @Test
    @DisplayName("Parse a good article successfully")
    void testSimpleFormat() throws JsonProcessingException {
        String data = """
                {
                  "description": "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                  "publishedAt": "2021-04-16 09:53:23.709229",
                  "title": "Assignment #2",
                  "url": "https://canvas.calpoly.edu/courses/55411/assignments/274503"
                }""";

        List<? extends Article> articles = Article.acceptParser(new SimpleFormatParseArticleVisitor(), data);

        assertEquals(1, articles.size());
        assertEquals(new SimpleFormatArticle("Assignment #2",
                "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                "https://canvas.calpoly.edu/courses/55411/assignments/274503",
                "2021-04-16 09:53:23.709229"), articles.getFirst());

        assertTrue(articles.getFirst().isValid());
    }

    @Test
    @DisplayName("Test Parser throws exception when extra property is found")
    void testExtraProperty() {
        String data = """
                {
                  "description": "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                  "publishedAt": "2021-04-16 09:53:23.709229",
                  "url": "https://canvas.calpoly.edu/courses/55411/assignments/274503",
                  "photo": "https://photo.website"
                }""";


        assertThrows(JsonProcessingException.class, () -> Article.acceptParser(new SimpleFormatParseArticleVisitor(), data));
    }
}
