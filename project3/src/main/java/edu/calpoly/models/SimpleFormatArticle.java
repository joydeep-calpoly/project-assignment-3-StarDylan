package edu.calpoly.models;

import edu.calpoly.abstractions.Article;
import edu.calpoly.abstractions.ParseArticleVisitor;

public record SimpleFormatArticle(String title, String description, String url, String publishedAt) implements Article {

    public SimpleFormatArticle acceptParser(ParseArticleVisitor visitor) {
        visitor.visitSimpleFormat()
    }

    @Override
    public String toString() {
        return "SimpleFormatArticle{" +
                "\n\ttitle='" + title + '\'' +
                "\n\tdescription='" + description + '\'' +
                "\n\turl='" + url + '\'' +
                "\n\tpublishedAt=" + publishedAt +
                "\n}";
    }
}