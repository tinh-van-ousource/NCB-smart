package com.tvo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.tvo.common.AppConstant;
import com.tvo.response.ResponeData;
import com.tvo.response.UploadFileResponse;
import com.tvo.service.FileService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = "/img", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "img")
public class FileController {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(value = "/uploadFile")
    public ResponeData<UploadFileResponse> uploadFile(@RequestParam("img") MultipartFile file) {
        UploadFileResponse uploadFileResponse = fileService.uploadFile(file);

        if (uploadFileResponse != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, uploadFileResponse);
        }
        return new ResponeData<>(AppConstant.UPLOAD_FILE_FAILED_CODE, AppConstant.UPLOAD_FILE_FAILED_MESSAGE, null);
    }

    @GetMapping("/{imageName}")
    public ResponeData<String> getImagePath(@PathVariable("imageName") String imageName) {
        String imagePath = fileService.getImagePath(AppConstant.RESOURCE_IMG, imageName);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, imagePath);
    }

    @DeleteMapping(value = "/deleteFile")
    public ResponeData<Boolean> deleteFile(String fileName) {
        boolean result = fileService.deleteImage(AppConstant.RESOURCE_IMG, fileName);
        if (result) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

    @PostMapping(value = "banner/uploadFile")
    public ResponeData<UploadFileResponse> uploadBannerFile(@RequestParam("img") MultipartFile file) {
        UploadFileResponse uploadFileResponse = fileService.uploadBannerFile(file);

        if (uploadFileResponse != null) {
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, uploadFileResponse);
        }
        return new ResponeData<>(AppConstant.UPLOAD_FILE_FAILED_CODE, AppConstant.UPLOAD_FILE_FAILED_MESSAGE, null);
    }

    @GetMapping("banner/{imageName}")
    public ResponeData<String> getBannerImagePath(@PathVariable("imageName") String imageName) {
        String imagePath = fileService.getImagePath(AppConstant.RESOURCE_BANNER_IMG, imageName);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, imagePath);
    }

    @DeleteMapping(value = "banner/deleteFile")
    public ResponeData<Boolean> deleteBannerFile(String fileName) {
        boolean result = fileService.deleteImage(AppConstant.RESOURCE_BANNER_IMG, fileName);
        if (result) {	
            return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.DELETED_SUCCESS_MESSAGE, true);
        }
        return new ResponeData<>(AppConstant.SYSTEM_ERROR_CODE, AppConstant.SYSTEM_ERROR_MESSAGE, null);
    }

}
