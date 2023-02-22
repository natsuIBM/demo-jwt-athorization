package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;

@RestController
@RequestMapping("/jwt")
public class API {
    
    Logger logger = LogManager.getLogger("CONSOLE_JSON_APPENDER");
    
    @RequestMapping(value = "/getToken")
    public String simpleAPI(HttpServletRequest request ,
                            @RequestHeader HttpHeaders headers
    ) throws Exception {
        String jwt_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6Ik5hdHN1IiwiYWxsb3dBUEkiOlsxLDJdfQ.r05SgSBef0CmnPlRaetgu8i-q7BivIV5UWj-JK5AKEI";

        return jwt_token;
    }

    @RequestMapping(value = "/adminonly")
    public String simpleAPI2(HttpServletRequest request ,
                            @RequestHeader HttpHeaders headers
    ) throws Exception {
//        JsonElement jelement = new JsonParser().parse(body);
//        JsonObject reqBody = jelement.getAsJsonObject();
//        String campaignHeader = reqBody.get("campaignHeader").getAsString();
//        
//        String[] parts = token.split("\\.");
//        jelement = new JsonParser().parse(decode(parts[1]));
//        JsonObject jwt = jelement.getAsJsonObject();
//        String username = jwt.get("username").getAsString();
//        JsonArray allowAPI = jwt.getAsJsonArray("allowAPI");

//        logger.info("body   :   "+campaignHeader);
//        logger.info("username:   "+username);
//        logger.info("allowAPI:   "+allowAPI);
        return "Success";
    }

    private static String decode(String encodedString) {
        Base64 base64 = new Base64();
        return new String(base64.decode(encodedString));
    }
}