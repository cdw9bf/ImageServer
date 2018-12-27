package imageserver.exceptions;



public class ImageLookupFailure extends RuntimeException {
    public ImageLookupFailure(String message, Throwable err) {
        super(message, err);
    }
}
