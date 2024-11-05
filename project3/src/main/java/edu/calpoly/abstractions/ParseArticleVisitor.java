package edu.calpoly.abstractions;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ParseArticleVisitor {

    /**
     * Parses Json into an object
     * @param content Simple Format Article json
     * @return Successfully parsed Article object or null if invalid
     * @throws JsonProcessingException if Json is Invalid
     */
    Article visitString(String content) throws JsonProcessingException;
}
