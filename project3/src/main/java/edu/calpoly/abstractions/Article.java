package edu.calpoly.abstractions;

public interface Article {
    String title();
    String description();
    String url();
    String publishedAt();

    /**
     * Whether the article is valid (has all required fields) or not
     * @return article validity
     */
    default boolean isValid() {
        return title() != null && description() != null && url() != null && publishedAt() != null;
    }
}
