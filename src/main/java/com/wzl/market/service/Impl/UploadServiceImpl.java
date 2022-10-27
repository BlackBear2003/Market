package com.wzl.market.service.Impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.wzl.market.service.UploadService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    public static final String bucketName = "luke-1309838234";

    @Autowired
    COSClient cosClient;

    public ResponseResult upload(MultipartFile multipartFile) throws IOException {
        String newFileName = generateUniqueName(multipartFile.getOriginalFilename());
        //生成临时文件
        File file = File.createTempFile("temp",null);
        //将MultipartFile类型转为File类型
        multipartFile.transferTo(file);

        //文件在存储桶中的key
        //创建存储对象的请求
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newFileName, file);
        //执行上传并返回结果信息
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        cosClient.shutdown();
//注意：
//COSClient 是线程安全的类，允许多线程访问同一实例。因为实例内部维持了一个连接池，创建多个实例可能导致程序资源耗尽，请确保程序生命周期内实例只有一个，并在不再需要使用时，调用 shutdown 方法将其关闭。如果需要新建实例，请先将之前的实例关闭。

        return new ResponseResult<>(200,"上传成功",newFileName);
    }


    public String generateUniqueName(String originalName) {
        return UUID.randomUUID() + originalName.substring(originalName.lastIndexOf("."));
    }
}
