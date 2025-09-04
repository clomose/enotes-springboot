package com.example.Enotes.handler;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GenericResponse {

    private HttpStatus responseStatus;
    private String status;  //success or failed
    private String message;  // saved message
    private Object data; //data

    public ResponseEntity<?> create(){
        Map<String,Object> map = new HashMap<>();
        map.put("status",status);
        map.put("message",message);
        if(!ObjectUtils.isEmpty(data)){
            map.put("data",data);
        }
        return new ResponseEntity<>(map,responseStatus);
    }
}
