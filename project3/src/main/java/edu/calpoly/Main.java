package edu.calpoly;

import edu.calpoly.abstractions.Article;
import edu.calpoly.models.SimpleFormatArticle;
import edu.calpoly.models.newsapi.NewsApiArticle;
import edu.calpoly.models.newsapi.NewsApiResponse;
import edu.calpoly.parsers.NewsApiParseArticleVisitor;
import edu.calpoly.parsers.SimpleFormatParseArticleVisitor;

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

        articles.addAll(Article.acceptParser(new SimpleFormatParseArticleVisitor(),  Files.readString(Path.of("inputs/simple.txt"), Charset.defaultCharset())));
        articles.addAll(Article.acceptParser(new NewsApiParseArticleVisitor(),  Files.readString(Path.of("inputs/newsapi.txt"), Charset.defaultCharset())));
        articles.addAll(Article.acceptParser(new NewsApiParseArticleVisitor(),  getNewsApiJsonFromHttp(newsApiKey, logger)));

        articles = ArticleValidator.validateAndFilter(articles, logger);

        for (Article article : articles) {
            System.out.println(article.toString());
            System.out.println();
        }
    }

    /**
     * Fetches NewsAPI and returns the json
     *
     * @param apiKey Apikey to send to NewsAPI
     * @param logger to log any HTTP errors
     * @throws IOException Failing to decode json
     * @throws InterruptedException Failed to send request
     */
    public static String getNewsApiJsonFromHttp(String apiKey, Logger logger) throws IOException, InterruptedException {

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://newsapi.org/v2/top-headlines?country=us&apiKey=%s".formatted(apiKey)))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                logger.log(Level.SEVERE, "Unexpected response code: " + response.statusCode() + "\n" + response.body());
            }

            return response.body();
        }
    }

}