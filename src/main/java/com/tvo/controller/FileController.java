package com.tvo.controller;

import com.tvo.common.AppConstant;
import com.tvo.response.ResponeData;
import com.tvo.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/img", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "img")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/{imageName}")
    public ResponeData<String> getImagePath(@PathVariable("imageName") String imageName) {
        String imagePath = fileService.getImagePath(imageName);
        return new ResponeData<>(AppConstant.SYSTEM_SUCCESS_CODE, AppConstant.SYSTEM_SUCCESS_MESSAGE, imagePath);
    }

}
