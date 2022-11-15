package com.wzl.market.service.Impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.transfer.TransferManager;
import com.wzl.market.service.DownLoadService;
import com.wzl.market.utils.ResponseResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Service
public class DownLoadServiceImpl implements DownLoadService {

    @Autowired
    COSClient cosClient;

    public HttpServletResponse download(String url, HttpServletResponse response) throws IOException {
        //System.out.println("in download");
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "luke-1309838234";
        String key = url;
        // 方法1 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        // 限流使用的单位是 bit/s, 这里设置下载带宽限制为10MB/s
        getObjectRequest.setTrafficLimit(80*1024*1024);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(url+"", "UTF8"));
        //3.输出流：

        IOUtils.copy(cosObjectInput, response.getOutputStream());
        // 下载对象的 CRC64
        //String crc64Ecma = cosObject.getObjectMetadata().getCrc64Ecma();
        // 关闭输入流
        cosObjectInput.close();

        return response;
    }

}
