package com.wzl.market.controller;

import com.wzl.market.utils.ResponseResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
public class downloadController {

    @GetMapping("/download/cat")
    public ResponseResult getCatGif(HttpServletResponse response, HttpServletRequest request) throws IOException {
        InputStream in = new FileInputStream(new File("/Library/MyProjects/ManageSys/src/main/resources/static/Cat.gif"));

        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode("Cat.gif", "UTF8"));
        //3.输出流：
        IOUtils.copy(in, response.getOutputStream());
        //4.在finally中关闭流
        in.close();

        return new ResponseResult<>(200,"获得下载资源","Cat.gif");

    }

}
