package imageserver.image;

import imageserver.beans.Image;
import imageserver.beans.ImageUploadResponse;
import imageserver.database.ImageDAO;
import imageserver.exceptions.InsertFailedException;
import imageserver.imagehelpers.CreateImageObject;
import imageserver.imagehelpers.ResizeImage;
import imageserver.properties.FileStorageProperties;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.commons.codec.digest.DigestUtils;

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


    public ImageUploadResponse storeFile(MultipartFile file) {
        // Normalize file name
        UUID uuid = UUID.randomUUID();
        ImageUploadResponse imageUploadResponse = new ImageUploadResponse();
        imageUploadResponse.setUuid(uuid);

        // Create File Path
        String pattern = "yyyy_MM_dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date todaysDate = new Date();
        String date = simpleDateFormat.format(todaysDate);
        Path fullSizePath = this.fileStorageLocation.resolve("full_size/" + date + "/" + uuid.toString());
        Path thumbNailPath = this.fileStorageLocation.resolve("thumbnail_size/" + date + "/" + uuid.toString());

        try {
            Image uploadedImage = CreateImageObject.createImageObject(file);
            if (Files.notExists(fullSizePath)) {
                Files.createDirectories(fullSizePath);
            }
            if (Files.notExists(thumbNailPath)) {
                Files.createDirectories(thumbNailPath);
            }

            // Create Checksum of file
            String md5Checksum = DigestUtils.md5Hex(uploadedImage.getOriginalUpload().getInputStream());
            imageUploadResponse.setChecksum(md5Checksum);

            // Copy file to the target location (Replacing existing file with the same name)
            this.imageDAO.insertImageMetadata(fullSizePath.toString(),
                    thumbNailPath.toString(),
                    md5Checksum,
                    todaysDate,
                    uuid,
                    uploadedImage.getOriginalUpload().getOriginalFilename(),
                    uploadedImage.getContentType());
            log.info(fullSizePath.toString());
            Files.copy(uploadedImage.getOriginalUpload().getInputStream(), fullSizePath, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(ResizeImage.bufferedToInputStream(uploadedImage.getBufferedThumbnailImage(), uploadedImage.getContentType()), thumbNailPath, StandardCopyOption.REPLACE_EXISTING);

        } catch (InsertFailedException ife ) {
            log.warn("Failed to store file in database");
            imageUploadResponse.setSuccess(false);
            return imageUploadResponse;
        } catch (IOException ex) {
//            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
            this.imageDAO.removeImageMetadata(uuid);
            try {
                Files.delete(fullSizePath);
            } catch (IOException ioe) {
                log.warn("Filed to delete " + fullSizePath.toString() + " from filesystem");
            }
            try {
                Files.delete(thumbNailPath);
            } catch (IOException ioe) {
                log.warn("Filed to delete " + thumbNailPath.toString() + " from filesystem");
            }
            imageUploadResponse.setSuccess(false);
            return imageUploadResponse;

        }
        imageUploadResponse.setSuccess(true);
        return imageUploadResponse;
    }




}
