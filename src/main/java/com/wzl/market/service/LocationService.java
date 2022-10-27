package com.wzl.market.service;

import com.wzl.market.utils.ResponseResult;
import org.springframework.http.ResponseEntity;

public interface LocationService {

    public ResponseEntity<String> searchLocations(String query, String ip);
}
