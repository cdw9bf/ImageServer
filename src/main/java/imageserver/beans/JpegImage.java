package imageserver.beans;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class JpegImage extends Image {
    private byte[] originalImage;
    private byte[] thumbnailImage;

    public JpegImage() {
        super();
    }

    public JpegImage(MultipartFile originalUpload) throws IOException {
        super(originalUpload);
        this.originalImage = originalUpload.getBytes();
    }

    public byte[] getOriginalImage() {
        return originalImage;
    }

    public void setOriginalImage(byte[] originalImage) {
        this.originalImage = originalImage;
    }

    public byte[] getThumbnailImage() {
        return thumbnailImage;
    }

    public void setThumbnailImage(byte[] thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
    }

}
