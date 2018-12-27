package imageserver.exceptions;

public class InsertFailedException extends RuntimeException {
    public InsertFailedException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
