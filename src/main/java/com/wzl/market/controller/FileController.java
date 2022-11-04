package com.wzl.market.controller;

import com.wzl.market.service.Impl.DownLoadServiceImpl;
import com.wzl.market.service.Impl.UploadServiceImpl;
import com.wzl.market.service.UploadService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class FileController {
    @Autowired
    UploadServiceImpl uploadService;
    @Autowired
    DownLoadServiceImpl downLoadService;

    @PostMapping("/file/upload")
    public ResponseResult upload(MultipartFile multipartFile) throws IOException {
        return uploadService.upload(multipartFile);
    }

    @GetMapping("/file/download")
    public HttpServletResponse download(String url, HttpServletResponse response) throws IOException {
        return downLoadService.download(url,response);
    }

}
