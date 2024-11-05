package edu.calpoly;

import edu.calpoly.abstractions.Article;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArticleValidator {

    private ArticleValidator() {}

    public static List<Article> validateAndFilter(List<Article> articles, Logger logger) {
        return articles.stream().filter(article -> {
                if (article.isValid()) {
                    return true;
                } else {
                    if (logger != null) {
                        logger.log(Level.WARNING, "article \"{0}\"is not valid", article.title());
                    }
                    return false;
                }
            }).toList();
    }
}
