package edu.calpoly.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.abstractions.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

        SimpleFormatArticle article = SimpleFormatArticle.fromJson(data);

        assertEquals(article, new SimpleFormatArticle("Assignment #2",
                "Extend Assignment #1 to support multiple sources and to introduce source processor.",
                "https://canvas.calpoly.edu/courses/55411/assignments/274503",
                "2021-04-16 09:53:23.709229"));

        assertNotNull(article);
        assertTrue(article.isValid());
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


        assertThrows(JsonProcessingException.class, () -> SimpleFormatArticle.fromJson(data));
    }
}
