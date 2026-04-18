package jsonreader.errors;

public class JSONParseError extends RuntimeException {
    public JSONParseError(String message) {
        super(message);
    }
}