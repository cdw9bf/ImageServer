package imageserver.imagehelpers;
import imageserver.beans.Image;
import imageserver.beans.JpegImage;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CreateImageObject {
    private static Logger log = Logger.getLogger(CreateImageObject.class);

    public static Image createImageObject(MultipartFile uploadedFile) throws IOException {
        Image image = new Image();
        if (uploadedFile.getOriginalFilename().endsWith(".jpg") || uploadedFile.getOriginalFilename().endsWith(".jpeg")) {
            log.debug("Creating JPG Image from Uploaded Image");
            image = new JpegImage(uploadedFile);
            image.setContentType("jpg");
            BufferedImage bi = ImageIO.read(uploadedFile.getInputStream());
            image.setBufferedOriginalImage(bi);
//            image.setBufferedThumbnailImage(ResizeImage.scale(image.getBufferedOriginalImage(), 0.5));

        }
        return image;
    }
}
