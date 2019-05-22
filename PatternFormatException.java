
public class PatternFormatException extends Exception {

    public PatternFormatException() {
        super("not correct format");
    }

    public PatternFormatException(String message) {
        super(message);
    }
}
