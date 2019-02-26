package app.exceptions;

public class PoemNotFoundException extends Exception {

    private static final String DEFAULT_MESSAGE = "Poem not found";

    public PoemNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}