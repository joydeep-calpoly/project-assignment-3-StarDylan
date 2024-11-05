package edu.calpoly.models.newsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiResponse {
    private NewsApiSuccess successObject;
    private NewsApiError errorObject;


    // We want to only create from deserialization
    private NewsApiResponse(){}

    public NewsApiSuccess getSuccessObject() {
        return successObject;
    }

    public NewsApiError getErrorObject() {
        return errorObject;
    }

    public boolean isSuccess() {
        return successObject != null;
    }
}
