package edu.calpoly.models.newsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.calpoly.abstractions.Article;

@JsonIgnoreProperties(ignoreUnknown = true)
public record NewsApiArticle(String title, String description, String url, String publishedAt) implements Article {
    @Override
    public String toString() {
        return "NewsApiArticle{" +
                "\n\ttitle='" + title + '\'' +
                "\n\tdescription='" + description + '\'' +
                "\n\turl='" + url + '\'' +
                "\n\tpublishedAt=" + publishedAt +
                "\n}";
    }
}