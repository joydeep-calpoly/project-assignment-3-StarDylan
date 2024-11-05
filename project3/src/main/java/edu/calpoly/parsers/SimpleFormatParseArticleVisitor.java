package edu.calpoly.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.calpoly.abstractions.Article;
import edu.calpoly.abstractions.ParseArticleVisitor;

public class SimpleFormatParseArticleVisitor implements ParseArticleVisitor {
    @Override
    public Article visitString(String content) throws JsonProcessingException {
        return null;
    }
}
