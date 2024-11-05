package edu.calpoly.models.newsapi;

/**
 * An error returned from the News API service
 * @param status status of returned response (should be "error")
 * @param code Code of error
 * @param message Any additional messages of error
 */
public record NewsApiError(String status, String code, String message) {}
