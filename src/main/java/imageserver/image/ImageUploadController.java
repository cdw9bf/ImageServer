package imageserver.image;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@RestController
public class ImageUploadController {
    private static Logger log = Logger.getLogger(ImageDownloadController.class);

    @Autowired
    private ImageStorageService imageStorageService;


    ImageUploadController(){}


    @PostMapping(path = "/image/upload")
    public ResponseEntity uploadImage(@RequestParam("file") MultipartFile file) {
        log.info("File name: " + file.getOriginalFilename());
        boolean ret = imageStorageService.storeFile(file);
        return ResponseEntity.ok().body(ret ? "Succesfully Stored File" : "Failed to Store File");
    }
}
