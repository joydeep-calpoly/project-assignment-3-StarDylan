package edu.calpoly.models.newsapi;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates a new Successful News API result object
 *
 * @param status       status of the request (should always be "ok")
 * @param totalResults Number of articles returned by the service
 * @param articles     Articles included in API response
 */
public record NewsApiSuccess(String status, int totalResults, ArrayList<NewsApiArticle> articles) { }
