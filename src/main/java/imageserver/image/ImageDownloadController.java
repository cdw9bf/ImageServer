package imageserver.image;

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

    ImageDownloadController() {
    }


    /**
     * Get file
     * @param name= Name of file
     * @param type= Type of file, optional. jpg Assumed
     * @return
     */
    @GetMapping(path = "/image")
    public ResponseEntity getImageById(@RequestParam("name") String name,
                                       @RequestParam(value="type", required=false) String type) {
        if (name == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Both id and name specified");
        }
        String fileType = handleFileType(type);

        try {
            FileInputStream is = new FileInputStream("/images/" + name + fileType);
            log.info("/images/" + name + fileType);
            byte[] bytes = StreamUtils.copyToByteArray(is);

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

    private String handleFileType(String type) {
        if (type != null) {
            type = "." + type;
        } else {
            type = ".jpg";
        }
        return type;
    }

}