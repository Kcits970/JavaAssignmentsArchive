public class InvalidHeaderException extends Exception {
    public InvalidHeaderException(String invalidArg) {
        super(String.format("'%s' is not a valid argument to define the number of subjects", invalidArg));
    }
    public InvalidHeaderException(int expected) {
        super(String.format("Found less arguments than the expected %d", expected));
    }
}
