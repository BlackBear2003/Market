package com.wzl.market.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wzl.market.service.LocationService;
import com.wzl.market.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public ResponseEntity<String> searchLocations(String query,String ip) {

        String loc = restTemplate.getForObject(
                "https://api.map.baidu.com/location/ip?" +
                        "ak=p9tOZ9SMNyrdrLO9XF8oFFbfckLRCsBL" +
                        "&ip=" +ip+
                        "&coor=bd09ll", String.class);

        JSONObject locJson = JSON.parseObject(loc);
        //System.out.println(locJson);
        JSONObject content = locJson.getObject("content",JSONObject.class);
        //System.out.println(content);
        String region = content.getString("address");
        //String region = "福州市";
        //System.out.println(region);

        return restTemplate.getForEntity(
                "https://api.map.baidu.com/place/v2/search?" +
                        "query=" +query+
                        "&region=" +region+
                        "&output=json" +
                        "&ak=p9tOZ9SMNyrdrLO9XF8oFFbfckLRCsBL", String.class);
    }
}
