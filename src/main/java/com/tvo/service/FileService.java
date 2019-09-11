package com.tvo.service;

import com.tvo.response.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String getImagePath(String imageName);

    UploadFileResponse uploadFile(MultipartFile multipartFiles);

    boolean deleteImage(String imageName);
}
