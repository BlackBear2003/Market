package com.wzl.market.service;

import com.wzl.market.utils.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DownLoadService {
    public HttpServletResponse download(String url,HttpServletResponse response) throws IOException;
}
