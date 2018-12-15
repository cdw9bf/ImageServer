package imageserver.image;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class ImageDownloadController {
    static Logger log = Logger.getLogger(ImageDownloadController.class);

    ImageDownloadController() {
    }

    /**
     * Gets Image by ID from the /images folder.
     *
     * @param id: Integer
     * @return
     */
    @GetMapping(path = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImageById(@RequestParam("id") Integer id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            FileInputStream is = new FileInputStream("/images/" + id.toString() + ".jpg");
            log.info("/images/" + id.toString() + ".jpg");
            byte[] bytes = StreamUtils.copyToByteArray(is);

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (IOException ioe) {
            return ResponseEntity.notFound().build();
        }
    }
}