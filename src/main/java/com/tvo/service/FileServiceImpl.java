package com.tvo.service;

import com.tvo.common.AppConstant;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String getImagePath(String imageName) {
        File file = new File(AppConstant.RESOURCE_IMG + imageName);
        return file.getAbsolutePath();
    }
}
