package edu.calpoly.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.models.newsapi.NewsApiArticle;
import edu.calpoly.models.newsapi.NewsApiError;
import edu.calpoly.models.newsapi.NewsApiResponse;
import edu.calpoly.models.newsapi.NewsApiSuccess;
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

        NewsApiResponse response = NewsApiResponse.fromJson(json);

        assertTrue(response.isSuccess());
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

        NewsApiResponse response = NewsApiResponse.fromJson(json);

        assertTrue(response.isSuccess());
        assertEquals(1, response.getSuccessObject().articles().size());
        assertEquals(new NewsApiSuccess("ok", 1, List.of(new NewsApiArticle("test", "test2", "https://google.com", "2024-10-09T01:00:00Z"))), response.getSuccessObject());

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

        NewsApiResponse response = NewsApiResponse.fromJson(json);

        assertFalse(response.isSuccess());
        assertEquals(new NewsApiError("error", "weirdError", "Please repeat request"), response.getErrorObject());
    }
}