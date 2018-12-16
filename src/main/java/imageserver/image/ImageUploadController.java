package imageserver.image;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import javax.servlet.MultipartConfigElement;

@RestController
public class ImageUploadController {
    private static Logger log = Logger.getLogger(ImageDownloadController.class);

    @Autowired
    private ImageStorageService imageStorageService;


    ImageUploadController(){}


    @PostMapping(path = "/image/upload")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("Got Request");
        log.info("File name: " + file.getOriginalFilename());

        String fileName = imageStorageService.storeFile(file);
        return ResponseEntity.ok().body(fileName);
    }
}
