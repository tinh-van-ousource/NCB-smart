package com.tvo.service;

import com.tvo.common.FileStorageException;
import com.tvo.config.FileBannerStorageProperties;
import com.tvo.config.FileStorageProperties;
import com.tvo.response.UploadFileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileServiceImpl implements FileService {

    private final Path fileStorageLocation;
    private final Path fileBannerStorageLocation;

    @Autowired
    public FileServiceImpl(FileStorageProperties fileStorageProperties,
                           FileBannerStorageProperties fileBannerStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        this.fileBannerStorageLocation = Paths.get(fileBannerStorageProperties.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.fileBannerStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.",
                    ex);
        }
    }

    @Override
    public UploadFileResponse uploadFile(MultipartFile file) {
        try {
            String fileName = storeFile(this.fileStorageLocation, file);

            String fileUploadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/")
                    .path(fileName).toUriString();

            return new UploadFileResponse(fileName, fileUploadUri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UploadFileResponse uploadBannerFile(MultipartFile file) {
        try {
            String fileName = storeFile(this.fileBannerStorageLocation, file);

            String fileUploadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/img/")
                    .path(fileName).toUriString();

            return new UploadFileResponse(fileName, fileUploadUri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String storeFile(Path path, MultipartFile file) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS");

        // Normalize file name
        String fileName = file.getOriginalFilename();
        fileName = currentUserName + "_" + now.format(formatter) + "_" + fileName;
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = path.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    @Override
    public String getImagePath(String directory, String imageName) {
        File file = new File(directory + imageName);
        return file.getAbsolutePath();
    }

    @Override
    public boolean deleteImage(String directory, String imageName) {
        File file = new File(directory + imageName);
        return file.delete();
    }

}
