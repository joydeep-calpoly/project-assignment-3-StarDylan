package edu.calpoly.models.newsapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsApiResponse {
    private final NewsApiSuccess successObject;
    private final NewsApiError errorObject;

    public boolean isSuccess() {
        return successObject != null;
    }
}
