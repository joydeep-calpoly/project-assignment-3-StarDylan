package edu.calpoly;

import edu.calpoly.abstractions.Article;
import edu.calpoly.models.SimpleFormatArticle;
import edu.calpoly.models.newsapi.NewsApiArticle;
import edu.calpoly.models.newsapi.NewsApiResponse;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Logger logger = Logger.getLogger("edu.calpoly");

        FileHandler fh = new FileHandler("log.txt");
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        String newsApiKey;

        try {
            newsApiKey = System.getenv("NEWS_API_KEY");
        } catch (NullPointerException e) {
            logger.severe("Missing \"NEWS_API_KEY\" environment variable");
            return;
        }

        List<Article> articles = new ArrayList<>();

        articles.addAll(getSimpleFormatArticleFromFile("inputs/simple.txt"));
        articles.addAll(getNewsApiArticlesFromFile("inputs/newsapi.txt"));
        articles.addAll(getNewsApiArticlesFromHttp(newsApiKey, logger));

        articles = ArticleValidator.validateAndFilter(articles, logger);

        for (Article article : articles) {
            System.out.println(article.toString());
            System.out.println();
        }
    }

    /**
     * Fetches NewsAPI and returns the articles
     *
     * @param apiKey Apikey to send to NewsAPI
     * @param logger to log any HTTP errors
     * @return Articles
     * @throws IOException Failing to decode json
     * @throws InterruptedException Failed to send request
     */
    public static List<Article> getNewsApiArticlesFromHttp(String apiKey, Logger logger) throws IOException, InterruptedException {
        List<Article> articles = new ArrayList<>();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://newsapi.org/v2/top-headlines?country=us&apiKey=%s".formatted(apiKey)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String body = response.body();

            NewsApiResponse parsedResponse = NewsApiResponse.fromJson(body);
            if (parsedResponse.isSuccess()) {
                articles.addAll(parsedResponse.getSuccessObject().articles());
            } else {
                logger.log(Level.WARNING, "NewsAPI returned error: {0}", parsedResponse);
            }
        }
        return articles;
    }

    /**
     * Get News API Article from file
     *
     * @param path to find file at
     * @return Articles extracted
     * @throws IOException file can't be found or JSON is invalid
     */
    public static List<Article> getNewsApiArticlesFromFile(String path) throws IOException {
        String content = Files.readString(Path.of(path), Charset.defaultCharset());
        NewsApiResponse response = NewsApiResponse.fromJson(content);

        if (response.isSuccess()) {
            return new ArrayList<>(response.getSuccessObject().articles());
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * Get simple Format Article frm a file
     * @param path to find file at
     * @return Articles extracted
     * @throws IOException file can't be found or JSON is invalid
     */
    public static List<Article> getSimpleFormatArticleFromFile(String path) throws IOException {
        String content = Files.readString(Path.of(path), Charset.defaultCharset());
        SimpleFormatArticle response = SimpleFormatArticle.fromJson(content);

        if (response != null) {
            return new ArrayList<>(Collections.singleton(response));
        } else {
            return new ArrayList<>();
        }
    }
}