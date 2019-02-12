package imageserver.beans;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;

public class Image {
    private MultipartFile originalUpload = null;
    private String contentType = null;
    private BufferedImage bufferedOriginalImage = null;
    private BufferedImage bufferedThumbnailImage = null;

    public Image(){}

    public Image(MultipartFile file) {
        this.originalUpload = file;
    }

    public MultipartFile getOriginalUpload() {
        return originalUpload;
    }

    public void setOriginalUpload(MultipartFile originalUpload) {
        this.originalUpload = originalUpload;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public BufferedImage getBufferedOriginalImage() {
        return bufferedOriginalImage;
    }

    public void setBufferedOriginalImage(BufferedImage bufferedOriginalImage) {
        this.bufferedOriginalImage = bufferedOriginalImage;
    }

    public BufferedImage getBufferedThumbnailImage() {
        return bufferedThumbnailImage;
    }

    public void setBufferedThumbnailImage(BufferedImage bufferedThumbnailImage) {
        this.bufferedThumbnailImage = bufferedThumbnailImage;
    }
}
