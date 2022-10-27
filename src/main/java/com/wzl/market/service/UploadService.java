package com.wzl.market.service;

import com.wzl.market.utils.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadService {
    public ResponseResult upload(MultipartFile multipartFile) throws IOException;
}
