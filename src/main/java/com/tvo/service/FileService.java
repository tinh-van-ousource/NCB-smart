package com.tvo.service;

import com.tvo.response.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String getImagePath(String directory, String imageName);

    UploadFileResponse uploadFile(MultipartFile multipartFiles);

    UploadFileResponse uploadBannerFile(MultipartFile multipartFiles);

    boolean deleteImage(String directory, String imageName);

}
