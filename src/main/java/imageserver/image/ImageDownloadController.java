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
import java.sql.SQLException;

@RestController
public class ImageDownloadController {
    private static Logger log = Logger.getLogger(ImageDownloadController.class);
    private ImageDAO imageDAO;

    ImageDownloadController() {
        this.imageDAO = new ImageDAO();
    }


    /**
     * Get file
     * @param uuid= Uuid of file
     * @param location= Location of file (Not recommended to use)
     * @return
     */
    @GetMapping(path = "/image/download")
    public ResponseEntity getImageById(@RequestParam(value = "uuid", required = false) String uuid,
                                       @RequestParam(value = "location", required = false) String location,
                                       @RequestParam(value = "type", required = false, defaultValue = "") String type) {
        if ((uuid == null) && (location == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All Parameters are null");
        }

        try {
            if (uuid != null) {
                String catalog = determineCatalog(type);
                location = this.imageDAO.fetchImageByUuid(uuid, catalog);
            }
        } catch (SQLException sqlex) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Uuid '" + uuid + "' not found.");
        }



        try {
            FileInputStream is = new FileInputStream(location);
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

    private String determineCatalog(String type) {
        String catalog;
        switch (type) {
            case "fullsize": {
                catalog = "fullSizeMetadata";
                break;
            }
            case "thumbnail": {
                catalog = "thumbnailMetadata";
                break;
            }
            default: {
                catalog = "fullSizeMetadata";
            }
        }
        return catalog;
    }



}