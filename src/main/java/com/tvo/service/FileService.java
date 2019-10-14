package com.tvo.service;

import org.springframework.web.multipart.MultipartFile;

import com.tvo.response.UploadFileResponse;

public interface FileService {
    String getImagePath(String directory, String imageName);

    UploadFileResponse uploadFile(MultipartFile multipartFiles);

    UploadFileResponse uploadBannerFile(MultipartFile multipartFiles);

    boolean deleteImage(String directory, String imageName);

}
