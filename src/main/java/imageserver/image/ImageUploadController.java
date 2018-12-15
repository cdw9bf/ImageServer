package imageserver.image;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageUploadController {
    static Logger log = Logger.getLogger(ImageDownloadController.class);

    ImageUploadController(){};

    @PostMapping(path = "/image/upload", consumes = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) {



        return ResponseEntity.ok().build();
    }
}
