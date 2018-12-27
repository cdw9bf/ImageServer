package imageserver.image;

import imageserver.database.ImageDAO;
import imageserver.properties.FileStorageProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class ImageStorageService {
    private static Logger log = Logger.getLogger(ImageStorageService.class);

    private ImageDAO imageDAO = new ImageDAO();


    @Autowired
    private FileStorageProperties fileStorageProperties;

    private final Path fileStorageLocation;

    @Autowired
    public ImageStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            //throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    public void storeFile(MultipartFile file) {
        // Normalize file name
        UUID uuid = UUID.randomUUID();


        try {
            // Insert into Database
            String pattern = "yyyy_MM_dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date todaysDate = new Date();
            String date = simpleDateFormat.format(todaysDate);
            Path fullSizePath = this.fileStorageLocation.resolve("/full_size/" + date + "/" + uuid.toString());

            if (Files.notExists(fullSizePath)) {
                Files.createDirectories(fullSizePath);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            this.imageDAO.insertImageMetadata(fullSizePath.toString(),
                    fullSizePath.toString(),
                    "checkSum",
                    todaysDate,
                    uuid);
            log.info(fullSizePath.toString());
            Files.copy(file.getInputStream(), fullSizePath, StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            this.imageDAO.removeImageMetadata(uuid);

        }
    }



}
