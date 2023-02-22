package com.example.demo.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.util.stream.Collectors;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;

@Configuration
public class APIFilter extends OncePerRequestFilter {
    JsonArray allowAPI;
    String id;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain
    ) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie: cookies) {
            if ("token".equals(cookie.getName())) {
                String token = cookie.getValue();
                String[] parts = token.split("\\.");
                String chunk = decode(parts[1]);
                String username = getValue(chunk, "username");
                allowAPI = getArray(chunk, "allowAPI");

                String body = request.getReader().lines().collect(Collectors.joining());
                String campaignHeader = getValue(body, "campaignHeader");
                
                id = getAPIID(getAPIIDs(), campaignHeader);
                
                logger.info("campaignHeader:   "+campaignHeader);
                logger.info("username:   "+username);
                logger.info("allowAPI:   "+allowAPI);
                logger.info("calling api id:   "+id);
                logger.info("AUTHORIZED:   "+hasAuthorization(allowAPI, id));
            }
        }
        
        if (hasAuthorization(allowAPI, id)){
            filterChain.doFilter(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error -> Unauthorized");
        }
    }

    private static String decode(String encodedString) {
        Base64 base64 = new Base64();
        return new String(base64.decode(encodedString));
    }

    private static JsonArray getAPIIDs(){
        JsonArray apiIDs = new JsonArray();
        String[] list = new String[]{"1.DAILYGAMES","2.MONTHLYGAMES","3.SUPERADMIN"};
        for(String i : list) { 
            JsonObject item = new JsonObject();
            String[] parts = i.split("\\.");
            item.addProperty("id",parts[0]);
            item.addProperty("campaignHeader",parts[1]);
            apiIDs.add(item);
        }
        //[{"id": "1", "campaignHeader": "DAILYGAMES"},
        //{"id": "2", "campaignHeader": "MONTHLYGAMES"},
        //{"id": "3", "campaignHeader": "SUPERADMIN"},];
        return apiIDs;
    }
    private static String getAPIID(JsonArray apiIDs, String campaignHeader){
        for(int i = 0; i < apiIDs.size(); i++) {
            if(apiIDs.get(i).getAsJsonObject().get("campaignHeader").getAsString().equals(campaignHeader)){
                return apiIDs.get(i).getAsJsonObject().get("id").getAsString();
            }
        }
        return "null";
    }
    private String getValue(String json, String name){
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jbody = jelement.getAsJsonObject();
        String result = jbody.get(name).getAsString();

        return result;
    }
    private JsonArray getArray(String json, String name){
        JsonElement jelement = new JsonParser().parse(json);
        JsonObject jbody = jelement.getAsJsonObject();
        JsonArray result = jbody.getAsJsonArray(name);
        
        return result;
    }

    private boolean hasAuthorization(JsonArray json, String value) {
        for(int i = 0; i < json.size(); i++) {
            if(json.get(i).getAsString().equals(value)) return true;
        }
        return false;
    }
}