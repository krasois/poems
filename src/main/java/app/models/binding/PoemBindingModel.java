package app.models.binding;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PoemBindingModel {

    private static final String EMPTY_TITLE_MESSAGE = "The poem must have a title.";
    private static final int MIN_TITLE_LENGTH = 1;
    private static final String EMPTY_AUTHOR_MESSAGE = "The poem must have an author.";
    private static final int MIN_AUTHOR_NAME_LENGTH = 1;
    private static final String EMPTY_POEM_CONTENT_MESSAGE = "Poem text cannot be empty.";
    private static final int MIN_POEM_TEXT_LENGTH = 1;
    private static final String NEGATIVE_YEAR_MESSAGE = "Publish year cannot be negative.";
    private static final int MIN_YEAR = 0;
    private static final String DECIMAL_POINT_MESSAGE = "Publish year cannot have a decimal point.";
    private static final int DECIMAL_POINTS = 0;
    private static final int INTEGER_DIGITS = 5;

    @NotEmpty(message = EMPTY_TITLE_MESSAGE)
    @Size(min = MIN_TITLE_LENGTH, message = EMPTY_TITLE_MESSAGE)
    private String title;

    @NotEmpty(message = EMPTY_AUTHOR_MESSAGE)
    @Size(min = MIN_AUTHOR_NAME_LENGTH, message = EMPTY_AUTHOR_MESSAGE)
    private String author;

    @Min(value = MIN_YEAR, message = NEGATIVE_YEAR_MESSAGE)
    @Digits(fraction = DECIMAL_POINTS, integer = INTEGER_DIGITS, message = DECIMAL_POINT_MESSAGE)
    private int publishYear;

    @NotEmpty(message = EMPTY_POEM_CONTENT_MESSAGE)
    @Size(min = MIN_POEM_TEXT_LENGTH, message = EMPTY_POEM_CONTENT_MESSAGE)
    private String poemContent;

    public PoemBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPoemContent() {
        return poemContent;
    }

    public void setPoemContent(String poemContent) {
        this.poemContent = poemContent;
    }
}