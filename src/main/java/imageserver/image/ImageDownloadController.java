package imageserver.image;

import imageserver.database.ImageDAO;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
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
    private static Logger log = Logger.getLogger(ImageDownloadController.class);
    private ImageDAO imageDAO;

    ImageDownloadController() {
        this.imageDAO = new ImageDAO();
    }


    /**
     * Get file
     * @param name= Name of file
     * @param type= Type of file, optional. jpg Assumed
     * @return
     */
    @GetMapping(path = "/image")
    public ResponseEntity getImageById(@RequestParam(value = "uuid", required = false) String uuid,
                                       @RequestParam(value = "location", required = false) String location) {
        if ((uuid == null) && (location == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All Parameters are null");
        }

        String fileName = this.imageDAO.fetchImageByUuid(uuid, "fullSizeMetadata");



        try {
            FileInputStream is = new FileInputStream("/images/" + name + fileType);
            byte[] bytes = this.readFile(is);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);

        } catch (IOException ioe) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(ioe.toString());
        }
    }

    private byte[] readFile(FileInputStream path) throws IOException {
        byte[] bytes = StreamUtils.copyToByteArray(path);
        return bytes;
    }

}