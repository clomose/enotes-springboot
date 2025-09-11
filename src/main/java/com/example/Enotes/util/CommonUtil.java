package com.example.Enotes.util;

import com.example.Enotes.config.security.CustomUserDetails;
import com.example.Enotes.dto.UserResponse;
import com.example.Enotes.entity.User;
import com.example.Enotes.handler.GenericResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonUtil {

    public static ResponseEntity<?> createBuildResponse(Object data, HttpStatus status){
        GenericResponse  response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message("success")
                .data(data)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createBuildResponseMessage(String message,HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("success")
                .message(message)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createErrorResponse(Object data,HttpStatus status){
        GenericResponse  response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message("failed")
                .data(data)
                .build();
        return response.create();
    }

    public static ResponseEntity<?> createErrorResponseMessage(String message,HttpStatus status){
        GenericResponse response = GenericResponse.builder()
                .responseStatus(status)
                .status("failed")
                .message(message)
                .build();
        return response.create();
    }

    public static String getContentType(String originalFileName) {
        String extension = FilenameUtils.getExtension(originalFileName);
        switch (extension){
            case "pdf":
                return "application/pdf";
            case "xlsx":
                return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet\n";
            case "txt":
                return "text/plain";
            case "png":
                return "image/png";
            case "jpeg":
                return "image/jpeg";
            default:
                return "application/octet-stream";
        }
    }

    public static String getUrl(HttpServletRequest request) {
        String apiUrl = request.getRequestURL().toString(); // http://localhost:8000/api/v1/auth
        String url = request.getServletPath(); // /api/v1/auth
        apiUrl = apiUrl.replace(url,"");
        return apiUrl;
    }

    //made it a static method
    public static User getLoggedInUser(){
        try {
            CustomUserDetails logUser = (CustomUserDetails) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal();
            return logUser.getUser();
        } catch (Exception e) {
            throw e;
        }

    }
}
